package com.example.demo_back_api.dto;

public record LoginResponse(String accessToken, Long expiresIn) { }