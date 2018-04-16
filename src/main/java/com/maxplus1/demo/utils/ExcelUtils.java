package com.maxplus1.demo.utils;

import lombok.Data;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExcelUtils {

    public static void main(String[] args) throws IOException {

        @Data
        class Employee {
            private String name;
            private Date birthDate;
            private int payment;
            private int bonus;
            public Employee(String name, Date birthDate, int payment, int bonus) {
                this.name = name;
                this.birthDate = birthDate;
                this.payment = payment;
                this.bonus = bonus;
            }
        }

        List<Employee> employees = new ArrayList<>();
        // init data
        for (int i = 0; i < 3; i++) {
            Employee employee = new Employee("test"+i,new Date(),2*i,05*i);
            employees.add(employee);
        }

        try(InputStream is = ExcelUtils.class.getResourceAsStream("files/excel_templ/object_collection_template.xls")) {
            /**
             * 这里是输出到文件。输出到文件可以存档，输出到浏览器则少了一步文件处理，省事，但没存档。
             * 可以直接输出到浏览器：
             * HttpServletResponse res = ……
             * OutputStream os = res.getOutputStream();
             * DateTime now = DateTime.now(TimeZone.getDefault());
             * // 设置文件名
             * res.setHeader("content-disposition", "attachment;filename="
                  + URLEncoder.encode("客户列表-"+now.format("YYYYMMDD")+".xls", "UTF-8"));
             * // ……
             * JxlsHelper.getInstance().processTemplate(is, os, context);
             */
            try (OutputStream os = new FileOutputStream("/opt/files/excel_output/object_collection_output.xls")) {
                Context context = new Context();
                context.putVar("employees", employees);
                JxlsHelper.getInstance().processTemplate(is, os, context);
            }
        }
    }
}
