package br.com.techchallenge.techbites.application.useCases;

import br.com.techchallenge.techbites.infrastructure.persistence.UserJpaEntity;

import java.util.List;
import java.util.Optional;

public interface UserUseCase {

    public Optional<UserJpaEntity> findUserByID(Long id);

    public UserJpaEntity createUser(UserJpaEntity jpaEntity);

    public List<UserJpaEntity> getAllUsers(Boolean active);

    public void deleteUserById(Long id);

    public void enableUserById(Long id);

    public UserJpaEntity updateById(Long id, UserJpaEntity entity);
}
