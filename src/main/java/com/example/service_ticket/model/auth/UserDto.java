package com.example.service_ticket.model.auth;

import com.example.service_ticket.entity.RoleEntity;
import com.example.service_ticket.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
public class UserDto {
    private long userId;
    private String email;
    private String password;
    private String username;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private List<RoleEntity> roles;
    private String status;
    private String category;

    public static UserEntity convertToEntity(UserDto userDto){
        UserEntity newUser = new UserEntity();
        newUser.setUserId(userDto.userId);
        newUser.setUsername(userDto.username);
        newUser.setEmail(userDto.email);
        newUser.setPassword(userDto.password);
        newUser.setLastName(userDto.lastName);
        newUser.setFirstName(userDto.firstName);
        newUser.setPhoneNumber(userDto.phoneNumber);
        newUser.setCategory(userDto.category);
        return newUser;
    }
    public static UserDto convertToDto(UserEntity userEntity, List<RoleEntity> roles){
        UserDto newUserDto = new UserDto();
        newUserDto.setUserId(userEntity.getUserId());
        newUserDto.setUsername(userEntity.getUsername());
        newUserDto.setEmail(userEntity.getEmail());
        newUserDto.setPassword(userEntity.getPassword());
        newUserDto.setLastName(userEntity.getLastName());
        newUserDto.setFirstName(userEntity.getFirstName());
        newUserDto.setPhoneNumber(userEntity.getPhoneNumber());
        newUserDto.setRoles(roles);
        newUserDto.setStatus(userEntity.getStatus());
        newUserDto.setCategory(userEntity.getCategory());
        return newUserDto;
    }

}
