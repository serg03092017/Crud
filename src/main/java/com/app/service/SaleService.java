package com.app.service;

import com.app.DBentities.Sale;

import java.util.List;

public interface SaleService {

    //делаем Object чтобы вернуть страницу ошибки в случае
    // неудачного добавления сущности с повторяющимся именем

    List<Sale> getSalesByName(String name);

    Object getSaleById(long id);

    List<Sale> getAll();

    List<Sale> getSaleListLastWeek();

    List<Sale> getSaleListMostPopularLastMonth();

    List<Sale> getSaleListMostBuyingLastHalfAYear();

    List<Sale> getSaleListMostBuyingBy18YearOld();

    Sale save(Sale sale);

    Object updateSale(Sale sale, long id);

    Object deleteSaleById(Long id);

    void deleteAll();
}
