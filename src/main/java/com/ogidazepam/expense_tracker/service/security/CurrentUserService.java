package com.ogidazepam.expense_tracker.service.security;

import com.ogidazepam.expense_tracker.model.Person;
import com.ogidazepam.expense_tracker.util.security.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CurrentUserService {

    public Person getCurrentPerson(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication != null && authentication.getPrincipal() instanceof CustomUserDetails(Person person)){
            return person;
        }
        throw new UsernameNotFoundException("User not found");
    }
}
