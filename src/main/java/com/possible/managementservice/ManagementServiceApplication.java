package com.possible.managementservice;

import com.possible.managementservice.model.Server;
import com.possible.managementservice.model.Status;
import com.possible.managementservice.repository.ServerRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ManagementServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManagementServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner run(ServerRepo serverRepo){
        return args -> {
            serverRepo.save(new Server(null, "192.168.1.160", "Ubuntu Linux", "16 GB", "PC",
                    "http://localhost:8085/server/image/Cable_TV.png", Status.SERVER_UP));
        };
    }

}
