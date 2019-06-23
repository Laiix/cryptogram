package com.eussi._08_certificate;

/**
 * Created by wangxueming on 2019/6/23.
 */
public class _04_ImportPfx {
    /**
     #查看导入
     [root@app2 TestCa]# keytool -list -keystore eussi.keystore -storepass 123456 -v
     Keystore type: jks
     Keystore provider: SUN

     Your keystore contains 2 entries

     Alias name: www.eussi.top
     Creation date: Jun 23, 2019
     Entry type: PrivateKeyEntry
     Certificate chain length: 1
     Certificate[1]:
     Owner: CN=www.eussi.top, OU=eussi, O=eussi, L=SH, ST=SH, C=CN
     Issuer: CN=www.eussi.top, OU=eussi, O=eussi, L=SH, ST=SH, C=CN
     Serial number: 16126345
     Valid from: Sun Jun 23 11:30:40 CST 2019 until: Sat Jan 15 11:30:40 CST 2118
     Certificate fingerprints:
     MD5:  86:B7:1B:72:8F:1F:14:34:70:AD:B7:AE:4F:93:A0:F2
     SHA1: C2:17:F8:02:73:95:CE:87:5F:B8:0B:15:22:FE:83:DB:62:5E:79:10
     SHA256: F6:D7:DD:A9:83:2B:8C:E6:AE:F2:43:5B:93:67:6F:28:94:2F:28:75:B1:DE:FF:35:C5:44:C3:33:34:6A:06:D8
     Signature algorithm name: SHA1withRSA
     Subject Public Key Algorithm: 2048-bit RSA key
     Version: 3

     Extensions:

     #1: ObjectId: 2.5.29.14 Criticality=false
     SubjectKeyIdentifier [
     KeyIdentifier [
     0000: 1F EB 76 14 B1 1B 95 AD   94 C7 80 45 15 7F BF 91  ..v........E....
     0010: 7A 16 02 7E                                        z...
     ]
     ]



     *******************************************
     *******************************************


     Alias name: www.euss.top
     Creation date: Jun 23, 2019
     Entry type: PrivateKeyEntry
     Certificate chain length: 1
     Certificate[1]:
     Owner: CN=www.eussi.top, OU=eussi, O=eussi, L=SH, ST=SH, C=CN
     Issuer: CN=www.eussi.top, OU=eussi, O=eussi, L=SH, ST=SH, C=CN
     Serial number: 3ab267ac
     Valid from: Sun Jun 23 11:28:19 CST 2019 until: Sat Jan 15 11:28:19 CST 2118
     Certificate fingerprints:
     MD5:  1C:F6:65:F5:47:A6:93:D5:7E:23:B7:35:D3:E3:24:0A
     SHA1: 38:44:E9:A6:06:E3:41:0C:A9:67:D8:BC:7B:06:10:EE:16:3D:B1:18
     SHA256: F5:25:23:5A:A5:6D:29:C4:E3:2D:1E:6F:F8:EA:DE:EF:D2:1B:F5:5E:55:AA:FE:1F:91:2A:0D:DA:EE:C7:A0:44
     Signature algorithm name: SHA1withRSA
     Subject Public Key Algorithm: 2048-bit RSA key
     Version: 3

     Extensions:

     #1: ObjectId: 2.5.29.14 Criticality=false
     SubjectKeyIdentifier [
     KeyIdentifier [
     0000: 09 5C 7E 24 A9 47 FF C1   9E AA FB 12 50 19 67 46  .\.$.G......P.gF
     0010: AC 82 7F 08                                        ....
     ]
     ]



     *******************************************
     *******************************************



     Warning:
     The JKS keystore uses a proprietary format. It is recommended to migrate to PKCS12 which is an industry standard format using "keytool -importkeystore -srckeystore eussi.keystore -destkeystore eussi.keystore -deststoretype pkcs12".

     #导入.p12/.pfx
     [root@app2 TestCa]# keytool -importkeystore -v -srckeystore certs/server.p12 -srcstoretype pkcs12 -srcstorepass 123456 -destkeystore eussi.keystore -deststoretype jks -deststorepass 123456
     Importing keystore certs/server.p12 to eussi.keystore...
     Entry for alias 1 successfully imported.
     Import command completed:  1 entries successfully imported, 0 entries failed or cancelled
     [Storing eussi.keystore]

     Warning:
     The JKS keystore uses a proprietary format. It is recommended to migrate to PKCS12 which is an industry standard format using "keytool -importkeystore -srckeystore eussi.keystore -destkeystore eussi.keystore -deststoretype pkcs12".


     #再次查看导入
     [root@app2 TestCa]# keytool -list -keystore eussi.keystore -storepass 123456 -v
     Keystore type: jks
     Keystore provider: SUN

     Your keystore contains 3 entries

     Alias name: www.eussi.top
     Creation date: Jun 23, 2019
     Entry type: PrivateKeyEntry
     Certificate chain length: 1
     Certificate[1]:
     Owner: CN=www.eussi.top, OU=eussi, O=eussi, L=SH, ST=SH, C=CN
     Issuer: CN=www.eussi.top, OU=eussi, O=eussi, L=SH, ST=SH, C=CN
     Serial number: 16126345
     Valid from: Sun Jun 23 11:30:40 CST 2019 until: Sat Jan 15 11:30:40 CST 2118
     Certificate fingerprints:
     MD5:  86:B7:1B:72:8F:1F:14:34:70:AD:B7:AE:4F:93:A0:F2
     SHA1: C2:17:F8:02:73:95:CE:87:5F:B8:0B:15:22:FE:83:DB:62:5E:79:10
     SHA256: F6:D7:DD:A9:83:2B:8C:E6:AE:F2:43:5B:93:67:6F:28:94:2F:28:75:B1:DE:FF:35:C5:44:C3:33:34:6A:06:D8
     Signature algorithm name: SHA1withRSA
     Subject Public Key Algorithm: 2048-bit RSA key
     Version: 3

     Extensions:

     #1: ObjectId: 2.5.29.14 Criticality=false
     SubjectKeyIdentifier [
     KeyIdentifier [
     0000: 1F EB 76 14 B1 1B 95 AD   94 C7 80 45 15 7F BF 91  ..v........E....
     0010: 7A 16 02 7E                                        z...
     ]
     ]



     *******************************************
     *******************************************


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


     Alias name: www.euss.top
     Creation date: Jun 23, 2019
     Entry type: PrivateKeyEntry
     Certificate chain length: 1
     Certificate[1]:
     Owner: CN=www.eussi.top, OU=eussi, O=eussi, L=SH, ST=SH, C=CN
     Issuer: CN=www.eussi.top, OU=eussi, O=eussi, L=SH, ST=SH, C=CN
     Serial number: 3ab267ac
     Valid from: Sun Jun 23 11:28:19 CST 2019 until: Sat Jan 15 11:28:19 CST 2118
     Certificate fingerprints:
     MD5:  1C:F6:65:F5:47:A6:93:D5:7E:23:B7:35:D3:E3:24:0A
     SHA1: 38:44:E9:A6:06:E3:41:0C:A9:67:D8:BC:7B:06:10:EE:16:3D:B1:18
     SHA256: F5:25:23:5A:A5:6D:29:C4:E3:2D:1E:6F:F8:EA:DE:EF:D2:1B:F5:5E:55:AA:FE:1F:91:2A:0D:DA:EE:C7:A0:44
     Signature algorithm name: SHA1withRSA
     Subject Public Key Algorithm: 2048-bit RSA key
     Version: 3

     Extensions:

     #1: ObjectId: 2.5.29.14 Criticality=false
     SubjectKeyIdentifier [
     KeyIdentifier [
     0000: 09 5C 7E 24 A9 47 FF C1   9E AA FB 12 50 19 67 46  .\.$.G......P.gF
     0010: AC 82 7F 08                                        ....
     ]
     ]



     *******************************************
     *******************************************



     Warning:
     The JKS keystore uses a proprietary format. It is recommended to migrate to PKCS12 which is an industry standard format using "keytool -importkeystore -srckeystore eussi.keystore -destkeystore eussi.keystore -deststoretype pkcs12".




     */
}
