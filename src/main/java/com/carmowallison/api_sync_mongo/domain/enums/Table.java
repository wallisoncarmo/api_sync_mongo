package com.carmowallison.api_sync_mongo.domain.enums;

public enum Table {

	USER(1, "USER"), PRODUTO(2, "PRODUTO");

	private Integer cod;
	private String descricao;

	private Table(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public int getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}

	public static Table toEnum(Integer cod) {

		if (cod == null) {
			return null;
		}

		for (Table x : Table.values()) {
			if (cod.equals(x.getCod())) {
				return x;
			}

		}

		throw new IllegalArgumentException("Id Inválido: " + cod);

	}
}
