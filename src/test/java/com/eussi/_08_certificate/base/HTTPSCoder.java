package com.eussi._08_certificate.base;

import org.bouncycastle.openssl.PEMDecryptorProvider;
import org.bouncycastle.openssl.PEMEncryptedKeyPair;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.openssl.jcajce.JcePEMDecryptorProviderBuilder;

import javax.net.ssl.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.SecureRandom;

/**
 * HTTPS组件
 * Created by wangxueming on 2019/6/25.
 */
public class HTTPSCoder {
    /**
     * 协议
     * 支持TLS和SSL协议
     */
    public static final String PROTOCOL = "TLS";

    /**
     * 获得KeyStore
     * @param keyStorePath
     * @param password
     * @return
     * @throws Exception
     */
    private static KeyStore getKeyStore(String keyStorePath, String password) throws Exception{
        //实例化密钥库
        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
        //获取密钥文件流
        FileInputStream fis = new FileInputStream(keyStorePath);
        //加载密钥库
        ks.load(fis, password.toCharArray());
        //关闭流
        fis.close();

        return ks;
    }

    /**
     * 获得SSLSocketFactory
     * @param keyStorePath
     * @param keyStorePassword
     * @param trustStorePath
     * @param trustStorePassword
     * @return
     * @throws Exception
     */
    private static SSLSocketFactory getSSLSocketFactory(String keyStorePath,
                                                        String keyStorePassword,
                                                        String trustStorePath,
                                                        String trustStorePassword) throws Exception{
        //实例化密钥库
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(
                KeyManagerFactory.getDefaultAlgorithm());
        //获得密钥库
        KeyStore keyStore = getKeyStore(keyStorePath, keyStorePassword);
        //初始化密钥工厂
        keyManagerFactory.init(keyStore, keyStorePassword.toCharArray());

        //实例化信任库
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                TrustManagerFactory.getDefaultAlgorithm());
        //获得信任库
        KeyStore trustStore = getKeyStore(trustStorePath, trustStorePassword);
        //初始化信任库
        trustManagerFactory.init(trustStore);

        //实例化SSL上下文
        SSLContext ctx = SSLContext.getInstance(PROTOCOL);
        //初始化上下文
        ctx.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(),
                null);

        //获得SSLSocketFactory
        return ctx.getSocketFactory();
    }

    /**
     * 为HttpsURLConnection配置SSLSocketFactory
     * @param httpsURLConnection
     * @param keyStorePath
     * @param keyStorePassword
     * @param trustStorePath
     * @param trustStorePassword
     * @throws Exception
     */
    public static void configSSLSocketFacotry(HttpsURLConnection httpsURLConnection,
                                               String keyStorePath,
                                               String keyStorePassword,
                                               String trustStorePath,
                                               String trustStorePassword) throws Exception {
        //获得SSLSocketFactory
        SSLSocketFactory sslSocketFactory = getSSLSocketFactory(keyStorePath,
                keyStorePassword,
                trustStorePath,
                trustStorePassword);

        //设置SSLSocketFactory
        httpsURLConnection.setSSLSocketFactory(sslSocketFactory);
    }



}
