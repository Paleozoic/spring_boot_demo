package com.maxplus1.bean.mail;

import lombok.Data;

@Data
public class MailContent  {
    /**
     * 邮件ID
     */
    private Long mailId;
    /**
     * 邮件内容
     */
    private String mailMontent;
    /**
     * 邮件状态 0-已发送 1-未发送
     */
    private Integer mailStatus;
}
