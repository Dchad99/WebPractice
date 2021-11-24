package com.store.security;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class SecurityServiceImplTest {

    @Test
    void testHashInputData(){
        String data = "Test";
        assertNotNull(DigestUtils.md5Hex(data));
    }

}