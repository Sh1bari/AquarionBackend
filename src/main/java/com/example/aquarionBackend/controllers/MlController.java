package com.example.aquarionBackend.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
@CrossOrigin
@RequestMapping("")
@Tag(name = "ML server API", description = "")
public class MlController {


}
