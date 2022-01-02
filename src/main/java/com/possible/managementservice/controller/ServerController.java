package com.possible.managementservice.controller;

import com.possible.managementservice.model.Server;
import com.possible.managementservice.model.Status;
import com.possible.managementservice.model.dto.Response;
import com.possible.managementservice.service.ServerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@RequiredArgsConstructor
@RestController
@RequestMapping("/server")
@Slf4j
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
    public ResponseEntity<Response> pingServer(@PathVariable String ipAddress) throws IOException {
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

    @PostMapping("/save")
    public ResponseEntity<Response> saveServer(@RequestBody Server server){
        Response response = Response.builder()
                .timeStamp(now())
                .data(Map.of("Server", serverService.create(server)))
                .message("Server created")
                .status(CREATED)
                .statusCode(CREATED.value())
                .build();
        return new ResponseEntity<>(response, CREATED);
    }

    @GetMapping("/get{id}")
    public ResponseEntity<Response> getServer(@PathVariable Long id){
        Response response = Response.builder()
                .timeStamp(now())
                .data(Map.of("Server", serverService.getServer(id)))
                .message("Server Retrieved")
                .status(OK)
                .statusCode(OK.value())
                .build();
        return new ResponseEntity<>(response, OK);
    }

    @DeleteMapping("/delete{id}")
    public ResponseEntity<Response> deleteServer(@PathVariable Long id){
        Response response = Response.builder()
                .timeStamp(now())
                .data(Map.of("deleted", serverService.deleteServer(id)))
                .message("Server Deleted")
                .status(OK)
                .statusCode(OK.value())
                .build();
        return new ResponseEntity<>(response, OK);
    }

    @GetMapping(path = "/image/{fileName}", produces = IMAGE_PNG_VALUE)
    public byte[] getServerImage(@PathVariable("fileName") String fileName) throws IOException {
        return Files.readAllBytes(Paths.get(System.getProperty("user.home") + "/Downloads/images/" + fileName));
    }

}
