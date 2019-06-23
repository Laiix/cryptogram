package com.eussi._01_jca_jce.sslpkg;

import org.junit.Test;

import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.security.KeyStore;

/**
 * Created by wangxueming on 2019/4/1.
 */
public class _02_TrustManagerFactory {//管理信任材料管理工厂

    @Test
    public void test() {

        try {
            //实例化
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("SunX509");
            //加载秘钥库文件
            FileInputStream fileInputStream = new FileInputStream("");
            //实例化KeyStore
            KeyStore keyStore = KeyStore.getInstance("JKS");
            //加载秘钥库
            keyStore.load(fileInputStream, "passwd".toCharArray());
            //关闭输入流
            fileInputStream.close();
            //初始化
            trustManagerFactory.init(keyStore);

            /**
             * 除了通过 KeyManagerFactory和 TrustManagerFactory两个工厂类来设定密钥库和信任库外,
             * 还可以通过 System setProperty( String key, Object value)进行密钥库、信任库文件路径及密码的
             * 设定,具体操作如下
             */

            System.setProperty("javax.net.ssl.keystore", "src/test/resources/?.keystore");
            System.setProperty("javax.net.ssl.keyStorePassword", "123456");
            //信任库
            System.setProperty("javax.net.ssl.trustStore", "src/test/resources/?.keystore");
            System.setProperty("javax.net.ssl.trustStorePassword", "123456");

            /**
             *  注意如果系统中有多个密钥库、信任库的配置,建议使用 Key Factory和
             * TrustManager Factory两个工厂类进行对应设定,以避免配置被覆盖
             */

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
