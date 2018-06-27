package com.maxplus1.bean.mail;

import lombok.Data;

import java.util.List;

@Data
public class Mail  {
    /**
     * 发件人
     */
    private MailUser sendUser;
    /**
     * 收件人
     */
    private List<MailUser> recipientList;
    /**
     * 邮件内容
     */
    private MailContent mailContent;

    /**
     * 邮件关联的业务ID
     */
    private List<MailBiz> mailBizList;

    /**
     * 邮件附件
     */
    private List<MailFile> mailFileList;
}
