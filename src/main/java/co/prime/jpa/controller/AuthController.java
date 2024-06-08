package co.prime.jpa.controller;

import co.prime.jpa.dto.LoginDTO;
import co.prime.jpa.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
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
    public String login(@RequestBody LoginDTO logInDto){

      String token =  authService.login(logInDto);
      return  token;

    }

}
