package com.magnesify.magneticOp.managers;

import java.security.SecureRandom;

public class Password {
    public Password() {
    }

    public static String generateRandomPassword(int len) {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < len; ++i) {
            int randomIndex = random.nextInt("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".length());
            sb.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".charAt(randomIndex));
        }

        return sb.toString();
    }
}
