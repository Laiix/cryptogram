package com.eussi._01_jca_jce.cryptopkg;

import org.junit.Test;

import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import java.security.*;
import java.util.Arrays;

/**
 * Created by wangxueming on 2019/4/1.
 */
public class _03_KeyAgreement {//提供密钥协定协议的功能，可参考DH算法

    @Test
    public void test() {
        //实例化
        try {
            //实例化KeyPairGenerator
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DH");

            //生成KeyPair1
            KeyPair keyPair1 = keyPairGenerator.generateKeyPair();
            //生成KeyPair2
            KeyPair keyPair2 = keyPairGenerator.generateKeyPair();

            //实例化KeyAgreement
            KeyAgreement keyAgreement = KeyAgreement.getInstance(keyPairGenerator.getAlgorithm());
            //初始化keyAgreeMent对象
            keyAgreement.init(keyPair1.getPrivate());

            //执行计划
            keyAgreement.doPhase(keyPair2.getPublic(), true);

            //生成secretyKey
            SecretKey secretKey = keyAgreement.generateSecret("DES");
            System.out.println("secretyKey：" + Arrays.toString(secretKey.getEncoded()));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
