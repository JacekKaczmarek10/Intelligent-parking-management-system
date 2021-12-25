package pl.kaczmarek.rest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;
import java.util.UUID;

@NoRepositoryBean
public interface EntityDAO<T extends IEntity> extends JpaRepository<T, UUID>, JpaSpecificationExecutor<T> {
    T getById(UUID id);
    Optional<T> findById(UUID id);
}
