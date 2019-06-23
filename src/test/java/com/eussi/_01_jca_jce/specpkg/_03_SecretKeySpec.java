package com.eussi._01_jca_jce.specpkg;

import org.junit.Test;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.util.Arrays;

/**
 * Created by wangxueming on 2019/4/1.
 */
public class _03_SecretKeySpec {//此类仅对能表示为一个字节数组并且没有与之关联的秘钥参数的原始秘钥有用，如DES和DES Triple的秘钥
    @Test
    public void test() {
        //实例化
        try {
            //实例化KeyGenerator
            KeyGenerator keyGenerator = KeyGenerator.getInstance("RC2");

            //生成SecretKey
            SecretKey secretKey = keyGenerator.generateKey();
            System.out.println("RC2 secretyKey：" + Arrays.toString(secretKey.getEncoded()));

            SecretKey secretKey1 = new SecretKeySpec(secretKey.getEncoded(), "RC2");
            System.out.println("RC2 new secretyKey：" + Arrays.toString(secretKey1.getEncoded()));

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
