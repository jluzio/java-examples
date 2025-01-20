package com.example.security_playground;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWK;
import java.io.IOException;
import java.io.StringWriter;
import lombok.experimental.UtilityClass;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;

@UtilityClass
public class KeyUtils {

  public static final String PUBLIC_KEY_TYPE = "PUBLIC KEY";
  public static final String PRIVATE_KEY_TYPE = "PRIVATE KEY";

  public static String getPem(JWK key) throws IOException, JOSEException {
    try (
        StringWriter outputWriter = new StringWriter();
        PemWriter pemWriter = new PemWriter(outputWriter);
    ) {
      var type = key.isPrivate() ? PRIVATE_KEY_TYPE : PUBLIC_KEY_TYPE;
      var pemObject = new PemObject(type, key.toRSAKey().toPublicKey().getEncoded());

      pemWriter.writeObject(pemObject);
      pemWriter.flush();
      pemWriter.close();
      return outputWriter.toString();
    }
  }

}
