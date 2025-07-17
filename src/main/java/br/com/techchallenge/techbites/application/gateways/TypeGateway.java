package br.com.techchallenge.techbites.application.gateways;

import br.com.techchallenge.techbites.domain.models.Type;

import java.util.List;
import java.util.Optional;

public interface TypeGateway {
    Type createType(Type type);

    List<Type> findAllType(Boolean active);

    Optional<Type> findTypeById(Long id);

    void deleteTypeById(Long id);

    void enableTypeById(Long id);

    Type updateTypeById(Long id, Type newTypeData);
}
