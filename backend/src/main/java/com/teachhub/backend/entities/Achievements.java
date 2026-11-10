package com.teachhub.backend.entities;

import com.teachhub.backend.enums.AchievementType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Table(name = "achievements")
@Data
public class Achievements {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    @Enumerated(EnumType.STRING)
    private AchievementType type;
    @ManyToOne()
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id")
    private User user;
    @Column(unique = true)
    private Integer priority=1;
    @PrePersist
    private void onCreate(){
        createdAt= LocalDateTime.now();
    }
}
