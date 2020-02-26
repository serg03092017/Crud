package com.app.logic.fileOperations;

import org.apache.commons.io.FileUtils;

import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class DeleteFile {

    public DeleteFile(String XML_FILE) {
        deleteFile(XML_FILE);
    }

    public synchronized void deleteFile(String XML_FILE) {

        File f = new File(XML_FILE);
        try {
            StreamResult sr = new StreamResult(new FileOutputStream(XML_FILE, false));
            sr.getOutputStream().close();
            FileOutputStream fos = new FileOutputStream(f);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.gc();
            if (f.exists()) {
                File fileToDelete = FileUtils.getFile(XML_FILE);
                FileUtils.deleteQuietly(fileToDelete);
            }
        }
    }
}
