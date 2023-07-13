package com.teamsix.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teamsix.model.bean.PasswordVerificationToken;

public interface PasswordVerificationTokenRepository extends JpaRepository<PasswordVerificationToken, Integer> {
}
