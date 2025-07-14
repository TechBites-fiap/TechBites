package br.com.techchallenge.techbites.repositories;

import br.com.techchallenge.techbites.entities.Type;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TypeRepository  extends JpaRepository<Type, Long> {
    List<Type> findByActive(Boolean active, Sort id);
}
