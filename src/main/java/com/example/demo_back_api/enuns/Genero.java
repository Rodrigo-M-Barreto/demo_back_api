package com.example.demo_back_api.enuns;

import lombok.Getter;

public enum Genero {
	MASCULINO("Masculino", "M"),
    FEMININO("Feminino", "F"),
    NAO_BINARIO("Não-Binário", "NB"),
    OUTRO("Outro", "O");

	@Getter
    private final String value;
	@Getter
    private final String display;
    
    Genero(String value, String display) {
        this.value = value;
        this.display = display;
    }   
}