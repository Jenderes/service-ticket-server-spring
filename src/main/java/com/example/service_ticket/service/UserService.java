package com.example.service_ticket.service;

import com.example.service_ticket.model.UserDto;
import com.sample.model.Tables;
import com.sample.model.tables.pojos.Role;
import com.sample.model.tables.pojos.User;
import com.sample.model.tables.records.RoleRecord;
import com.sample.model.tables.records.UserRecord;
import com.sample.model.tables.records.UserRoleRecord;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final DSLContext dsl;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(DSLContext dsl, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.dsl = dsl;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public boolean existsByUsername(String username) {
        return findUserByUserName(username) != null;
    }

    public boolean existsByEmail(String email) {
        return findByEmail(email) != null;
    }

    public User findUserById(long userId){
        UserRecord userRecord = dsl.selectFrom(Tables.USER)
                .where(Tables.USER.USER_ID.eq(userId))
                .fetchAny();
        if (userRecord == null) {
            return null;
        }
        return findUserByUserRecord(userRecord);
    }

    public User findByEmail(String email){
        UserRecord userRecord = dsl.selectFrom(Tables.USER)
                .where(Tables.USER.EMAIL.eq(email))
                .fetchAny();
        if (userRecord == null) {
            return null;
        }
        return findUserByUserRecord(userRecord);
    }

    public User findUserByUserName(String username){
        UserRecord userRecord = dsl.selectFrom(Tables.USER)
                .where(Tables.USER.LOGIN.eq(username))
                .fetchAny();
        if (userRecord == null) {
            return null;
        }
        return findUserByUserRecord(userRecord);
    }

    public List<User> findAllUser() {
        Result<UserRecord> userRecord = dsl.selectFrom(Tables.USER)
                .fetch();
        if (userRecord.isEmpty()) {
            return null;
        }
        return userRecord.stream()
                .map(this::findUserByUserRecord)
                .collect(Collectors.toList());
    }

    public void createUser(UserDto userDto) {
        dsl.insertInto(Tables.USER)
                .set(Tables.USER.LOGIN, userDto.getLogin())
                .set(Tables.USER.EMAIL, userDto.getEmail())
                .set(Tables.USER.PASSWORD, bCryptPasswordEncoder.encode(userDto.getPassword()))
                .set(Tables.USER.FIRST_NAME, userDto.getFirstName())
                .set(Tables.USER.LAST_NAME, userDto.getLastName())
                .set(Tables.USER.STATUS, "active")
                .execute();
    }

    public UserDto userDtoByUsername(String username){
        User user = findUserByUserName(username);
        UserDto userDto = new UserDto();
        userDto.setUserId(user.getUserId());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setLogin(user.getLogin());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setStatus(user.getStatus());
        userDto.setRoles(findRolesByUserName(username));
        return userDto;
    }

    public List<Role> findRolesByUserName(String username){
        UserRecord userRecord = dsl.selectFrom(Tables.USER)
                .where(Tables.USER.LOGIN.eq(username))
                .fetchAny();
        if (userRecord == null) {
            return null;
        }
        return getRolesUser(userRecord);
    }


    private User findUserByUserRecord(UserRecord userRecord){
        User user = new User();
        user.setUserId(userRecord.getUserId());
        user.setLogin(userRecord.getLogin());
        user.setFirstName(userRecord.getFirstName());
        user.setLastName(userRecord.getLastName());
        user.setStatus(userRecord.getStatus());
        user.setEmail(userRecord.getEmail());
        user.setPassword(userRecord.getPassword());
        return user;
    }

    private List<Role> getRolesUser (UserRecord userRecord) {
        Result<UserRoleRecord> userRoleRecord = dsl.selectFrom(Tables.USER_ROLE).
                where(Tables.USER_ROLE.USER_ID.eq(userRecord.getUserId()))
                .fetch();

        return userRoleRecord.stream().map((userRoleRecord1) -> {
            RoleRecord roleRecord = dsl.selectFrom(Tables.ROLE)
                    .where(Tables.ROLE.ROLE_ID.eq(userRoleRecord1.getRoleId())).fetchAny();
            return new Role(roleRecord.getRoleId(), roleRecord.getRoleName());
        }).collect(Collectors.toList());
    }
}
