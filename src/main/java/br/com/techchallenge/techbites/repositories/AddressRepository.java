package br.com.techchallenge.techbites.repositories;

import br.com.techchallenge.techbites.entities.Address;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findByActive(Boolean active, Sort id);
}
