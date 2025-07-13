package br.com.techchallenge.techbites.repositories;

import br.com.techchallenge.techbites.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
