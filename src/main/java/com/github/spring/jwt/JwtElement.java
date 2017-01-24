package com.github.spring.jwt;

import static org.springframework.security.jwt.codec.Codecs.b64UrlEncode;
import static org.springframework.security.jwt.codec.Codecs.concat;
import static org.springframework.security.jwt.codec.Codecs.utf8Encode;

/**
 * A class representing the different fields of a jwt token
 * 
 * @author heman
 *
 */
public final class JwtElement {

    // header part
    private final byte[] headers;

    // content part
    private final byte[] claims;

    // signature part
    private final byte[] crypto;

    // headers + '.' + content, will be directly used for signing with the
    // generated key,
    private final byte[] signingInput;

    public JwtElement(byte[] headers, byte[] claims, byte[] crypto) {

        this.headers = headers;
        this.claims = claims;
        this.crypto = crypto;
        this.signingInput = concat(b64UrlEncode(headers), utf8Encode("."), b64UrlEncode(claims));
    }

    public byte[] headers() {

        return headers;
    }

    public byte[] claims() {

        return claims;
    }

    public byte[] crypto() {

        return crypto;
    }

    public byte[] signingInput() {

        return signingInput;
    }
}
