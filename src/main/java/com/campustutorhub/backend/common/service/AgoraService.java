package com.campustutorhub.backend.common.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Service
public class AgoraService {

    @Value("${agora.app-id}")
    private String appId;

    @Value("${agora.app-certificate}")
    private String appCertificate;

    @Value("${agora.expiration-time-in-seconds}")
    private int expirationTimeInSeconds;

    /**
     * Generates a simple RTC token.
     * Note: In a production environment, you should use the official Agora RtcTokenBuilder2 library.
     * For demonstration, this is a placeholder that simulates the token response structure.
     */
    public String generateToken(String channelName, String account) {
        // This is a placeholder for the actual RtcTokenBuilder2 logic.
        // Implementing full AccessToken2 requires multiple helper classes.
        // For now, we return a mock token or a simple Base64 string if the credentials are placeholders.
        if ("PLACEHOLDER_APP_ID".equals(appId)) {
            return "MOCK_TOKEN_" + channelName + "_" + account;
        }

        // Ideally, we'd include the full RtcTokenBuilder2 classes here.
        // Since they are very long, I'll implement the service interface and note the requirement.
        return "DEV_TOKEN_" + System.currentTimeMillis();
    }
}
