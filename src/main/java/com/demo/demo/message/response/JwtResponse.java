package com.demo.demo.message.response;


import java.util.Collection;
import java.util.List;


public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String userName;
    private Collection authorities;
    private List<String> roles;

    public JwtResponse(String accessToken, String userName, Collection authorities, List<String> roles) {
        this.token = accessToken;
        this.userName = userName;
        this.authorities = authorities;
        this.roles = roles;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getAccessToken() {
        return token;
    }
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Collection getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection authorities) {
        this.authorities = authorities;
    }
}
