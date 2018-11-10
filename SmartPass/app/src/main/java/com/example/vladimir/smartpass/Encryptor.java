package com.example.vladimir.smartpass;


import android.util.Base64;

public class Encryptor {
    private static final String KEY = "justmonika";

    public Encryptor() {

    }

    public String encrypt(final String text) {
        return Base64.encodeToString(this.xor(text.getBytes()), Base64.URL_SAFE);
    }

    public String decrypt(final String hash) {
        try {
            return new String(this.xor(Base64.decode(hash.getBytes(), Base64.URL_SAFE)), "UTF-8");
        } catch (java.io.UnsupportedEncodingException ex) {
            throw new IllegalStateException(ex);
        }
    }

    private byte[] xor(final byte[] input) {
        final byte[] output = new byte[input.length];
        final byte[] secret = this.KEY.getBytes();
        int spos = 0;
        for (int pos = 0; pos < input.length; ++pos) {
            output[pos] = (byte) (input[pos] ^ secret[spos]);
            spos += 1;
            if (spos >= secret.length) {
                spos = 0;
            }
        }
        return output;
    }
}
