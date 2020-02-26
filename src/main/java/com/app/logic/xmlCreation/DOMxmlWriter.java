package com.app.logic.xmlCreation;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.Map;
import java.util.Set;

public class DOMxmlWriter {

    public DOMxmlWriter() {
    }

    public void create(Map<String, Object> input, String FileName) {

        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();

            Set<String> keySet = input.keySet();

            Element root = doc.createElement("sale");

            for (String keys : keySet) {
                String value = (String) input.get(keys);
                Element element = doc.createElement(keys);
                element.setTextContent(value);
                root.appendChild(element);
                System.out.println(value);
            }
            doc.appendChild(root);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult file = new StreamResult(new File(FileName));
            transformer.transform(source, file);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
