package com.eussi.signature.dsa;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.junit.Assert;
import org.junit.Test;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.DSAPublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * Created by wangxueming on 2019/4/2.
 */
public class _01_JavaApiDsa {

    @Test
    public void test() {
        try {
            //实例化密钥对生成器
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DSA");
            //初始化密钥对生成器
            keyPairGenerator.initialize(1024, new SecureRandom());
            //生成密钥对
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            //公钥
            DSAPublicKey dsaPublicKey = (DSAPublicKey) keyPair.getPublic();
            //私钥
            DSAPrivateKey dsaPrivateKey = (DSAPrivateKey) keyPair.getPrivate();

            System.out.println("公钥：" + Base64.encodeBase64String(dsaPublicKey.getEncoded()));
            System.out.println("私钥：" + Base64.encodeBase64String(dsaPrivateKey.getEncoded()));

            String input = "DSA数字签名";
            byte[] data = input.getBytes();

            //密钥还原这里不在详述，可参考rsa算法实现

            //产生签名
            //实例化signature
            Signature signature = Signature.getInstance("SHA1withDSA");
            //初始化signature
            signature.initSign(dsaPrivateKey);
            //更新
            signature.update(data);
            //签名
            byte[] sign = signature.sign();
            System.out.println("签名信息：" + Hex.encodeHexString(sign));

            //验证签名
            //实例化signature
            Signature signature1 = Signature.getInstance("SHA1withDSA");
            //初始化signature
            signature1.initVerify(dsaPublicKey);
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
