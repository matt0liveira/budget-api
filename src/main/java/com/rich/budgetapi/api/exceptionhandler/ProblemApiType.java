package com.rich.budgetapi.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemApiType {
    RESOURCE_NOT_FOUND("Recurso não encontrado", "/recurso-nao-encontrado"),
    DOMAIN("Violação de regra de negócio", "/erro-dominio"),
    METHOD_ARGUMENT_TYPE_MISMATCH("Tipo de parâmetro imcompatível", "/parametro-imcompativel"),
    INVALID_DATA("Dados inválidos", "/dados-invalidos");

    private String title;
    private String uri;

    private ProblemApiType(String title, String path) {
        this.title = title;
        this.uri = "https://budget.com/kb" + path;
    }
}