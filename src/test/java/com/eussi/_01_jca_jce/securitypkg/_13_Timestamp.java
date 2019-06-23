package com.eussi._01_jca_jce.securitypkg;

import java.io.FileInputStream;
import java.security.*;
import java.security.cert.CertPath;
import java.security.cert.CertificateFactory;
import java.util.Date;

/**
 * demo待完善，后面看到Https时在完善
 * Created by wangxueming on 2019/4/1.
 */
public class _13_Timestamp {//主要用于常见实际运行时对象的类

    public static void main(String[] args) {
        try {
            //构建certificateFactory对象，并指定证书类型为X.509
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            //生成CertPath对象
            //java.security.cert.CertificateException: Could not parse certificate: java.io.IOException: Empty input
            //待解决问题
            CertPath certPath = certificateFactory.generateCertPath(new FileInputStream("src/test/resources/aliyun.cer"));
            //实例化数字时间戳
            Timestamp t = new Timestamp(new Date(), certPath);
            System.out.println(t.toString());

        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}
