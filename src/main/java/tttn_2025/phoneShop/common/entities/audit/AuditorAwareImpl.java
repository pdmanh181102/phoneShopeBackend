package tttn_2025.phoneShop.common.entities.audit;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.AuditorAware;

public class AuditorAwareImpl implements AuditorAware<UUID> {

    @Override
    public Optional<UUID> getCurrentAuditor() {
        return Optional.of(UUID.randomUUID());
    }

}
