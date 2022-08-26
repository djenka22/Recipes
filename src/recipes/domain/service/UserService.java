package recipes.domain.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import recipes.domain.entity.User;
import recipes.domain.models.RegisterDto;
import recipes.domain.repostory.UserRepo;


import java.util.List;

@Service
@Slf4j
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(username);
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                List.of()
                );
    }

    public HttpStatus register(RegisterDto registerDto) {
        User user = userRepo.findByEmail(registerDto.getEmail());
        if (user != null) {
            log.error("user already exist");
            return HttpStatus.BAD_REQUEST;
        }
        User userToRegister = new User.Builder()
                .createEmail(registerDto.getEmail())
                .createPassword(
                        bCryptPasswordEncoder.encode(registerDto.getPassword())
                )
                .build();

        userRepo.save(userToRegister);
        log.info("user {} has been created", userToRegister.getEmail());
        return HttpStatus.OK;
    }

    public User findByEmail(String name) {
        return userRepo.findByEmail(name);
    }
}