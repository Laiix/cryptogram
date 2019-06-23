package com.eussi._01_jca_jce.securitypkg;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created by wangxueming on 2019/2/20.
 */
public class _04_MessageDigestInputStreamTest {
    @Test
    public void test() {
        DigestInputStream dis = null;//注意流包含一个摘要开关，关闭后和普通流相同 on(boolean on)
        try {
            //原始信息
            byte[] input = "test".getBytes();
            //初始化摘要对象
            MessageDigest md = MessageDigest.getInstance("md5");
            //构建DigestInputStream对象
            dis = new DigestInputStream(new ByteArrayInputStream(input), md);
            dis.read(input, 0, input.length);
            //获取摘要结果
            byte[] output = dis.getMessageDigest().digest();
            System.out.println("test摘要：" + Arrays.toString(output));

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(dis!=null) {
                    dis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
