package com.teachhub.backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "about")
public class About {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Lob
    private String about;
    private String instagram;
    private String role;
    private String image;
    private String imagePath;
    private LocalDateTime createdAt;
    @PrePersist
    private void onCreate(){
        createdAt= LocalDateTime.now();
    }
}
