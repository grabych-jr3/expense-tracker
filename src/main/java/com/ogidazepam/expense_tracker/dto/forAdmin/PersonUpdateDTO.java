package com.ogidazepam.expense_tracker.dto.forAdmin;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;

public class PersonUpdateDTO {
    @NotBlank(message = "Username can't be empty")
    private String username;

    @NotBlank(message = "Password can't be empty")
    private String password;

    public PersonUpdateDTO() {
    }

    public PersonUpdateDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
