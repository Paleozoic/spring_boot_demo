package com.maxplus1;

import com.maxplus1.demo.utils.EncryptorUtils;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
//@ImportResource("classpath:spring/beans.xml")
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}


	@Bean("jasyptStringEncryptor")
	public StringEncryptor stringEncryptor() {
        //实现自己的加密解析器
        return new StringEncryptor() {
            @Override
            public String encrypt(String s) {
                return EncryptorUtils.encrypt(s.getBytes());
            }

            @Override
            public String decrypt(String s) {
                return new String(EncryptorUtils.decrypt(s));
            }
        };
	}

}
