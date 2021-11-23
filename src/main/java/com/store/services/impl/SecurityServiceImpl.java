package com.store.services.impl;

import com.store.services.SecurityService;
import org.apache.commons.codec.digest.DigestUtils;

public class SecurityServiceImpl implements SecurityService {

    @Override
    public String encryptData(String data) {
        return DigestUtils.md5Hex(data);
    }
}
