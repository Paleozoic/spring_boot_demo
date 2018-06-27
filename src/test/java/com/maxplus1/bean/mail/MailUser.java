package com.maxplus1.bean.mail;

import lombok.Data;

@Data
public class MailUser  {
    /**
     * 联系人ID或者发送人ID
     */
    private String userId;
    /**
     * 邮件地址
     */
    private String mailAddress;
}
