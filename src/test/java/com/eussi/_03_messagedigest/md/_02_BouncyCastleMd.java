package com.eussi._03_messagedigest.md;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.Arrays;

/**
 * Created by wangxueming on 2019/2/22.
 */
public class _02_BouncyCastleMd {

    @Test
    public void test() {
        try {
            Security.addProvider(new BouncyCastleProvider());
            //原始信息
            byte[] input = "test".getBytes();
            //初始化摘要对象
            MessageDigest md4 = MessageDigest.getInstance("MD4");
            //获取摘要结果
            byte[] output = md4.digest(input);
            System.out.println("test摘要MD4：" + Arrays.toString(output));
            System.out.println("test摘要MD4：" + new String(Hex.encode(output)));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }
}
