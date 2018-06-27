package com.maxplus1.demo.utils;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class EncryptorUtils {

    /**
     * 防止byte[] String互转发生变化
     */
    public final static String DEFAULT_CHARCODE = "ISO-8859-1";
    /**
     * 加密
     *
     * @param content  需要加密的内容
     * @param password 加密密码
     * @return
     */
    public static byte[] encrypt(String content, String password) {
        KeyGenerator kgen = null;
        try {
            kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, new SecureRandom(password.getBytes()));
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
            byte[] byteContent = content.getBytes("utf-8");
            byte[] result = cipher.doFinal(byteContent);
            return result;//加密
        } catch (NoSuchAlgorithmException | InvalidKeyException
                | NoSuchPaddingException | BadPaddingException
                | UnsupportedEncodingException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密
     *
     * @param content  待解密内容
     * @param password 解密密钥
     * @return
     */
    public static byte[] decrypt(byte[] content, String password) {
        KeyGenerator kgen = null;
        try {
            kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, new SecureRandom(password.getBytes()));
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(content);
            return result; // 解密
        } catch (NoSuchAlgorithmException | BadPaddingException
                | IllegalBlockSizeException | NoSuchPaddingException
                | InvalidKeyException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String decryptBase64(String content, String password){
        //Base64解码
        byte[] decode = Base64.getDecoder().decode(content);
        return new String(decrypt(decode,password));

    }


    public static String encryptBase64(String content, String password){
        byte[] encrypt = encrypt(content, password);
        // Base64编码
        String encryptStr = Base64.getEncoder().encodeToString(encrypt);
        return encryptStr;

    }

    public static void main(String[] args) throws Exception {
        String[] vals = new String[]{"test1db","test1dbPass","test2db","test2dbPass"};
        for (String source : vals) {
            System.out.println("原文: " + source);
            String encryptData = new String(encrypt(source,"passwordpassword"),DEFAULT_CHARCODE);
            System.out.println("加密后: " + encryptData);
            String decryptData = new String(decrypt(encryptData.getBytes(DEFAULT_CHARCODE),"passwordpassword"));
            System.out.println("解密后: " + decryptData);
        }
    }

}
