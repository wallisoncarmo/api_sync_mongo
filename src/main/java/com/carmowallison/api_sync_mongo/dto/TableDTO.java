package com.carmowallison.api_sync_mongo.dto;

import com.carmowallison.api_sync_mongo.domain.Produto;
import com.carmowallison.api_sync_mongo.domain.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TableDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<User> users = new ArrayList<>();
	private List<Produto> produtos = new ArrayList<>();

	public TableDTO() {
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}
}
