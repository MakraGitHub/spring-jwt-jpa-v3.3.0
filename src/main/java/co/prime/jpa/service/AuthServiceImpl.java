package co.prime.jpa.service;

import co.prime.jpa.dto.AuthDTO;
import co.prime.jpa.dto.LoginDTO;
import co.prime.jpa.dto.RefreshTokenDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final JwtEncoder accessTokenjwtEncoder;
    private JwtEncoder refreshTokenJwtEncoder;
    @Autowired
    @Qualifier("refreshTokenJwtEncoder")
    public void setRefreshTokenJwtEncoder(JwtEncoder refreshTokenJwtEncoder) {
        this.refreshTokenJwtEncoder = refreshTokenJwtEncoder;
    }

    private final DaoAuthenticationProvider daoAuthenticationProvider;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    @Override
    public AuthDTO login(LoginDTO loginDTO) {
        Authentication auth = new UsernamePasswordAuthenticationToken(
                loginDTO.email(), loginDTO.password());
      auth =  daoAuthenticationProvider.authenticate(auth);

        Instant now = Instant.now();

        String scope = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(authority->!authority.contains("ROLE_"))
                .collect(Collectors.joining(" "));

        //When expired jwt checked like this :
        JwtClaimsSet accessTokenjwtClaimsSet = JwtClaimsSet.builder()
                .issuedAt(now)
                .issuer("client")
                .expiresAt(now.plus(1, ChronoUnit.SECONDS))
                .subject(auth.getName())
                .claim("scope", scope)
                .build();

        JwtClaimsSet refreshTokenjwtClaimsSet = JwtClaimsSet.builder()
                .issuedAt(now)
                .issuer("client")
                .expiresAt(now.plus(1, ChronoUnit.DAYS))
                .subject(auth.getName())
                .claim("scope", scope)
                .build();

        String accessToken = accessTokenjwtEncoder.encode(
                JwtEncoderParameters.from(accessTokenjwtClaimsSet)
        ).getTokenValue();

        String refreshToken = refreshTokenJwtEncoder.encode(
                JwtEncoderParameters.from(refreshTokenjwtClaimsSet)
        ).getTokenValue();

        return new AuthDTO(accessToken,refreshToken);
    }

    @Override
    public AuthDTO refresh(RefreshTokenDTO refreshTokenDTO) {
        Instant now = Instant.now();
        BearerTokenAuthenticationToken token = new BearerTokenAuthenticationToken(refreshTokenDTO.refreshToken());

        Authentication auth = jwtAuthenticationProvider.authenticate(token);

        System.out.println(auth.getName());
        auth.getAuthorities().forEach(System.out::println);

        String scope = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(authority->!authority.contains("ROLE_"))
                .collect(Collectors.joining(" "));

        JwtClaimsSet accessTokenjwtClaimsSet = JwtClaimsSet.builder()
                .issuedAt(now)
                .issuer("client")
                .expiresAt(now.plus(1,ChronoUnit.SECONDS))
                .subject(auth.getName())
               .claim("scope", scope)
                .build();
        String accessToken = accessTokenjwtEncoder.encode(
               JwtEncoderParameters.from(accessTokenjwtClaimsSet)
        ).getTokenValue();

        return new AuthDTO(accessToken,refreshTokenDTO.refreshToken());
    }
}
