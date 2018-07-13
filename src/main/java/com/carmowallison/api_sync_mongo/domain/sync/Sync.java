package com.carmowallison.api_sync_mongo.domain.sync;

import com.carmowallison.api_sync_mongo.dto.TableDTO;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Sync implements Serializable {

    @Id
    private String id;
    private String sync;
    private List<TableDTO> add = new ArrayList<>();
    private List<TableDTO> update = new ArrayList<>();
    private List<TableDTO> delete = new ArrayList<>();
    private List<Log> logs= new ArrayList<>();

}
