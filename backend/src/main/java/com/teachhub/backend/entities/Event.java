package com.teachhub.backend.entities;

import com.teachhub.backend.enums.EventStatus;
import com.teachhub.backend.enums.EventType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Table(name = "events")
@Data
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private EventType eventType;

    private LocalDateTime createdAt;
    private LocalDateTime acceptedAt;

    private Boolean isAccepted=false;

    private Long price;

    @Enumerated(EnumType.STRING)
    private EventStatus status=EventStatus.Upcoming;

    @Embedded
    private Address address;

    @Column(nullable = false)
    private Long mobileNumber;

    private LocalDateTime heldOn;

    @ManyToOne()
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id")
    private User user;

    @PrePersist
    public void onCreate(){
        createdAt=LocalDateTime.now();
    }
}
