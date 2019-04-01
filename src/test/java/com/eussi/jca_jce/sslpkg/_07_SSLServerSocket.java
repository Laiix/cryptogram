package com.eussi.jca_jce.sslpkg;

import org.junit.Test;

import javax.net.ssl.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.cert.Certificate;

/**
 * Created by wangxueming on 2019/4/1.
 */
public class _07_SSLServerSocket {

    @Test
    public void test() {
        String keyStorePath = "";
        String trustKeyStorePath = "";
        String passwd = "";

        try {
            //实例化密钥库
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
            //初始化密钥库
            KeyStore keyStore = getKeyStore(keyStorePath, passwd);
            //初始化
            keyManagerFactory.init(keyStore, passwd.toCharArray());

            //初始化信任库
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("SunX509");
            //初始化信任库
            KeyStore trustKeyStore = getKeyStore(trustKeyStorePath, passwd);
            //初始化
            trustManagerFactory.init(trustKeyStore);

            //初始化ssl上下文
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(),null);

            //获得SSLSocketFactory实例
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            //获得SSLServerSocketFactory实例
            SSLServerSocketFactory sslServerSocketFactory = sslContext.getServerSocketFactory();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static KeyStore getKeyStore(String keyStorePath, String passwd) {
        try {
            //加载秘钥库文件
            FileInputStream fileInputStream = null;
            fileInputStream = new FileInputStream(keyStorePath);
            //实例化KeyStore
            KeyStore keyStore = KeyStore.getInstance("JKS");
            //加载秘钥库
            keyStore.load(fileInputStream, passwd.toCharArray());
            //关闭输入流
            fileInputStream.close();
            return keyStore;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
