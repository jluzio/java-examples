# Specs
- JSON Web Key (JWK): https://datatracker.ietf.org/doc/html/rfc7517
- PKIX: https://datatracker.ietf.org/doc/html/rfc5280

# libs
## nimbus-jose-jwt
https://connect2id.com/products/nimbus-jose-jwt
https://connect2id.com/products/nimbus-jose-jwt/examples

Refs:
- https://connect2id.com/products/nimbus-jose-jwt/examples/jws-with-rsa-signature

## generate keystore with key-pair
~~~bash
# pkcs12
keytool -genkeypair -storepass changeit -alias main_key -keyalg RSA -keysize 2048 -dname CN=CA -keystore ./keystore.pkcs12
# jks (storepass and keypass)
keytool -genkeypair -storepass changeit -keypass keypwd -alias main_key -keyalg RSA -keysize 2048 -dname CN=CA -keystore ./keystore.jks -storetype jks
~~~
