package com.maxplus1.demo.config.shiro.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.RequestContextUtils;

public class WebResource {

    /**
     * ThreadLocal封装，每个线程使用其一个备份，不会造成线程安全问题
     */
    protected final static ThreadLocal<HttpServletRequest> REQUEST_THREAD = new ThreadLocal<HttpServletRequest>();

    protected final static ThreadLocal<HttpServletResponse> RESPONSE_THREAD = new ThreadLocal<HttpServletResponse>();

    /**
     * 获得 request
     * @return
     */
    public static HttpServletRequest request() {
        HttpServletRequest request = REQUEST_THREAD.get();
        if (request == null) {
            throw new IllegalAccessError("无效的调用");
        }
        return request;
    }
    /**
     * 获得 response
     * @return
     */
    public static HttpServletResponse response() {
        HttpServletResponse response = RESPONSE_THREAD.get();
        if (response == null) {
            throw new IllegalAccessError("无效的调用");
        }
        return response;
    }

    public static Session session() {
        return SecurityUtils.getSubject().getSession();
    }

    //HttpSession和Shiro的session不是同一个session，过期时间也不一样。需要配置
//	/**
//	 * 获得session
//	 * @return
//	 */
//	public static HttpSession session() {
//		HttpServletRequest request = request();
//		if (request != null) {
//			return request.getSession();
//		} else {
//			return null;
//		}
//	}

    /**
     * 获得Web上下文WebApplicationContext getWebApplicationContext is deprecated as of Spring 4.2.1
     * @return
     */
    public static WebApplicationContext webcontext() {
        HttpServletRequest request = request();
        WebApplicationContext cxt = RequestContextUtils
                .findWebApplicationContext(request);
        return cxt;
    }

}