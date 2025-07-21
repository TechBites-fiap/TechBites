package br.com.techchallenge.techbites.infrastructure.gateways;

import br.com.techchallenge.techbites.application.gateways.UserGateway;
import br.com.techchallenge.techbites.infrastructure.persistence.UserJpaEntity;
import br.com.techchallenge.techbites.infrastructure.persistence.UserRepository;
import br.com.techchallenge.techbites.services.exceptions.UserNotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryGateway implements UserGateway {

    private final UserRepository repository;

    public UserRepositoryGateway(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<UserJpaEntity> findUserById(Long id) {
        return Optional.of(repository.findById(id).orElseThrow(() -> new UserNotFoundException("id", id.toString())));
    }

    @Override
    public Optional<UserJpaEntity> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public UserJpaEntity createUser(UserJpaEntity entity) {
        return repository.save(entity);
    }

    @Override
    public List<UserJpaEntity> getAllUsers(Boolean active) {
        List<UserJpaEntity> entities;
        if (active == null) {
            entities = repository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        }else {
            entities = repository.findByActive(active, Sort.by(Sort.Direction.ASC, "id"));
        }

        return entities;
    }

    @Override
    public void deleteTypeById(Long id) {
        UserJpaEntity user = repository.findById(id).orElseThrow(() -> new UserNotFoundException("id", id.toString()));
        repository.delete(user);
    }

    @Override
    public void enableUserById(Long id) {
        UserJpaEntity user = repository.findById(id).orElseThrow(() -> new UserNotFoundException("id", id.toString()));
        user.setActive(true);
        user.setLastUpdatedAt(LocalDateTime.now());
        repository.save(user);
    }

    @Override
    public UserJpaEntity updateUser(UserJpaEntity entity) {
        return repository.save(entity);
    }


}
