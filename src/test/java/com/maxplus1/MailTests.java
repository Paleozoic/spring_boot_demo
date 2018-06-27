package com.maxplus1;

import com.jcabi.email.Envelope;
import com.jcabi.email.Postman;
import com.jcabi.email.Protocol;
import com.jcabi.email.Token;
import com.jcabi.email.enclosure.EnBinary;
import com.jcabi.email.enclosure.EnPlain;
import com.jcabi.email.stamp.StRecipient;
import com.jcabi.email.stamp.StSender;
import com.jcabi.email.stamp.StSubject;
import com.jcabi.email.wire.Smtp;
import com.maxplus1.bean.mail.Mail;
import com.maxplus1.bean.mail.MailFile;
import com.maxplus1.bean.mail.MailPass;
import com.maxplus1.bean.mail.MailUser;
import com.maxplus1.demo.utils.EncryptorUtils;
import com.maxplus1.demo.utils.MimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.internet.MimeUtility;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class MailTests {

	@Test
	public void contextLoads() {
	}
	@Test
	public void sendMail(Mail mail) {
		MailUser sendUser = mail.getSendUser();
		long userId = Long.parseLong(sendUser.getUserId());
		MailPass mailPass = new MailPass();//根据userId获取用户邮箱密码
		Postman postman = null;
		try {
			postman = new Postman.Default(
					new Smtp(
							new Token(mailPass.getMailAddress(), new String(
									EncryptorUtils.decryptBase64(mailPass.getMailPass(), "ENCRYPT_KEY"))
							).access(
									/**
									 * maiIP
									 * maiPort
									 */
									new Protocol.Smtp("127.0.0.1", 21)
							)
					)
			);
		} catch (Exception e) {
			log.error("[ERROR===>>>]无法连接邮件服务器！", e);
			throw new RuntimeException("[ERROR===>>>]无法连接邮件服务器！", e);
		}
		for (MailUser recipient : mail.getRecipientList()) {

			try {
				/**
				 * http://www.w3school.com.cn/media/media_mimeref.asp
				 * application/pdf
				 * application/msword
				 */
				Envelope.Mime mime = new Envelope.Mime()
						.with(new StSender("发件人姓名", "发件人地址"))
						.with(new StRecipient("收件人姓名", "收件人地址"))
						.with(new StSubject("主题"))
						.with(new EnPlain(mail.getMailContent().getMailMontent()));

				List<MailFile> mailFileList = mail.getMailFileList();

				for (MailFile mailFile : mailFileList) {
					mime = mime.with(new EnBinary(
							mailFile.getFile(),
							MimeUtility.encodeText(mailFile.getFileName()),
							MimeUtils.getMimeType(mailFile.getFileName())
					));
				}
				postman.send(mime);
			} catch (Exception e) {
				// TODO 日志记录重发
				log.error("[ERROR===>>>]邮件发送失败！[发送人：" + recipient.getUserId() + "][收件人：" + "xxxxx" + "]"
						, e);
			}
		}
	}

}
