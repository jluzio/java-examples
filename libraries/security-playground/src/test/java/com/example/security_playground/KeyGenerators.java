package com.example.security_playground;

import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.spec.KeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.function.Function;
import org.springframework.stereotype.Component;

@Component
public class KeyGenerators {

  public PublicKey generatePublicKey(
      Key key, Function<PrivateKey, KeySpec> keySpecExtractor) throws GeneralSecurityException {
    if (key instanceof PublicKey publicKey) {
      return publicKey;
    }
    if (!(key instanceof PrivateKey privateKey)) {
      throw new IllegalArgumentException("Unknown key type: %s".formatted(key));
    }
    var keyFactory = KeyFactory.getInstance(key.getAlgorithm());
    var keySpec = keySpecExtractor.apply(privateKey);
    return keyFactory.generatePublic(keySpec);
  }

  public KeySpec getRsaKeySpec(PrivateKey privateKey) {
    if (!(privateKey instanceof RSAPrivateCrtKey rsaPrivateKey)) {
      throw new IllegalArgumentException("Unknown: %s".formatted(privateKey));
    }
    return new RSAPublicKeySpec(rsaPrivateKey.getModulus(), rsaPrivateKey.getPublicExponent());
  }

}
