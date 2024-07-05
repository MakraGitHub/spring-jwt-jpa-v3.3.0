package co.prime.jpa.service;

import co.prime.jpa.dto.AuthDTO;
import co.prime.jpa.dto.LoginDTO;
import co.prime.jpa.dto.RefreshTokenDTO;

public interface AuthService {
    AuthDTO login(LoginDTO loginDTO);

    AuthDTO refresh(RefreshTokenDTO refreshTokenDTO);
}
