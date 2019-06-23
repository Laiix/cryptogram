package com.eussi._08_certificate;

/**
 * Created by wangxueming on 2019/6/23.
 */
public class _02_Openssl {
    /**
     * passwd:123456
     *
        OpenSSL是一个开源的代码软件包，实现了SSL及相关加密技术，时最常用的证书管理工具。
        其功能远胜于KeyTool，可用于根证书，服务器证书和客户证书的管理

        1. 准备工作
            # 配置openssl配置文件
            [root@app2 ~]# pwd
            /root
            [root@app2 ~]# cp /etc/pki/tls/openssl.cnf ./
            [root@app2 ~]# grep dir  openssl.cnf
            dir		= /root/TestCa		# Where everything is kept
            ......
            [root@app2 ~]# export OPENSSL_CONF=/root/openssl.cnf

            #建立CA工作目录，以及一些子目录，用于存放证书，密钥等，最终证书在certs目录中
            [root@app2 ~]# mkdir TestCa
            [root@app2 ~]# cd TestCa/
            [root@app2 TestCa]# mkdir certs #构建已发行证书存放目录
            [root@app2 TestCa]# mkdir newcerts #构建新证书存放目录
            [root@app2 TestCa]# mkdir private #构建私钥存放目录
            [root@app2 TestCa]# mkdir crl #构建证书吊销列表存放目录

            #创建一些需要的文件
            [root@app2 TestCa]# echo 0>index.txt #构建索引文件
                                #注意，应该是>index.txt,此文件有值，
                                #签发客户端证书时：wrong number of fields on line 1 (looking for field 6, got 1, '' left)
            [root@app2 TestCa]# echo 01>serial #构建序列号文件

        2. 构建根证书
            #构建随机数文件
            [root@app2 TestCa]# openssl rand -out private/.rand 1000


            #构建根证书密钥
            [root@app2 TestCa]# openssl genrsa -aes256 -out private/ca.key.pem 2048
            Generating RSA private key, 2048 bit long modulus
            ........................................+++
            ....................................+++
            e is 65537 (0x10001)
            Enter pass phrase for private/ca.key.pem:
            Verifying - Enter pass phrase for private/ca.key.pem:

            #生成根证书签发申请文件
            [root@app2 TestCa]# openssl req -new -key private/ca.key.pem -out private/ca.csr -subj "/C=CN/ST=SH/L=SH/O=eussi/OU=eussi/CN=*.eussi.top"
            Enter pass phrase for private/ca.key.pem:

            #申请文件可以将其发送给CA机构，也可以自行签发根证书
            [root@app2 TestCa]# openssl x509 -req -days 10000 -sha1 -extensions v3_ca -signkey private/ca.key.pem -in private/ca.csr -out certs/ca.cer
            Signature ok
            subject=/C=CN/ST=SH/L=SH/O=eussi/OU=eussi/CN=*.eussi.top
            Getting Private key
            Enter pass phrase for private/ca.key.pem:


            #Openssl产生的证书不能在Java环境直接使用，需要将其转化为PKCS#12编码格式
            [root@app2 TestCa]# openssl pkcs12 -export -cacerts -inkey private/ca.key.pem -in certs/ca.cer -out certs/ca.p12
            Enter pass phrase for private/ca.key.pem:
            Enter Export Password:
            Verifying - Enter Export Password:

            #keytool工具查看
            [root@app2 TestCa]# keytool -list -keystore certs/ca.p12 -storetype pkcs12 -v -storepass 123456
            Keystore type: PKCS12
            Keystore provider: SunJSSE

            Your keystore contains 1 entry

            Alias name: 1
            Creation date: Jun 23, 2019
            Entry type: PrivateKeyEntry
            Certificate chain length: 1
            Certificate[1]:
            Owner: CN=*.eussi.top, OU=eussi, O=eussi, L=SH, ST=SH, C=CN
            Issuer: CN=*.eussi.top, OU=eussi, O=eussi, L=SH, ST=SH, C=CN
            Serial number: 895882440522d5af
            Valid from: Sun Jun 23 13:18:05 CST 2019 until: Thu Nov 08 13:18:05 CST 2046
            Certificate fingerprints:
            MD5:  91:4F:72:33:F2:E0:A8:58:98:E6:C6:1A:D0:1D:93:4B
            SHA1: 54:71:67:D8:2C:35:98:07:C7:90:87:0C:DB:9B:A5:B9:7E:BB:69:E1
            SHA256: 7E:0A:DA:6D:D6:A5:35:03:C9:85:F0:4B:C3:DF:A4:C5:3A:D7:5C:52:D6:0F:AD:1F:64:99:85:18:CF:AB:B3:60
            Signature algorithm name: SHA1withRSA
            Subject Public Key Algorithm: 2048-bit RSA key
            Version: 1

        3. 构建服务器证书

            #构建私钥
            [root@app2 TestCa]# openssl genrsa -aes256 -out private/server.key.pem 2048
            Generating RSA private key, 2048 bit long modulus
            ...........................+++
            .......................................................+++
            e is 65537 (0x10001)
            Enter pass phrase for private/server.key.pem:
            Verifying - Enter pass phrase for private/server.key.pem:

            #生成服务器签发申请
            [root@app2 TestCa]# openssl req -new -key private/server.key.pem -out private/server.csr -subj "/C=CN/ST=SH/L=SH/O=eussi/OU=eussi/CN=www.eussi.top"
            Enter pass phrase for private/server.key.pem:

            #使用根证书签发服务器证书
            [root@app2 TestCa]# openssl x509 -req -days 3650 -sha1 -extensions v3_req -CA certs/ca.cer -CAkey private/ca.key.pem -CAserial ca.srl -CAcreateserial -in private/server.csr -out certs/server.cer
            Signature ok
            subject=/C=CN/ST=SH/L=SH/O=eussi/OU=eussi/CN=www.eussi.top
            Getting CA Private Key
            Enter pass phrase for private/ca.key.pem:

            #以下同理，转格式，然后查看
            [root@app2 TestCa]# openssl pkcs12 -export -clcerts -inkey private/server.key.pem -in certs/server.cer -out certs/server.p12
            Enter pass phrase for private/server.key.pem:
            Enter Export Password:
            Verifying - Enter Export Password:

            [root@app2 TestCa]# keytool -list -keystore certs/server.p12 -storetype pkcs12 -v -storepass 123456
            Keystore type: PKCS12
            Keystore provider: SunJSSE

            Your keystore contains 1 entry

            Alias name: 1
            Creation date: Jun 23, 2019
            Entry type: PrivateKeyEntry
            Certificate chain length: 1
            Certificate[1]:
            Owner: CN=www.eussi.top, OU=eussi, O=eussi, L=SH, ST=SH, C=CN
            Issuer: CN=*.eussi.top, OU=eussi, O=eussi, L=SH, ST=SH, C=CN
            Serial number: 8b4ad5defd96b19c
            Valid from: Sun Jun 23 13:32:23 CST 2019 until: Wed Jun 20 13:32:23 CST 2029
            Certificate fingerprints:
            MD5:  A7:8F:7B:3E:89:1F:A0:71:7F:66:96:B8:91:51:B3:37
            SHA1: 04:D1:35:54:27:40:D5:65:66:23:AD:32:18:AF:C3:31:F0:A5:4E:68
            SHA256: 8C:64:14:28:AC:5A:37:3D:E6:1B:4B:E6:37:CF:CB:8A:12:34:41:CA:DB:2F:BD:A2:0E:9B:5E:38:3D:AD:7C:1C
            Signature algorithm name: SHA1withRSA
            Subject Public Key Algorithm: 2048-bit RSA key
            Version: 1


            *******************************************
            *******************************************

     4. 构建客户端证书
            #构建私钥
            [root@app2 TestCa]# openssl genrsa -aes256 -out private/client.key.pem 2048
            Generating RSA private key, 2048 bit long modulus
            ......+++
            .................................................................+++
            e is 65537 (0x10001)
            Enter pass phrase for private/client.key.pem:
            Verifying - Enter pass phrase for private/client.key.pem:

            #生成客户端签发申请
            [root@app2 TestCa]# openssl req -new -key private/client.key.pem -out private/client.csr -subj "/C=CN/ST=SH/L=SH/O=eussi/OU=eussi/CN=eussi"
            Enter pass phrase for private/client.key.pem:

            #使用根证书签发客户端证书
            [root@app2 TestCa]# openssl ca -days 3650 -in private/client.csr -out certs/client.cer -cert certs/ca.cer -keyfile private/ca.key.pem
            Using configuration from /root/openssl.cnf
            Enter pass phrase for private/ca.key.pem:
            Check that the request matches the signature
            Signature ok
            Certificate Details:
            Serial Number: 1 (0x1)
            Validity
            Not Before: Jun 23 05:46:39 2019 GMT
            Not After : Jun 20 05:46:39 2029 GMT
            Subject:
            countryName               = CN
            stateOrProvinceName       = SH
            organizationName          = eussi
            organizationalUnitName    = eussi
            commonName                = eussi
            X509v3 extensions:
            X509v3 Basic Constraints:
            CA:FALSE
            Netscape Comment:
            OpenSSL Generated Certificate
            X509v3 Subject Key Identifier:
            E2:DA:CC:8C:DA:08:15:11:BB:96:48:7F:5D:90:E5:30:D2:F4:C1:E6
            X509v3 Authority Key Identifier:
            DirName:/C=CN/ST=SH/L=SH/O=eussi/OU=eussi/CN=*.eussi.top
            serial:89:58:82:44:05:22:D5:AF

            Certificate is to be certified until Jun 20 05:46:39 2029 GMT (3650 days)
            Sign the certificate? [y/n]:y


            1 out of 1 certificate requests certified, commit? [y/n]y
            Write out database with 1 new entries
            Data Base Updated


            #以下同理，转格式，然后查看
            [root@app2 TestCa]# openssl pkcs12 -export -inkey private/client.key.pem -in certs/client.cer -out certs/client.p12
            Enter pass phrase for private/client.key.pem:
            Enter Export Password:
            Verifying - Enter Export Password:


     */
}
