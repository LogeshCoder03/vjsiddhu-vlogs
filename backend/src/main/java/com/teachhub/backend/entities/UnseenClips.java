package com.teachhub.backend.entities;

import com.teachhub.backend.enums.ShootType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Table(name = "unseen_clips")
@Data
public class UnseenClips {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private ShootType type;
    private String videoPath;
    private String fileName;
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
