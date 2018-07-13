package com.carmowallison.api_sync_mongo.domain.enums;

public enum Action {

    ADD(1, "ADD", "Foi adicionado um "), UPDATE(2, "UPDATE", "Foi atualizado um "), DELETE(3, "DELETE", "Foi deleteado um ");

    private int cod;
    private String titulo;
    private String descricao;

    private Action(int cod, String titulo, String descricao) {
        this.cod = cod;
        this.titulo = titulo;
        this.descricao = descricao;
    }

    public int getCod() {
        return cod;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getTitulo() {
        return titulo;
    }

    public static Action toEnum(Integer cod) {

        if (cod == null) {
            return null;
        }

        for (Action x : Action.values()) {
            if (cod.equals(x.getCod())) {
                return x;
            }

        }

        throw new IllegalArgumentException("Id Inválido: " + cod);

    }
}
