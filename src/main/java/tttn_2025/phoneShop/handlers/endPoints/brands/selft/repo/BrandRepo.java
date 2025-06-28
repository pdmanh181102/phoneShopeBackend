package tttn_2025.phoneShop.handlers.endPoints.brands.selft.repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tttn_2025.phoneShop.handlers.endPoints.brands.selft.entity.BrandEntity;

@Repository
public interface BrandRepo extends JpaRepository<BrandEntity, UUID> {
    boolean existsByName(String name);
}
