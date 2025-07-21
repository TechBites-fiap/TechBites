package br.com.techchallenge.techbites.application.useCases;

import br.com.techchallenge.techbites.application.gateways.UserGateway;
import br.com.techchallenge.techbites.domain.models.User;
import br.com.techchallenge.techbites.dtos.UserResponseDTO;
import br.com.techchallenge.techbites.infrastructure.gateways.UserEntityMapper;
import br.com.techchallenge.techbites.infrastructure.persistence.UserJpaEntity;
import br.com.techchallenge.techbites.services.exceptions.DuplicateKeyException;
import br.com.techchallenge.techbites.services.exceptions.UserNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserUseCaseImp implements UserUseCase {

    private final UserGateway repository;
    private final UserEntityMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public UserUseCaseImp(UserGateway repository, UserEntityMapper mapper , PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<UserJpaEntity> findUserByID(Long id) {
        Optional<UserJpaEntity> found = repository.findUserById(id);
        User domain = mapper.toDomain(found.get());
        if (domain.isInactive()) throw new UserNotFoundException("id", id.toString());
        return found;
    }

    @Override
    public UserJpaEntity createUser(UserJpaEntity jpaEntity) {

        this.validateEmail(jpaEntity.getEmail());

        User domain = mapper.toDomain(jpaEntity);
        LocalDateTime now = LocalDateTime.now();

        domain.setCreatedAt(now);
        domain.setLastUpdatedAt(now);
        domain.setPassword(passwordEncoder.encode(jpaEntity.getPassword()));

        UserJpaEntity saveEntity = repository.createUser(mapper.toJpaEntity(domain));
        return saveEntity;
    }

    @Override
    public List<UserJpaEntity> getAllUsers(Boolean active) {
        return repository.getAllUsers(active);
    }

    @Override
    public void deleteUserById(Long id) {
        repository.deleteTypeById(id);
    }

    @Override
    public void enableUserById(Long id) {
        repository.enableUserById(id);
    }

    @Override
    public UserJpaEntity updateById(Long id, UserJpaEntity jpaEntity) {
        UserJpaEntity found = repository.findUserById(id).orElseThrow(() -> new UserNotFoundException("id", id.toString()));

        User domain = mapper.toDomain(found);
        if (domain.isInactive()) throw new UserNotFoundException("id", id.toString());


        Optional<UserJpaEntity> userByEmail = repository.findByEmail(jpaEntity.getEmail());

        if (userByEmail.isPresent() && !userByEmail.get().getId().equals(id)) {
            throw new DuplicateKeyException("User", "email", jpaEntity.getEmail());
        }

        domain.setName(jpaEntity.getName());
        domain.setEmail(jpaEntity.getEmail());
        domain.setRole(jpaEntity.getRole());

        domain.setLastUpdatedAt(LocalDateTime.now());

        UserJpaEntity saveEntity = mapper.toJpaEntity(domain);

        return repository.updateUser(saveEntity);
    }

    private void validateEmail(String email) {
        if (repository.findByEmail(email).isPresent()) {
            throw new DuplicateKeyException("User" , "email" , email);
        }
    }

}
