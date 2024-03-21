package com.example.aquarionBackend.configs;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ConstantsConfig {
    @Value("${serverName}")
    private String serverName;

    public List<String> getSystems(){
        switch (serverName) {
            case "aquarion" -> {
                return List.of("IMS 3.0", "IMS 4.0", "MDP 2.0", "UTS");
            }
            case "green_maze" -> {
                return List.of("MDP 2.0", "UTS");
            }
            case "crystallia" -> {
                return List.of("IMS 3.0", "IMS 4.0");
            }
            case "desert_whirlwind" -> {
                return List.of("IMS 4.0", "MDP 2.0");
            }
            case "terramorph" ->{
                return List.of();
            }
            default -> throw new RuntimeException("Colony name not exists");
        }
    }
}
