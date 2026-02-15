package com.aitenant.auth.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "tenant")
public class Tenant {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tenant_seq")
    @SequenceGenerator(name = "tenant_seq", sequenceName = "tenant_seq", allocationSize = 20)
    @Column(name = "id", updatable = false,nullable = false)
    private Long id;

    @Column(name = "tenant_name", unique = true, nullable = false, length = 150)
    private String tenantName;

    @Column(name = "short_name", unique = true, nullable = false, length = 10)
    private String name;
}
