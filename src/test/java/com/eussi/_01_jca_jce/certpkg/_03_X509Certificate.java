package com.eussi._01_jca_jce.certpkg;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.Signature;
import java.security.cert.X509Certificate;

/**
 * demo 待完善
 * Created by wangxueming on 2019/4/1.
 */
public class _03_X509Certificate {

    public static void main(String[] args) {
        try {
            //加载秘钥库文件
            FileInputStream fileInputStream = new FileInputStream("");
            //实例化KeyStore
            KeyStore keyStore = KeyStore.getInstance("JKS");
            //加载秘钥库
            keyStore.load(fileInputStream, "passwd".toCharArray());
            //关闭输入流
            fileInputStream.close();
            //获得X509证书
            X509Certificate x509Certificate = (X509Certificate) keyStore.getCertificate("alias");
            //通过证书标明的签名算法构建Signtrue
            Signature signature = Signature.getInstance(x509Certificate.getSigAlgName());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
