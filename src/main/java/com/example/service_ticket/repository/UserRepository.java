package com.example.service_ticket.repository;

import com.example.service_ticket.entity.RoleEntity;
import com.example.service_ticket.entity.UserEntity;
import com.sample.model.Tables;
import com.sample.model.tables.records.RoleRecord;
import com.sample.model.tables.records.UserRecord;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository implements BaseRepository<UserEntity, Long> {

    private final DSLContext dslContext;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public List<UserEntity> findAll() {
        return dslContext.selectFrom(Tables.USER)
                .fetch()
                .into(UserEntity.class);
    }

    @Override
    public UserEntity findById(Long id) {
        UserRecord userRecord = dslContext.selectFrom(Tables.USER)
                .where(Tables.USER.USER_ID.eq(id))
                .fetchOne();
        if (userRecord == null)
            return null;
        return userRecord.into(UserEntity.class);
    }

    @Override
    public void delete(UserEntity entity) {
        dslContext.delete(Tables.USER)
                .where(Tables.USER.USER_ID.eq(entity.getUserId()))
                .execute();
    }

    @Override
    public void deleteById(Long id) {
        dslContext.delete(Tables.USER)
                .where(Tables.USER.USER_ID.eq(id))
                .execute();
    }

    @Override
    public UserEntity update(UserEntity entity) {
        return dslContext.update(Tables.USER)
                .set(Tables.USER.USERNAME, entity.getUsername())
                .set(Tables.USER.EMAIL, entity.getEmail())
                .set(Tables.USER.FIRST_NAME, entity.getFirstName())
                .set(Tables.USER.LAST_NAME, entity.getLastName())
                .set(Tables.USER.PHONE_NUMBER, entity.getPhoneNumber())
                .set(Tables.USER.STATUS, entity.getStatus())
                .set(Tables.USER.CATEGORY, entity.getCategory())
                .set(Tables.USER.PASSWORD, entity.getPassword())
                .where(Tables.USER.USER_ID.eq(entity.getUserId()))
                .returning().fetchOne().into(UserEntity.class);
    }

    @Override
    public UserEntity save(UserEntity entity) {
        UserEntity userEntity = dslContext.insertInto(Tables.USER)
                .set(Tables.USER.USERNAME, entity.getUsername())
                .set(Tables.USER.EMAIL, entity.getEmail())
                .set(Tables.USER.FIRST_NAME, entity.getFirstName())
                .set(Tables.USER.LAST_NAME, entity.getLastName())
                .set(Tables.USER.PHONE_NUMBER, entity.getPhoneNumber())
                .set(Tables.USER.STATUS, "ACTIVE")
                .set(Tables.USER.CATEGORY, entity.getCategory())
                .set(Tables.USER.PASSWORD, bCryptPasswordEncoder.encode(entity.getPassword()))
                .returning().fetchOne().into(UserEntity.class);
        RoleRecord roleRecord = dslContext.selectFrom(Tables.ROLE)
                .where(Tables.ROLE.ROLE_NAME.eq("ROLE_USER"))
                .fetchAny();
        if(userEntity != null && roleRecord != null)
            dslContext.insertInto(Tables.USER_ROLE)
                    .set(Tables.USER_ROLE.USER_ID, userEntity.getUserId())
                    .set(Tables.USER_ROLE.ROLE_ID, roleRecord.getRoleId())
                    .execute();
        return userEntity;
    }

    public boolean existsById(Long id) {
        return findById(id) != null;
    }

    public UserEntity findByUsername(String username){
        UserRecord userRecord = dslContext.selectFrom(Tables.USER)
                .where(Tables.USER.USERNAME.eq(username))
                .fetchOne();
        if (userRecord == null)
            return null;
        return userRecord.into(UserEntity.class);
    };

    public UserEntity findByEmail(String email){
        UserRecord userRecord = dslContext.selectFrom(Tables.USER)
                .where(Tables.USER.EMAIL.eq(email))
                .fetchOne();
        if (userRecord == null)
            return null;
        return userRecord.into(UserEntity.class);
    };
}
