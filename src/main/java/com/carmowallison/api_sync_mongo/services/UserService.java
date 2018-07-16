package com.carmowallison.api_sync_mongo.services;

import java.util.List;
import java.util.Optional;

import com.carmowallison.api_sync_mongo.domain.enums.Action;
import com.carmowallison.api_sync_mongo.domain.enums.Table;
import com.carmowallison.api_sync_mongo.domain.sync.Log;
import com.carmowallison.api_sync_mongo.domain.sync.Sync;
import com.carmowallison.api_sync_mongo.dto.UserDTO;
import com.carmowallison.api_sync_mongo.dto.UserNewDTO;
import com.carmowallison.api_sync_mongo.repositoties.UserRepository;
import com.carmowallison.api_sync_mongo.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.carmowallison.api_sync_mongo.domain.User;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private BCryptPasswordEncoder bc;


    public List<User> findAll() {
        return repository.findAll();
    }

    public List<User> findAllBySync(List<Sync> list) {
        return repository.findBySyncIn(list);
    }

    public User findById(String id) {
        Optional<User> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
    }

    public User insert(User obj) {
        obj.setAction(Action.ADD.getCod());
        return repository.insert(obj);
    }

    public User delete(String id) {
        User obj = findById(id);
        obj.setAction(Action.DELETE.getCod());

        repository.deleteById(id);
        return obj;
    }

    public User update(User obj) {
        User newObj = findById(obj.getId());
        updateData(newObj, obj);
        newObj.setAction(Action.UPDATE.getCod());
        return repository.save(newObj);

    }

    private void updateData(User newObj, User obj) {

        newObj.setActive(obj.isActive());

        if (obj.getEmail() != null) {
            newObj.setEmail(obj.getEmail());
        }
        if (obj.getName() != null) {
            newObj.setName(obj.getName());
        }
        if (obj.getSenha() != null) {
            newObj.setSenha(bc.encode(obj.getSenha()));
        }

    }

    public User fromDTO(UserDTO objDTO) {
        return new User(objDTO.getId(), objDTO.getName(), objDTO.getEmail(), objDTO.isActive(), null);
    }

    public User fromDTO(UserNewDTO objDTO) {
        return new User(null, objDTO.getName(), objDTO.getEmail(), objDTO.isActive(), bc.encode(objDTO.getSenha()));
    }

}
