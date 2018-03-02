package com.example.hp.ssa;


import android.util.Base64;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Cipher {
    public static byte[] decrypt(byte[]bytes, String secretKey) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException {
        byte[] encodedKey = Base64.decode(secretKey, Base64.DEFAULT);
        SecretKey skey = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        byte[] skey_bytes=skey.getEncoded();
        SecretKeySpec skeySpec = new SecretKeySpec(skey_bytes, "AES");
        javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("AES");
        cipher.init(javax.crypto.Cipher.DECRYPT_MODE, skeySpec);

        byte[] decrypted = cipher.doFinal(bytes);

        return decrypted;
    }
}
