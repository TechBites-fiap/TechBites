package br.com.techchallenge.techbites.services;

import br.com.techchallenge.techbites.dtos.LoginRequestDTO;
import br.com.techchallenge.techbites.entities.User;
import br.com.techchallenge.techbites.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean validateLogin(LoginRequestDTO loginRequest) {

        var userOptional = userRepository.findByEmail(loginRequest.email());

        if (userOptional.isEmpty()) {
            return false;
        }

        User user = userOptional.get();

        return passwordEncoder.matches(loginRequest.password(), user.getPassword());
    }
}
