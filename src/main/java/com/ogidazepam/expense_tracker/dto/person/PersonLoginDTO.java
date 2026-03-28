package com.ogidazepam.expense_tracker.dto.person;

import jakarta.validation.constraints.NotBlank;

public class PersonLoginDTO {
    @NotBlank(message = "Username can't be empty")
    private String username;

    @NotBlank(message = "Password can't be empty")
    private String password;

    public PersonLoginDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public PersonLoginDTO() {
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

    @Override
    public String toString() {
        return "PersonLoginDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
