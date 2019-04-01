package com.eussi.jca_jce.securitypkg;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.DigestOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created by wangxueming on 2019/2/20.
 */
public class _05_MessageDigestOutputStreamTest {
    @Test
    public void test() {
        DigestOutputStream dos = null;//注意流包含一个摘要开关，关闭后和普通流相同 on(boolean on)
        try {
            //原始信息
            byte[] input = "test".getBytes();
            //初始化摘要对象
            MessageDigest md = MessageDigest.getInstance("md5");
            //构建DigestInputStream对象
            dos = new DigestOutputStream(new ByteArrayOutputStream(), md);
            dos.write(input, 0, input.length);
            //获取摘要结果
            byte[] output = dos.getMessageDigest().digest();
            System.out.println("test摘要：" + Arrays.toString(output));

            dos.flush();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(dos!=null) {
                    dos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
