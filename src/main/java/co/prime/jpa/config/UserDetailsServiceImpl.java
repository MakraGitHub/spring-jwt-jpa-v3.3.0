package co.prime.jpa.config;

import co.prime.jpa.entity.Authority;
import co.prime.jpa.entity.Role;
import co.prime.jpa.entity.User;
import co.prime.jpa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository repository;
    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        //Error JPA
        User user = repository.findByEmail(email).orElseThrow(()->
                new UsernameNotFoundException("User with this email is not found"));



        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for(Role role: user.getRoles()){
            authorities.add(new SimpleGrantedAuthority(role.getName()));
            for(Authority authority : role.getAuthorities()){
                authorities.add(new SimpleGrantedAuthority(authority.getName()));
            }
        }

        org.springframework.security.core.userdetails.User securityUser = new
                org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                authorities);

        return securityUser;

    }
}
