package com.app.service;

import com.app.repository.SaleRepository;
import com.app.DBentities.Sale;
import com.app.model.DBRequestError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SaleServiceImpl implements SaleService {

    @Autowired
    private SaleRepository saleRepository;

    //делаем возврат метода типа Object чтобы вернуть страницу случае ошибки
    @Override
    public Sale save(Sale sale) {
        return saleRepository.save(sale);
    }

    @Override
    public Object updateSale(Sale sale, long id) {
        Optional<Sale> saleData = saleRepository.findById(id);

        //если существует найденная сущность с таким id
        if (saleData.isPresent()) {
            Sale newSale = saleData.get();
            newSale.setName(sale.getName());
            newSale.setLastName(sale.getLastName());
            newSale.setAge(sale.getAge());
            newSale.setPurchase_item(sale.getPurchase_item());
            newSale.setCount(sale.getCount());
            newSale.setAmount(sale.getAmount());
            newSale.setPurchase_date(sale.getPurchase_date());
            return saleRepository.save(newSale);
        }
        //если не существует найденная сущность с таким id
        else {
            DBRequestError mistake = new DBRequestError();
            mistake.setError("try to update sale with NOT_FOUND id: '" + id + "'");
            return mistake;
        }
    }

    @Override
    public List<Sale> getSalesByName(String name) {

        List<Sale> SaleData = saleRepository.findAllByName(name);
        //если существует найденная сущность с таким name
        if (SaleData != null) {
            return SaleData;
            //если не существует найденная сущность с таким name
        } else {
            return null;
        }

    }

    @Override
    public Object getSaleById(long id) {

        Optional<Sale> SaleData = saleRepository.findById(id);

        //если существует найденная сущность с таким id
        if (SaleData.isPresent()) {
            return SaleData.get();
        }
        //если не существует найденная сущность с таким id
        else {
            DBRequestError mistake = new DBRequestError();
            mistake.setError("can't find sale with id: '" + id + "'");
            return mistake;
        }

    }

    @Override
    public Object deleteSaleById(Long id) {

        Optional<Sale> SaleData = saleRepository.findById(id);

        //если существует найденная сущность с таким id
        if (SaleData.isPresent()) {
            saleRepository.deleteById(id);
            return null;
        }
        //если не существует найденная сущность с таким id
        else {
            DBRequestError mistake = new DBRequestError();
            mistake.setError("try to delete sale with NOT_FOUND id: '" + id + "'");
            return mistake;
        }

    }

    @Override
    public void deleteAll() {
        saleRepository.deleteAll();
    }

    @Override
    public List<Sale> getAll() {
        return saleRepository.findAll();
    }

    @Override
    public List<Sale> getSaleListLastWeek() {
        return saleRepository.findSaleListLastWeek();
    }

    @Override
    public List<Sale> getSaleListMostPopularLastMonth() {
        return saleRepository.findSaleListMostPopularLastMonth();
    }

    @Override
    public List<Sale> getSaleListMostBuyingLastHalfAYear() {
        return saleRepository.findSaleListMostBuyingLastHalfAYear();
    }

    @Override
    public List<Sale> getSaleListMostBuyingBy18YearOld() {
        return saleRepository.findSaleListMostBuyingBy18YearOld();
    }

}