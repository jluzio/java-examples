package com.example.security_playground;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

@SpringBootTest(classes = {KeyTools.class, KeyGenerators.class, JacksonAutoConfiguration.class})
@Log4j2
class KeyToolsTest {

  @Autowired
  KeyTools keyTools;
  @Autowired
  KeyGenerators keyGenerators;
  @Autowired
  ObjectMapper objectMapper;
  @Autowired
  ResourceLoader resourceLoader;

  @Test
  void test() throws IOException, GeneralSecurityException {
    var paramsJson = """
        {
          "storeResource": "classpath:keystore.pkcs12",
          "storePass": "changeit",
          "alias": "main_key"
        }
        """;
    var params = objectMapper.readValue(paramsJson, KeyFetchParams.class);
    log.debug("params: {}", params);

    KeyStore keyStore = keyTools.keyStore(params);
    Key key = keyTools.key(params, keyStore);

    log.debug("key.pem:{}{}", System.lineSeparator(), keyTools.getPem(key));
  }

  @Test
  void test_base64() throws IOException, GeneralSecurityException, JOSEException {
    Resource keyStoreResource = resourceLoader.getResource("classpath:keystore.pkcs12");
    byte[] keyStoreBytes = Files.readAllBytes(keyStoreResource.getFile().toPath());
    String keyStoreBase64 = Base64.getEncoder().encodeToString(keyStoreBytes);
    String storePassBase64 = Base64.getEncoder().encodeToString("changeit".getBytes(StandardCharsets.UTF_8));

    var paramsJson = """
        {
          "storeBase64": "%s",
          "storePassBase64": "%s",
          "alias": "main_key"
        }
        """.formatted(keyStoreBase64, storePassBase64);
    var params = objectMapper.readValue(paramsJson, KeyFetchParams.class);
    log.debug("params: {}", params);

    KeyStore keyStore = keyTools.keyStore(params);

    Key key = keyTools.key(params, keyStore);
    log.debug("key.pem:{}{}", System.lineSeparator(), keyTools.getPem(key));

    PublicKey publicKey = keyGenerators.generatePublicKey(key, keyGenerators::getRsaKeySpec);
    log.debug("publicKey.pem:{}{}", System.lineSeparator(), keyTools.getPem(publicKey));

    JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.RS256);
    JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
        .subject("some_user")
        .build();
    SignedJWT signedJWT = new SignedJWT(
        jwsHeader,
        jwtClaimsSet
    );
    signedJWT.sign(new RSASSASigner((PrivateKey) key));
    log.debug("jwt:{}{}", System.lineSeparator(), signedJWT.serialize());

    JWSVerifier jwsVerifier = new RSASSAVerifier((RSAPublicKey) publicKey);
    assertThat(signedJWT.verify(jwsVerifier))
        .isTrue();
  }

}