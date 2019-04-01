package com.eussi.jca_jce.cryptopkg;

import org.junit.Test;

import javax.crypto.*;
import java.io.*;
import java.util.Arrays;

/**
 * Created by wangxueming on 2019/4/1.
 */
public class _07_SealedObject {//用加密对象并保护其机密性

    @Test
    public void test() {
        //实例化
        try {
            //加密数据
            String input = "test";

            //实例化KeyPairGenerator
            KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
            //生成SecretKey
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] key = secretKey.getEncoded();
            System.out.println("secretyKey：" + Arrays.toString(secretKey.getEncoded()));

            //实例化Cipher对象
            Cipher cipher = Cipher.getInstance(secretKey.getAlgorithm());
            //初始化，用与解密
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            //构建sealedObject
            SealedObject sealedObject = new SealedObject(input, cipher);

            //初始化，用与解密
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            String output = (String)sealedObject.getObject(cipher);

            System.out.println("解密后信息：" + output);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
