package com.carmowallison.api_sync_mongo.services;


import com.carmowallison.api_sync_mongo.domain.Produto;
import com.carmowallison.api_sync_mongo.domain.User;
import com.carmowallison.api_sync_mongo.domain.enums.Action;
import com.carmowallison.api_sync_mongo.domain.sync.Log;
import com.carmowallison.api_sync_mongo.domain.sync.Sync;
import com.carmowallison.api_sync_mongo.dto.LogDTO;
import com.carmowallison.api_sync_mongo.dto.SyncDTO;
import com.carmowallison.api_sync_mongo.dto.TableDTO;
import com.carmowallison.api_sync_mongo.dto.UserDTO;
import com.carmowallison.api_sync_mongo.repositoties.SyncRepository;
import com.carmowallison.api_sync_mongo.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort.Direction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SyncService {

    @Autowired
    SyncRepository repo;
    @Autowired
    LogService logService;
    @Autowired
    UserService userService;
    @Autowired
    ProdutoService produtoService;

    public Sync findById(String id) {
        Optional<Sync> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
    }

    public SyncDTO findSyncById(String id) {
        List<Sync> list = new ArrayList<>();
        Sync sync = this.findById(id);
        list.add(sync);
        return new SyncDTO(sync, logService.findAllBySync(list));
    }

    public Sync insert(Sync obj) {
        obj.setId(null);
        return repo.save(obj);
    }

    public Sync update(Sync obj) {
        Sync newObj = findById(obj.getId());

        updateData(newObj, obj);

        return repo.save(newObj);
    }

    public void delete(String id) {
        findById(id);
        repo.deleteById(id);
    }

    public List<Sync> findAll() {
        return repo.findAll();
    }

    public Page<Sync> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = new PageRequest(page, linesPerPage, Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    private void updateData(Sync newObj, Sync obj) {
        if (obj.getUser() != null) {
            newObj.setUser(obj.getUser());
        }
    }

    public SyncDTO getLastSync(String id) {

        Sync syncCurrent = this.findById(id);

        List<Sync> list = repo.findByCurrentGreaterThanOrderByCurrentAsc(syncCurrent.getCurrent());
        SyncDTO sync = new SyncDTO();

        if (list.isEmpty()) {
            return null;
        }

        sync.setId(list.get(list.size() - 1).getId());

        List<User> users = userService.findAllBySync(list);

        sync.setAdd(new TableDTO());
        sync.setUpdate(new TableDTO());
        sync.setDelete(new TableDTO());

        users.parallelStream().forEach(obj -> {
            switch (obj.getAction()) {
                case 1:
                    sync.getAdd().getUsers().add(new UserDTO(obj));
                    break;
                case 2:
                    sync.getUpdate().getUsers().add(new UserDTO(obj));
                    break;
                case 3:
                    sync.getDelete().getUsers().add(new UserDTO(obj));
                    break;
                default:
                    break;
            }

        });

        List<Produto> produtos = produtoService.findAllBySync(list);
        produtos.parallelStream().forEach(obj -> {
            switch (obj.getAction()) {
                case 1:
                    sync.getAdd().getProdutos().add(obj);
                    break;
                case 2:
                    sync.getUpdate().getProdutos().add(obj);
                    break;
                case 3:
                    sync.getDelete().getProdutos().add(obj);
                    break;
                default:
                    break;
            }

        });

        List<Log> logs = logService.findAllBySync(list);

        List<LogDTO> listDTO = logs.stream().map(obj -> new LogDTO(obj)).collect(Collectors.toList());

        sync.setLogs(listDTO);
        return sync;
    }

    public SyncDTO insertDataSync(SyncDTO obj) {
        String id = obj.getId();
        Sync sync = insert(new Sync(null, obj.getUser()));

        if (obj.getAdd() != null) {
            obj.getAdd().getUsers().parallelStream().forEach(userDTO -> {
                User current = userService.findByEmail(userDTO.getEmail());
                User user = userService.fromDTO(userDTO);

                user.setAction(Action.ADD.getCod());
                if (current != null) {
                    user.setId(current.getId());
                    user.setAction(Action.UPDATE.getCod());
                }

                user.setSync(sync);
                updateDataUser(user);

            });
        }

        if (obj.getUpdate() != null) {
            obj.getUpdate().getUsers().parallelStream().forEach(userDTO -> {
                User current = userService.findById(userDTO.getId());
                User user = userService.fromDTO(userDTO);

                user.setAction(Action.ADD.getCod());
                if (current != null) {
                    user.setId(current.getId());
                    user.setAction(Action.UPDATE.getCod());
                }
                user.setSync(sync);
                updateDataUser(user);

            });
        }

        if (obj.getDelete() != null) {

            obj.getDelete().getUsers().parallelStream().forEach(userDTO -> {
                User current = userService.findById(userDTO.getId());
                User user = userService.fromDTO(userDTO);

                user.setAction(Action.ADD.getCod());
                if (current != null) {
                    user.setId(current.getId());
                    user.setAction(Action.DELETE.getCod());
                }
                user.setSync(sync);
                updateDataUser(user);

            });

        }

        return getLastSync(id);
    }


    public User updateDataUser(User obj) {

        Integer code = 0;
        String description = "";

        switch (obj.getAction()) {
            case 1:
                description = Action.ADD.getDescricao().concat(" #" + obj.getName());
                userService.insert(obj);
                break;
            case 2:
                description = Action.UPDATE.getDescricao().concat(" #" + obj.getName());
                obj = userService.update(obj);
                break;
            case 3:
                description = Action.DELETE.getDescricao().concat(" #" + obj.getName());
                obj = userService.delete(obj.getId());
                break;
        }

        logService.gernerateLog(obj.getAction(), description, obj.getSync(), new User(), obj);

        return obj;
    }

}
