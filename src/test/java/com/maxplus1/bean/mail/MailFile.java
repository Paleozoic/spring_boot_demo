package com.maxplus1.bean.mail;

import lombok.Data;

import java.io.File;

@Data
public class MailFile {
    private String fileName;
    private byte[] fileContent;
    private File file;
}
