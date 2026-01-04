package com.invoiceprocessing.server.service;

import com.invoiceprocessing.server.model.User;

public interface UserService {
    User register(User user);
    User login(String username, String password);
}
