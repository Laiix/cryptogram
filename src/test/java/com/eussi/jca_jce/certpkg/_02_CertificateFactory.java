package com.eussi.jca_jce.certpkg;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import java.io.FileInputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Arrays;

/**
 *
 * Created by wangxueming on 2019/4/1.
 */
public class _02_CertificateFactory {//证书工厂，用于将证书导入程序中

    public static void main(String[] args) {
        try {

            //实例化，并指明证书类型为X.509
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            //获得证书输入流
            FileInputStream fileInputStream = new FileInputStream("src/test/resources/aliyun.cer");
            //获得证书
            Certificate certificate = certificateFactory.generateCertificate(fileInputStream);
            //关闭流
            fileInputStream.close();
            //输出
            System.out.println(certificate.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
