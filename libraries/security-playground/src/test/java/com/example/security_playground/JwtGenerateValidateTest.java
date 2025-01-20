package com.example.security_playground;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;
import java.util.Date;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class JwtGenerateValidateTest {

  @Test
  void test_generate_and_validate() throws JOSEException, ParseException, IOException {
    // RSA signatures require a public and private RSA key pair, the public key
    // must be made known to the JWS recipient in order to verify the signatures
    RSAKey rsaJWK = new RSAKeyGenerator(2048)
        .keyID("123")
        .generate();
    RSAKey rsaPublicJWK = rsaJWK.toPublicJWK();

    // Create RSA-signer with the private key
    JWSSigner signer = new RSASSASigner(rsaJWK);

    // Prepare JWT with claims set
    JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
        .subject("alice")
        .issuer("https://c2id.com")
        .expirationTime(new Date(new Date().getTime() + 60 * 1000))
        .build();

    SignedJWT signedJWT = new SignedJWT(
        new JWSHeader.Builder(JWSAlgorithm.RS256).keyID(rsaJWK.getKeyID()).build(),
        claimsSet);

    // Compute the RSA signature
    signedJWT.sign(signer);

    // To serialize to compact form, produces something like
    // eyJhbGciOiJSUzI1NiJ9.SW4gUlNBIHdlIHRydXN0IQ.IRMQENi4nJyp4er2L
    // mZq3ivwoAjqa1uUkSBKFIX7ATndFF5ivnt-m8uApHO4kfIFOrW7w2Ezmlg3Qd
    // maXlS9DhN0nUk_hGI3amEjkKd0BWYCB8vfUbUv0XGjQip78AI4z1PrFRNidm7
    // -jPDm5Iq0SZnjKjCNS5Q15fokXZc8u0A
    String s = signedJWT.serialize();

    // On the consumer side, parse the JWS and verify its RSA signature
    signedJWT = SignedJWT.parse(s);

    JWSVerifier verifier = new RSASSAVerifier(rsaPublicJWK);
    assertTrue(signedJWT.verify(verifier));

    // Retrieve / verify the JWT claims according to the app requirements
    assertEquals("alice", signedJWT.getJWTClaimsSet().getSubject());
    assertEquals("https://c2id.com", signedJWT.getJWTClaimsSet().getIssuer());
    assertTrue(new Date().before(signedJWT.getJWTClaimsSet().getExpirationTime()));

    log.info("Public Key:{}{}", System.lineSeparator(), KeyUtils.getPem(rsaPublicJWK));
    log.info("Private Key:{}{}", System.lineSeparator(), KeyUtils.getPem(rsaJWK));
  }

  @Test
  void convert_standard_to_jwk() throws NoSuchAlgorithmException {
    // Generate the RSA key pair
    KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
    gen.initialize(2048);
    KeyPair keyPair = gen.generateKeyPair();

    // Convert to JWK format
    JWK jwk = new RSAKey.Builder((RSAPublicKey) keyPair.getPublic())
        .privateKey((RSAPrivateKey) keyPair.getPrivate())
        .keyUse(KeyUse.SIGNATURE)
        .keyID(UUID.randomUUID().toString())
        .issueTime(new Date())
        .build();
    assertThat(jwk)
        .isNotNull();
  }

}
