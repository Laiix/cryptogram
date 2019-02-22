package com.eussi.messagedigest.sha;

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
public class _02_BouncyCastleSha {

    @Test
    public void test() {
        try {
            Security.addProvider(new BouncyCastleProvider());
            //原始信息
            byte[] input = "test".getBytes();
            //初始化摘要对象
            MessageDigest sha224 = MessageDigest.getInstance("SHA-224");
            //获取摘要结果
            byte[] output = sha224.digest(input);
            System.out.println("test摘要SHA-224：" + Arrays.toString(output));
            System.out.println("test摘要SHA-224：" + new String(Hex.encode(output)));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }
}
