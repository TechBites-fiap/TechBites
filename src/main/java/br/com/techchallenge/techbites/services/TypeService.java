package br.com.techchallenge.techbites.services;

import br.com.techchallenge.techbites.dtos.TypeRequestDTO;
import br.com.techchallenge.techbites.dtos.TypeResponseDTO;
import br.com.techchallenge.techbites.entities.Type;
import br.com.techchallenge.techbites.mappers.TypeMapper;
import br.com.techchallenge.techbites.repositories.TypeRepository;
import br.com.techchallenge.techbites.services.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TypeService {

    private final TypeRepository repository;
    private final TypeMapper mapper;

    public TypeService(TypeRepository repository , TypeMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Type createType(TypeRequestDTO typeDto) {
        Type entity = mapper.toEntity(typeDto);
        entity.setActive(true);
        return repository.save(entity);
    }

    public List<TypeResponseDTO> findAllType(Boolean active) {
        List<Type> types;

        if (active == null) {
            types = repository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        } else {
            types = repository.findByActive(active, Sort.by(Sort.Direction.ASC, "id"));
        }

        return mapper.toListDTO(types);

    }

    public Optional<TypeResponseDTO> findTypeById(Long id) {
        return Optional.of(repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Type" , "id" , id.toString() )
                ))
                .map(this.mapper::toDTO);
    }

    public void deleteTypeById(Long id) {
        Type type = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Type" , "id" , id.toString())
        );
        repository.delete(type);
    }

    public void enableTypeById(Long id) {
        Type type = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Type" , "id" , id.toString())
        );
        type.setActive(true);
        type.setLastUpdatedAt(LocalDateTime.now());
        repository.save(type);
    }

    public TypeResponseDTO updateTypeById(Long id, TypeRequestDTO typeDto) {
        Type typeExist = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Type" , "id" , id.toString())
        );
        mapper.updateEntity(typeExist, typeDto);
        return mapper.toDTO(repository.save(typeExist));
    }


}
