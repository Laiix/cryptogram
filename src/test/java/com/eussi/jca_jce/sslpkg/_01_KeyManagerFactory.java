package com.eussi.jca_jce.sslpkg;

import org.junit.Test;

import javax.net.ssl.KeyManagerFactory;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.Signature;
import java.security.cert.X509Certificate;

/**
 * Created by wangxueming on 2019/4/1.
 */
public class _01_KeyManagerFactory {//管理密钥，密钥管理工厂

    @Test
    public void test() {

        try {
            //实例化
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
            //加载秘钥库文件
            FileInputStream fileInputStream = new FileInputStream("");
            //实例化KeyStore
            KeyStore keyStore = KeyStore.getInstance("JKS");
            //加载秘钥库
            keyStore.load(fileInputStream, "passwd".toCharArray());
            //关闭输入流
            fileInputStream.close();
            //初始化
            keyManagerFactory.init(keyStore, "passwd".toCharArray());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
