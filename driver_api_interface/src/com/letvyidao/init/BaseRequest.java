
package com.letvyidao.init;


import com.letvyidao.utils.InterfaceUtils;
import com.letvyidao.utils.ReadFileUtils;

import net.sf.json.JSONObject;

public class BaseRequest {
protected Constant constant = null;
protected ReadFileUtils readFileUtils = null;
protected JSONObject jsonObj = null;
protected InterfaceUtils interfaceUtils;


protected BaseRequest(){
	readFileUtils = new ReadFileUtils();
	constant = new Constant();	
	interfaceUtils = new InterfaceUtils();
//	TODO
}
	
}

