package com.example.aquarionBackend.controllers;

import com.example.aquarionBackend.models.enums.SystemEnum;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Validated
@CrossOrigin
@RequestMapping("")
@Tag(name = "File API", description = "")
public class FileController {

    @PutMapping("/access/file")
    public ResponseEntity<?> updateAccessFile(
            @RequestParam(name = "system") SystemEnum system){
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @PutMapping("/management/file")
    public ResponseEntity<?> updateManagementFile(
            @RequestParam(name = "system") SystemEnum system){
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
