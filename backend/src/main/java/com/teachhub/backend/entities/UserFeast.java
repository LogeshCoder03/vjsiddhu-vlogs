package com.teachhub.backend.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Table(name = "userFeasts")
@Data
public class UserFeast {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer mobileNumber;

    @Column(unique = true,nullable = false)
    private String token;

    @Column()
    private Boolean isVerified=false;
    private String otp;
    private LocalDateTime otpExpires;

    private LocalDateTime joinedAt;

    @ManyToOne()
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne()
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "feast_id")
    private Feast feast;

    @PrePersist
    public void onCreate(){
        joinedAt=LocalDateTime.now();
    }
}
