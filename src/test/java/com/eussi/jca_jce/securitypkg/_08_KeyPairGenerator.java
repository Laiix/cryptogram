package com.eussi.jca_jce.securitypkg;

import org.junit.Test;

import java.security.*;
import java.util.Arrays;

/**
 * Created by wangxueming on 2019/3/28.
 */
public class _08_KeyPairGenerator {

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


        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }
}
