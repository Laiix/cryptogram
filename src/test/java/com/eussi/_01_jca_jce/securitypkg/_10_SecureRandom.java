package com.eussi._01_jca_jce.securitypkg;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.*;
import java.util.Arrays;

/**
 * Created by wangxueming on 2019/4/1.
 */
public class _10_SecureRandom {//主要用来配合生成秘密密钥

    public static void main(String[] args) {
        //实例化
        try {

            SecureRandom secureRandom = new SecureRandom();
            KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");

            keyGenerator.init(secureRandom);

            SecretKey secretKey = keyGenerator.generateKey();
            System.out.println("secretKey:" + Arrays.toString(secretKey.getEncoded()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
