package com.eussi.jca_jce.sslpkg;

import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.security.cert.Certificate;

/**
 * Created by wangxueming on 2019/4/1.
 */
public class _06_SSLSocket {//保持SSL协议网络交互状态

    /**
     * 获得数字证书
     *
     * 有时候,我们需要与远程服务建立基于 SSLSocket的连接。远程服务仅通过 SSLSocket传递
     * 数字证书。这时候,就不能通过 HttpsurlconnectioN类来获得数字证书了。我们可以通过
     * SSLSocket实例获得 SSLSession实例,最终获得数字证书
     *
     * @param hostname
     * @param port
     * @return
     */
    public static Certificate[] getCertificate(String hostname, int port) {
        try {
            //获得SSLSocketFactory实例
            SSLSocketFactory sslSocketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();

            //构建SSlSocket实例
            SSLSocket sslSocket = (SSLSocket) sslSocketFactory.createSocket(hostname, port);

            //SSL握手
            sslSocket.startHandshake();

            //获得SSLSession实例
            SSLSession sslSession = sslSocket.getSession();

            //关闭socket
            sslSocket.close();

            //获得数字证书
            return sslSession.getPeerCertificates();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
