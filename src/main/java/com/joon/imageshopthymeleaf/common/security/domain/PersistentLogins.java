package com.joon.imageshopthymeleaf.common.security.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PersistentLogins {
    @Id
    @GeneratedValue
    private long series;

    private String username;

    private String token;

    @CreationTimestamp
    private LocalDateTime lastUsed;



}
