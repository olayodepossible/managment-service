package com.possible.managementservice.controller;

import com.possible.managementservice.model.Server;
import com.possible.managementservice.model.Status;
import com.possible.managementservice.model.dto.Response;
import com.possible.managementservice.service.ServerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.io.IOException;
import java.util.Map;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.OK;

@RequiredArgsConstructor
@RestController
@RequestMapping("/server")
public class ServerController {
    private final ServerService serverService;

    @GetMapping("/list/{limit}")
    public ResponseEntity<Response> getServers(@PathVariable int limit){
        Response response = Response.builder()
                .timeStamp(now())
                .data(Map.of("Servers", serverService.serverList(limit)))
                .message("Servers Retrieved")
                .status(OK)
                .statusCode(OK.value())
                .build();
        return new ResponseEntity<>(response, OK);
    }

    @GetMapping("/ping/{ipAddress}")
    public ResponseEntity<Response> pingServers(@PathVariable String ipAddress) throws IOException {
        Server server = serverService.ping(ipAddress);
        Response response = Response.builder()
                .timeStamp(now())
                .data(Map.of("Server", server))
                .message(server.getStatus() == Status.SERVER_UP? "Ping Success" : "Ping Failed")
                .status(OK)
                .statusCode(OK.value())
                .build();
        return new ResponseEntity<>(response, OK);
    }
}
