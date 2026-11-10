package com.teachhub.backend.entities;

import com.teachhub.backend.enums.ShootType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Table(name = "photoshoots")
@Data
public class PhotoShoots {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imagePath;
    private String fileName;
    @Enumerated(EnumType.STRING)
    private ShootType type;
    private LocalDateTime createdAt;
    private Boolean isPublished=false;
    @ManyToOne()
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id")
    private User user;
    @PrePersist
    private void onCreate(){
        createdAt= LocalDateTime.now();
    }
}
