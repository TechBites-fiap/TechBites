package br.com.techchallenge.techbites.infrastructure.persistence;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserJpaEntity, Long> {

    List<UserJpaEntity> findByActive(Boolean active, Sort sort);
    Optional<UserJpaEntity> findByEmail(String email);

}
