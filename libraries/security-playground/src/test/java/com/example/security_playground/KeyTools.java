package com.example.security_playground;

import static java.util.Optional.ofNullable;

import java.io.IOException;
import java.io.StringWriter;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.util.Collections;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;
import org.springframework.stereotype.Component;

@Component
public class KeyTools {

  public static final String PUBLIC_KEY_TYPE = "PUBLIC KEY";
  public static final String PRIVATE_KEY_TYPE = "PRIVATE KEY";

  public KeyStore keyStore(KeyFetchParams params) throws GeneralSecurityException, IOException {
    var storeType = ofNullable(params.storeType())
        .orElseGet(KeyStore::getDefaultType);
    var keyStore = KeyStore.getInstance(storeType);
    try (var inputStream = params.storeInputStream().get()) {
      keyStore.load(inputStream, params.keyPass());
      return keyStore;
    }
  }

  public Key key(KeyFetchParams params, KeyStore keyStore) throws GeneralSecurityException {
    var alias = params.alias();
    if (alias == null) {
      var aliases = Collections.list(keyStore.aliases());
      if (aliases.size() != 1) {
        throw new IllegalArgumentException("Unable to use default key :: aliases=%s".formatted(aliases));
      }
      alias = aliases.getFirst();
    }
    var keyPass = ofNullable(params.keyPass())
        .orElseGet(params::storePass);
    return keyStore.getKey(alias, keyPass);
  }

  public Certificate certificate(KeyFetchParams params, KeyStore keyStore) throws GeneralSecurityException {
    var alias = params.alias();
    if (alias == null) {
      var aliases = Collections.list(keyStore.aliases());
      if (aliases.size() != 1) {
        throw new IllegalArgumentException("Unable to use default key :: aliases=%s".formatted(aliases));
      }
      alias = aliases.getFirst();
    }
    return keyStore.getCertificate(alias);
  }

  public String getPem(Key key) throws IOException {
    try (
        StringWriter outputWriter = new StringWriter();
        PemWriter pemWriter = new PemWriter(outputWriter);
    ) {
      var type = key instanceof PrivateKey
          ? PRIVATE_KEY_TYPE : PUBLIC_KEY_TYPE;
      var pemObject = new PemObject(type, key.getEncoded());

      pemWriter.writeObject(pemObject);
      pemWriter.flush();
      pemWriter.close();
      return outputWriter.toString();
    }
  }

}
