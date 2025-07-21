package br.com.techchallenge.techbites.application.gateways;

import br.com.techchallenge.techbites.infrastructure.persistence.UserJpaEntity;

import java.util.List;
import java.util.Optional;

public interface UserGateway {

    public Optional <UserJpaEntity> findUserById(Long id);

    public Optional<UserJpaEntity> findByEmail(String email);

    public UserJpaEntity createUser(UserJpaEntity jpaEntity);

    public List<UserJpaEntity> getAllUsers(Boolean active);

    public void deleteTypeById(Long id);

    public void enableUserById(Long id);

    public UserJpaEntity updateUser(UserJpaEntity saveEntity);
}
