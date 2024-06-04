package co.prime.jpa.service;

import co.prime.jpa.dto.LoginDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final JwtEncoder jwtEncoder;

    @Override
    public String login(LoginDTO loginDTO) {

        return null;
    }
}
