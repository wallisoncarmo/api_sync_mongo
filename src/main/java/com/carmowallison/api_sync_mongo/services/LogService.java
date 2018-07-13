package com.carmowallison.api_sync_mongo.services;


import com.carmowallison.api_sync_mongo.domain.sync.Log;
import com.carmowallison.api_sync_mongo.domain.sync.Sync;
import com.carmowallison.api_sync_mongo.repositoties.LogRepository;
import com.carmowallison.api_sync_mongo.repositoties.SyncRepository;
import com.carmowallison.api_sync_mongo.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LogService {

    @Autowired
    LogRepository repo;

    public Log findById(String id) {
        Optional<Log> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
    }

    public Log insert(Log obj) {
        obj.setId(null);
        return repo.save(obj);
    }

    public Log update(Log obj) {
        Log newObj = findById(obj.getId());

        updateData(newObj, obj);

        return repo.save(newObj);
    }

    public void delete(String id) {
        findById(id);
        repo.deleteById(id);
    }

    public List<Log> findAll() {
        return repo.findAll();
    }

    public Page<Log> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = new PageRequest(page, linesPerPage, Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    private void updateData(Log newObj, Log obj) {
        if (!obj.getSync().equals("")) {
            newObj.setSync(obj.getSync());
        }
        if (obj.getAction() != null) {
            newObj.setAction(obj.getAction().getCod());
        }
        if (obj.getDescription() != null) {
            newObj.setDescription(obj.getDescription());
        }
        if (obj.getTable() != null) {
            newObj.setTable(obj.getTable().getCod());
        }
        if (obj.getUser() != null) {
            newObj.setUser(obj.getUser());
        }
    }


    public List<Log> findAllBySync(List<Sync> list){
        return  repo.findBySyncIn(list);
    }
}
