package com.example.springintegrationexample.config;

import com.example.springintegrationexample.service.FileTransformer;
import java.io.File;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.FileWritingMessageHandler;

@Configuration
public class IntegrationConfig {

  private final FileTransformer fileTransformer;

  public IntegrationConfig(
      FileTransformer fileTransformer) {
    this.fileTransformer = fileTransformer;
  }

  @Bean
  public IntegrationFlow integrationFlow(){
    return IntegrationFlows.from(fileReader()
        , spec -> spec.poller(Pollers.fixedDelay(5)))
        .transform(fileTransformer,"transform")
        .handle(fileWriter()).get();
  }

  @Bean
  public FileWritingMessageHandler fileWriter(){
    FileWritingMessageHandler handler = new FileWritingMessageHandler(new File("output"));
    handler.setExpectReply(false);
    return handler;
  }

  @Bean
  public FileReadingMessageSource fileReader(){
    FileReadingMessageSource source = new FileReadingMessageSource();
    source.setDirectory(new File("files"));
    return source;
  }
}
