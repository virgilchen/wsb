package com.globalwave.ocr;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.params.CookiePolicy;

/**
 * https://github.com/fabiojose/pwntcha
 * http://www.cnblogs.com/hsapphire/archive/2011/01/07/1929564.html
 * http://blog.csdn.net/beimuaihui/article/details/6702387
 * http://z-redsky.blog.163.com/blog/static/4498616220111114103419963/
 * 
 * 
 * 
 * @author Administrator
 *
 */
public class UserLoginTest {
    public static void main(String[] args){
 
        try {
        	HttpBrowser browser = new HttpBrowser() ;
        	Map<String, String> params = new HashMap<String, String>() ;
        	IOUtils.copy(
        			browser.post("http://211.160.72.166:8080/ydsys/pages/login/image.jsp", params), 
        			new FileOutputStream("C:\\Users\\Administrator\\Downloads\\photo\\" + System.currentTimeMillis() + ".jpg")
        	);

        	params.put("cUserAtr", "E");
        	params.put("userNick", "1120705082014000573YD");
        	params.put("userPassword", "350583198010311334");
        	params.put("userName", "");
        	String validateCode = null ;
        	params.put("validateCode", validateCode);
        	
        	IOUtils.copy(
        	        browser.post("http://211.160.72.166:8080/ydsys/loginAction.do?method=login", params),
        	        System.err
        	);

        	params.clear();
        	/*
        	IOUtils.copy(
        	        browser.post("http://211.160.72.166:8080/ydsys/vehicleAHAction.do?method=query&flag=cb", params),
        	        System.err
        	);
        	*/
        	StringBuffer sb = new StringBuffer();
        	sb.append(IOUtils.toCharArray(browser.post("http://211.160.72.166:8080/ydsys/vehicleAHAction.do?method=query&flag=cb", params)));
        	
        	int index = sb.indexOf("车牌号:");
        	System.err.println("车牌号:index" + index);
        	
        	index = sb.indexOf("<td>", index) ;
        	System.err.println("车牌号:" + sb.subSequence(index + 4, sb.indexOf("</td>", index)));
        	
        } catch (Exception e) {
            e.printStackTrace();
        }   
    }
    
    
    public static class HttpBrowser {

        HttpClient httpClient = new HttpClient();
        
        public InputStream post(String url, Map<String, String> params) throws Exception {

            //模拟登陆，按实际服务器端要求选用 Post 或 Get 请求方式
            PostMethod postMethod = new PostMethod(url);
            //NameValuePair[]
            		
            List<NameValuePair> nameValuesList = new ArrayList<NameValuePair>() ;
            for (String key:params.keySet()) {
            	nameValuesList.add(new NameValuePair(key, params.get(key))) ;
            }
            
            
            //获得登陆后的 Cookie
            Cookie[] cookies=httpClient.getState().getCookies();
            String tmpcookies= "";
            for(Cookie c:cookies){
                tmpcookies += c.toString()+";";
            }
            postMethod.setRequestHeader("cookie",tmpcookies);
            postMethod.setRequestBody(nameValuesList.toArray(new NameValuePair[]{}));

            //你还可以通过 PostMethod/GetMethod 设置更多的请求后数据
            //例如，referer 从哪里来的，UA 像搜索引擎都会表名自己是谁，无良搜索引擎除外
            postMethod.setRequestHeader("Referer", "http://globalwave.com");
            postMethod.setRequestHeader("User-Agent","Unmi Spot");
            
            httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
            httpClient.executeMethod(postMethod);
            
            //打印出返回数据，检验一下是否成功
            //String text = postMethod.getResponseBodyAsString();
            //return text ;
            return postMethod.getResponseBodyAsStream();
        }
    }
}
