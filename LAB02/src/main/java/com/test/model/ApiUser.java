package com.test.model;

public class ApiUser {
    public String name;
    public String email;
    public ApiCompany company;

    public static class ApiCompany {
        public String name;
    }
}