package com.example.service_ticket.repository;

import com.example.service_ticket.entity.RoleEntity;
import com.example.service_ticket.entity.UserEntity;
import com.example.service_ticket.entity.UserRoleEntity;
import com.sample.model.Tables;
import com.sample.model.tables.User;
import com.sample.model.tables.records.RoleRecord;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Table;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class RoleRepository implements BaseRepository<RoleEntity, Long>{

    private final DSLContext dslContext;
    private final UserRepository userRepository;

    @Override
    public List<RoleEntity> findAll() {
        return dslContext.selectFrom(Tables.ROLE)
                .fetch()
                .into(RoleEntity.class);
    }

    @Override
    public RoleEntity findById(Long id) {
        RoleRecord roleRecord = dslContext.selectFrom(Tables.ROLE)
                .where(Tables.ROLE.ROLE_ID.eq(id))
                .fetchOne();
        if (roleRecord == null)
            return null;
        return roleRecord.into(RoleEntity.class);
    }

    @Override
    public void delete(RoleEntity entity) {
        dslContext.delete(Tables.ROLE)
                .where(Tables.ROLE.ROLE_ID.eq(entity.getRoleId()))
                .execute();
    }

    @Override
    public void deleteById(Long id) {
        dslContext.delete(Tables.ROLE)
                .where(Tables.ROLE.ROLE_ID.eq(id))
                .execute();
    }

    @Override
    public void update(RoleEntity entity) {
        dslContext.update(Tables.ROLE)
                .set(Tables.ROLE.ROLE_NAME, entity.getRoleName())
                .execute();
    }

    @Override
    public void save(RoleEntity entity) {
        dslContext.insertInto(Tables.ROLE)
                .set(Tables.ROLE.ROLE_NAME, entity.getRoleName())
                .execute();
    }

    public List<RoleEntity> findRolesByUserName(String username){
        UserEntity user = userRepository.findByUsername(username);
        List<UserRoleEntity> userRoleEntities = dslContext.selectFrom(Tables.USER_ROLE)
                .where(Tables.USER_ROLE.USER_ID.eq(user.getUserId()))
                .fetch()
                .into(UserRoleEntity.class);
        List<Long> RoleID = userRoleEntities.stream().map(UserRoleEntity::getRoleId).collect(Collectors.toList());
        return dslContext.selectFrom(Tables.ROLE)
                    .where(Tables.ROLE.ROLE_ID.in(RoleID))
                    .fetch()
                    .into(RoleEntity.class);
    }
}
