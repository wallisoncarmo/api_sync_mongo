package com.carmowallison.api_sync_mongo.dto;

import com.carmowallison.api_sync_mongo.domain.User;
import com.carmowallison.api_sync_mongo.domain.enums.Action;
import com.carmowallison.api_sync_mongo.domain.enums.Table;
import com.carmowallison.api_sync_mongo.domain.sync.Log;
import com.carmowallison.api_sync_mongo.domain.sync.Sync;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Document
public class LogDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String table;
    private String action;
    private String description;
    private String sync;
    private UserDTO user;
    private Date current;


    public LogDTO() {
    }

    public LogDTO(Log obj) {
        this.id = obj.getId();
        this.table = obj.getDescription();
        this.action = Action.toEnum(obj.getAction()).getTitulo();
        this.description = obj.getDescription();
        this.sync = obj.getSync().getId();
        this.user = obj.getUser();
        this.current = new Date();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSync() {
        return sync;
    }

    public void setSync(String sync) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogDTO log = (LogDTO) o;
        return Objects.equals(id, log.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
