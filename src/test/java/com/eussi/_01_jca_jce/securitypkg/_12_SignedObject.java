package com.eussi._01_jca_jce.securitypkg;

import java.security.*;
import java.util.Arrays;

/**
 * Created by wangxueming on 2019/4/1.
 */
public class _12_SignedObject {//主要用于常见实际运行时对象的类

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

            //实例化 Signature.对象
            Signature signature = Signature.getInstance(keyPairGenerator.getAlgorithm());

            //实例化SignedObject对象
            SignedObject s = new SignedObject(data, privateKey, signature);

            //通过另外一种方式的到签名
            byte[] sign = s.getSignature();
            System.out.println("签名信息：" + Arrays.toString(sign));

            //获得验证结果
            boolean status = s.verify(publicKey, signature);
            System.out.println("验证结果：" + status);

        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}
