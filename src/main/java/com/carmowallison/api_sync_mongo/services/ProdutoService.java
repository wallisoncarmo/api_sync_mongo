package com.carmowallison.api_sync_mongo.services;


import com.carmowallison.api_sync_mongo.domain.Produto;
import com.carmowallison.api_sync_mongo.domain.sync.Sync;
import com.carmowallison.api_sync_mongo.repositoties.ProdutoRepository;
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
public class ProdutoService {

    @Autowired
    ProdutoRepository repo;

    public Produto findById(String id) {
        Optional<Produto> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
    }

    public List<Produto> findAllBySync(List<Sync> list){
        return  repo.findBySyncIn(list);
    }

    public Produto insert(Produto obj) {
        obj.setId(null);
        return repo.save(obj);
    }

    public Produto update(Produto obj) {
        Produto newObj = findById(obj.getId());

        updateData(newObj, obj);

        return repo.save(newObj);
    }

    public void delete(String id) {
        findById(id);
        repo.deleteById(id);
    }

    public List<Produto> findAll() {
        return repo.findAll();
    }

    public Page<Produto> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = new PageRequest(page, linesPerPage, Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    private void updateData(Produto newObj, Produto obj) {
        if (!obj.getSync().equals("")) {
            newObj.setSync(obj.getSync());
        }
        if (!obj.getName().equals("")) {
            newObj.setName(obj.getName());
        }

    }
}
