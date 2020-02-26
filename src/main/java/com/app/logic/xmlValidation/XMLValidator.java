package com.app.logic.xmlValidation;

import org.xml.sax.SAXException;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public class XMLValidator {
    public boolean valid(String XML_FILE, String SCHEMA_FILE) throws SAXException, IOException {

        boolean result = false;

        SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");

        // 2. Компиляция схемы
        // Схема загружается в объект типа java.io.File, но вы также можете использовать
        // классы java.net.URL и javax.xml.transform.Source
        File schemaLocation = new File(SCHEMA_FILE);
        Schema schema = factory.newSchema(schemaLocation);

        // 3. Создание валидатора для схемы
        Validator validator = schema.newValidator();

        // 4. Разбор проверяемого документа
        Source source = new StreamSource(XML_FILE);

        // 5. Валидация документа
        try {
            validator.validate(source);
            //System.out.println(XML_FILE + " is valid");
            result = true;
            return result;
        } catch (SAXException ex) {
            //System.out.println(XML_FILE + " is not valid because ");
            //System.out.println(ex.getMessage());
            return result;
        }
    }
}