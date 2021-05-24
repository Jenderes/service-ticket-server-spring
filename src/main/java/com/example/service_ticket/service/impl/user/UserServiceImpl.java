package com.example.service_ticket.service.impl.user;

import com.example.service_ticket.entity.RoleEntity;
import com.example.service_ticket.entity.UserEntity;
import com.example.service_ticket.repository.RoleRepository;
import com.example.service_ticket.repository.UserRepository;
import com.example.service_ticket.service.UpdateAutoFillService;
import com.example.service_ticket.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UpdateAutoFillService updateAutoFillService;

    @Override
    public void updateUser(UserEntity userEntity) {
        UserEntity oldUser = userRepository.findById(userEntity.getUserId());
        userEntity = updateAutoFillService.fillOnUpdate(userEntity, oldUser);
        userRepository.update(userEntity);
    }

    @Override
    public void creatUser(UserEntity userEntity) throws AuthenticationServiceException {
        if (existsUserByEmail(userEntity.getEmail()) || existsUserByUsername(userEntity.getUsername()))
            throw new AuthenticationServiceException("Пользователь уэе харегистрирован");
        userRepository.save(userEntity);
    }

    @Override
    public void deleteUser(UserEntity userEntity) {
        userRepository.delete(userEntity);
    }

    @Override
    public List<UserEntity> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public Optional<UserEntity> getUserById(Long id) {
        return Optional.of(userRepository.findById(id));
    }

    @Override
    public Optional<UserEntity> getUserByUsername(String username) {
        return Optional.of(userRepository.findByUsername(username));
    }

    @Override
    public UserEntity getCurrentUser() throws IllegalStateException{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return getUserByUsername(username).orElseThrow(
                () -> new IllegalStateException("CurrentUser not found")
        );
    }

    @Override
    public boolean existsUserById(Long id) {
        return getUserById(id).isPresent();
    }

    @Override
    public boolean existsUserByUsername(String username) {
        return userRepository.findByUsername(username) != null;
    }

    @Override
    public boolean existsUserByEmail(String email) {
        return userRepository.findByEmail(email) != null;
    }

    @Override
    public List<RoleEntity> getRoleByUsername(String username){
        List<RoleEntity> roleEntities= roleRepository.findRolesByUserName(username);
        return roleRepository.findRolesByUserName(username);
    }
}
