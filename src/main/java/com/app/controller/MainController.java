package com.app.controller;


import com.app.DBentities.Sale;
import com.app.logic.xmlValidation.XMLValidator;
import com.app.logic.convertEnumAndDate.ConversionPurchase_ItemAndPurchase_Date;
import com.app.logic.fileOperations.DeleteFile;
import com.app.logic.formattingData.ViewListFormattedDate;
import com.app.logic.processingRequest.ProcessingSaleXmlRequest;
import com.app.model.*;
import com.app.xml.entity.PurchaseItemEnum;
import com.app.logic.xmlCreation.DOMxmlWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Controller
@Transactional
@RequestMapping(value = "/api")
public class MainController {

    //DataSource
    @Autowired
    private com.app.service.SaleService saleService;

    //http://localhost:8080/api/sales  || http://localhost:8080/api/sales?name=bf
    @GetMapping("/sales")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public String showAPISales(Map<String, Object> model, @RequestParam(required = false) String name) {

        try {
            List<com.app.DBentities.Sale> sales = new ArrayList<>();

            if (name == null)
                http://localhost:8080/api/sales?name=bf
                        saleService.getAll().forEach(sales::add);
            else
                //http://localhost:8080/api/sales
                saleService.getSalesByName(name).forEach(sales::add);

            if (sales.isEmpty()) {
                ResponceMessage message = new ResponceMessage();
                message.setMessage("NO_CONTENT");
                model.put("message", message);
                return "message";
            }

            ViewListFormattedDate viewListFormattedDate = new ViewListFormattedDate();
            List<DTOAllFormattedDate> listForModel = viewListFormattedDate.createListForView(sales);
            model.put("sales", listForModel);
            return "main";

        } catch (Exception e) {
            DBRequestError dbRequestError = new DBRequestError();
            dbRequestError.setError("Interanal_Server_Error");
            model.put("error", dbRequestError);
            return "error";
        }

    }

    //http://localhost:8080/api/sale/185
    @GetMapping("/sale/{id}")
    @Secured("ROLE_ADMIN")
    public String showAPISaleById(Map<String, Object> model, @PathVariable("id") long id) {

        Object result = saleService.getSaleById(id);
        if (result instanceof com.app.DBentities.Sale) {

            com.app.DBentities.Sale sale = (com.app.DBentities.Sale) result;
            ViewListFormattedDate viewListFormattedDate = new ViewListFormattedDate();
            DTOAllFormattedDate valueForModel = viewListFormattedDate.createOneStringForView(sale);
            model.put("sales", valueForModel);
            return "main";

        } else if (result instanceof DBRequestError) {
            model.put("error", result);
            return "error";
        }
        return null;
    }

