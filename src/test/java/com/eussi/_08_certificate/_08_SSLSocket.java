package com.eussi._08_certificate;

import org.junit.Assert;
import org.junit.Test;

import javax.net.ssl.*;
import java.io.*;
import java.security.cert.Certificate;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangxueming on 2019/6/27.
 */
public class _08_SSLSocket {

    private String hostname = "itunes.apple.com";
    private int port = 443;

    /**
     * 获取数字证书
     * @throws Exception
     */
    @Test
    public void test() throws Exception {
        //debug模式
//        System.setProperty("javax.net.debug", "ssl,record");
//        System.setProperty("javax.net.debug", "all");

        //打开ssl debug可以看到协议交互过程
        /**
         第一步: 客户端发送ClientHello消息，发起SSL连接请求，告诉服务器自己支持的SSL选项（加密方式等）。
            *** ClientHello, TLSv1
         第二步: 服务器响应请求，回复ServerHello消息，和客户端确认SSL加密方式：
            *** ServerHello, TLSv1
         第三步: 服务端向客户端发布自己的公钥。
         第四步: 客户端与服务端的协通沟通完毕，服务端发送ServerHelloDone消息：
            *** ServerHelloDone
         第五步: 客户端使用服务端给予的公钥，创建会话用密钥（SSL证书认证完成后，为了提高性能，所有的信息交互就可能会使用对称加密算法），并通过ClientKeyExchange消息发给服务器：
            *** ClientKeyExchange, RSA PreMasterSecret, TLSv1
         第六步： 客户端通知服务器改变加密算法，通过ChangeCipherSpec消息发给服务端：
            main, WRITE: TLSv1 Change Cipher Spec, length = 1
         第七步： 客户端发送Finished消息，告知服务器请检查加密算法的变更请求：
            *** Finished
         第八步：服务端确认算法变更，返回ChangeCipherSpec消息
            main, READ: TLSv1 Change Cipher Spec, length = 1
         第九步：服务端发送Finished消息，加密算法生效：
            *** Finished
         */


        SSLSocketFactory sslSocketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        SSLSocket sslSocket = (SSLSocket) sslSocketFactory.createSocket(hostname, port);

        //握手，开始会话
        sslSocket.startHandshake();
        SSLSession sslSession = sslSocket.getSession();
        sslSocket.close();

        //获取服务器证书链
        Certificate[] certificates = sslSession.getPeerCertificates();
        for(Certificate certificate : certificates) {
            FileOutputStream f = new FileOutputStream(
                    "src/test/resources/" + System.currentTimeMillis() + "-itumes.cer");
            DataOutputStream dataOutputStream = new DataOutputStream(f);
            dataOutputStream.write(certificate.getEncoded());
            dataOutputStream.flush();
            dataOutputStream.close();
        }


        /**
             比对单向认证的日志输出，我们可以发现双向认证时，多出了服务端认证客户端证书的步骤：
             *** CertificateRequest
             Cert Types: RSA, DSS
             Cert Authorities:
             <CN=localhost, OU=cn, O=cn, L=cn, ST=cn, C=cn>
             <CN=localhost, OU=cn, O=cn, L=cn, ST=cn, C=cn>
             *** ServerHelloDone

             *** CertificateVerify
             main, WRITE: TLSv1 Handshake, length = 134
             main, WRITE: TLSv1 Change Cipher Spec, length = 1
             在 @*** ServerHelloDone@ 之前，服务端向客户端发起了需要证书的请求 @*** CertificateRequest@ 。
             在客户端向服务端发出 @Change Cipher Spec@ 请求之前，多了一步客户端证书认证的过程 @*** CertificateVerify@ 。
         */
    }


    /**
     * SSL Server服务端
     */
    @Test
    public void testServer() {
        //加密端口
        int port = 7070;
        System.setProperty("javax.net.ssl.keyStore", "src/test/resources/_08_certificate/keytool/eussi.keystore");
        System.setProperty("javax.net.ssl.keyStorePassword", "123456");
        System.setProperty("javax.net.ssl.trustStore", "src/test/resources/_08_certificate/keytool/eussi.keystore");
        System.setProperty("javax.net.ssl.trustStorePassword", "123456");

        SSLServerSocket sslServerSocket = null;
        try {
            //初始化SSLServerSocketFactory
            SSLServerSocketFactory sslServerSocketFactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
            //构建sslServerSocket实例
            sslServerSocket = (SSLServerSocket) sslServerSocketFactory.createServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(!sslServerSocket.isClosed()) {
            try {
                // SSLServerSocket阻塞，等待客户端进来
                SSLSocket sslSocket = (SSLSocket) sslServerSocket.accept();
                // 获得输入流
                ObjectInputStream objectInputStream = new ObjectInputStream(sslSocket.getInputStream());
                //获得用户名密码
                Map<String, String> map = (Map<String, String>) objectInputStream.readObject();
                String username = map.get("USERNAME");
                String password = map.get("PASSWORD");
                //验证用户名密码
                Assert.assertNotNull(username);
                Assert.assertEquals("123456", password);
                System.out.println("receive:" + username + "  " + password);
                //获得输出流
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(sslSocket.getOutputStream());
                //输出OK
                objectOutputStream.writeUTF("OK");
                objectOutputStream.flush();
                //关闭套接字
                objectOutputStream.close();
                objectInputStream.close();
                sslSocket.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * SSL Client客户端
     */
    @Test
    public void testClient() {
        //加密端口
        int port = 7070;
        String hostname = "localhost";
        System.setProperty("javax.net.ssl.keyStore", "src/test/resources/_08_certificate/keytool/eussi.keystore");
        System.setProperty("javax.net.ssl.keyStorePassword", "123456");
        System.setProperty("javax.net.ssl.trustStore", "src/test/resources/_08_certificate/keytool/eussi.keystore");
        System.setProperty("javax.net.ssl.trustStorePassword", "123456");

        try {
            //初始化SSLSocketFactory
            SSLSocketFactory sslSocketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            //构建sslServerSocket实例
            SSLSocket sslSocket = (SSLSocket) sslSocketFactory.createSocket(hostname, port);
            //获得输出流
            //获得输出流
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(sslSocket.getOutputStream());
            //构建map对象
            Map<String, String> map = new HashMap<String, String>();
            map.put("USERNAME", "NAME");
            map.put("PASSWORD", "123456");
            //输出
            objectOutputStream.writeObject(map);
            objectOutputStream.flush();
            // 获得输入流
            ObjectInputStream objectInputStream = new ObjectInputStream(sslSocket.getInputStream());
            String result =  objectInputStream.readUTF();
            System.out.println(result);
            //关闭流
            objectInputStream.close();
            objectOutputStream.close();
            sslSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
