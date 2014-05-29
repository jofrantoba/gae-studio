/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.service.auth;

import com.arcbees.oauth.api.domain.Token;
import com.arcbees.oauth.api.domain.User;

public interface AuthService {
    Long register(String email, String password, String firstName, String lastName);

    void requestResetToken(String email);

    User checkLogin();

    Token login(String email, String password);

    void logout();
}
