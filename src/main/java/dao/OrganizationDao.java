package dao;

import db.tables.records.OrganizationRecord;
import entities.Organization;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import static db.Tables.ORGANIZATION;

public final class OrganizationDao implements DAO<Organization> {
    final Connection connection;

    public OrganizationDao(Connection connection) {
        this.connection = connection;
    }

    @Override //Получить организацию по инн
    public Organization get(int inn) {
        DSLContext dslContext = DSL.using(connection, SQLDialect.POSTGRES_10);
        Result<Record> result = dslContext
                .select()
                .from(ORGANIZATION)
                .where(ORGANIZATION.INN.eq(inn))
                .fetch();
        String name;
        int innR;
        long payment_account;

        final OrganizationRecord organizationRecord = result.get(0).into(ORGANIZATION);
        innR = organizationRecord.getValue(ORGANIZATION.INN);
        name = organizationRecord.getValue(ORGANIZATION.NAME);
        payment_account = organizationRecord.getValue(ORGANIZATION.PAYMENT_ACCOUNT);
        return new Organization(name, innR, payment_account);

    }

    @Override
    public List<Organization> getAll() {
        final List<Organization> orgs = new ArrayList<>();
        DSLContext dslContext = DSL.using(connection, SQLDialect.POSTGRES_10);
        Result<Record> result = dslContext
                .select()
                .from(ORGANIZATION)
                .fetch();
        for (Record r : result){
            String name;
            int innR;
            long payment_account;

            final OrganizationRecord organizationRecord = r.into(ORGANIZATION);
            innR = organizationRecord.getValue(ORGANIZATION.INN);
            name = organizationRecord.getValue(ORGANIZATION.NAME);
            payment_account = organizationRecord.getValue(ORGANIZATION.PAYMENT_ACCOUNT);
            orgs.add(new Organization(name, innR, payment_account));
        }

        return orgs;
    }

    @Override
    public void save(Organization entity) {

        DSLContext dslContext = DSL.using(connection, SQLDialect.POSTGRES_10);
        dslContext.insertInto(ORGANIZATION, ORGANIZATION.NAME, ORGANIZATION.INN, ORGANIZATION.PAYMENT_ACCOUNT)
                .values(entity.getName(), entity.getInn(),entity.getPayment_account())
                .execute();

    }

    @Override //Обновить данные органищации с некоторым инн
    public void update(Organization entity) {
        DSLContext dslContext = DSL.using(connection, SQLDialect.POSTGRES_10);
        dslContext.update(ORGANIZATION)
                .set(ORGANIZATION.NAME, entity.getName())
                .set(ORGANIZATION.PAYMENT_ACCOUNT, entity.getPayment_account())
                .where(ORGANIZATION.INN.eq(entity.getInn()))
                .execute();
    }

    @Override
    public void delete(Organization entity) {
        DSLContext dslContext = DSL.using(connection, SQLDialect.POSTGRES_10);
        dslContext.delete(ORGANIZATION)
                .where(ORGANIZATION.INN.eq(entity.getInn()))
                .execute();
    }
}
