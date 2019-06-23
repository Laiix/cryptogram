package com.eussi._01_jca_jce.specpkg;

import org.junit.Test;

import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

/**
 * Created by wangxueming on 2019/4/1.
 */
public class _02_EncodedKeySpec {//用编码格式来表示公钥和私钥，称之为编码密钥规范，编码密钥规范实现子类很多，这里只展示最常用的两个
    @Test
    public void test() {
        //实例化
        try {
            //实例化KeyPairGenerator
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DSA");
            //初始化对象
            keyPairGenerator.initialize(1024);
            //生成KeyPair
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();

            System.out.println("public:" + Arrays.toString(publicKey.getEncoded()));
            System.out.println("private:" + Arrays.toString(privateKey.getEncoded()));

            byte[] publicKeyBytes = publicKey.getEncoded();

            //将公钥字节再转换为公钥对象
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKeyBytes);
            //实例化keyFactory
            KeyFactory keyFactory = KeyFactory.getInstance("DSA");
            //获取公钥对象
            PublicKey publicKey1 = keyFactory.generatePublic(x509EncodedKeySpec);
            System.out.println("new public:" + Arrays.toString(publicKey1.getEncoded()));

            byte[] privateKeyBytes = privateKey.getEncoded();
            //实例化PKCS8EncodedKeySpec
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            //获取私钥对象
            PrivateKey privateKey1 = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            System.out.println("new private:" + Arrays.toString(privateKey1.getEncoded()));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
