package com.store.security;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {

    @Override
    public String encryptData(String data) {
        return DigestUtils.md5Hex(data);
    }
}
