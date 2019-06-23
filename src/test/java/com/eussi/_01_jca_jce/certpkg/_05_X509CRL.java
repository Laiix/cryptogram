package com.eussi._01_jca_jce.certpkg;

import java.io.FileInputStream;
import java.security.cert.*;

/**
 * demo 待完善
 * Created by wangxueming on 2019/4/1.
 */
public class _05_X509CRL {

    public static void main(String[] args) {//用于撤销证书
        try {

            //实例化，并指明证书类型为X.509
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            //获得证书输入流
            FileInputStream fileInputStream = new FileInputStream("");
            //获得证书
            X509Certificate x509Certificate = (X509Certificate) certificateFactory.generateCertificate(fileInputStream);
            //获得证书撤销列表
            X509CRL x509CRL = (X509CRL) certificateFactory.generateCRL(fileInputStream);
            //获得证书撤销列表实体
            X509CRLEntry x509CRLEntry =  x509CRL.getRevokedCertificate(x509Certificate);
            //关闭流
            fileInputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
