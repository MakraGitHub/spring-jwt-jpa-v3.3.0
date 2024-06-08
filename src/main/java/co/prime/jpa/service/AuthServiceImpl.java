package co.prime.jpa.service;

import co.prime.jpa.dto.LoginDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final JwtEncoder jwtEncoder;
    private final DaoAuthenticationProvider daoAuthenticationProvider;

    @Override
    public String login(LoginDTO loginDTO) {
        System.out.println("Start login");
        System.out.println(loginDTO);
        Authentication auth = new UsernamePasswordAuthenticationToken(
                loginDTO.email(), loginDTO.password());
      auth =  daoAuthenticationProvider.authenticate(auth);
        System.out.println(auth.getPrincipal());

        return "token";
    }
}
