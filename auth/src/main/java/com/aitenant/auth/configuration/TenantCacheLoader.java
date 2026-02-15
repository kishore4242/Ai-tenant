package com.aitenant.auth.configuration;

import com.aitenant.auth.models.Tenant;
import com.aitenant.auth.repository.TenantRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class TenantCacheLoader implements CommandLineRunner {

    private final TenantRepo tenantRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void run(String... args) {
        try {
            List<Tenant> tenants = tenantRepository.findAll();

            for (Tenant tenant : tenants) {
                String key = "TENANT:" + tenant.getName();
                redisTemplate.opsForHash().put(key, "id", tenant.getId());
            }
            log.info("Tenants db loaded into Redis");
        }
        catch (Exception e){
            log.error(e.getMessage());
        }
    }
}

