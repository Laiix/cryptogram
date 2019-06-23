package com.eussi._06_sm.sm4;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;
import org.junit.Test;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.SecureRandom;
import java.security.Security;

/**
 * Created by wangxueming on 2019/2/24.
 */
public class _01_BouncyCastleSm4 {

    @Test
    public void test() {
        try {
            Security.addProvider(new BouncyCastleProvider());
            KeyGenerator kg = KeyGenerator.getInstance("SM4");
            kg.init(128, new SecureRandom());
            byte[] key = kg.generateKey().getEncoded();

            KeyGenerator kg1 = KeyGenerator.getInstance("SM4");
            kg.init(128, new SecureRandom());
            byte[] iv = kg.generateKey().getEncoded();

            //密钥还原
            Key sm4Key = new SecretKeySpec(key, "SM4");

            byte[] input = "test".getBytes();
            //加密
            Cipher cipher = Cipher.getInstance("SM4/ECB/PKCS5Padding");//还支持CBC
            cipher.init(Cipher.ENCRYPT_MODE, sm4Key);
            byte[] output = cipher.doFinal(input);
            System.out.println("test加密SM4：" + Base64.toBase64String(output));
            //解密
            Cipher cipher2 = Cipher.getInstance("SM4/ECB/PKCS5Padding");//还支持CBC
            cipher2.init(Cipher.DECRYPT_MODE, sm4Key);
            byte[] origin = cipher2.doFinal(output);
            System.out.println("test解密SM4：" + new String(origin));

            //加密
            Cipher cipher3 = Cipher.getInstance("SM4/CBC/PKCS5Padding");
            cipher3.init(Cipher.ENCRYPT_MODE, sm4Key, new IvParameterSpec(iv));
            output = cipher3.doFinal(input);
            System.out.println("test加密SM4：" + Base64.toBase64String(output));
            //解密
            Cipher cipher4 = Cipher.getInstance("SM4/CBC/PKCS5Padding");
            cipher4.init(Cipher.DECRYPT_MODE, sm4Key);
            origin = cipher4.doFinal(output);
            System.out.println("test解密SM4：" + new String(origin));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
