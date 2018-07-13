package com.carmowallison.api_sync_mongo.services;

import org.springframework.security.core.context.SecurityContextHolder;

import com.carmowallison.api_sync_mongo.security.UserSS;

public class UserSService {

	public static UserSS authenticated() {
		try {
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			return null;
		}
	}

}
