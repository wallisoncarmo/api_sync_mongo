package com.carmowallison.api_sync_mongo.resources;


import com.carmowallison.api_sync_mongo.domain.Produto;
import com.carmowallison.api_sync_mongo.domain.sync.Sync;
import com.carmowallison.api_sync_mongo.dto.SyncDTO;
import com.carmowallison.api_sync_mongo.services.ProdutoService;
import com.carmowallison.api_sync_mongo.services.SyncService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/sync")
public class SyncResource {

    @Autowired
    private SyncService service;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @ApiOperation(value = "Busca Todos os sync")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Sync>> findAll() {
        List<Sync> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @ApiOperation(value = "Busca por um sync pelo seu id")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<SyncDTO> findSyncById(@PathVariable String id) {
        SyncDTO obj = service.findSyncById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @ApiOperation(value = "Busca por um sync pelo seu id")
    @RequestMapping(value = "/{sync}/sync", method = RequestMethod.GET)
    public ResponseEntity<SyncDTO> findBySync(@PathVariable String sync) {
        return ResponseEntity.ok().body(service.getLastSync(sync));
    }
}
