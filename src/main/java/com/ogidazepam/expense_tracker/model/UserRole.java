package com.ogidazepam.expense_tracker.model;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum UserRole {
    USER,
    ADMIN;

    public SimpleGrantedAuthority toAuthority(){
        return new SimpleGrantedAuthority("ROLE_" + this.name());
    }
}
