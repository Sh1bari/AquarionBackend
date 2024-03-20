package com.example.aquarionBackend.migrations;

import com.example.aquarionBackend.models.entities.Access;
import com.example.aquarionBackend.models.entities.Management;
import com.example.aquarionBackend.models.enums.SystemEnum;
import com.example.aquarionBackend.repositories.AccessRepo;
import com.example.aquarionBackend.repositories.ManagementRepo;
import com.example.aquarionBackend.services.FileService;
import com.example.aquarionBackend.services.MinioService;
import jakarta.annotation.PostConstruct;
import jakarta.mail.internet.MimeMessage;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class Migration {
    private final FileService fileService;
    private final AccessRepo accessRepo;
    private final ManagementRepo managementRepo;
    @Value("${init}")
    private boolean init;
    private final ResourceLoader resourceLoader;


    @PostConstruct
    private void init(){
        if(init) {
            initAccess();
        }
        //sendEmail("vova_krasnov_2004@mail.ru", "", "");
    }
    private final JavaMailSender mailSender;
    public void sendEmail(String to, String subject, String body) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setFrom("no-reply@rt5-61b.ru");
            // Устанавливаем получателя
            helper.setTo(to);

            // Устанавливаем тему письма
            helper.setSubject(subject);

            // Устанавливаем текст письма
            helper.setText(body);

            // Отправляем письмо
            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            // Обработка ошибок
        }
    }

    private void initManagement() {
        try {
            Management management = Management.builder()
                    .system(SystemEnum.IMS_3)
                    .build();
            managementRepo.save(management);
            management.setFile(fileService.saveFile(getFileFromResources("management/IMS 3.0/Руководство.docx")));
            managementRepo.save(management);
        }catch (Exception e){

        }
        try {
            Management management = Management.builder()
                    .system(SystemEnum.IMS_4)
                    .build();
            managementRepo.save(management);
            management.setFile(fileService.saveFile(getFileFromResources("management/IMS 4.0/Руководство.docx")));
            managementRepo.save(management);
        }catch (Exception e){

        }
        try {
            Management management = Management.builder()
                    .system(SystemEnum.MDP_2)
                    .build();
            managementRepo.save(management);
            management.setFile(fileService.saveFile(getFileFromResources("management/MDP 2.0/Руководство.docx")));
            managementRepo.save(management);
        }catch (Exception e){

        }
        try {
            Management management = Management.builder()
                    .system(SystemEnum.UTS)
                    .build();
            managementRepo.save(management);
            management.setFile(fileService.saveFile(getFileFromResources("management/UTS/Руководство.docx")));
            managementRepo.save(management);
        }catch (Exception e){

        }
    }
    private void initAccess(){
        try {
            Access access1 = Access.builder()
                    .system(SystemEnum.IMS_3)
                    .build();
            accessRepo.save(access1);
            access1.setFile(fileService.saveFile(getFileFromResources("access/IMS 3.0/Шаблон получения доступа.docx")));
            accessRepo.save(access1);
        }catch (Exception e){

        }
        try {
            Access access2 = Access.builder()
                    .system(SystemEnum.IMS_4)
                    .build();
            accessRepo.save(access2);
            access2.setFile(fileService.saveFile(getFileFromResources("access/IMS 4.0/Инструкция по заполнению.docx")));
            accessRepo.save(access2);
        }catch (Exception e){

        }
        try {
            Access access3 = Access.builder()
                    .system(SystemEnum.MDP_2)
                    .build();
            accessRepo.save(access3);
            access3.setFile(fileService.saveFile(getFileFromResources("access/MDP 2.0/Инструкция по заполнению.docx")));
            accessRepo.save(access3);
        }catch (Exception e){

        }
        try {
            Access access4 = Access.builder()
                    .system(SystemEnum.UTS)
                    .build();
            accessRepo.save(access4);
            access4.setFile(fileService.saveFile(getFileFromResources("access/UTS/Инструкция по заполнению.docx")));
            accessRepo.save(access4);
        }catch (Exception e){

        }
    }
    public File getFileFromResources(String fileName) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:" + fileName);
        File file = resource.getFile();
        return file;
    }
}
