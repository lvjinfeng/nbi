package com.nbi.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.ModelAndView;


public class BaseController {

	/** 
     * 把浏览器参数转化放到Map集合中 
     * @param request 
     * @return 
     * @throws UnsupportedEncodingException 
     */  
    protected Map<String, Object> getParam(HttpServletRequest request) {  
        Map<String, Object> paramMap = new HashMap<String, Object>();  
        String method = request.getMethod();  
        Enumeration<?> keys = request.getParameterNames();  
        while (keys.hasMoreElements()) {  
            Object key = keys.nextElement();  
            if(key!=null){  
                if (key instanceof String) {  
                    String value = request.getParameter(key.toString());  
                    if("GET".equals(method)){//前台encodeURIComponent('我们');转码后到后台还是ISO-8859-1，所以还需要转码  
                         try {  
                            value =new String(value.getBytes("ISO-8859-1"),"UTF-8");  
                        } catch (UnsupportedEncodingException e) {  
                            e.printStackTrace();  
                        }      
                    }  
                    paramMap.put(key.toString(), value);  
                }  
            }   
        }  
        return paramMap;  
    }  
    //将数据刷新写回web端  
    protected void flushResponse(HttpServletResponse response, String responseContent) {  
        PrintWriter writer = null;  
        try {  
            response.setCharacterEncoding("GBK");  
            // 针对ajax中页面编码为GBK的情况，一定要加上以下两句  
            response.setHeader("Cache-Control", "no-cache");  
            response.setContentType("text/html;charset=UTF-8");  
            writer = response.getWriter();  
            if (responseContent==null || "".equals(responseContent) || "null".equals(responseContent)) {  
                writer.write("");  
            } else {  
                writer.write(responseContent);  
            }  
        } catch (IOException e) {  
            throw new RuntimeException(e);  
        } finally {  
            if (writer != null) {  
                writer.flush();  
                writer.close();  
            }  
        }  
    } 
    protected HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }
    protected HttpServletResponse getResponse() {
        return ((ServletWebRequest)RequestContextHolder.getRequestAttributes()).getResponse();
    }
	protected void setSessionMsg(String key ,String msg){
		if(!StringUtils.isEmpty(msg)){
			getRequest().getSession().setAttribute(key, msg);
		}
	}
	protected Object getSessionMsg(String key){
		return getRequest().getSession().getAttribute(key);
	}
	protected void removeSessionMsg(String key){
		getRequest().getSession().removeAttribute(key);
	}
	
	public ModelAndView getPageNumberInfo(int total,int startIndex,int pageSize ,ModelAndView result) {  
        //Math.ceil整数则为该整数，Math.ceil小数则为靠近大的整数  
        int current =  (int) Math.ceil((startIndex + 1.0) / pageSize);  
        result.addObject("start", startIndex);  
        result.addObject("limit", pageSize);  
        result.addObject("total", total);  
        result.addObject("current", current);  
        if(total > 0) {  
            int page =  (int) Math.ceil(total/ pageSize);  
            double totald= total;  
            if(totald/pageSize>total/pageSize){  
                page =page +1;  
            }  
            result.addObject("page", page);  
            int startPage = 0;  
            int endPage = 0;  
            if (page<8) {  
                startPage = 1;  
                endPage = page;  
            } else {  
                if (current<5) { //展示前6页  
                    startPage = 1;  
                    endPage = 6;//page>6?6:page;   
                } else if (page-current<6) {//展示最后6页，例如：current=10 page=15   10-15  
                    startPage = page-5;  
                    endPage = page;  
                } else {//current=20    18——22  
                    startPage = current - 2;  
                    endPage = current + 2;  
                }  
            }  
            result.addObject("startPage", startPage);  
            result.addObject("endPage", endPage);  
        } else {  
            result.addObject("page", 0);  
        }  
        return result;  
    }  
	
	/********end set msg********/
}
