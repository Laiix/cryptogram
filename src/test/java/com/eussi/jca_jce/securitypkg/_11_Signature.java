package com.eussi.jca_jce.securitypkg;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.*;
import java.util.Arrays;

/**
 * Created by wangxueming on 2019/4/1.
 */
public class _11_Signature {//主要生成和验证数字签名

    public static void main(String[] args) {
        try {
            //待做数字签名的原始信息
            byte[] data ="Data signature".getBytes();

            //实例化 KeypairGenerator对象,并指定DSA算法
            KeyPairGenerator keyPairGenerator = KeyPairGenerator. getInstance("DSA");
            //初始化 KeypairGenerator对象
            keyPairGenerator.initialize(1024);

            //生成 Keypair对象
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            PrivateKey privateKey = keyPair.getPrivate();
            PublicKey publicKey = keyPair.getPublic();

            /**
             * 三个步骤：
             *  1、初始化
             *  2、更新要签名或者验证的字节
             *  3、签名或者验证
             */

            //签名流程
            //实例化 Signature.对象
            Signature signature = Signature.getInstance(keyPairGenerator.getAlgorithm());
            //使用私钥初始化用于签名操作的 signature对象，准备签名
            signature.initSign(privateKey);

            //更新
            signature.update(data);

            //获得签名,即字节数组sign
            byte[] sign = signature.sign();
            System.out.println("签名信息：" + Arrays.toString(sign));

            //验证流程
            //初始化用于验证操作的 signature对象
            signature.initVerify(publicKey);

            //更新
            signature.update(data);

            //获得验证结果
            boolean status = signature.verify(sign);
            System.out.println("验证结果：" + status);

        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}
