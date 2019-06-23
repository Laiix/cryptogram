package com.eussi._07_signature.rsa;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.junit.Assert;
import org.junit.Test;

import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * Created by wangxueming on 2019/4/2.
 */
public class _01_JavaApiRsa {

    @Test
    public void test() {
        try {
            //实例化密钥对生成器
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            //初始化密钥对生成器
            keyPairGenerator.initialize(512);
            //生成密钥对
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            //公钥
            RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
            //私钥
            RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();

            System.out.println("公钥：" + Base64.encodeBase64String(rsaPublicKey.getEncoded()));
            System.out.println("私钥：" + Base64.encodeBase64String(rsaPrivateKey.getEncoded()));

            String input = "RSA数字签名";
            byte[] data = input.getBytes();

            //密钥还原这里不在详述，可参考rsa算法实现

            //产生签名
            //实例化signature
            Signature signature = Signature.getInstance("MD5withRSA");
            //初始化signature
            signature.initSign(rsaPrivateKey);
            //更新
            signature.update(data);
            //签名
            byte[] sign = signature.sign();
            System.out.println("签名信息：" + Hex.encodeHexString(sign));

            //验证签名
            //实例化signature
            Signature signature1 = Signature.getInstance("MD5withRSA");
            //初始化signature
            signature1.initVerify(rsaPublicKey);
            //更新
            signature1.update(data);
            //验证
            boolean status = signature1.verify(sign);
            Assert.assertTrue(status);
            System.out.println("验证结果：" + status);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
