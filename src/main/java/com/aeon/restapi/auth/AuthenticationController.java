package com.aeon.restapi.auth;

import com.aeon.restapi.dto.TemplateData;
import com.aeon.restapi.user.UserRepository;
import com.aeon.restapi.utils.FormValidationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    private final UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        TemplateData templateData = new TemplateData();

        // registration validation
        ArrayList<String> registrationErrorMessages = FormValidationUtils.checkRegistration(registerRequest);
        if (!registrationErrorMessages.isEmpty()) {
            return templateData.failTemplate(registrationErrorMessages);
        }

        if (userRepository.findUserByUsername(registerRequest.getUsername()).isPresent()) {
            return templateData.failTemplate("username is exist");
        }

        return templateData.successTemplate(service.register(registerRequest), "registration success");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest) {
        TemplateData templateData = new TemplateData();

        // login validation
        ArrayList<String> registrationErrorMessages = FormValidationUtils.checkLogin(authenticationRequest);
        if (!registrationErrorMessages.isEmpty()) {
            return templateData.failTemplate(registrationErrorMessages);
        }

        if (!userRepository.findUserByUsername(authenticationRequest.getUsername()).isPresent()) {
            return templateData.failTemplate("username not found");
        }

        return templateData.successTemplate(service.login(authenticationRequest), "login success");
    }
}
