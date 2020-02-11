package com.railway.booking.service;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.Assert.assertEquals;

public class PasswordEncryptorTest {
    private PasswordEncoder passwordEncryptor = new BCryptPasswordEncoder();

    @Test
    public void encryptSimplePass() {
        String actualHash = passwordEncryptor.encode("PASSWORD");
        String expectedHash = passwordEncryptor.encode("PASSWORD");
        assertEquals(expectedHash, actualHash);
    }

    @Test
    public void encryptSimplePassWithDigits() {
        String actualHash = passwordEncryptor.encode("PASSWORDmulti298");
        String expectedHash = passwordEncryptor.encode("PASSWORDmulti298");
        assertEquals(expectedHash, actualHash);

    }

    @Test
    public void encryptLongPassWithDigits() {
        String actualHash = passwordEncryptor.encode("PASSWORDmulti298sdfjkehkjUsdlkjhkje9834SD");
        String expectedHash = passwordEncryptor.encode("PASSWORDmulti298sdfjkehkjUsdlkjhkje9834SD");
        assertEquals(expectedHash, actualHash);
    }

    @Test
    public void encryptComplexPass() {
        String actualHash = passwordEncryptor.encode("4738hsJfl34Sksdl3_@349");
        String expectedHash = passwordEncryptor.encode("4738hsJfl34Sksdl3_@349");
        assertEquals(expectedHash, actualHash);
    }
}