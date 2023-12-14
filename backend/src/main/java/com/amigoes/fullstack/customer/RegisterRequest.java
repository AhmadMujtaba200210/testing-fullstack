package com.amigoes.fullstack.customer;

public record RegisterRequest (String name, String email, String password, int age, Gender gender){ }
