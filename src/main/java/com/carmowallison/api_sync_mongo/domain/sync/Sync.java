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
    private Integer sync;
    private TableDTO add;
    private TableDTO update;
    private TableDTO delete;
    private User user;
    private Date current;


    public Sync() {
    }

    public Sync(String id, Integer sync, TableDTO add, TableDTO update, TableDTO delete, User user) {
        this.id = id;
        this.sync = sync;
        this.add = add;
        this.update = update;
        this.delete = delete;
        this.user = user;
        this.current = new Date();
    }

    public Sync fromDTO(SyncDTO objDTO) {
        return new Sync(
                this.id = objDTO.getId(),
                this.sync = objDTO.getSync(),
                this.add = objDTO.getAdd(),
                this.update = objDTO.getUpdate(),
                this.delete = objDTO.getDelete(),
                this.user = objDTO.getUser()
        );
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getSync() {
        return sync;
    }

    public void setSync(Integer sync) {
        this.sync = sync;
    }

    public TableDTO getAdd() {
        return add;
    }

    public void setAdd(TableDTO add) {
        this.add = add;
    }

    public TableDTO getUpdate() {
        return update;
    }

    public void setUpdate(TableDTO update) {
        this.update = update;
    }

    public TableDTO getDelete() {
        return delete;
    }

    public void setDelete(TableDTO delete) {
        this.delete = delete;
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
