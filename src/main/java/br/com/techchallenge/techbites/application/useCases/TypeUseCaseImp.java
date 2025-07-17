package br.com.techchallenge.techbites.application.useCases;

import br.com.techchallenge.techbites.application.gateways.TypeGateway;
import br.com.techchallenge.techbites.domain.models.Type;
import br.com.techchallenge.techbites.services.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TypeUseCaseImp implements TypeUseCase {

    private final TypeGateway repository;

    public TypeUseCaseImp(TypeGateway repository) {
        this.repository = repository;
    }

    @Override
    public Type createType(Type type) {
        type.setActive(true);
        LocalDateTime now = LocalDateTime.now();
        type.setCreatedAt(now);
        type.setLastUpdatedAt(now);
        return repository.createType(type);
    }

    @Override
    public List<Type> findAllType(Boolean active) {
        if (active == null) {
            return repository.findAll();
        }
        return repository.findAllByActive(active);
    }

    @Override
    public Optional<Type> findTypeById(Long id) {
        return Optional.of(repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Type", "id", id.toString())));
    }

    @Override
    public void deleteTypeById(Long id) {
        Type type = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Type", "id", id.toString()));
        repository.delete(type);
    }

    @Override
    public void enableTypeById(Long id) {
        Type type = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Type", "id", id.toString()));
        type.setActive(true);
        type.setLastUpdatedAt(LocalDateTime.now());
        repository.save(type);
    }

    @Override
    public Type updateTypeById(Long id, Type newTypeData) {
        Type existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Type", "id", id.toString()));
        existing.setType(newTypeData.getType());
        existing.setLastUpdatedAt(LocalDateTime.now());
        return repository.save(existing);
    }

}
