package com.eussi._01_jca_jce.cryptopkg;

import org.junit.Test;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.util.Arrays;

/**
 * Created by wangxueming on 2019/4/1.
 */
public class _04_SecretKeyFactory {//与KeyFactory类对应，用于产生秘密密钥

    @Test
    public void test() {
        //实例化
        try {
            //实例化KeyPairGenerator
            KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");

            //生成SecretKey
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] key = secretKey.getEncoded();
            System.out.println("secretyKey：" + Arrays.toString(secretKey.getEncoded()));

            //由获得的秘钥编码字节数组构建DESKeySpec对象
            DESKeySpec desKeySpec = new DESKeySpec(key);
            //实例化SecretKeyFactory对象
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DES");
            //生成SecretKey
            SecretKey secretKey1 = secretKeyFactory.generateSecret(desKeySpec);
            System.out.println("secretyKey1：" + Arrays.toString(secretKey1.getEncoded()));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
