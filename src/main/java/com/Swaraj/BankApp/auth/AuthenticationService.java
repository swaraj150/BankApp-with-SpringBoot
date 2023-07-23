package com.Swaraj.BankApp.auth;

import com.Swaraj.BankApp.config.JwtService;
import com.Swaraj.BankApp.user.Role;
import com.Swaraj.BankApp.user.User;
import com.Swaraj.BankApp.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(RegisterRequest request){
        System.out.println("register");
        Random random=new Random();
        var user= User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .account(random.nextInt(200000-100000+1)+100000)
                .pin(passwordEncoder.encode(request.getPin()))
                .balance(request.getBalance())
                .role(Role.USER)
                .build();
        userRepository.save(user);
        var jwtToken=jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .msg("Registration Successful")
                .token(jwtToken)
                .build();
    }
    public AuthenticationResponse authenticate(AuthenticationRequest request){
        try {
            UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(request.getAccount(),request.getPin());
            authenticationManager.authenticate(authenticationToken);
            System.out.println("hell no"+" "+request.getAccount()+request.getPin());
        }
        catch (Exception b){
            System.out.println(b.getLocalizedMessage());
            return AuthenticationResponse.builder().msg("Invalid Credentials").build();
        }
        System.out.println("hell no");
        var user=userRepository.findByAccount(request.getAccount()).orElseThrow();
        var jwtToken=jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .msg("Login Successful")
                .build();
    }

}
