package com.eussi.messagedigest.crc;

import org.junit.Test;

import java.util.zip.CRC32;

/**
 * Created by wangxueming on 2019/2/23.
 */
public class _01_JavaApiCrc {
    @Test
    public void test() {
        String test = "test";
        CRC32 crc32 = new CRC32();
        crc32.update(test.getBytes());
        String hex = Long.toHexString(crc32.getValue());
        System.out.println("test crc32：" + hex);

    }
}
