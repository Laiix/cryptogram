package com.eussi._08_certificate;

/**
 * Created by wangxueming on 2019/6/23.
 */
public class _01_Keytool {
    /**
     * passwd:123456
     *
        1. 构建自签名证书
            #构建证书前，生成密钥对，即基于一种非对称加密的公私钥
             C:\Users\wangxueming>keytool -genkeypair -keyalg RSA -keysize 2048 -sigalg SHA1withRSA -validity 36000 -alias www.eussi.top -keystore eussi.keystore -storepass 123456 -dname "CN=www.eussi.top, OU=eussi, O=eussi, L=SH, ST=SH, C=CN"
             输入 <www.eussi.top> 的密钥口令
             (如果和密钥库口令相同, 按回车):

            #上述操作创建了数字证书，虽然还未经过CA认证，但是并不影响使用，我们仍可以导出，发给合作伙伴进行加密交互
             C:\Users\wangxueming>keytool -exportcert -alias www.eussi.top -keystore eussi.keystore -file eussi.cer -rfc -storepass 123456
             存储在文件 <eussi.cer> 中的证书

            #查看证书内容
             C:\Users\wangxueming>keytool -printcert -file eussi.cer
             所有者: CN=www.eussi.top, OU=eussi, O=eussi, L=SH, ST=SH, C=CN
             发布者: CN=www.eussi.top, OU=eussi, O=eussi, L=SH, ST=SH, C=CN
             序列号: 16126345
             有效期开始日期: Sun Jun 23 11:30:40 CST 2019, 截止日期: Sat Jan 15 11:30:40 CST 2118
             证书指纹:
             MD5: 86:B7:1B:72:8F:1F:14:34:70:AD:B7:AE:4F:93:A0:F2
             SHA1: C2:17:F8:02:73:95:CE:87:5F:B8:0B:15:22:FE:83:DB:62:5E:79:10
             SHA256: F6:D7:DD:A9:83:2B:8C:E6:AE:F2:43:5B:93:67:6F:28:94:2F:28:75:B1:DE:FF:35:C5:44:C3:33:34:6A:06:D8
             签名算法名称: SHA1withRSA
             版本: 3

             扩展:

             #1: ObjectId: 2.5.29.14 Criticality=false
             SubjectKeyIdentifier [
             KeyIdentifier [
             0000: 1F EB 76 14 B1 1B 95 AD   94 C7 80 45 15 7F BF 91  ..v........E....
             0010: 7A 16 02 7E                                        z...
             ]
             ]
        2. 构建CA签发证书
            #获取CA机构认证的数字证书，需要生成数字签发申请（CSR），经由CA机构认证并颁发，同时将认证后的证书导入本地密钥库和信任库
             C:\Users\wangxueming>keytool -certreq -alias www.eussi.top -keystore eussi.keystore -file eussi.csr -V -storepass 123456
             存储在文件 <eussi.csr> 中的认证请求
             将此提交给您的 CA

            #此处我并未提交给CA，只是重新导入一下自己生成的证书，这里会报错，正常情况下，是导入CA下发的证书
             C:\Users\wangxueming>keytool -importcert -trustcacerts -alias www.eussi.top -file eussi.cer -keystore eussi.keystore -storepass 123456
             keytool 错误: java.lang.Exception: 证书回复与密钥库中的证书是相同的

            #导入后便可以查看证书了
             C:\Users\wangxueming>keytool -list -alias www.eussi.top -keystore eussi.keystore
             输入密钥库口令:
             www.eussi.top, 2019-6-23, PrivateKeyEntry,
             证书指纹 (SHA1): C2:17:F8:02:73:95:CE:87:5F:B8:0B:15:22:FE:83:DB:62:5E:79:10

            #加-V或者-rfc显示更加详细的信息
             C:\Users\wangxueming>keytool -list -alias www.eussi.top -keystore eussi.keystore -V  -storepass 123456
             别名: www.eussi.top
             创建日期: 2019-6-23
             条目类型: PrivateKeyEntry
             证书链长度: 1
             证书[1]:
             所有者: CN=www.eussi.top, OU=eussi, O=eussi, L=SH, ST=SH, C=CN
             发布者: CN=www.eussi.top, OU=eussi, O=eussi, L=SH, ST=SH, C=CN
             序列号: 16126345
             有效期开始日期: Sun Jun 23 11:30:40 CST 2019, 截止日期: Sat Jan 15 11:30:40 CST 2118
             证书指纹:
             MD5: 86:B7:1B:72:8F:1F:14:34:70:AD:B7:AE:4F:93:A0:F2
             SHA1: C2:17:F8:02:73:95:CE:87:5F:B8:0B:15:22:FE:83:DB:62:5E:79:10
             SHA256: F6:D7:DD:A9:83:2B:8C:E6:AE:F2:43:5B:93:67:6F:28:94:2F:28:75:B1:DE:FF:35:C5:44:C3:33:34:6A:06:D8
             签名算法名称: SHA1withRSA
             版本: 3

             扩展:

             #1: ObjectId: 2.5.29.14 Criticality=false
             SubjectKeyIdentifier [
             KeyIdentifier [
             0000: 1F EB 76 14 B1 1B 95 AD   94 C7 80 45 15 7F BF 91  ..v........E....
             0010: 7A 16 02 7E                                        z...
             ]
             ]


             C:\Users\wangxueming>keytool -list -alias www.eussi.top -keystore eussi.keystore -rfc  -storepass 123456
             别名: www.eussi.top
             创建日期: 2019-6-23
             条目类型: PrivateKeyEntry
             证书链长度: 1
             证书[1]:
             -----BEGIN CERTIFICATE-----
             MIIDXzCCAkegAwIBAgIEFhJjRTANBgkqhkiG9w0BAQUFADBfMQswCQYDVQQGEwJDTjELMAkGA1UE
             CBMCU0gxCzAJBgNVBAcTAlNIMQ4wDAYDVQQKEwVldXNzaTEOMAwGA1UECxMFZXVzc2kxFjAUBgNV
             BAMTDXd3dy5ldXNzaS50b3AwIBcNMTkwNjIzMDMzMDQwWhgPMjExODAxMTUwMzMwNDBaMF8xCzAJ
             BgNVBAYTAkNOMQswCQYDVQQIEwJTSDELMAkGA1UEBxMCU0gxDjAMBgNVBAoTBWV1c3NpMQ4wDAYD
             VQQLEwVldXNzaTEWMBQGA1UEAxMNd3d3LmV1c3NpLnRvcDCCASIwDQYJKoZIhvcNAQEBBQADggEP
             ADCCAQoCggEBAOTK7kQEXiKFUaLeX6b7e3Brk1oOOb4TCH8q/MYbsnO3Bqo4/lbVTok1drHk4OUJ
             kg/+IzkZNptNFCM5prk/PBVyqGnq4JHgoRr8vTLendGxP+198RdudJf7rZfSQM2IrV1ZEbBqD6Kd
             3oiQJQYRCgX9KZmc/zqFLv7ZzoHA7hd0+itlAjby3a+Tl9GPOQz1AA2O/0J8G7KqqJNscCyoEsxL
             oIlKeYFOr89e7qDElzaVnmaC62i9ZsOTr/sCXz+AZvb6sWjJiRx4T+iYAa+AM824ojdvVr2ka04M
             HH0S1RiaMz8/25cJNBwyCusWaOEmu55Kd66GfhcAr1WKzJaVg78CAwEAAaMhMB8wHQYDVR0OBBYE
             FB/rdhSxG5WtlMeARRV/v5F6FgJ+MA0GCSqGSIb3DQEBBQUAA4IBAQAt0+iPcNzs25UWC67kqwGD
             nMdRDGfoJqpzVoaFRe7xsWlZ/2RZ9FCMTXAFPEvChY1cPrOUzpqQ6ZoAQqvGPL1jhObGsBqjL51o
             1LjSKLAtYHjBMFCldgKSZJLEm8GMqaDFDNlEMaRhQrkrcTXJ22qgv/9SQOObJT0r+Q18H147BsHG
             kQnLlRKwGoW++zIWsLaxbTw0kDvwFS1jr+BghqTNdocf0XDBalDsJJ9WsP5GlcfRKT94FRht4+Sr
             DdJy33OTpIjv+EoCD7qSC2caPBWwsvGhM5SkRETeNq+Pmju2sDzWKVsaYf7bEjtT/KoXjNN5jVMZ
             3jDPs6jx0QGHc6X3
             -----END CERTIFICATE-----


            #完成以上操作后，我们需要再次导出证书，将上面【-exportcert】证书导出命令，接着便可以将证书发给合作伙伴使用了

            #注意，此处的CA认证可以省略，直接使用步骤1里导出的未认证的证书，同样可以实现证书的功能
     */

}
