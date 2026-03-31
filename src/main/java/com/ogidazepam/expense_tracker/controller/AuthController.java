package com.ogidazepam.expense_tracker.controller;

import com.ogidazepam.expense_tracker.dto.person.PersonLoginDTO;
import com.ogidazepam.expense_tracker.dto.person.PersonRegisterDTO;
import com.ogidazepam.expense_tracker.model.Person;
import com.ogidazepam.expense_tracker.repository.PersonRepository;
import com.ogidazepam.expense_tracker.service.PersonService;
import com.ogidazepam.expense_tracker.service.jwt.JwtService;
import com.ogidazepam.expense_tracker.service.security.MyUserDetailsService;
import com.ogidazepam.expense_tracker.util.security.CustomUserDetails;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtService jwtService;
    private final PersonService personService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(JwtService jwtService, PersonService personService, AuthenticationManager authenticationManager) {
        this.jwtService = jwtService;
        this.personService = personService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public void registerUser(@RequestBody @Valid PersonRegisterDTO dto){
        personService.saveUser(dto);
    }

    @PostMapping("/login")
    public String authUser(@RequestBody @Valid PersonLoginDTO dto){

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getUsername(),
                        dto.getPassword()
                )
        );

        if (authentication.isAuthenticated()){
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            return jwtService.generateToken(userDetails.person());
        }else{
            throw new UsernameNotFoundException("Not today, bro");
        }
    }
}
