package br.com.techchallenge.techbites.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<AddressJpaEntity, Long> {
}
