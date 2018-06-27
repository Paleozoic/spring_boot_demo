package com.maxplus1.bean.mail;

import lombok.Data;

@Data
public class MailPass  {
    /**
     * 发送人ID
     */
    private Long userId;
    /**
     * 发件人邮箱
     */
    private String mailAddress;
    /**
     * 密码
     */
    private String mailPass;
}
