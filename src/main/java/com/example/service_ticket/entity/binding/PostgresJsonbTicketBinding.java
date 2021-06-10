package com.example.service_ticket.entity.binding;

import com.example.service_ticket.entity.TicketInformationEntity;
import org.jooq.*;
import org.jooq.conf.ParamType;
import org.jooq.impl.DSL;

import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.Types;
import java.util.Objects;

public class PostgresJsonbTicketBinding implements Binding<JSONB, TicketInformationEntity> {
    @Override
    public Converter<JSONB, TicketInformationEntity> converter() {
        return new PostgresJsonbTicketConverter();
    }

    @Override
    public void sql(BindingSQLContext<TicketInformationEntity> bindingSQLContext) throws SQLException {
        if (bindingSQLContext.render().paramType() == ParamType.INLINED)
            bindingSQLContext.render().visit(DSL.inline(bindingSQLContext.convert(converter()).value())).sql("::json");
        else
            bindingSQLContext.render().sql("?::json");
    }

    @Override
    public void register(BindingRegisterContext<TicketInformationEntity> bindingRegisterContext) throws SQLException {
        bindingRegisterContext.statement().registerOutParameter(bindingRegisterContext.index(), Types.VARCHAR);
    }

    @Override
    public void set(BindingSetStatementContext<TicketInformationEntity> bindingSetStatementContext) throws SQLException {
        bindingSetStatementContext.statement().setString(bindingSetStatementContext.index(), Objects.toString(bindingSetStatementContext.convert(converter()).value(), null));
    }

    @Override
    public void set(BindingSetSQLOutputContext<TicketInformationEntity> bindingSetSQLOutputContext) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void get(BindingGetResultSetContext<TicketInformationEntity> bindingGetResultSetContext) throws SQLException {
        bindingGetResultSetContext.convert(converter()).value(JSONB.valueOf(bindingGetResultSetContext.resultSet().getString(bindingGetResultSetContext.index())));
    }

    @Override
    public void get(BindingGetStatementContext<TicketInformationEntity> bindingGetStatementContext) throws SQLException {
        bindingGetStatementContext.convert(converter()).value(JSONB.valueOf(bindingGetStatementContext.statement().getString(bindingGetStatementContext.index())));
    }

    @Override
    public void get(BindingGetSQLInputContext<TicketInformationEntity> bindingGetSQLInputContext) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
}
