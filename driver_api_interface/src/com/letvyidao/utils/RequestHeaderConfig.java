package com.letvyidao.utils;

import java.util.HashMap;
import java.util.Map;

public class RequestHeaderConfig {

    private String oauth_consumer_key;
    private String oauth_signature_method;
    private String oauth_timestamp;
    private String x_auth_mode;
    public String oauth_token;
    private String oauth_nonce;
    private String oauth_signature;
    private String oauth_version;
    public String user_Agent;
    public String oauth_token_secret;
    public HashMap<String, String> headerMap;

    public RequestHeaderConfig() {
        this.setOauth_consumer_key();
        this.setOauth_signature_method();
        this.setOauth_timestamp(String.valueOf(System.currentTimeMillis() / 1000));
        this.setOauth_nonce();
        this.setOauth_signature();
        this.setOauth_version();
        this.setUser_Agent();
        this.setX_auth_mode();
        headerMap = new HashMap<String, String>();
    }

    public RequestHeaderConfig(String oauth_token) {
        this();
        this.setOauth_token(oauth_token);
    }

    public RequestHeaderConfig(String oauth_token, String oauth_secret) {
        this(oauth_token);
        this.setOauth_token_secret(oauth_secret);
    }

    public HashMap<String, String> getHeaderMapWithOutToken() {
        headerMap.put("oauth_consumer_key", this.oauth_consumer_key);
        headerMap.put("oauth_signature_method", this.oauth_signature_method);
        headerMap.put("oauth_timestamp", this.oauth_timestamp);
        headerMap.put("oauth_nonce", this.oauth_nonce);
        headerMap.put("oauth_signature", this.oauth_signature);
        headerMap.put("oauth_version", this.oauth_version);
        return headerMap;
    }

    public Map<String, String> getHeaderMapWithToken() {
        this.getHeaderMapWithOutToken();
        headerMap.put("oauth_token", this.oauth_token);
        headerMap.put("oauth_token_secret", this.oauth_token_secret);
        return headerMap;
    }

    public String getHeaderString() {
        String authString = "";
        if (this.oauth_token == null) {
            authString = this.getHeaderMapWithOutToken().toString();
        } else {
            authString = this.getHeaderMapWithToken().toString();
            int l = authString.length();
            authString = authString.substring(1, l - 1);
        }
        return "Authorization: OAuth " + authString;
    }


    public String get_userAgent_and() {
        String userAgent = "aCarMaster/6.0.1/205//353222063475100(samsung-SM-G900F; Android-6.0.1)";
        return userAgent;

    }

    public String get_userAgent_ios() {
        String userAgent = "aCarMaster/6.0.1/205//353222063475100(iphone-6s;ios-9.3.0 )";
        return userAgent;
        //TODO

    }


    public String getOauth_timestamp() {
        return oauth_timestamp;
    }

    public void setOauth_timestamp(String oauth_timestamp) {
        this.oauth_timestamp = oauth_timestamp;
    }

    public String getOauth_consumer_key() {
        return oauth_consumer_key;
    }

    void setOauth_consumer_key() {
        this.oauth_consumer_key = "2afdd89f5c6dbdc34542ab04933a091004eba18e2";
    }

    public String getOauth_signature_method() {
        return oauth_signature_method;
    }

    void setOauth_signature_method() {
        this.oauth_signature_method = "PLAINTEXT";
    }

    public String getOauth_nonce() {
        return oauth_nonce;
    }

    void setOauth_nonce() {
        this.oauth_nonce = String.valueOf(Long.parseLong(this.oauth_timestamp) + 1000);
    }

    public String getOauth_signature() {
        return oauth_signature;
    }

    void setOauth_signature() {
        this.oauth_signature = "5sARLGoVkNAPhh5wq1Hl95crWIk";
    }

    public String getOauth_version() {
        return oauth_version;
    }

    public void setOauth_version() {
        this.oauth_version = "1.0";
    }

    public String getUser_Agent() {
        return user_Agent;
    }

    public void setUser_Agent() {
        this.user_Agent = "HUAWEI G520-0000";
    }

    public void setX_auth_mode() {
        this.x_auth_mode = "client_auth";
    }

    public String getX_auth_mod() {
        return x_auth_mode;
    }

    public String getOauth_token() {
        return oauth_token;
    }

    public void setOauth_token(String oauth_token) {
        this.oauth_token = oauth_token;
    }

    public void setOauth_token_secret(String oauth_token_secret) {
        this.oauth_token_secret = oauth_token_secret;
    }

    public void refreshTimeStamp() {

    }
}