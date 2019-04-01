package com.eussi.jca_jce.certpkg;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.Signature;
import java.security.cert.CRL;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

/**
 * demo 待完善
 * Created by wangxueming on 2019/4/1.
 */
public class _04_CRL { //证书撤销列表

    public static void main(String[] args) {
        try {

            //实例化，并指明证书类型为X.509
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            //获得证书输入流
            FileInputStream fileInputStream = new FileInputStream("");
            //获得证书
            CRL crl = certificateFactory.generateCRL(fileInputStream);
            //关闭流
            fileInputStream.close();
            //输出
            System.out.println(crl.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
