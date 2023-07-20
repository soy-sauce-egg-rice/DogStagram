package com.example.dogstar.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Builder
public class Member {

    @Id
    @Column(nullable = false)
    @Setter
    private String id;

    @Setter
    @Column(nullable = false)
    private String password;

    @Setter
    @Column(nullable = false)
    private String email;

    @Setter
//    @ColumnDefault("user")
    private String role;
}
