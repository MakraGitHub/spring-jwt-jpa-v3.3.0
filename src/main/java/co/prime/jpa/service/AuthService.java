package co.prime.jpa.service;

import co.prime.jpa.dto.LoginDTO;

public interface AuthService {
    String login(LoginDTO loginDTO);
}
