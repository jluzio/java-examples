package com.example.security_playground;

import java.security.PrivateKey;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.spec.KeySpec;
import java.security.spec.RSAPublicKeySpec;
import lombok.experimental.UtilityClass;

@UtilityClass
public class KeySpecs {

  public static KeySpec getRsaKeySpec(PrivateKey privateKey) {
    if (!(privateKey instanceof RSAPrivateCrtKey rsaPrivateKey)) {
      throw new IllegalArgumentException("Unknown: %s".formatted(privateKey));
    }
    return new RSAPublicKeySpec(rsaPrivateKey.getModulus(), rsaPrivateKey.getPublicExponent());
  }

}
