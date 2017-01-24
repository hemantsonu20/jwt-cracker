package com.github.spring.jwt;

import static org.springframework.security.jwt.codec.Codecs.b64UrlDecode;

import java.io.IOException;
import java.nio.CharBuffer;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.jwt.crypto.sign.InvalidSignatureException;
import org.springframework.security.jwt.crypto.sign.MacSigner;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JwtService {

    private static final Map<String, String> ALGO_MAP = new HashMap<>();
    static {

        ALGO_MAP.put("HS256", "HMACSHA256");
        ALGO_MAP.put("HS384", "HMACSHA384");
        ALGO_MAP.put("HS512", "HMACSHA512");
        ALGO_MAP.put(null, "HMACSHA512");
    }

    private final String alg;

    private final JwtElement jwtElement;

    ObjectMapper mapper = new ObjectMapper();

    public JwtService(String token) {

        jwtElement = parseJwt(token);
        alg = parseAlgorithm(jwtElement.headers());

        System.out.println("algo:" + alg);
    }

    public boolean isMatched(String secretKey) {

        try {
            MacSigner signer = new MacSigner(alg, new SecretKeySpec(secretKey.getBytes(), alg));
            signer.verify(jwtElement.signingInput(), jwtElement.crypto());
            return true;
        } catch (InvalidSignatureException e) {
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
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

    private String parseAlgorithm(byte[] headers) {

        Map<String, String> map;
        try {
            map = mapper.readValue(headers, new TypeReference<Map<String, String>>() {});
            return ALGO_MAP.get(map.get("alg"));

        } catch (IOException e) {
            return ALGO_MAP.get(null);
        }
    }
}
