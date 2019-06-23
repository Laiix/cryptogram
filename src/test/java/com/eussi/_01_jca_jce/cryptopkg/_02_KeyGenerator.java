package com.eussi._01_jca_jce.cryptopkg;

import org.junit.Test;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.*;
import java.util.Arrays;

/**
 * Created by wangxueming on 2019/4/1.
 */
public class _02_KeyGenerator {//类似KeyPairGenerator,他用来生成秘密密钥，称之为秘密密钥生成器

    @Test
    public void test() {
        //实例化
        try {
            //实例化KeyPairGenerator
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacMD5");

            //生成SecretKey
            SecretKey secretKey = keyGenerator.generateKey();
            System.out.println("HmacMD5 secretyKey：" + Arrays.toString(secretKey.getEncoded()));

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }
}
