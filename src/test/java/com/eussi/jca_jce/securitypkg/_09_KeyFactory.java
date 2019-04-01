package com.eussi.jca_jce.securitypkg;

import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Arrays;

/**
 * Created by wangxueming on 2019/4/1.
 */
public class _09_KeyFactory {//主要用来通过密钥规范还原密钥

    public static void main(String[] args) {
        //实例化
        try {
            //实例化KeyPairGenerator
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            //初始化对象
            keyPairGenerator.initialize(1024);
            //生成KeyPair
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            //获取公私钥
            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();

            //获得私钥密钥字节数组，实际使用中会将此字节数组保存为另一种形式保存
            byte[] keyBytes = privateKey.getEncoded();

            System.out.println("private:" + Arrays.toString(keyBytes));

            //由私钥密钥字节数组获得密钥规范
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
            //实例化密钥工厂，并制定RSA算法
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            //生成私钥
            Key privateKey1 = keyFactory.generatePrivate(pkcs8EncodedKeySpec);

            System.out.println("还原后生成private:" + Arrays.toString(privateKey1.getEncoded()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
