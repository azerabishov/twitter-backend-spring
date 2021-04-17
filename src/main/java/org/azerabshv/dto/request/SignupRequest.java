package org.azerabshv.dto.request;

import java.util.Date;

public class SignupRequest {
    private String email;
    private String username;
    private String screen_name;
    private String phone_number;
    private Date birthday;
    private String password;



    public SignupRequest(String email, String username, String password, String phone_number, String screen_name, Date birthday) {
        this.email = email;
        this.username = username;
        this.screen_name = screen_name;
        this.phone_number = phone_number;
        this.birthday = birthday;
        this.password = password;
    }

    public String getScreen_name() {
        return screen_name;
    }

    public void setScreen_name(String screen_name) {
        this.screen_name = screen_name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}
