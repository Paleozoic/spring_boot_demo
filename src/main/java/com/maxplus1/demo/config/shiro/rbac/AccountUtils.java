package com.maxplus1.demo.config.shiro.rbac;


import com.maxplus1.demo.config.shiro.interceptor.WebResource;

public class AccountUtils {

    private final static String USER_ID_SESSION_KEY = "session:com.maxplus1.manager.model.account.UserId";
    private final static String USER_SESSION_KEY = "session:com.maxplus1.manager.model.account.User";

    /**
     * 从会话中取出userId
     * @return
     */
    public static Long getUserId() {
        Object obj = WebResource.session().getAttribute(USER_ID_SESSION_KEY);
        if (obj != null && obj.getClass() == Long.class) {
            return (Long)obj;
        } else {
            return null;
        }
    }

    /**
     * 将userId加入会话
     * @return
     */
    public static void setUserId(Long userId) {
        WebResource.session().setAttribute(USER_ID_SESSION_KEY, userId);
    }

    public static void setUser(User user){
        WebResource.session().setAttribute(USER_SESSION_KEY, user);
    }
    public static User getUser(){
        Object obj = WebResource.session().getAttribute(USER_SESSION_KEY);
        if (obj != null && obj.getClass() == User.class) {
            return (User)obj;
        } else {
            return null;
        }
    }

}