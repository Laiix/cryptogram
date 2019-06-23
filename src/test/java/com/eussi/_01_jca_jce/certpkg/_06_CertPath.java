package com.eussi._01_jca_jce.certpkg;

import java.io.FileInputStream;
import java.security.cert.*;

/**
 * demo 待完善
 * Created by wangxueming on 2019/4/1.
 */
public class _06_CertPath { //证书链

    public static void main(String[] args) {
        try {
            //构建certificateFactory对象，并指定证书类型为X.509
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            //输入流
            FileInputStream fileInputStream = new FileInputStream("");
            //待解决问题
            CertPath certPath = certificateFactory.generateCertPath(fileInputStream);

            fileInputStream.close();

        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}
