package com.example.security_playground;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;

@SpringBootTest
@Log4j2
class JwtProcessingTest {

  // Sample from jwt.io with RSA256
  // https://jwt.io/
  public static final String SAMPLE_JWT = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImlhdCI6MTUxNjIzOTAyMn0.NHVaYe26MbtOYhSKkoKYdFVomg4i8ZJd8_-RU8VNbftc4TSMb4bXP3l3YlNWACwyXPGffz5aXHc6lty1Y2t4SWRqGteragsVdZufDn5BlnJl9pdR_kdVFUsra2rWKEofkZeIC4yWytE58sMIihvo9H1ScmmVwBcQP6XETqYd0aSHp1gOa9RdUPDvoXQ5oqygTqVtxaDr6wUFKrKItgBMzWIdNZ6y7O9E0DhEPTbE9rfBo6KTFsHAZnMg4k68CDp2woYIaXbmYTWcvbzIuHO7_37GT79XdIwkm95QJ7hYC9RiwrV7mesbY4PAahERJawntho0my942XheVLmGwLMBkQ";
  public static final String SAMPLE_JWT_PUBLIC_KEY_PEM = """
      -----BEGIN PUBLIC KEY-----
      MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAu1SU1LfVLPHCozMxH2Mo
      4lgOEePzNm0tRgeLezV6ffAt0gunVTLw7onLRnrq0/IzW7yWR7QkrmBL7jTKEn5u
      +qKhbwKfBstIs+bMY2Zkp18gnTxKLxoS2tFczGkPLPgizskuemMghRniWaoLcyeh
      kd3qqGElvW/VDL5AaWTg0nLVkjRo9z+40RQzuVaE8AkAFmxZzow3x+VJYKdjykkJ
      0iT9wCS0DRTXu269V264Vf/3jvredZiKRkgwlL9xNAwxXFg0x/XFw005UWVRIkdg
      cKWTjpBP2dPwVZ4WWC+9aGVd+Gyn1o0CLelf4rEjGoXbAAEgAqeGUxrcIlbjXfbc
      mwIDAQAB
      -----END PUBLIC KEY-----
      """;

  @Configuration
  @Import({KeyTools.class, JacksonAutoConfiguration.class})
  static class Config {

  }

  @Value("classpath:private_keys_jwks.json")
  Resource privateKeysResource;
  @Autowired
  ObjectMapper objectMapper;
  @Autowired
  KeyTools keyTools;

  @Test
  void read_verify_jwt() throws Exception {
    SignedJWT signedJwt = SignedJWT.parse(SAMPLE_JWT);
    log.debug(signedJwt.serialize());
    log.debug("header: {}", objectMapper.writeValueAsString(signedJwt.getHeader().toJSONObject()));
    log.debug("payload: {}", objectMapper.writeValueAsString(signedJwt.getPayload().toJSONObject()));
    log.debug("signature: {}", signedJwt.getSignature());

    JWK key = JWK.parseFromPEMEncodedObjects(SAMPLE_JWT_PUBLIC_KEY_PEM);
    log.debug(key);
    JWSVerifier verifier = new RSASSAVerifier(key.toRSAKey());

    assertThat(signedJwt.verify(verifier))
        .isTrue();
  }

  @Test
  void process_jwt_with_new_signature() throws Exception {
    SignedJWT inputJwt = SignedJWT.parse(SAMPLE_JWT);
    String kid = "2011-04-29";

    JWKSet jwkSet = JWKSet.load(privateKeysResource.getFile());
    JWK key = jwkSet.getKeyByKeyId(kid);

    RSASSASigner signer = new RSASSASigner(key.toRSAKey());

    JWSHeader targetHeader = new JWSHeader.Builder(JWSAlgorithm.RS256)
        .keyID(kid)
        .build();
    SignedJWT targetJwt = new SignedJWT(targetHeader, inputJwt.getJWTClaimsSet());
    targetJwt.sign(signer);

    log.debug("header: {}", objectMapper.writeValueAsString(targetJwt.getHeader().toJSONObject()));
    log.debug("payload: {}", objectMapper.writeValueAsString(targetJwt.getPayload().toJSONObject()));
    log.debug("signature: {}", targetJwt.getSignature());
    log.debug("jwt: {}", targetJwt.serialize());

    JWSVerifier verifier = new RSASSAVerifier(key.toPublicJWK().toRSAKey());
    assertThat(targetJwt.verify(verifier))
        .isTrue();

    log.debug("Private Key:{}{}",
        System.lineSeparator(), keyTools.getPem(key.toRSAKey().toPrivateKey()));
    log.debug("Public Key:{}{}",
        System.lineSeparator(), keyTools.getPem(key.toPublicJWK().toRSAKey().toPublicKey()));
  }

}
