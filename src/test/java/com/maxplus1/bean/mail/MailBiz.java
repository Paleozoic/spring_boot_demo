package com.maxplus1.bean.mail;

import lombok.Data;

@Data
public class MailBiz {
    /**
     * 邮件关联的业务ID
     */
    private String bizId;
    /**
     * 关联的业务类型 1-我的项目
     */
    private String bizType;

}
