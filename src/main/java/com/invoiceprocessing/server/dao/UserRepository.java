package com.invoiceprocessing.server.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.invoiceprocessing.server.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
