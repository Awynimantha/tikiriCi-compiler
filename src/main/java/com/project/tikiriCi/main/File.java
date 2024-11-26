package com.project.tikiriCi.main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class File {
    private String location;
    private FileInputStream readContent;
    private String writeContent;

    public File(String location) throws FileNotFoundException {
        this.location = location;
       
    }

    public void writeToFile() throws FileNotFoundException,IOException{
        try{
            FileWriter fileWriter = new FileWriter(this.location);
            fileWriter.write(this.writeContent);
            fileWriter.close();
        } catch(FileNotFoundException e){
            throw e;
        }

    }

    public void readFile() throws FileNotFoundException{
         try {
            this.readContent = new FileInputStream(location);
        } catch (FileNotFoundException e) {
            throw e;
        }     
    }

    public FileInputStream getContent() {
        return readContent;
    } 

    public String getLocation() {
        return location;
    }

    public void setWriteContent(String content) {
        this.writeContent = content;
    }
    

}
