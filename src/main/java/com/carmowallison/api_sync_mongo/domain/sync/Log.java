package com.carmowallison.api_sync_mongo.domain.sync;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;

public class Log  implements Serializable {

    @Id
    private String id;
    private Integer table;
    private Integer action;
    private String description;
    @DBRef
    private Sync sync;
}
