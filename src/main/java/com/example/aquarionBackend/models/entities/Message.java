package com.example.aquarionBackend.models.entities;

import com.example.aquarionBackend.models.enums.MessageEnum;
import com.example.aquarionBackend.models.enums.MessageType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private Integer position;

    @Enumerated(EnumType.STRING)
    private MessageEnum messageEnum;

    @Enumerated(EnumType.STRING)
    private MessageType messageType;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, orphanRemoval = true)
    @JoinColumn(name = "message_id")
    private List<File> files;

    @Column(length = 2000)
    private String messageText;

    @Column(length = 2000)
    private String replyText;

    @Basic
    private LocalDateTime sentToUserTime;

    @Basic
    private LocalDateTime receivedFromUserTime;
}
