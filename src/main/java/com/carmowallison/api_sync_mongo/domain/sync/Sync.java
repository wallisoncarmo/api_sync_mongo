package com.carmowallison.api_sync_mongo.domain.sync;

import com.carmowallison.api_sync_mongo.domain.User;
import com.carmowallison.api_sync_mongo.dto.SyncDTO;
import com.carmowallison.api_sync_mongo.dto.TableDTO;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Document
public class Sync implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    private User user;
    private Date current;


    public Sync() {
    }

    public Sync(String id, User user) {
        this.id = id;
        this.user = user;
        this.current = new Date();
    }

    public Sync fromDTO(SyncDTO objDTO) {
        return new Sync(
                this.id = objDTO.getId(),
                this.user = objDTO.getUser()
        );
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
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
        Sync sync = (Sync) o;
        return Objects.equals(id, sync.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
