package dao;

import db.tables.records.NomenclatureRecord;
import entities.Nomenclature;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import static db.Tables.NOMENCLATURE;

public class NomenclatureDao implements DAO<Nomenclature> {
    final Connection connection;

    public NomenclatureDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Nomenclature get(int inside_code) {
        DSLContext dslContext = DSL.using(connection, SQLDialect.POSTGRES_10);
        Result<Record> result = dslContext
                .select()
                .from(NOMENCLATURE)
                .where(NOMENCLATURE.INSIDE_CODE.eq(inside_code))
                .fetch();

        int ins_code;
        String name;

        final NomenclatureRecord nomenclatureRecord = result.get(0).into(NOMENCLATURE);
        ins_code = nomenclatureRecord.getValue(NOMENCLATURE.INSIDE_CODE);
        name = nomenclatureRecord.getValue(NOMENCLATURE.NAME);

        return new Nomenclature(name, ins_code);

    }

    @Override
    public List<Nomenclature> getAll() {
        final List<Nomenclature> nomenclatures = new ArrayList<>();
        DSLContext dslContext = DSL.using(connection, SQLDialect.POSTGRES_10);
        Result<Record> result = dslContext
                .select()
                .from(NOMENCLATURE)
                .fetch();

        for (Record r : result) {
            int inside_code;
            String name;

            final NomenclatureRecord nomenclatureRecord = r.into(NOMENCLATURE);
            inside_code = nomenclatureRecord.getValue(NOMENCLATURE.INSIDE_CODE);
            name = nomenclatureRecord.getValue(NOMENCLATURE.NAME);
            nomenclatures.add(new Nomenclature(name, inside_code));
        }
        return nomenclatures;
    }

    @Override
    public void save(Nomenclature entity) {
        DSLContext dslContext = DSL.using(connection, SQLDialect.POSTGRES_10);
        dslContext.insertInto(NOMENCLATURE, NOMENCLATURE.NAME, NOMENCLATURE.INSIDE_CODE)
                .values(entity.getName(), entity.getInside_code())
                .execute();
    }

    @Override
    public void update(Nomenclature entity) {
        DSLContext dslContext = DSL.using(connection, SQLDialect.POSTGRES_10);
        dslContext.update(NOMENCLATURE)
                .set(NOMENCLATURE.NAME, entity.getName())
                .where(NOMENCLATURE.INSIDE_CODE.eq(entity.getInside_code()))
                .execute();
    }

    @Override
    public void delete(Nomenclature entity) {
        DSLContext dslContext = DSL.using(connection, SQLDialect.POSTGRES_10);
        dslContext.delete(NOMENCLATURE)
                .where(NOMENCLATURE.INSIDE_CODE.eq(entity.getInside_code()))

                .execute();
    }
}
