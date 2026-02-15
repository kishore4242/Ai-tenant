package com.aitenant.auth.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Service;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class RegisterUser {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_seq", allocationSize = 20)
    @Column(name = "id", updatable = false,nullable = false)
    private Long id;
    @Column(name = "user_name",nullable = false, length = 100)
    private String email;
    @Column(name = "password", nullable = false,length = 150)
    private String password;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;
    @Column(name = "role", nullable = false, length = 50)
    private String role;
    @Column(name = "status", nullable = false, length = 20)
    private String status;
}
