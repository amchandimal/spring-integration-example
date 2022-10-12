package com.example.springintegrationexample.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.springframework.stereotype.Component;

@Component
public class FileTransformer {

  public String transform(String filePath) throws IOException {
    byte []  fileBytes = Files.readAllBytes(Paths.get(filePath));
    return "Transformed: "+  new String(fileBytes);
  }

}
