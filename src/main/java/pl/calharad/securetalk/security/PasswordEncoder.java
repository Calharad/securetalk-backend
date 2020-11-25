package pl.calharad.securetalk.security;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.enterprise.context.ApplicationScoped;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

@ApplicationScoped
public class PasswordEncoder {

    @ConfigProperty(name = "pl.calharad.security.pbkdf2.iterations")
    Integer iterations;

    @ConfigProperty(name = "pl.calharad.security.pbkdf2.algorithm")
    String algorithm;

    @ConfigProperty(name = "pl.calharad.security.pbkdf2.derivedKeyLength")
    Integer derivedKeyLength;

    @ConfigProperty(name = "pl.calharad.security.pbkdf2.randomAlgorithm")
    String randomAlgorithm;

    public boolean equals(String attemptedPassword, byte[] encryptedPassword, byte[] salt) {

        byte[] encryptedAttemptedPassword = encode(attemptedPassword, salt);
        return Arrays.equals(encryptedPassword, encryptedAttemptedPassword);
    }

    public byte[] encode(String password, byte[] salt) {

        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, derivedKeyLength);

        try {
            SecretKeyFactory f = SecretKeyFactory.getInstance(algorithm);
            return f.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Incorrect SecureRandom algorithm");
        } catch (InvalidKeySpecException e) {
            throw new IllegalStateException("Invalid key spec");
        }
    }

    public byte[] generateSalt() {
        try {
            SecureRandom random = SecureRandom.getInstance(randomAlgorithm);
            byte[] salt = new byte[8];
            random.nextBytes(salt);

            return salt;
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Incorrect SecureRandom algorithm");
        }
    }
}
