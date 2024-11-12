package com.project.tikiriCi.main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class File {
    private String location;
    private FileInputStream content;

    public File(String location) throws FileNotFoundException {
        this.location = location;
        try {
            this.content = new FileInputStream(location);

        } catch (FileNotFoundException e) {
            throw e;

        }
        
    }

    public FileInputStream getContent() {
        return content;

    } 

    public String getLocation() {
        return location;

    }

    

}
