package co.prime.jpa.controller;

import co.prime.jpa.dto.AuthDTO;
import co.prime.jpa.dto.LoginDTO;
import co.prime.jpa.dto.RefreshTokenDTO;
import co.prime.jpa.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    @PostMapping("/login")
    public AuthDTO login(@RequestBody LoginDTO logInDto){
      return  authService.login(logInDto);

    }

    @PostMapping("/refresh")
    public AuthDTO refresh(@RequestBody RefreshTokenDTO refreshToken){
        return authService.refresh(refreshToken);
    }

}
