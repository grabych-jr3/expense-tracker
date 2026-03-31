package com.ogidazepam.expense_tracker.filter;

import com.ogidazepam.expense_tracker.model.Person;
import com.ogidazepam.expense_tracker.model.UserRole;
import com.ogidazepam.expense_tracker.service.jwt.JwtService;
import com.ogidazepam.expense_tracker.service.security.MyUserDetailsService;
import com.ogidazepam.expense_tracker.util.security.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Autowired
    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = header.substring(7);
        String username = jwtService.extractUsername(jwt);
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            long id = jwtService.getClaim(jwt, claims -> claims.get("id", Long.class));
            String role = jwtService.getClaim(jwt, claims -> claims.get("role", String.class));
            if(jwtService.isTokenValid(jwt)){
                Person person = new Person();

                person.setId(id);
                person.setUsername(username);
                person.setRole(UserRole.valueOf(role));

                CustomUserDetails userDetails = new CustomUserDetails(person);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
