package dao;

import db.tables.records.InvoiceRecord;
import entities.Invoice;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import java.sql.*;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import static db.Tables.INVOICE;

public class InvoiceDao implements DAO<Invoice> {
    final Connection connection;

    public InvoiceDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Invoice get(int id) {
        DSLContext dslContext = DSL.using(connection, SQLDialect.POSTGRES_10);
        Result<Record> result = dslContext
                .select()
                .from(INVOICE)
                .where(INVOICE.ID.eq(id))
                .fetch();

        int id1;
        Date organization_date;
        String organization_name;

        final InvoiceRecord invoiceRecord = result.get(0).into(INVOICE);
        id1 = invoiceRecord.getValue(INVOICE.ID);
        organization_date = invoiceRecord.getValue(INVOICE.ORGANIZATION_DATE);
        organization_name = invoiceRecord.getValue(INVOICE.ORGANIZATION_NAME);

        return new Invoice(id1, organization_date, organization_name);

    }

    @Override
    public List<Invoice> getAll() {
        final List<Invoice> invoices = new ArrayList<>();
        DSLContext dslContext = DSL.using(connection, SQLDialect.POSTGRES_10);
        Result<Record> result = dslContext
                .select()
                .from(INVOICE)
                .fetch();

        for (Record r : result) {
            int id1;
            Date organization_date;
            String organization_name;

            final InvoiceRecord invoiceRecord = r.into(INVOICE);
            id1 = invoiceRecord.getValue(INVOICE.ID);
            organization_date = invoiceRecord.getValue(INVOICE.ORGANIZATION_DATE);
            organization_name = invoiceRecord.getValue(INVOICE.ORGANIZATION_NAME);

            invoices.add(new Invoice(id1, organization_date, organization_name));
        }

        return invoices;
    }

    @Override
    public void save(Invoice entity) {
        DSLContext dslContext = DSL.using(connection, SQLDialect.POSTGRES_10);
        dslContext.insertInto(INVOICE, INVOICE.ID, INVOICE.ORGANIZATION_DATE, INVOICE.ORGANIZATION_NAME)
                .values(entity.getId(), entity.getOrganization_date(), entity.getOrganization_name())
                .execute();
    }

    @Override
    public void update(Invoice entity) {
        DSLContext dslContext = DSL.using(connection, SQLDialect.POSTGRES_10);
        dslContext.update(INVOICE)
                .set(INVOICE.ORGANIZATION_DATE, entity.getOrganization_date())
                .set(INVOICE.ORGANIZATION_NAME, entity.getOrganization_name())
                .where(INVOICE.ID.eq(entity.getId()))
                .execute();
    }

    @Override
    public void delete(Invoice entity) {
        DSLContext dslContext = DSL.using(connection, SQLDialect.POSTGRES_10);
        dslContext.delete(INVOICE)
                .where(INVOICE.ID.eq(entity.getId()))
                .execute();
    }
}
