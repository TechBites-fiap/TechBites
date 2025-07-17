package br.com.techchallenge.techbites.application.useCases;

import br.com.techchallenge.techbites.domain.models.Type;
import br.com.techchallenge.techbites.services.exceptions.ResourceNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TypeUseCase {

    public Type createType(Type type);

    public List<Type> findAllType(Boolean active);

    public Optional<Type> findTypeById(Long id);

    public void deleteTypeById(Long id);

    public void enableTypeById(Long id);

    public Type updateTypeById(Long id, Type newTypeData);

}
