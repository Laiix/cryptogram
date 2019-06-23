package com.eussi._06_sm.sm2;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.engines.SM2Engine;
import org.bouncycastle.crypto.generators.ECKeyPairGenerator;
import org.bouncycastle.crypto.params.*;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.math.ec.custom.gm.SM2P256V1Curve;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;
import org.junit.Test;

import java.math.BigInteger;
import java.security.*;

/**
 * Created by wangxueming on 2019/2/24.
 */
public class _01_BouncyCastleSm2 {

    @Test
    public void test() {
        try {
            Security.addProvider(new BouncyCastleProvider());
            SecureRandom random = new SecureRandom();
            SM2P256V1Curve CURVE = new SM2P256V1Curve();

            BigInteger SM2_ECC_GX = new BigInteger(
                    "32C4AE2C1F1981195F9904466A39C9948FE30BBFF2660BE1715A4589334C74C7", 16);
            BigInteger SM2_ECC_GY = new BigInteger(
                    "BC3736A2F4F6779C59BDCEE36B692153D0A9877CC62A474002DF32E52139F0A0", 16);
            ECPoint G_POINT = CURVE.createPoint(SM2_ECC_GX, SM2_ECC_GY);

            BigInteger SM2_ECC_N = CURVE.getOrder();
            BigInteger SM2_ECC_H = CURVE.getCofactor();

            ECDomainParameters ecDomainParameters = new ECDomainParameters(CURVE, G_POINT,
                    SM2_ECC_N, SM2_ECC_H);
            ECKeyGenerationParameters keyGenerationParams = new ECKeyGenerationParameters(ecDomainParameters,random);
            ECKeyPairGenerator keyGen = new ECKeyPairGenerator();
            keyGen.init(keyGenerationParams);
            AsymmetricCipherKeyPair keyPair = keyGen.generateKeyPair();
            ECPublicKeyParameters pubKey = (ECPublicKeyParameters) keyPair.getPublic();
            ECPrivateKeyParameters priKey = (ECPrivateKeyParameters) keyPair.getPrivate();

            System.out.println("Pri Hex:"
                    + ByteUtils.toHexString(priKey.getD().toByteArray()).toUpperCase());
            System.out.println("Pub X Hex:"
                    + ByteUtils.toHexString(pubKey.getQ().getAffineXCoord().getEncoded()).toUpperCase());
            System.out.println("Pub Y Hex:"
                    + ByteUtils.toHexString(pubKey.getQ().getAffineYCoord().getEncoded()).toUpperCase());
            System.out.println("Pub Point Hex:"
                    + ByteUtils.toHexString(pubKey.getQ().getEncoded(false)).toUpperCase());

            //密钥还原
            ECPrivateKeyParameters priKey1 = new ECPrivateKeyParameters(
                    new BigInteger(priKey.getD().toByteArray()), ecDomainParameters);

            ECPublicKeyParameters pubKey1 = createECPublicKeyParameters(pubKey.getQ().getAffineXCoord().getEncoded(),
                    pubKey.getQ().getAffineYCoord().getEncoded(), CURVE, ecDomainParameters);


            byte[] input = "test".getBytes();
            //加密
            SM2Engine engine = new SM2Engine();
            ParametersWithRandom pwr = new ParametersWithRandom(pubKey1, new SecureRandom());
            engine.init(true, pwr);
            byte[] output = engine.processBlock(input, 0, input.length);
            System.out.println("SM2 encrypt result:\n" + Base64.encodeBase64String(output));

            //解密
            engine = new SM2Engine();
            engine.init(false, priKey1);
            byte[] origin = engine.processBlock(output, 0, output.length);
            System.out.println("SM2 decrypt result:\n" + new String(origin));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private ECPublicKeyParameters createECPublicKeyParameters(byte[] xBytes,
          byte[] yBytes,
          SM2P256V1Curve curve,
          ECDomainParameters ecDomainParameters) {
        final byte uncompressedFlag = 0x04;
        int curveLength = (ecDomainParameters.getCurve().getFieldSize() + 7) / 8;
        xBytes = fixToCurveLengthBytes(curveLength, xBytes);
        yBytes = fixToCurveLengthBytes(curveLength, yBytes);
        byte[] encodedPubKey = new byte[1 + xBytes.length + yBytes.length];
        encodedPubKey[0] = uncompressedFlag;
        System.arraycopy(xBytes, 0, encodedPubKey, 1, xBytes.length);
        System.arraycopy(yBytes, 0, encodedPubKey, 1 + xBytes.length, yBytes.length);
        return new ECPublicKeyParameters(curve.decodePoint(encodedPubKey), ecDomainParameters);
    }

    private byte[] fixToCurveLengthBytes(int curveLength, byte[] src) {
        if (src.length == curveLength) {
            return src;
        }
        byte[] result = new byte[curveLength];
        if (src.length > curveLength) {
            System.arraycopy(src, src.length - result.length, result, 0, result.length);
        } else {
            System.arraycopy(src, result.length - src.length, result, 0, src.length);
        }
        return result;
    }
}
