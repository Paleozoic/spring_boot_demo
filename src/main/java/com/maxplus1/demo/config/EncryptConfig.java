package com.maxplus1.demo.config;

import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EncryptConfig {



//    @Bean("basicTextEncryptor")
//    public BasicTextEncryptor basicTextEncryptor() {
//        return new BasicTextEncryptor();
//    }


    public static void main(String[] args) throws Exception{
        BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
        String dbEncryptKey="7YOqgfqbpne6gJfBT7PSJ0zEXuN1vxE2o8GSQGe0o5E6OVL3wsPd59VcOY1PKML9HGsbMTboC3zNN35cxM1s5jLLQKfBqU7g8FR3jVroI1kCg5Oa7U5luweP6PgUqt6S";
        basicTextEncryptor.setPassword(dbEncryptKey);
        String paleo = basicTextEncryptor.encrypt("paleo");
        System.out.println(paleo);

    }

//    @Bean("jasyptStringEncryptor")
//    public StringEncryptor stringEncryptor() {
//        //实现自己的加密解析器
//        return new StringEncryptor() {
//            @Override
//            public String encrypt(String s) {
//                byte[] encrypt = EncryptorUtils.encrypt(s, dbEncryptKey);
//                String encryptStr = Base64.getEncoder().encodeToString(encrypt);
//                return encryptStr;
//            }
//
//            @Override
//            public String decrypt(String s) {
//                return new String(EncryptorUtils.decrypt(Base64.getDecoder().decode(s), dbEncryptKey));
//            }
//        };
//    }
}
