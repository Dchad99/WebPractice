package com.store.services;

import java.util.UUID;

public interface SecurityService {

    default String getRandomUUID(){
        return UUID.randomUUID().toString();
    }

    String encryptData(String data);
}
