package com.eussi.messagedigest.sha;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created by wangxueming on 2019/2/22.
 */
public class _03_CommonsCodecSha {

    @Test
    public void test() {
        try {
            //原始信息
            byte[] input = "test".getBytes();
            //SHA
            byte[] output = DigestUtils.sha1(input);
            System.out.println("test摘要SHA：" + Arrays.toString(output));
            System.out.println("test摘要SHA：" + DigestUtils.sha1Hex("test"));
            //SHA256
            output = DigestUtils.sha256(input);
            System.out.println("test摘要SHA256：" + Arrays.toString(output));
            System.out.println("test摘要SHA256：" + DigestUtils.sha256Hex("test"));
            //SHA384
            output = DigestUtils.sha384(input);
            System.out.println("test摘要SHA384：" + Arrays.toString(output));
            System.out.println("test摘要SHA384：" + DigestUtils.sha384Hex("test"));
            //SHA512
            output = DigestUtils.sha512(input);
            System.out.println("test摘要SHA512：" + Arrays.toString(output));
            System.out.println("test摘要SHA512：" + DigestUtils.sha512Hex("test"));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
