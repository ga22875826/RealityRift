package com.teamsix.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teamsix.model.bean.GoogleAuthenticatorSecret;

public interface GoogleAuthenticatorSecretRepository extends JpaRepository<GoogleAuthenticatorSecret, Integer> {

}
