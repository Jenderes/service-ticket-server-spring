package com.example.service_ticket.service;

import com.example.service_ticket.entity.RoleEntity;
import com.example.service_ticket.entity.TicketEntity;
import com.example.service_ticket.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {

    void updateUser(UserEntity userEntity);

    void creatUser(UserEntity userEntity);

    void deleteUser(UserEntity userEntity);

    List<UserEntity> getAllUser();

    Optional<UserEntity> getUserById(Long id);

    Optional<UserEntity> getUserByUsername(String username);

    UserEntity getCurrentUser();

    boolean existsUserById(Long id);

    boolean existsUserByUsername(String username);

    boolean existsUserByEmail(String email);

    List<RoleEntity> getRoleByUsername(String username);
}
