package com.tatari.magiclamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import com.tatari.magiclamp.service.GenieToJson;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class GenieApplication implements CommandLineRunner {
	@Autowired protected GenieToJson genieToJson;

    public static void main(String[] args) {
        SpringApplication.run(GenieApplication.class, args);

    }

    @Override
    public void run(String[] args) throws Exception {
        
        if(args[0].equals("clinical_sample")) {
        	genieToJson.readGenieDataFile(args[2], args[1], args[0], args[3]);
        }
        else if(args[0].equals("mutations_extended")) {
        	genieToJson.readGenieDataFile(args[2], args[1], args[0], null);
        }
    	
    }

    
}
