package com.app.logic.processingRequest;

import com.app.xml.entity.PurchaseItemEnum;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ProcessingSaleXmlRequest {

    public List casting(Map<String, Object> input) {

        String nameReq = (String) input.get("name");
        String lastNameReq = (String) input.get("lastName");
        String ageReq = (String) input.get("age");
        String purchase_itemReq = (String) input.get("purchase_item");
        String countReq = (String) input.get("count");
        String amountReq = (String) input.get("amount");
        String purchase_dateReq = (String) input.get("purchase_date");

        //указываем типы переменных явно
        String nameValid;
        String lastNameValid;
        int ageValid;
        PurchaseItemEnum purchase_itemValid;
        int countValid;
        float amountValid;
        XMLGregorianCalendar purchase_dateValid;

        List<String> mistakes = new ArrayList<>();

        final String NullValue = "Нет значения";
        final String NFE = "Ошибка при задании формата";
        final String NFEEnum = "Ошибка при задании из набора значений";

        //коллекция тегов
        List<String> valuesFromClass = new ArrayList<>();
        valuesFromClass.add("name");
        valuesFromClass.add("lastName");
        valuesFromClass.add("age");
        valuesFromClass.add("purchase_item");
        valuesFromClass.add("count");
        valuesFromClass.add("amount");
        valuesFromClass.add("purchase_date");

        //ошибки лишних тегов
        //find extra tags
        for (String tag : input.keySet()) {
            boolean excess = true;
            for (String tagValues : valuesFromClass) {
                if (tag == tagValues) {
                    //нашли тег
                    excess = false;
                    break;
                }
            }
            if (excess == true) {
                mistakes.add("Лишний тег <" + tag + "> в теле запроса");
            }
        }

        HashMap<String, Integer> map = new HashMap<String, Integer>();

        //name

        try {
            if (nameReq == null) {
                throw new Exception();
            }
            nameValid = (String) nameReq;
        } catch (NumberFormatException ex1) {
            mistakes.add(NFE + " имени");
            //System.out.println(NFE + " имени");
        } catch (Exception e) {
            mistakes.add(NullValue + " имени: " + "Invalid content One of '{name}' is expected");
            //System.out.println(NullValue + " имени: " + "Invalid content One of '{name}' is expected");
        }

        //lastName
        try {
            if (lastNameReq == null) {
                throw new Exception();
            }
            lastNameValid = (String) lastNameReq;
        } catch (NumberFormatException ex1) {
            mistakes.add(NFE + " фамилии ");
            //System.out.println(NFE + " фамилии ");
        } catch (Exception e) {
            mistakes.add(NullValue + " фамилии: " + "Invalid content One of '{lastName}' is expected");
            //System.out.println(NullValue + " фамилии: " + "Invalid content One of '{lastName}' is expected");
        }

        //age
        try {
            if (ageReq == null) {
                throw new Exception();
            }
            if (ageReq == "") {
                throw new NumberFormatException();
            }
            ageValid = Integer.parseInt(ageReq);
        } catch (NumberFormatException ex1) {
            mistakes.add(NFE + " возраста: " + "'" + ageReq + " is not a valid value for 'integer'");
            //System.out.println(NFE + " возраста: " + "'" + ageReq + " is not a valid value for 'integer'");
        } catch (Exception e) {
            mistakes.add(NullValue + " возраста: " + "Invalid content One of '{age}' is expected");
            //System.out.println(NullValue + " возраста: " + "Invalid content One of '{age}' is expected");
        }

        //purchase_item
        try {
            if (purchase_itemReq == null) {
                throw new Exception();
            }
            try {
                purchase_itemValid = PurchaseItemEnum.fromValue((String) purchase_itemReq);
            } catch (Exception ex2) {
                mistakes.add(NFEEnum + " покупки: " + "'" + purchase_itemReq + "'" + " is not facet-valid with respect to enumeration");
                //System.out.println(NFEEnum + " покупки: " + "'" + purchase_itemReq + "'" + " is not facet-valid with respect to enumeration");
            }
        } catch (NumberFormatException ex1) {
            mistakes.add(NFE + " покупки");
            //System.out.println(NFE + " покупки");
        } catch (Exception e) {
            mistakes.add(NullValue + " покупки: " + "Invalid content One of '{purchase_item}' is expected");
            //System.out.println(NullValue + " покупки: " + "Invalid content One of '{purchase_item}' is expected");
        }

        //count
        try {
            if (countReq == null) {
                throw new Exception();
            }
            if (countReq == "") {
                throw new NumberFormatException();
            }
            countValid = Integer.parseInt(countReq);
        } catch (NumberFormatException ex1) {
            mistakes.add(NFE + " количества: " + "'" + countReq + "'" + " is not a valid value for 'integer'");
            //System.out.println(NFE + " количества: " + "'" + countReq + "'" + " is not a valid value for 'integer'");
        } catch (Exception e) {
            mistakes.add(NullValue + " количества: " + "Invalid content One of '{count}' is expected");
            //System.out.println(NullValue + " количества: " + "Invalid content One of '{count}' is expected");
        }

        //amount
        try {
            if (amountReq == null) {
                throw new Exception();
            }
            if (amountReq == "") {
                throw new NumberFormatException();
            }
            amountValid = Float.parseFloat(amountReq);
        } catch (NumberFormatException ex1) {
            mistakes.add(NFE + " суммы: " + "'" + amountReq + "'" + " is not a valid value for 'float'");
            //System.out.println(NFE + " суммы: " + "'" + amountReq + "'" + " is not a valid value for 'float'");
        } catch (Exception e) {
            mistakes.add(NullValue + " суммы: " + "Invalid content One of '{amount}' is expected");
            //System.out.println(NullValue + " суммы: " + "Invalid content One of '{amount}' is expected");
        }

        //purchase_dateValid
        try {
            if (purchase_dateReq == null) {
                throw new Exception();
            }
            purchase_dateValid = stringToXMLGregorianCalendar(purchase_dateReq);
            if (purchase_dateReq.equals(convertXmlGregorianToString(purchase_dateValid))) {

            } else {
                throw new NumberFormatException();
            }

        } catch (NumberFormatException ex1) {
            mistakes.add(NFE + " даты:" + "'" + purchase_dateReq + "'" + " is not a valid value for 'date', format must be 'yyyy-MM-dd'");
            //System.out.println(NFE + " даты:" + "'" + purchase_dateReq + "'" + " is not a valid value for 'date', format must be 'yyyy-MM-dd'");
        } catch (ParseException ex2) {
            mistakes.add(NFE + " даты:" + "'" + purchase_dateReq + "'" + " is not a valid value for 'date', format must be 'yyyy-MM-dd'");
            //System.out.println(NFE + " даты:" + "'" + purchase_dateReq + "'" + " is not a valid value for 'date', format must be 'yyyy-MM-dd'");
        } catch (Exception e) {
            mistakes.add(NullValue + " даты: " + "Invalid content One of '{purchase_dateValid}' is expected");
            //System.out.println(NullValue + " даты: " + "Invalid content One of '{purchase_dateValid}' is expected");
        }


        //после валидации
        return mistakes;
    }

    public XMLGregorianCalendar stringToXMLGregorianCalendar(String s)
            throws ParseException,
            DatatypeConfigurationException {
        XMLGregorianCalendar result = null;
        Date date;
        SimpleDateFormat simpleDateFormat;
        GregorianCalendar gregorianCalendar;

        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        date = simpleDateFormat.parse(s);
        gregorianCalendar =
                (GregorianCalendar) GregorianCalendar.getInstance();
        gregorianCalendar.setTime(date);
        result = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
        return result;
    }

    public String convertXmlGregorianToString(XMLGregorianCalendar xc) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        GregorianCalendar gCalendar = xc.toGregorianCalendar();

        //Converted to date object
        Date date = gCalendar.getTime();

        //Formatted to String value
        String dateString = df.format(date);

        return dateString;
    }

}