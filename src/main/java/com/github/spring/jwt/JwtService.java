package com.github.spring.jwt;

import static org.springframework.security.jwt.codec.Codecs.b64UrlDecode;

import java.io.IOException;
import java.nio.CharBuffer;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.jwt.crypto.sign.InvalidSignatureException;
import org.springframework.security.jwt.crypto.sign.MacSigner;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JwtService {
    
    private static final Logger LOG = LoggerFactory.getLogger(CommandLineOptions.class);

    // mapping of algorithm from jwt header to actual algoritm
    private static final Map<String, String> ALGO_MAP = new HashMap<>();
    static {

        // key part is present in jwt header, value part is used to sign the
        // content.
        ALGO_MAP.put("HS256", "HMACSHA256");
        ALGO_MAP.put("HS384", "HMACSHA384");
        ALGO_MAP.put("HS512", "HMACSHA512");
        ALGO_MAP.put(null, "HMACSHA512");
    }

    // algorithm in the jwt token, probably one of the keys in ALGO_MAP
    private final String alg;

    private final JwtElement jwtElement;

    public JwtService(String token) {

        jwtElement = parseJwt(token);
        alg = parseAlgorithm(jwtElement.headers());
    }

    /**
     * This method signs the content with the new generated secret key and
     * verifies with the existing signature.
     * 
     * @param secretKey
     * @return
     */
    public boolean isMatched(String secretKey) {

        try {
            MacSigner signer = new MacSigner(alg, new SecretKeySpec(secretKey.getBytes(), alg));
            signer.verify(jwtElement.signingInput(), jwtElement.crypto());
            return true;
        } catch (InvalidSignatureException e) {
            LOG.trace("secretKey did'nt match", e);
            return false;
        } catch (Exception e) {
            LOG.warn("something unexpected happened", e);
            return false;
        }
    }

    /**
     * Method parses individual parts of the JWT token
     * 
     * @param token
     * @return
     */
    private JwtElement parseJwt(String token) {

        int firstPeriod = token.indexOf('.');
        int lastPeriod = token.lastIndexOf('.');

        if (firstPeriod <= 0 || lastPeriod <= firstPeriod) {
            throw new IllegalArgumentException("JWT must have 3 tokens");
        }
        CharBuffer buffer = CharBuffer.wrap(token, 0, firstPeriod);
        byte[] header = b64UrlDecode(buffer);

        buffer.limit(lastPeriod).position(firstPeriod + 1);
        byte[] claims = b64UrlDecode(buffer);

        buffer.limit(token.length()).position(lastPeriod + 1);
        byte[] crypto = b64UrlDecode(buffer);

        return new JwtElement(header, claims, crypto);
    }

    /**
     * Method parses alg from the jwt header part
     * 
     * @param headers
     * @return
     */
    private String parseAlgorithm(byte[] headers) {

        Map<String, String> map;
        try {
            map = new ObjectMapper().readValue(headers, new TypeReference<Map<String, String>>() {
            });
            return ALGO_MAP.get(map.get("alg"));

        } catch (IOException e) {
            LOG.warn("deserialization failed", e);
            return ALGO_MAP.get(null);
        }
    }
}
