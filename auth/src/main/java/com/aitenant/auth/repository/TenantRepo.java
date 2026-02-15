package com.aitenant.auth.repository;

import com.aitenant.auth.models.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TenantRepo extends JpaRepository<Tenant, Long> {

    @Query("SELECT t.id FROM Tenant t WHERE t.name = :name")
    Optional<Long> findIdByName(@Param("shortName") String name);

}