    //http://localhost:8080/api/sale
    @PostMapping("/sale")
    @Secured("ROLE_ADMIN")
    @Consumes({MediaType.APPLICATION_XML})
    public String addAPISale(@RequestBody Map<String, Object> input,
                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "dd-MM-yyyy") Date purchase_date,
                             Map<String, Object> model,
                             HttpServletRequest request) throws IOException, SAXException, ParseException, DatatypeConfigurationException {
        List<String> mistakes = new ArrayList();

        try {
            //если нет тега <sale>, то не создаем xml файл, т.к. отсутствует корневой тэг
            if (input.size() == 0) {
                //System.out.println("Неправильный формат содержания покупки: " + "Invalid content One of '{sale}' is expected");
                mistakes.add("Неправильный формат содержания покупки: " + "Invalid content One of '{sale}' is expected");
            } else {
                //сначала валидируем по xsd, если успешно, то заносим в Database, если неуспешно,
                //то ищем ошибку в вводе данных

                //создаём xml файл
                DOMxmlWriter doMxmlWriter = new DOMxmlWriter();

                HttpSession session = request.getSession();
                String keySessionId = session.getId();
                //Тут создадим уникальный ключ для создания xml файла из запроса
                String XML_FILE = "src/main/resources/saleCreate" + keySessionId + ".xml";
                //keySessionId исключает коллизии во время сессии
                doMxmlWriter.create(input, XML_FILE);
                //завершено создание xml файла

                //теперь валидируем
                XMLValidator xmlValidator = new XMLValidator();
                String SCHEMA_FILE = "src/main/resources/sale.xsd";
                boolean resultValidation = xmlValidator.valid(XML_FILE, SCHEMA_FILE);

                //удалим созданный файл
                new DeleteFile(XML_FILE);

                if (resultValidation == true) {

                    // return saleDAO.updateEmployee(sale);

                    String nameReq = (String) input.get("name");
                    String lastNameReq = (String) input.get("lastName");
                    String ageReq = (String) input.get("age");
                    String purchase_itemReq = (String) input.get("purchase_item");
                    String countReq = (String) input.get("count");
                    String amountReq = (String) input.get("amount");
                    String purchase_dateReq = (String) input.get("purchase_date");

                    String nameValid = (String) nameReq;
                    String lastNameValid = (String) lastNameReq;
                    int ageValid = Integer.parseInt(ageReq);
                    PurchaseItemEnum purchase_itemValid = PurchaseItemEnum.fromValue((String) purchase_itemReq);
                    int countValid = Integer.parseInt(countReq);
                    float amountValid = Float.parseFloat(amountReq);

                    //начинаем получение даты
                    XMLGregorianCalendar purchase_dateValid;
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = format.parse((String) purchase_dateReq);

                    GregorianCalendar cal = new GregorianCalendar();
                    cal.setTime(date);

                    XMLGregorianCalendar xmlGregCal = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
                    //получили дату
                    purchase_dateValid = xmlGregCal;


                    //подготавливаем данные для БД
                    //Для перевода типов даты и покупки
                    ConversionPurchase_ItemAndPurchase_Date conversionPurchase_itemAndPurchase_date = new ConversionPurchase_ItemAndPurchase_Date();

                    com.app.DBentities.Sale saleToAdd = new com.app.DBentities.Sale(
                            nameValid,
                            lastNameValid,
                            ageValid,
                            conversionPurchase_itemAndPurchase_date.convertXmlPurchase_itemToDbEntity(purchase_itemValid),
                            countValid,
                            amountValid,
                            conversionPurchase_itemAndPurchase_date.asDate(purchase_dateValid)
                    );

                    Object result = saleService.save(saleToAdd);

                    if (result instanceof com.app.DBentities.Sale) {
                    } else
                        //ошибка добавления в базу данных сущности с неуникальным именем
                        if (result instanceof DBRequestError) {
                            model.put("error", result);
                            return "error";
                        }

                } else {
                    //если присутствуют ошибки валидации, тогда валидируем и ищем ошибки
                    ProcessingSaleXmlRequest castSale = new ProcessingSaleXmlRequest();
                    castSale.casting(input);
                    mistakes.addAll(castSale.casting(input));
                }

            }

            //если валидация прошла успешно и в корне документа нет ошибок, то отобразим таблицу
            if (mistakes.size() == 0) {
                return "redirect:/api/sales";

            } else {

                List<Mistakes> listForModelWithMistakes = new ArrayList<>();

                int i = 0;

                for (String k : mistakes) {
                    i++;
                    System.out.println(k);
                    Mistakes mistake = new Mistakes();
                    mistake.setMistake(k);
                    mistake.setNumber(i);
                    listForModelWithMistakes.add(mistake);
                }

                model.put("mistakes", listForModelWithMistakes);

                return "mistakes";
            }
        } catch (Exception e) {
            DBRequestError dbRequestError = new DBRequestError();
            dbRequestError.setError("EXPECTATION_FAILED");
            model.put("error", dbRequestError);
            return "error";
        }
    }

    //http://localhost:8080/api/sale/165
    @PutMapping("/sale/{id}")
    @Secured("ROLE_ADMIN")
    @Consumes({MediaType.APPLICATION_XML})
    public String updateAPISaleById(@RequestBody Map<String, Object> input, @PathVariable("id") long id,
                                    Map<String, Object> model,
                                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "dd-MM-yyyy") Date purchase_date,
                                    HttpServletRequest request) throws DatatypeConfigurationException, IOException, SAXException, ParseException {
        List<String> mistakes = new ArrayList();


        //если нет тега <sale>, то не создаем xml файл, т.к. отсутствует корневой тэг
        if (input.size() == 0) {
            //System.out.println("Неправильный формат содержания покупки: " + "Invalid content One of '{sale}' is expected");
            mistakes.add("Неправильный формат содержания покупки: " + "Invalid content One of '{sale}' is expected");
        } else {
            //сначала валидируем по xsd, если успешно, то заносим в Database, если неуспешно,
            //то ищем ошибку в вводе данных

            //создаём xml файл
            DOMxmlWriter doMxmlWriter = new DOMxmlWriter();

            HttpSession session = request.getSession();
            String keySessionId = session.getId();
            //Тут создадим уникальный ключ для создания xml файла из запроса
            String XML_FILE = "src/main/resources/saleUpdateByName" + keySessionId + ".xml";
            //keySessionId исключает коллизии во время сессии
            doMxmlWriter.create(input, XML_FILE);
            //завершено создание xml файла

            //теперь валидируем
            XMLValidator xmlValidator = new XMLValidator();
            String SCHEMA_FILE = "src/main/resources/sale.xsd";
            boolean resultValidation = xmlValidator.valid(XML_FILE, SCHEMA_FILE);

            //удалим созданный файл
            new DeleteFile(XML_FILE);

            if (resultValidation == true) {

                //return saleDAO.updateEmployee(sale);

                String nameReq = (String) input.get("name");
                String lastNameReq = (String) input.get("lastName");
                String ageReq = (String) input.get("age");
                String purchase_itemReq = (String) input.get("purchase_item");
                String countReq = (String) input.get("count");
                String amountReq = (String) input.get("amount");
                String purchase_dateReq = (String) input.get("purchase_date");

                String nameValid = (String) nameReq;
                String lastNameValid = (String) lastNameReq;
                int ageValid = Integer.parseInt(ageReq);
                PurchaseItemEnum purchase_itemValid = PurchaseItemEnum.fromValue((String) purchase_itemReq);
                int countValid = Integer.parseInt(countReq);
                float amountValid = Float.parseFloat(amountReq);

                //начинаем получение даты
                XMLGregorianCalendar purchase_dateValid;
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date date = format.parse((String) purchase_dateReq);

                GregorianCalendar cal = new GregorianCalendar();
                cal.setTime(date);

                XMLGregorianCalendar xmlGregCal = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
                //получили дату
                purchase_dateValid = xmlGregCal;


                //подготавливаем данные для БД
                //Для перевода типов даты и покупки
                ConversionPurchase_ItemAndPurchase_Date conversionPurchase_itemAndPurchase_date = new ConversionPurchase_ItemAndPurchase_Date();

                com.app.DBentities.Sale saleToAdd = new com.app.DBentities.Sale(
                        nameValid,
                        lastNameValid,
                        ageValid,
                        conversionPurchase_itemAndPurchase_date.convertXmlPurchase_itemToDbEntity(purchase_itemValid),
                        countValid,
                        amountValid,
                        conversionPurchase_itemAndPurchase_date.asDate(purchase_dateValid)
                );

                //обновляем результат в БД
                Object result = saleService.updateSale(saleToAdd, id);

                if (result instanceof com.app.DBentities.Sale) {
                } else
                    //ошибка добавления в базу данных сущности с неуникальным именем
                    // или при отсутствии (задано, что при отсутствии ничего не обновляем)
                    if (result instanceof DBRequestError) {
                        model.put("error", result);
                        return "error";
                    }
            } else {
                //если присутствуют ошибки валидации, тогда валидируем и ищем ошибки
                ProcessingSaleXmlRequest castSale = new ProcessingSaleXmlRequest();
                castSale.casting(input);
                mistakes.addAll(castSale.casting(input));
            }

        }

        //если валидация прошла успешно и в корне документа нет ошибок, то отобразим таблицу
        if (mistakes.size() == 0) {
            return "redirect:/api/sales";

        } else {

            List<Mistakes> listForModelWithMistakes = new ArrayList<>();

            int i = 0;

            for (String k : mistakes) {
                i++;
                System.out.println(k);
                Mistakes mistake = new Mistakes();
                mistake.setMistake(k);
                mistake.setNumber(i);
                listForModelWithMistakes.add(mistake);
            }

            model.put("mistakes", listForModelWithMistakes);

            return "mistakes";
        }
    }

    //http://localhost:8080/api/sale/221
    @DeleteMapping("/sale/{id}")
    @Secured("ROLE_ADMIN")
    public String deleteAPISaleById(Map<String, Object> model, @PathVariable("id") long id) throws Exception {
        try {

            Object result = saleService.deleteSaleById(id);
            if (result == null) {
                return "redirect:/api/sales";
            } else if (result instanceof DBRequestError) {
                model.put("error", result);
                return "error";
            }
        } catch (Exception e) {
            DBRequestError dbRequestError = new DBRequestError();
            dbRequestError.setError("Interanal_Server_Error");
            model.put("error", dbRequestError);
            return "error";
        }

        return null;

    }

    //http://localhost:8080/api/sales
    @DeleteMapping("/sales")
    @Secured("ROLE_ADMIN")
    public String deleteAPIAllSale(Map<String, Object> model) {
        try {
            saleService.deleteAll();
            ResponceMessage message = new ResponceMessage();
            message.setMessage("NO_CONTENT");
            model.put("message", message);
            return "message";
        } catch (Exception e) {
            DBRequestError dbRequestError = new DBRequestError();
            dbRequestError.setError("EXPECTATION_FAILED");
            model.put("error", dbRequestError);
            return "error";
        }
    }

    //http://localhost:8080/saleListForLastWeek
    /*Вывести список покупок за последнюю неделю*/
    @GetMapping("/saleListForLastWeek")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public String getSaleListLastWeek(Map<String, Object> model) {

        List<com.app.DBentities.Sale> listWithAllProperties = saleService.getSaleListLastWeek();

        List<DTOSalePurchaseItem> list = new ArrayList<>();

        for (com.app.DBentities.Sale i : listWithAllProperties) {
            DTOSalePurchaseItem dto = new DTOSalePurchaseItem();
            dto.setPurchase_item(i.getPurchase_item());
            list.add(dto);
        }

        model.put("sales", list);
        return "saleListForLastWeek";

    }

    //http://localhost:8080/api/saleListMostPopularLastMonth
    /*Вывести самый покупаемый товар за последний месяц*/
    @GetMapping("/saleListMostPopularLastMonth")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public String getSaleListMostPopularLastMonth(Map<String, Object> model) {

        List<com.app.DBentities.Sale> listWithAllProperties = saleService.getSaleListMostPopularLastMonth();

        List<DTOSalePurchaseItem> list = new ArrayList<>();

        for (com.app.DBentities.Sale i : listWithAllProperties) {
            DTOSalePurchaseItem dto = new DTOSalePurchaseItem();
            dto.setPurchase_item(i.getPurchase_item());
            list.add(dto);
        }

        model.put("sales", list);
        return "saleListMostPopularLastMonth";

    }

    //http://localhost:8080/api/saleListBuyingNameAndLastNameLastHalfAYear
    /*Вывести имя и фамилию человека, совершившего больше всего покупок за полгода*/
    @GetMapping("/saleListBuyingNameAndLastNameLastHalfAYear")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public String getSaleListBuyingNameAndLastNameLastHalfAYear(Map<String, Object> model) {

        List<com.app.DBentities.Sale> listWithAllProperties = saleService.getSaleListMostBuyingLastHalfAYear();

        List<DTONameAndLastName> list = new ArrayList<>();

        for (com.app.DBentities.Sale i : listWithAllProperties) {
            DTONameAndLastName dto = new DTONameAndLastName();
            dto.setName(i.getName());
            dto.setLastName(i.getLastName());
            list.add(dto);
        }

        model.put("sales", list);
        return "saleListBuyingNameAndLastNameLastHalfAYear";

    }

    //http://localhost:8080/api/saleListListMostBuyingBy18YearOld
    /*Вывести Что чаще всего покупают люди в возрасте 18 лет*/
    @GetMapping("/saleListListMostBuyingBy18YearOld")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public String getSaleListListMostBuyingBy18YearOld(Map<String, Object> model) {

        List<com.app.DBentities.Sale> listWithAllProperties = saleService.getSaleListMostBuyingBy18YearOld();

        List<DTOSalePurchaseItem> list = new ArrayList<>();

        for (Sale i : listWithAllProperties) {
            DTOSalePurchaseItem dto = new DTOSalePurchaseItem();
            dto.setPurchase_item(i.getPurchase_item());
            list.add(dto);
        }

        model.put("sales", list);
        return "saleListListMostBuyingBy18YearOld";

    }

}