package com.eussi._01_jca_jce.sslpkg;

import org.junit.Test;

import javax.net.ssl.*;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by wangxueming on 2019/4/1.
 */
public class _04_HttpsURLConnection {

    @Test
    public void test() {

        try {
            //构建URL对象
            URL url = new URL("https://www.baidu.com");
            //获得HttpsURLConnection
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
            //打开输入模式
            httpsURLConnection.setDoInput(true);
            //打开输出模式
            httpsURLConnection.setDoOutput(true);
            //设置SSLSocketFactory
            httpsURLConnection.setSSLSocketFactory(_03_SSLContext.getSSLSocketFactory("", "", ""));
            //获得输入流
            InputStream inputStream = httpsURLConnection.getInputStream();
            //若正常打开HTTPS，将获得一个有效值，即contentlength不为-1
            int length = httpsURLConnection.getContentLength();

            //............

            //关闭流
            inputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
