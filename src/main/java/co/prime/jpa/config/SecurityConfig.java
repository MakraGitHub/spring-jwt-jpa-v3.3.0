package co.prime.jpa.config;

import co.prime.jpa.utils.KeyUtil;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final KeyUtil keyUtil;
    private final UserDetailsServiceImpl userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter(){
        System.out.println("JwtAuthenticationConverter");
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthoritiesClaimName("scope");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }
    @Bean
    @Qualifier("jwtRefreshTokenAuthProvider")
    public JwtAuthenticationProvider jwtRefreshTokenAuthProvider() {
        JwtAuthenticationProvider provider = new JwtAuthenticationProvider(refreshTokenJwtDecoder());
        provider.setJwtAuthenticationConverter(jwtAuthenticationConverter());
        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http.csrf(token-> token.disable());
        http.authorizeHttpRequests(auth->{
            auth.requestMatchers("/anonymous/**", "/api/v1/auth/**").permitAll();
            auth.requestMatchers(HttpMethod.GET, "/api/v1/books/**").hasAuthority("SCOPE_book:read");
            auth.requestMatchers(HttpMethod.POST, "/api/v1/books/**").hasAuthority("SCOPE_book:write");
            auth.requestMatchers(HttpMethod.PUT, "/api/v1/books/**").hasAuthority("SCOPE_book:update");
            auth.requestMatchers(HttpMethod.DELETE, "/api/v1/books/**").hasAuthority("SCOPE_book:delete");
            auth.anyRequest().authenticated();
        });
        //Configurer oauth2 add dependency , so it required jwtDecoder.(Create bean decoder)
        http.oauth2ResourceServer(oauth2->
                oauth2.jwt((jwt)-> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
        );
        return http.build();
    }
    @Bean
    @Primary
    public JwtDecoder accessTokenJwtDecoder(){
        return  NimbusJwtDecoder
                .withPublicKey(keyUtil.getAccessTokenPublicKey()).build();
    }
    @Bean
    @Qualifier("refreshTokenJwtDecoder")
    public JwtDecoder refreshTokenJwtDecoder(){
        return NimbusJwtDecoder
                .withPublicKey(keyUtil.getRefreshTokenPublicKey()).build();
    }

    @Bean
    @Primary
    public JwtEncoder accessTokenJwtEncoder(){

        JWK jwk = new RSAKey
                .Builder(keyUtil.getAccessTokenPublicKey())
                .privateKey(keyUtil.getAccessTokenPrivateKey())
                .build();

        //Creat jwk object
        JWKSet jwkSet = new JWKSet(jwk);
        //Create jwtSet.
        JWKSource<SecurityContext> jwkSource =
                ((jwkSelector, context) -> jwkSelector.select(jwkSet));
        //Create jwkSource.
        return  new NimbusJwtEncoder(jwkSource);
    }

    @Bean
    @Qualifier("refreshTokenJwtEncoder")
    public JwtEncoder refreshTokenJwtEncoder(){

        JWK jwk = new RSAKey
                .Builder(keyUtil.getRefreshTokenPublicKey())
                .privateKey(keyUtil.getRefreshTokenPrivateKey())
                .build();
        JWKSet jwkSet = new JWKSet(jwk);

        JWKSource<SecurityContext> jwkSource =
                ((jwkSelector, context) -> jwkSelector.select(jwkSet));
        return  new NimbusJwtEncoder(jwkSource);
    }


  /*  @Bean
    public JWKSource<SecurityContext> jwkSource(){
        JWKSet jwkSet = new JWKSet(rsaKey);
        return (jwkSelector, context) -> jwkSelector.select(jwkSet);
    }
    //User for create jwt token.
    @Bean
    public JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource){
        return new NimbusJwtEncoder(jwkSource);
    }
    //Use for Verify jwt token.
    @Bean
    public JwtDecoder jwtDecoder(){
        try {
            return NimbusJwtDecoder
                    .withPublicKey(rsaKey.toRSAPublicKey())
                    .build();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }*/

}
