package com.carmowallison.api_sync_mongo.domain.sync;

import com.carmowallison.api_sync_mongo.domain.User;
import com.carmowallison.api_sync_mongo.dto.UserDTO;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Document
public class Log implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    private Integer table;
    private Integer action;
    private String description;
    @DBRef
    private Sync sync;
    private UserDTO user;
    private Date current;
    private Object data;


    public Log() {
    }

    public Log(String id, Integer table, Integer action, String description, Sync sync, UserDTO user, Object data) {
        this.id = id;
        this.table = table;
        this.action = action;
        this.description = description;
        this.sync = sync;
        this.user = user;
        this.current = new Date();
        this.data = data;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getTable() {
        return table;
    }

    public void setTable(Integer table) {
        this.table = table;
    }

    public Integer getAction() {
        return action;
    }

    public void setAction(Integer action) {
        this.action = action;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Sync getSync() {
        return sync;
    }

    public void setSync(Sync sync) {
        this.sync = sync;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public Date getCurrent() {
        return current;
    }

    public void setCurrent(Date current) {
        this.current = current;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Log log = (Log) o;
        return Objects.equals(id, log.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
