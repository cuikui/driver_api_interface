package com.letvyidao.utils;


import java.net.URI;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;


public class HttpUtils {
    private final CloseableHttpClient httpClient;
    protected HttpGet httpGet = null;
    protected HttpPost httpPost = null;
    protected CloseableHttpResponse response = null;
    public Header[] headers = null;
    protected TrustManager trustManager = null;
    protected SSLConnectionSocketFactory socketFactory = null;
    private PSFClient psf = null;
    public PSFClient.PSFRPCRequestData request = null;


    private static class HttpUtilHolder {
        private static final HttpUtils INSTANCE = new HttpUtils();
    }

    public static HttpUtils getIntance() {
        return HttpUtilHolder.INSTANCE;
    }

    private HttpUtils() {
        trustManager = new X509TrustManager() {

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                // TODO Auto-generated method stub

            }

            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                // TODO Auto-generated method stub

            }
        };
        enableSSL();
        RequestConfig config = RequestConfig.custom().setSocketTimeout(50000).setConnectTimeout(50000).
                setExpectContinueEnabled(true).setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST)).setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC)).build();
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create().register("http", PlainConnectionSocketFactory.INSTANCE).register("https", socketFactory).build();
        PoolingHttpClientConnectionManager conneManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        httpClient = HttpClients.custom().setConnectionManager(conneManager).setDefaultRequestConfig(config).build();
        //测试环境
        String[] serviceCenter = {"10.0.11.71:5201", "10.0.11.72:5201"};
        //线上环境地址
        // String[] serviceCenter = {"172.17.0.77:5201","172.17.0.78:5201"};
        try {
            psf = new PSFClient(serviceCenter);
            request = new PSFClient.PSFRPCRequestData();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void enableSSL() {
        try {
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new TrustManager[]{trustManager}, null);
            socketFactory = new SSLConnectionSocketFactory(context, NoopHostnameVerifier.INSTANCE);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public URI strParseToUri(String url) {
        try {
            URL toURL = new URL(url);
            URI uri = new URI(toURL.getProtocol(), toURL.getHost(), toURL.getPath(), toURL.getQuery(), null);
            return uri;
        } catch (Exception e) {

        }
        return null;
    }


    public String doPSFRequest(String service_type) {
        String psfResponse = "";
        try {
            psfResponse = psf.call(service_type, request);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return psfResponse;
    }

    public Object doSendGet(String url, Map<String, String> headerParams) {
        url = url.replaceAll(" +", "%20").replaceAll("\"+", "%22").replaceAll("\\{", "%7b").replaceAll("\\}", "%7d");
        httpGet = new HttpGet(url);
        for (Map.Entry<String, String> entry : headerParams.entrySet()) {
            httpGet.addHeader(entry.getKey(), entry.getValue());
        }
        return doExecute(httpGet);
    }


    public Object doSendGet(String url) {
        url = url.replaceAll(" +", "%20").replaceAll("\"+", "%22").replaceAll("\\{", "%7b").replaceAll("\\}", "%7d");
        httpGet = new HttpGet(url);
        httpGet.setHeader("Upgrade-Insecure-Requests", "1");
        httpGet.setHeader("Cookie", "B=jkumhj4tyd594xn&1&gd.1b0403095; E_3=a=0&c=&d=eyJuYW1lIjoiXHU3MzhiXHU3MGIzXHU0ZjFmIiwicGhvbmUiOiIxMzI2MDAyODY5MiIsImRlcHRfaWQiOjE1NDY4MTQ2fQ--&e=1476725627&f=3&g=1476716827&i=111.207.160.68&n=d2FuZ2Jpbmd3ZWk-&t=I_N_AB_AV_AW_AZ_BA_BB_BF_BW_BX_CK_&u=2398&v=1&s=MEYCIQDn1wQjrTfYJwqOpQeiCWCcMSzozN3LLdZRN3Xy6l.m_gIhANchf6DgW0anioShC5rq2uwH383m6CrkZx1FYBCjPycH");
        return doExecute(httpGet);
    }


    public Object doSendPostOAuth(String url, Map<String, String> mapParams, Map<String, String> headerParams) {
        List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
        if (mapParams != null) {
            for (Map.Entry<String, String> entry : mapParams.entrySet()) {
                BasicNameValuePair kvPair = new BasicNameValuePair(entry.getKey(), entry.getValue());
                list.add(kvPair);
            }
            //出现问题，需要查看参数时，可以将下面调试代码放开
            System.out.println("postParams:" + getPostParameter(mapParams));
        }

        httpPost = new HttpPost(strParseToUri(url));
        StringBuffer sb = new StringBuffer();
        if (headerParams != null) {
            for (Map.Entry<String, String> entry : headerParams.entrySet()) {
                sb.append(entry.getKey() + "=" + entry.getValue() + ",");
            }
        }
        String header = sb.substring(0, sb.length() - 1);
        System.out.println("OAuth " + header);
        httpPost.addHeader("Authorization", "OAuth " + header);
        //	httpPost.addHeader("Content-Type","application/x-www-form-urlencoded");
        httpPost.setHeader("Accept-Encoding", "gzip,deflate,sdch");
        //	httpPost.setHeader("User-Agent","Driver/1.0.0/1 (ZTE U795; Android 2.3)");
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(list, "utf-8"));
            return doExecute(httpPost);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String doGetParamsStr(Map<String, String> params) {
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (!"".equals(entry.getValue()) && !"null".equals(entry.getValue())) {
                sb.append(entry.getKey() + "=" + entry.getValue() + "&");
            }
        }
        return sb.substring(0, sb.length()-1);
    }

    public Object doSendGetOAuth(String url, Map<String, String> headerParams) {
        url = url.replaceAll(" +", "%20").replaceAll("\"+", "%22").replaceAll("\\{", "%7b").replaceAll("\\}", "%7d");
        //strParseToUri(url)
        httpGet = new HttpGet(url);
        System.out.println(httpGet.getURI());
        StringBuffer sb = new StringBuffer();
        if (headerParams != null) {
            for (Map.Entry<String, String> entry : headerParams.entrySet()) {
                sb.append(entry.getKey() + "=" + entry.getValue() + ",");
            }
        }

        String header = sb.substring(0, sb.length() - 1);
        System.out.println(header);
        httpGet.addHeader("Authorization", "OAuth " + header);
        httpGet.addHeader("Content-Type", "application/x-www-form-urlencoded");
        httpGet.setHeader("Accept-Encoding", "gzip,deflate,sdch");
        httpGet.setHeader("User-Agent", "Driver/1.0.0/1 (ZTE U795; Android 2.3)");
        return doExecute(httpGet);

    }

    public Object doSendPost(String url, String json) {
        httpPost = new HttpPost(url);
        try {
            httpPost.setHeader("RecContentType", "application/json");
            httpPost.setEntity(new StringEntity(json));
            return doExecute(httpPost);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public Object doSendPostXml(String url, Map<String, String> mapParams) {
        StringBuffer sb = new StringBuffer("<xml>\n");
        for (Map.Entry<String, String> entry : mapParams.entrySet()) {
            sb.append("<" + entry.getKey() + ">" + entry.getValue() + "</" + entry.getKey() + ">\n");
        }
        sb.append("</xml>");
        try {
            url = url.replaceAll(" +", "%20").replaceAll("\"+", "%22");
            httpPost = new HttpPost(url);
            httpPost.setHeader("Content-Type", "application/xml");
            httpPost.setEntity(new StringEntity(sb.toString().trim(), "utf-8"));
            return doExecute(httpPost);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public Object doSendPost(String url, Map<String, String> mapParams) {
        List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
        if (mapParams == null) {
            return null;
        }
        System.out.println("postParams:" + getPostParameter(mapParams));
        for (Map.Entry<String, String> entry : mapParams.entrySet()) {
            BasicNameValuePair kvPair = new BasicNameValuePair(entry.getKey(), entry.getValue());
            list.add(kvPair);
        }
        url = url.replaceAll(" +", "%20").replaceAll("\"+", "%22");
        httpPost = new HttpPost(url);
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(list, "utf-8"));
            return doExecute(httpPost);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public Object doSendPost(String url, List<BasicNameValuePair> list) {
        url = url.replaceAll(" +", "%20").replaceAll("\"+", "%22");
        httpPost = new HttpPost(url);
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(list, "utf-8"));
            return doExecute(httpPost);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static  String getPostParameter(Map<String, String> map) {
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            sb.append(entry.getKey() + "=" + entry.getValue() + "&");
        }
        return sb.substring(0,sb.length()-1);
    }


    public Object doSendPost(String url, Map<String, String> mapParams, String cookies) {
        List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
        if (mapParams == null) {
            return null;
        }

        //出现问题，需要查看参数时，可以将下面调试代码放开
        System.out.println("postParams:" + getPostParameter(mapParams));
        for (Map.Entry<String, String> entry : mapParams.entrySet()) {
            BasicNameValuePair kvPair = new BasicNameValuePair(entry.getKey(), entry.getValue());
            list.add(kvPair);
        }
        httpPost = new HttpPost(strParseToUri(url));
        if (cookies != null) {
            httpPost.setHeader("Cookie", cookies);
        }
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(list, "utf-8"));
            return doExecute(httpPost);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


//	private Header[] doExecuteGetHeader(HttpUriRequest request) {
//		try {
//			response = httpClient.execute(request);
//			System.out.println(response.getStatusLine().getStatusCode());
//			Header[] headers = response.getAllHeaders();
//			return headers;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	return null;
//	}


    private Object doExecute(HttpUriRequest request) {
        String rs = null;
        try {
            response = httpClient.execute(request);
            System.out.println(response.getStatusLine().getStatusCode());
            headers = response.getAllHeaders();
            HttpEntity entityStr = response.getEntity();
            rs = EntityUtils.toString(entityStr, "UTF-8").trim();
            EntityUtils.consume(entityStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rs;
    }



}
