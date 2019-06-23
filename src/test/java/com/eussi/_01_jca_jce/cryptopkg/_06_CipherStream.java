package com.eussi._01_jca_jce.cryptopkg;

import org.junit.Test;

import javax.crypto.*;
import java.io.*;
import java.util.Arrays;

/**
 * Created by wangxueming on 2019/4/1.
 */
public class _06_CipherStream {//密钥流

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

            //实例化Cipher对象
            Cipher cipher = Cipher.getInstance("DES");

            //初始化，用与解密
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            //加密数据
            String input = "test";
            CipherOutputStream cipherOutputStream = new CipherOutputStream(new FileOutputStream("src/test/resources/cipherStreamTest.txt"), cipher);

            DataOutputStream dataOutputStream = new DataOutputStream(cipherOutputStream);

            //写入加密数据
            dataOutputStream.writeUTF(input);
            dataOutputStream.flush();
            dataOutputStream.close();



            //初始化，用与解密
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            CipherInputStream cipherInputStream = new CipherInputStream(new FileInputStream(new File("src/test/resources/cipherStreamTest.txt")), cipher);
            //解密
            DataInputStream dataInputStream = new DataInputStream(cipherInputStream);
            String output = dataInputStream.readUTF();
            dataInputStream.close();

            System.out.println("解密后信息：" + output);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
