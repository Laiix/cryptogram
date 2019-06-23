package com.eussi._01_jca_jce.cryptopkg;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 *
 * Created by wangxueming on 2019/4/1.
 */
public class _01_Mac {//安全消息摘要

    public static void main(String[] args) {
        try {
            //待安全摘要信息
            byte[] input = "MAC".getBytes();

            //初始化KeyGenerator
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacMD5");
            //产生密钥
            SecretKey secretKey = keyGenerator.generateKey();
            //获得密钥
            byte[] key = secretKey.getEncoded();
            System.out.println("生成秘钥HmacMD5：" + Arrays.toString(key));

            //获取mac
            Mac mac = Mac.getInstance(secretKey.getAlgorithm());
            //初始化mac
            mac.init(secretKey);

            //执行消息摘要
            byte[] output = mac.doFinal(input);
            System.out.println("test摘要HmacMD5：" + Arrays.toString(output));

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
    }
}
