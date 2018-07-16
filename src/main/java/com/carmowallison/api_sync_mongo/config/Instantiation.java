package com.carmowallison.api_sync_mongo.config;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import com.carmowallison.api_sync_mongo.domain.enums.Action;
import com.carmowallison.api_sync_mongo.domain.enums.Perfil;
import com.carmowallison.api_sync_mongo.domain.enums.Table;
import com.carmowallison.api_sync_mongo.domain.sync.Log;
import com.carmowallison.api_sync_mongo.domain.sync.Sync;
import com.carmowallison.api_sync_mongo.dto.TableDTO;
import com.carmowallison.api_sync_mongo.dto.UserDTO;
import com.carmowallison.api_sync_mongo.repositoties.LogRepository;
import com.carmowallison.api_sync_mongo.repositoties.SyncRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.carmowallison.api_sync_mongo.domain.User;
import com.carmowallison.api_sync_mongo.repositoties.UserRepository;

@Configuration
public class Instantiation implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SyncRepository syncRepository;
    @Autowired
    private LogRepository logRepository;

    @Autowired
    private BCryptPasswordEncoder bc;

    @Override
    public void run(String... args) throws Exception {

        // EXCLUIR TODA A BASE
        userRepository.deleteAll();
        syncRepository.deleteAll();
        logRepository.deleteAll();

        // CRIA NOVA BASE

        // BLOCO USUARIOS
        User wallison = new User(null, "Wallison do Carmo Costa", "admin@email.com", true, bc.encode("123"));
        wallison.addPerfil(Perfil.ADMIN);

        User yasmin = new User(null, "yasmin", "yasmin@gmail.com", false, bc.encode("123"));
        User lucy = new User(null, "lucy", "lucy@gmail.com", true, bc.encode("123"));

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        Sync sync1 = new Sync(null, wallison);
        sync1.setCurrent(sdf.parse("30/09/2017 10:32"));

        Sync sync2 = new Sync(null, wallison);
        syncRepository.saveAll(Arrays.asList(sync1, sync2));

        wallison.setSync(sync1);
        wallison.setAction(Action.ADD.getCod());

        yasmin.setSync(sync1);
        yasmin.setAction(Action.ADD.getCod());

        lucy.setSync(sync2);
        lucy.setAction(Action.ADD.getCod());

        userRepository.saveAll(Arrays.asList(wallison, yasmin, lucy));

        Log addWallison = new Log(null, Table.USER.getCod(), Action.ADD.getCod(), Action.ADD.getDescricao().concat(" #" + wallison.getId()), sync1, new UserDTO(wallison), wallison);
        Log addYasmin = new Log(null, Table.USER.getCod(), Action.ADD.getCod(), Action.ADD.getDescricao().concat(" #" + yasmin.getId()), sync1, new UserDTO(wallison), yasmin);
        Log addLucy = new Log(null, Table.USER.getCod(), Action.ADD.getCod(), Action.ADD.getDescricao().concat(" #" + lucy.getId()), sync2, new UserDTO(wallison), lucy);
        logRepository.saveAll(Arrays.asList(addWallison, addYasmin, addLucy));

    }

}
