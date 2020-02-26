package com.app.repository;

import com.app.DBentities.Sale;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleRepository extends CrudRepository<Sale, Long> {

    List<Sale> findAllByName(String name);

    @Query("SELECT e FROM Sale e")
    List<Sale> findAll();

    /*Вывести список покупок за последнюю неделю*/
    /*выборка всех полей из таблицы*/
    @Query(value = "SELECT * FROM sales.sale\n" +
            "WHERE `sale_purchase_date` >= DATE_SUB(curdate(), Interval 7 Day)\n" +
            "AND `sale_purchase_date` <= curdate()\n" +
            "ORDER BY 'sale_purchase_date'\n" +
            "LIMIT 100", nativeQuery = true)
    List<Sale> findSaleListLastWeek();

    /*Вывести самый покупаемый товар за последний месяц*/
    /*выборка всех полей из таблицы*/
    @Query(value = "SELECT * FROM sales.sale\n" +
            "WHERE `sale_purchase_date` >= DATE_SUB(curdate(), INTERVAL 1 MONTH)\n" +
            "AND `sale_purchase_date` <= CURDATE()\n" +
            "GROUP BY sale_purchase_item\n" +
            "HAVING COUNT(sale_purchase_item) =\n" +
            "(SELECT MAX(cf_num)\n" +
            "           FROM (\n" +
            "          SELECT COUNT(sale_purchase_item), COUNT(*) AS cf_num FROM sales.sale\n" +
            "          WHERE sale_purchase_date >= DATE_SUB(curdate(), INTERVAL 1 MONTH)\n" +
            "\t\t\tAND sale_purchase_date <= CURDATE()\n" +
            "\t\t\tGROUP BY sale_purchase_item\n" +
            "              ) t1 )", nativeQuery = true)
    List<Sale> findSaleListMostPopularLastMonth();

    /*Вывести имя и фамилию человека, совершившего больше всего покупок за полгода*/
    /*выборка всех полей из таблицы*/
    @Query(value = "SELECT * FROM sales.sale\n" +
            "WHERE sale_purchase_date >= DATE_SUB(CURDATE(), INTERVAL 6 MONTH)\n" +
            "AND sale_purchase_date <= CURDATE()\n" +
            "GROUP BY sale_name, sale_last_name\n" +
            "HAVING COUNT(sale_name) =\n" +
            "(SELECT MAX(cf_num)\n" +
            "           FROM (\n" +
            "          SELECT COUNT(sale_name), COUNT(sale_last_name) AS cf_num FROM sales.sale\n" +
            "          WHERE sale_purchase_date >= DATE_SUB(curdate(), INTERVAL 6 MONTH)\n" +
            "\t\t\tAND sale_purchase_date <= CURDATE()\n" +
            "\t\t\tGROUP BY sale_name, sale_last_name\n" +
            "              ) t1 )", nativeQuery = true)
    List<Sale> findSaleListMostBuyingLastHalfAYear();

    /*Вывести Что чаще всего покупают люди в возрасте 18 лет*/
    /*выборка всех полей из таблицы*/
    @Query(value = "SELECT * " +
            "FROM sales.sale " +
            "WHERE sale_age = 18 " +
            "GROUP BY sale_purchase_item " +
            "HAVING COUNT(*) = (SELECT MAX(cf_num) " +
            "           FROM (\n" +
            "            SELECT sale_purchase_item, COUNT(*) AS cf_num " +
            "            FROM sales.sale " +
            "            WHERE sale_age = 18 " +
            "            GROUP BY sale_purchase_item " +
            "              ) t1 )", nativeQuery = true)
    List<Sale> findSaleListMostBuyingBy18YearOld();

}
