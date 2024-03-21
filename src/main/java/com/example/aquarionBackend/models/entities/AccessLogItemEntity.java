package com.example.aquarionBackend.models.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "access_log")
@Getter
@Setter
@ToString
@NoArgsConstructor()
public class AccessLogItemEntity{
    @Id
    @UuidGenerator
    @Column(name = "uuid", nullable = false, updatable = false)
    @Setter(AccessLevel.PRIVATE)
    UUID uuid;

    @CreationTimestamp
    @Column(name = "timestamp", nullable = false, updatable = false)
    Instant timestamp;

    String mail;

    @Column(name = "endpoint", nullable = false)
    String endpoint;

    @Column(name = "request_method")
    String method;

    @Column(name = "incoming_ip", nullable = false)
    String incomingIp;

    @Column(name = "http_resp_code", nullable = false)
    Integer httpResponseCode;


}
