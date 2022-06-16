package HelperClasses;

import org.jasypt.util.password.BasicPasswordEncryptor;

public class Encryptor {
    private final BasicPasswordEncryptor spe;

    public Encryptor() {
        spe = new BasicPasswordEncryptor();
    }

    public boolean checkPassword(String originalPassword, String hashPassword) {
        return spe.checkPassword(originalPassword, hashPassword);
    }

    public String encryptPassword(String password) {
        return spe.encryptPassword(password);
    }
}
