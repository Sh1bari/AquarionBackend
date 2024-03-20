package com.example.aquarionBackend.models.entities;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class System {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String systemName;

    private String colonyName;
}
