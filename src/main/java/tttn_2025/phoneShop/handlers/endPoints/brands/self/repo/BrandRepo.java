package tttn_2025.phoneShop.handlers.endPoints.brands.self.repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tttn_2025.phoneShop.handlers.endPoints.brands.self.entity.BrandEntity;

@Repository
public interface BrandRepo extends JpaRepository<BrandEntity, UUID> {
    boolean existsByName(String name);
}
