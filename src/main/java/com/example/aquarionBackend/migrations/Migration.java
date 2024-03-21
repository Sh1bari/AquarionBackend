package com.example.aquarionBackend.migrations;

import com.example.aquarionBackend.configs.ConstantsConfig;
import com.example.aquarionBackend.models.entities.Access;
import com.example.aquarionBackend.models.entities.Management;
import com.example.aquarionBackend.models.entities.System;
import com.example.aquarionBackend.models.enums.SystemEnum;
import com.example.aquarionBackend.repositories.AccessRepo;
import com.example.aquarionBackend.repositories.ManagementRepo;
import com.example.aquarionBackend.repositories.SystemRepo;
import com.example.aquarionBackend.services.FileService;
import jakarta.annotation.PostConstruct;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class Migration {
    private final FileService fileService;
    private final AccessRepo accessRepo;
    private final ManagementRepo managementRepo;
    @Value("${init}")
    private boolean init;
    @Value("${serverName}")
    private String serverName;
    private final ResourceLoader resourceLoader;
    private final ConstantsConfig constantsConfig;
    private final SystemRepo systemRepo;


    @PostConstruct
    private void init(){
        if(init) {
            initAccess();
            initManagement();
            initDB();
        }
    }

    private void initDB(){
        constantsConfig.getSystems().forEach(o->{
            try {
                systemRepo.save(System.builder()
                        .colonyName(serverName)
                        .systemName(o)
                        .build());
            }catch (Exception e){

            }

        });

    }

    private void initManagement() {
        switch (serverName){
            case "aquarion" -> {
                initManagementSolo(SystemEnum.IMS_3, "aquarion/management/IMS 3.0/Руководство.docx");
                initManagementSolo(SystemEnum.IMS_4, "aquarion/management/IMS 4.0/Руководство.docx");
                initManagementSolo(SystemEnum.MDP_2, "aquarion/management/MDP 2.0/Руководство.docx");
                initManagementSolo(SystemEnum.UTS, "aquarion/management/UTS/Руководство.docx");
            }
            case "green_maze" -> {
                initManagementSolo(SystemEnum.MDP_2, "green_maze/management/MDP 2.0/Руководство.docx");
                initManagementSolo(SystemEnum.UTS, "green_maze/management/UTS/Руководство.docx");
            }
            case "crystallia" -> {
                initManagementSolo(SystemEnum.IMS_3, "crystallia/management/IMS 3.0/Руководство.docx");
                initManagementSolo(SystemEnum.IMS_4, "crystallia/management/IMS 4.0/Руководство.docx");
            }
            case "desert_whirlwind" -> {
                initManagementSolo(SystemEnum.IMS_4, "desert_whirlwind/management/IMS 4.0/Руководство.docx");
                initManagementSolo(SystemEnum.MDP_2, "desert_whirlwind/management/MDP 2.0/Руководство.docx");
            }
            default -> throw new RuntimeException("Colony name not exists");
        }
    }
    private void initAccess(){
        switch (serverName){
            case "aquarion" -> {
                initAccessSolo(SystemEnum.IMS_3, "aquarion/access/IMS 3.0/Шаблон получения доступа.docx");
                initAccessSolo(SystemEnum.IMS_4, "aquarion/access/IMS 4.0/Инструкция по заполнению.docx");
                initAccessSolo(SystemEnum.MDP_2, "aquarion/access/MDP 2.0/Инструкция по заполнению.docx");
                initAccessSolo(SystemEnum.UTS, "aquarion/access/UTS/Инструкция по заполнению.docx");
            }
            case "green_maze" -> {
                initAccessSolo(SystemEnum.MDP_2, "green_maze/access/MDP 2.0/Инструкция по заполнению.docx");
                initAccessSolo(SystemEnum.UTS, "green_maze/access/UTS/Инструкция по заполнению.docx");
            }
            case "crystallia" -> {
                initAccessSolo(SystemEnum.IMS_3, "crystallia/access/IMS 3.0/Инструкция по заполнению.docx");
                initAccessSolo(SystemEnum.IMS_4, "crystallia/access/IMS 4.0/Инструкция по заполнению.docx");
            }
            case "desert_whirlwind" -> {
                initAccessSolo(SystemEnum.IMS_4, "desert_whirlwind/access/IMS 4.0/Инструкция по заполнению.docx");
                initAccessSolo(SystemEnum.MDP_2, "desert_whirlwind/access/MDP 2.0/Инструкция по заполнению.docx");
            }
            default -> throw new RuntimeException("Colony name not exists");
        }
    }
    private void initManagementSolo(SystemEnum systemEnum, String path){
        try {
            Management management = Management.builder()
                    .system(systemEnum)
                    .build();
            managementRepo.save(management);
            management.setFile(fileService.saveFile(getFileFromResources(path)));
            managementRepo.save(management);
        }catch (Exception e){}
    }

    private void initAccessSolo(SystemEnum systemEnum, String path){
        try {
            Access access = Access.builder()
                    .system(systemEnum)
                    .build();
            accessRepo.save(access);
            access.setFile(fileService.saveFile(getFileFromResources(path)));
            accessRepo.save(access);
        }catch (Exception e){}
    }
    public File getFileFromResources(String fileName) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:" + fileName);
        File file = resource.getFile();
        return file;
    }
}
