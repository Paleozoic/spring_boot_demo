package com.maxplus1;

import com.maxplus1.demo.utils.EncryptorUtils;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:spring/beans.xml")
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

    private final static String KEY = "you need to set it in the yml/props";

	@Bean("jasyptStringEncryptor")
	public StringEncryptor stringEncryptor() {
        //实现自己的加密解析器
        return new StringEncryptor() {
            @Override
            public String encrypt(String s) {
                return EncryptorUtils.encrypt(s,KEY);
            }

            @Override
            public String decrypt(String s) {
                return EncryptorUtils.decrypt(s,KEY);
            }
        };
	}
}
