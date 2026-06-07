package com.java.user.springsecurity;

import com.java.user.springsecurity.model.UserAuthority;
import com.java.user.springsecurity.model.UserEntity;
import com.java.user.springsecurity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
@RequiredArgsConstructor
public class SpringsecurityApplication implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public static void main(String[] args) {
        SpringApplication.run(SpringsecurityApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        UserAuthority userAuthority = UserAuthority.builder()
                .authority("ROLE_ADMIN")
                .build();

        UserEntity admin = UserEntity
                .builder()
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                .authorities(List.of(userAuthority))
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .isEnabled(true)
                .build();

        userAuthority.setUser(admin);

        Optional<UserEntity> userEntityOptional = userRepository.findByUsername(admin.getUsername());
        if(userEntityOptional.isEmpty()) {
            userRepository.save(admin);
        }
        UserAuthority userAuthority1 = UserAuthority.builder()
                .authority("ROLE_ADMIN")
                .build();

        UserEntity admin1 = UserEntity
                .builder()
                .username("user")
                .password(passwordEncoder.encode("user"))
                .authorities(List.of(userAuthority1))
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .isEnabled(true)
                .build();

        userAuthority.setUser(admin1);


        Optional<UserEntity> userEntityOptional1 = userRepository.findByUsername(admin1.getUsername());
        if(userEntityOptional1.isEmpty()) {
            userRepository.save(admin1);
        }


    }
}
