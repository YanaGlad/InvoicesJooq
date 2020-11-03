import dao.InvoiceDao;
import dao.InvoicePositionDao;
import dao.NomenclatureDao;
import dao.OrganizationDao;
import entities.Invoice;
import entities.InvoicePosition;
import entities.Nomenclature;
import entities.Organization;
import org.flywaydb.core.Flyway;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public final class Main {
    private static final String URL = "jdbc:postgresql://localhost:5432/Finance";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "postgres";

    public static void main(String[] args) {
        final Flyway flyway = Flyway.configure()
                .dataSource("jdbc:postgresql://localhost:5432/Finance", "postgres", "money10")
                .locations("db")
                .load();
        flyway.clean();
        flyway.migrate();

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            System.out.println("Connection Ok.");

            //Примеры работы CRUD
            final OrganizationDao organizationDAO = new OrganizationDao(connection);

            organizationDAO.save(new Organization("Company1", 2234, 12000));
            organizationDAO.save(new Organization("Company2", 2342, 70000));
            organizationDAO.save(new Organization("Company3", 3, 50000));
            organizationDAO.save(new Organization("Company4", 4, 50000));
            organizationDAO.save(new Organization("Company5", 532, 50000));
            organizationDAO.save(new Organization("Company6", 21, 30000));
            organizationDAO.save(new Organization("Company7", 423, 50000));
            organizationDAO.save(new Organization("Company8", 123, 44522));
            organizationDAO.save(new Organization("Company9", 757, 50000));
            organizationDAO.save(new Organization("Company10", 456, 19000));
            organizationDAO.save(new Organization("Company11", 34, 15000));
            organizationDAO.save(new Organization("Company12", 234, 200000));


            for (Organization organization : organizationDAO.getAll()) {
                System.out.println("name : " + organization.getName() + " inn: " + organization.getInn() + " payment account: " + organization.getPayment_account());
            }


            final InvoiceDao invoiceDao = new InvoiceDao(connection);

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));
            calendar.setTimeInMillis(0);

            calendar.set(2020, Calendar.SEPTEMBER, 10, 1, 0, 0);
            long date = calendar.getTimeInMillis();
            invoiceDao.save(new Invoice(1, new Date(date), "Company3"));

            calendar.set(2020, Calendar.SEPTEMBER, 20, 1, 0, 0);
            date = calendar.getTimeInMillis();
            invoiceDao.save(new Invoice(2, new Date(date), "Company1"));

            calendar.set(2020, Calendar.SEPTEMBER, 12, 1, 0, 0);
            date = calendar.getTimeInMillis();
            invoiceDao.save(new Invoice(3, new Date(date), "Company2"));

            calendar.set(2020, Calendar.JANUARY, 2, 1, 0, 0);
            date = calendar.getTimeInMillis();
            invoiceDao.save(new Invoice(4, new Date(date), "Company4"));

            calendar.set(2020, Calendar.JUNE, 27, 1, 0, 0);
            date = calendar.getTimeInMillis();
            invoiceDao.save(new Invoice(5, new Date(date), "Company5"));

            calendar.set(2020, Calendar.MARCH, 14, 1, 0, 0);
            date = calendar.getTimeInMillis();
            invoiceDao.save(new Invoice(6, new Date(date), "Company5"));

            calendar.set(2020, Calendar.APRIL, 10, 1, 0, 0);
            date = calendar.getTimeInMillis();
            invoiceDao.save(new Invoice(7, new Date(date), "Company7"));

            calendar.set(2020, Calendar.SEPTEMBER, 5, 1, 0, 0);
            date = calendar.getTimeInMillis();
            invoiceDao.save(new Invoice(8, new Date(date), "Company8"));

            calendar.set(2020, Calendar.JULY, 22, 1, 0, 0);
            date = calendar.getTimeInMillis();
            invoiceDao.save(new Invoice(9, new Date(date), "Company9"));

            calendar.set(2019, Calendar.DECEMBER, 20, 1, 0, 0);
            date = calendar.getTimeInMillis();
            invoiceDao.save(new Invoice(10, new Date(date), "Company10"));

            calendar.set(2019, Calendar.NOVEMBER, 1, 1, 0, 0);
            date = calendar.getTimeInMillis();
            invoiceDao.save(new Invoice(11, new Date(date), "Company11"));

            calendar.set(2018, Calendar.SEPTEMBER, 12, 1, 0, 0);
            date = calendar.getTimeInMillis();
            invoiceDao.save(new Invoice(12, new Date(date), "Company12"));

            for (Invoice invoice : invoiceDao.getAll()) {
                System.out.println("id : " + invoice.getId() + " date: " + invoice.getOrganization_date() + " company: " + invoice.getOrganization_name());
            }

            final NomenclatureDao nomenclatureDao = new NomenclatureDao(connection);

            nomenclatureDao.save(new Nomenclature("nomenclature1", 3245));
            nomenclatureDao.save(new Nomenclature("nomenclature2", 5463));
            nomenclatureDao.save(new Nomenclature("nomenclature3", 9545));
            nomenclatureDao.save(new Nomenclature("nomenclature4", 1231));
            nomenclatureDao.save(new Nomenclature("nomenclature5", 3243));
            nomenclatureDao.save(new Nomenclature("nomenclature6", 1239));
            nomenclatureDao.save(new Nomenclature("nomenclature7", 2221));
            nomenclatureDao.save(new Nomenclature("nomenclature8", 2411));
            nomenclatureDao.save(new Nomenclature("nomenclature9", 1208));
            nomenclatureDao.save(new Nomenclature("nomenclature10", 1234));
            nomenclatureDao.save(new Nomenclature("nomenclature11", 2904));
            nomenclatureDao.save(new Nomenclature("nomenclature12", 2303));

            for (Nomenclature nomenclature : nomenclatureDao.getAll()) {
                System.out.println("name : " + nomenclature.getName() + " inside code: " + nomenclature.getInside_code());
            }

            final InvoicePositionDao invoicePositionDao = new InvoicePositionDao(connection);

            invoicePositionDao.save(new InvoicePosition(4, 8000, "nomenclature5", 2));
            invoicePositionDao.save(new InvoicePosition(2, 8000, "nomenclature2", 30));
            invoicePositionDao.save(new InvoicePosition(1, 1000, "nomenclature3", 23));
            invoicePositionDao.save(new InvoicePosition(3, 5000, "nomenclature3", 12));
            invoicePositionDao.save(new InvoicePosition(5, 5000, "nomenclature2", 6));
            invoicePositionDao.save(new InvoicePosition(6, 5000, "nomenclature6", 7));
            invoicePositionDao.save(new InvoicePosition(7, 5000, "nomenclature7", 11));
            invoicePositionDao.save(new InvoicePosition(8, 5000, "nomenclature2", 6));
            invoicePositionDao.save(new InvoicePosition(9, 5000, "nomenclature9", 31));
            invoicePositionDao.save(new InvoicePosition(10, 5000, "nomenclature3", 23));
            invoicePositionDao.save(new InvoicePosition(11, 5000, "nomenclature11", 43));
            invoicePositionDao.save(new InvoicePosition(12, 5000, "nomenclature3", 1));

            for (InvoicePosition invoicePosition : invoicePositionDao.getAll()) {
                System.out.println("id: " + invoicePosition.getId() + " price: " + invoicePosition.getPrice() + " nomenclature name: " + invoicePosition.getNomenclature_name() + " count: " + invoicePosition.getCount());
            }


            List<Organization> firstTenOrganizations = getFirstTenOrganizationsWithTopCount(invoicePositionDao, invoiceDao, organizationDAO);

            System.out.println("первые 10 поставщиков по количеству поставленного товара: ");
            for (Organization org : firstTenOrganizations) {
                System.out.println("Name: " + org.getName() + " inn: " + org.getInn() + " payment account: " + org.getPayment_account());
            }

            List<Organization> chosenOrgs = chooseOrganizations(connection,20, "nomenclature3" );

            System.out.println("поставщики с суммой поставленного товара выше указанного количества: ");
            for (Organization org : chosenOrgs) {
                System.out.println("Name: " + org.getName() + " inn: " + org.getInn() + " payment account: " + org.getPayment_account());
            }

            calendar.set(2020, Calendar.SEPTEMBER, 1, 1, 0, 0);
            date = calendar.getTimeInMillis();
            Date date1 = new Date(date);
            calendar.set(2020, Calendar.OCTOBER, 31, 1, 0, 0);
            date = calendar.getTimeInMillis();
            Date date2 = new Date(date);

            countTotal(connection, date1, date2);

            System.out.println("Средняя цена полученного товара за период");
            System.out.println(countAveragePrice(connection, date1, date2));
            System.out.println();

            printOrganizationProducts(connection);


        } catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
        }
    }

    //Вывести список товаров, поставленных организациями за период.
    // Если организация товары не поставляла, то она все равно должна быть отражена в списке.
    public static void printOrganizationProducts(Connection connection) {
        try (Statement stmt = connection.createStatement()) {
            ResultSet items = stmt.executeQuery("SELECT * FROM public.\"Invoice\"");

            while (items.next()) {
                System.out.println("Компания: " + items.getString(2));
                try (ResultSet rs = stmt.executeQuery("SELECT nomenclature_name  count FROM public.\"InvoicePositions\" WHERE id = " + items.getString(0))) {
                    System.out.println("Товар: " + rs.getString(0));
                }

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    //Рассчитать среднюю цену полученного товара за период
    public static double countAveragePrice(Connection connection, Date date1, Date date2) {
        double sum = 0, totalCount = 0;
        try (Statement stmt = connection.createStatement()) {
            ResultSet invoice = stmt.executeQuery("SELECT * FROM public.\"Invoice\"");
            while (invoice.next()) {
                ResultSet position = stmt.executeQuery("SELECT * FROM public.\"InvoicePositions\" WHERE id = " + invoice.getInt(0));
                if (invoice.getDate(1).before(date2) && invoice.getDate(1).after(date1)) {
                    sum += position.getLong(3); //сумма полученного товара
                    totalCount++; //С товара
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return sum / totalCount;
    }

    //За каждый день в разрезе номенклатур рассчитать количество
    // и сумму полученного товара всех организаций в указанном периоде, посчитать итоги за период
    public static void countTotal(Connection connection, Date date1, Date date2) {
        int sum = 0, totalCount = 0;
        try (Statement stmt = connection.createStatement()) {
            ResultSet invoice = stmt.executeQuery("SELECT * FROM public.\"Invoice\"");
            while (invoice.next()) {
                ResultSet position = stmt.executeQuery("SELECT * FROM public.\"InvoicePositions\" WHERE id = " + invoice.getInt(0));
                if (invoice.getDate(1).before(date2) && invoice.getDate(1).after(date1)) {
                    sum += position.getLong(3); //сумма полученного товара
                    totalCount += position.getLong(3); //С товара
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Сумма полученного товара за данный период: " + sum);
        System.out.println("Количество товара: " + totalCount);

    }

    public static Organization findOrganisationByInvoicePositionAll(List<InvoicePosition> allPos,
                                                                    InvoiceDao invoiceDao,
                                                                    OrganizationDao organizationDAO) {
        for (InvoicePosition pos : allPos) {
            Invoice currentInvoice = invoiceDao.get(pos.getId());
            for (int i = 0; i < organizationDAO.getAll().size(); i++) {
                if (organizationDAO.getAll().get(i).getName().equals(currentInvoice.getOrganization_name())) {
                    Organization result = organizationDAO.getAll().get(i);
                    allPos.remove(pos);
                    return result;
                }
            }
        }
        return null;
    }

    //Выбрать первые 10 поставщиков по количеству поставленного товара
    public static List<Organization> getFirstTenOrganizationsWithTopCount(InvoicePositionDao invoicePositionDao,
                                                                          InvoiceDao invoiceDao,
                                                                          OrganizationDao organizationDAO) {
        List<Organization> result = new ArrayList<>();
        List<InvoicePosition> allPos = invoicePositionDao.getAll();

        Collections.sort(allPos, InvoicePosition::compareTo);

        int total = invoicePositionDao.getAll().size();
        if (total > 10)
            total = 10;

        System.out.println("total = " + total);
        int c = 1;
        while (c <= total) {
            result.add(findOrganisationByInvoicePositionAll(allPos, invoiceDao, organizationDAO));
            c++;
        }
        return result;
    }

    //Выбрать поставщиков с суммой поставленного товара выше указанного количества
    public static List<Organization> chooseOrganizations(Connection connection, int count, String name ) {
        List<Organization> result = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet positions = stmt.executeQuery("SELECT * FROM public.\"InvoicePositions\"");
            while (positions.next()) {
                ResultSet invoice = stmt.executeQuery("SELECT * FROM public.\"Invoice\" WHERE id = " + positions.getInt(0));
                if (positions.getString(2).equals(name) && positions.getLong(3) > count) {
                    ResultSet org = stmt.executeQuery("SELECT * FROM public.\"Organization\" WHERE name = " + invoice.getString(2));
                    result.add(new Organization(org.getString(0), org.getInt(1), org.getLong(2)));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
}