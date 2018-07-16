package com.carmowallison.api_sync_mongo.dto;

import com.carmowallison.api_sync_mongo.domain.User;
import com.carmowallison.api_sync_mongo.domain.sync.Log;
import com.carmowallison.api_sync_mongo.domain.sync.Sync;

import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SyncDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    private TableDTO add;
    private TableDTO update;
    private TableDTO delete;
    private User user;
    private List<LogDTO> logs = new ArrayList<>();

    public SyncDTO() {
    }


    public SyncDTO(Sync obj) {
        id = obj.getId();
    }

    public SyncDTO(Sync obj, List<Log> logs) {
        id = obj.getId();
        logs = logs;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public List<LogDTO> getLogs() {
        return logs;
    }

    public void setLogs(List<LogDTO> logs) {
        this.logs = logs;
    }
}
