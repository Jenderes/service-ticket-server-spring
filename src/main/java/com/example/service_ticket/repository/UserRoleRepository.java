package com.example.service_ticket.repository;

import com.example.service_ticket.entity.UserEntity;
import com.example.service_ticket.entity.UserRoleEntity;
import com.sample.model.Tables;
import com.sample.model.tables.records.UserRoleRecord;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRoleRepository implements BaseRepository<UserRoleEntity, Long> {

    private final DSLContext dslContext;

    @Override
    public List<UserRoleEntity> findAll() {
        return dslContext.selectFrom(Tables.USER_ROLE)
                .fetch()
                .into(UserRoleEntity.class);
    }

    @Override
    public UserRoleEntity findById(Long id) {
        UserRoleRecord userRoleRecord = dslContext.selectFrom(Tables.USER_ROLE)
                .where(Tables.USER_ROLE.USER_ID.eq(id))
                .fetchOne();
        if (userRoleRecord == null)
            return null;
        return userRoleRecord.into(UserRoleEntity.class);
    }

    @Override
    public void delete(UserRoleEntity entity) {
        dslContext.delete(Tables.USER_ROLE)
                .where(Tables.USER_ROLE.ROLE_ID.eq(entity.getRoleId())
                .and(Tables.USER_ROLE.USER_ID.eq(entity.getUserId())))
                .execute();
    }

    @Override
    public void deleteById(Long id) {
        dslContext.delete(Tables.USER_ROLE)
                .where(Tables.USER_ROLE.USER_ID.eq(id))
                .execute();
    }

    @Override
    public UserRoleEntity update(UserRoleEntity entity) {
        return dslContext.update(Tables.USER_ROLE)
                .set(Tables.USER_ROLE.ROLE_ID, entity.getRoleId())
                .set(Tables.USER_ROLE.USER_ID, entity.getUserId())
                .where(Tables.USER_ROLE.USER_ID.eq(entity.getUserId()))
                .returning().fetchOne().into(UserRoleEntity.class);
    }

    @Override
    public UserRoleEntity save(UserRoleEntity entity) {
        return dslContext.insertInto(Tables.USER_ROLE)
                .set(Tables.USER_ROLE.ROLE_ID, entity.getRoleId())
                .set(Tables.USER_ROLE.USER_ID, entity.getUserId())
                .returning().fetchOne().into(UserRoleEntity.class);
    }
}
