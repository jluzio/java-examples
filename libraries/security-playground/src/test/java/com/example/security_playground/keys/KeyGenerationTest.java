package com.example.security_playground.keys;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {KeyTools.class})
@Slf4j
class KeyGenerationTest {

  @Autowired
  KeyTools keyTools;

  @Test
  void test() throws GeneralSecurityException, IOException {
    KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
    keyPairGenerator.initialize(2048);
    KeyPair keyPair = keyPairGenerator.generateKeyPair();
    log.debug("private-key:\n{}", keyTools.getPem(keyPair.getPrivate()));
    log.debug("public-key:\n{}", keyTools.getPem(keyPair.getPublic()));
  }

}
