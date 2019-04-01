package com.eussi.jca_jce.cryptopkg;

import org.junit.Test;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.util.Arrays;

/**
 * Created by wangxueming on 2019/4/1.
 */
public class _05_Cipher {//未加密解密提供密码功能，他是JCE框架的核心

    @Test
    public void test() {//密码的包装和解包操作
        //实例化
        try {
            //实例化KeyPairGenerator
            KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");

            //生成SecretKey
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] key = secretKey.getEncoded();
            System.out.println("secretyKey：" + Arrays.toString(secretKey.getEncoded()));

            //实例化Cipher对象
            Cipher cipher = Cipher.getInstance("DES");

            //初始化Cipher对象，用于包装
            cipher.init(Cipher.WRAP_MODE, secretKey);

            //包装秘密密钥
            byte[] k = cipher.wrap(secretKey);

            //得到字节数组后，可以将其传给需要解包的一方//初始化Cipher对象，用于包装
            cipher.init(Cipher.UNWRAP_MODE, secretKey);
            //解包秘密密钥
            SecretKey secretKey1 = (SecretKey) cipher.unwrap(k, "DES", Cipher.SECRET_KEY);
            System.out.println("secretyKey1：" + Arrays.toString(secretKey1.getEncoded()));

            //初始化，用于加密
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            //加密
            byte[] input = cipher.doFinal("test".getBytes());
            System.out.println("test加密后信息：" + Arrays.toString(input));

            //初始化，用与解密
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            //解密
            byte[] output = cipher.doFinal(input);
            System.out.println("解密后信息：" + new String(output));



        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
