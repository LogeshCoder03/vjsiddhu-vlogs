package com.teachhub.backend.entities;

import com.teachhub.backend.enums.EventStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Table(name = "feasts")
@Data
public class Feast {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    private Integer totalMembers=0;
    private Boolean isLaunched=false;

    @ManyToOne()
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime createdAt;
    private LocalDateTime launchedAt;

    @Embedded
    private Address venue;

    @Column(nullable = false)
    private LocalDateTime timing;

    private String logo;
    private String logoPath;

    @Enumerated(EnumType.STRING)
    private EventStatus status=EventStatus.Upcoming;

    @PrePersist
    public void onCreate(){
        createdAt= LocalDateTime.now();
    }
}
