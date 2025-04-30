package todoapp.project.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import todoapp.project.security.JwtService;
import todoapp.project.enums.Role;
import todoapp.project.models.dtos.AuthenticationRequest;
import todoapp.project.models.dtos.AuthenticationResponse;
import todoapp.project.models.dtos.RegisterRequest;
import todoapp.project.models.entities.User;
import todoapp.project.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final JwtService service;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    // for when user first signs up
    public AuthenticationResponse register(RegisterRequest request){
        var user = User.builder()
                .firstName(request.getFirstname())
                .lastName(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        repository.save(user);
        var jwtToken = service.generateToken(user);

        return AuthenticationResponse.builder()
                .message(String.format("User with name %s %s created successfully", request.getFirstname(), request.getLastname()))
                .token(jwtToken)
                .build();
    }

    // for when user tries to sign in to check if the user is authenticated first
    public AuthenticationResponse authenticate(AuthenticationRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = service.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

}
