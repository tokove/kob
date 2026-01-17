package com.kob.backend.service.user.account;

import com.kob.backend.service.impl.user.LoginServiceImpl;

import java.util.Map;

public interface LoginService {
    public Map<String, String> getToken(String username, String password);
}
