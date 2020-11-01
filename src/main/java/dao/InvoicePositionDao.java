package dao;

import db.tables.records.InvoicepositionsRecord;
import entities.InvoicePosition;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import static db.Tables.INVOICEPOSITIONS;

public class InvoicePositionDao implements DAO<InvoicePosition> {
    final Connection connection;

    public InvoicePositionDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public InvoicePosition get(int id) {

        DSLContext dslContext = DSL.using(connection, SQLDialect.POSTGRES_10);
        Result<Record> result = dslContext
                .select()
                .from(INVOICEPOSITIONS)
                .where(INVOICEPOSITIONS.ID.eq(id))
                .fetch();

        int id1;
        long price;
        String nomenclature_name;
        int count;

        final InvoicepositionsRecord invoicepositionsRecord = result.get(0).into(INVOICEPOSITIONS);
        id1 = invoicepositionsRecord.getValue(INVOICEPOSITIONS.ID);
        price = invoicepositionsRecord.getValue(INVOICEPOSITIONS.PRICE);
        nomenclature_name = invoicepositionsRecord.getValue(INVOICEPOSITIONS.NOMENCLATURE_NAME);
        count = invoicepositionsRecord.getValue(INVOICEPOSITIONS.COUNT);

        return new InvoicePosition(id1, price, nomenclature_name, count);
    }

    @Override
    public List<InvoicePosition> getAll() {
        final List<InvoicePosition> invoicePositions = new ArrayList<>();
        DSLContext dslContext = DSL.using(connection, SQLDialect.POSTGRES_10);
        Result<Record> result = dslContext
                .select()
                .from(INVOICEPOSITIONS)
                .fetch();

        for (Record r : result) {
            int id1;
            long price;
            String nomenclature_name;
            int count;

            final InvoicepositionsRecord invoicepositionsRecord = r.into(INVOICEPOSITIONS);
            id1 = invoicepositionsRecord.getValue(INVOICEPOSITIONS.ID);
            price = invoicepositionsRecord.getValue(INVOICEPOSITIONS.PRICE);
            nomenclature_name = invoicepositionsRecord.getValue(INVOICEPOSITIONS.NOMENCLATURE_NAME);
            count = invoicepositionsRecord.getValue(INVOICEPOSITIONS.COUNT);
            ;

            invoicePositions.add(new InvoicePosition(id1, price, nomenclature_name, count));
        }

        return invoicePositions;

    }

    @Override
    public void save(InvoicePosition entity) {
        DSLContext dslContext = DSL.using(connection, SQLDialect.POSTGRES_10);
        dslContext.insertInto(INVOICEPOSITIONS, INVOICEPOSITIONS.ID, INVOICEPOSITIONS.PRICE, INVOICEPOSITIONS.NOMENCLATURE_NAME, INVOICEPOSITIONS.COUNT)
                .values(entity.getId(), entity.getPrice(), entity.getNomenclature_name(), entity.getCount())
                .execute();

    }

    @Override
    public void update(InvoicePosition entity) {
        DSLContext dslContext = DSL.using(connection, SQLDialect.POSTGRES_10);
        dslContext.update(INVOICEPOSITIONS)
                .set(INVOICEPOSITIONS.PRICE, entity.getPrice())
                .set(INVOICEPOSITIONS.NOMENCLATURE_NAME, entity.getNomenclature_name())
                .set(INVOICEPOSITIONS.COUNT, entity.getCount())
                .where(INVOICEPOSITIONS.ID.eq(entity.getId()))
                .execute();
    }

    @Override
    public void delete(InvoicePosition entity) {
        DSLContext dslContext = DSL.using(connection, SQLDialect.POSTGRES_10);
        dslContext.delete(INVOICEPOSITIONS)
                .where(INVOICEPOSITIONS.ID.eq(entity.getId()))
                .execute();
    }

}
