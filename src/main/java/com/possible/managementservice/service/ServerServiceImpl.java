package com.possible.managementservice.service;

import com.possible.managementservice.model.Server;
import com.possible.managementservice.model.Status;
import com.possible.managementservice.repository.ServerRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Collection;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ServerServiceImpl implements ServerService {
    private final ServerRepo serverRepo;

    @Value("${image.names}")
    private String[] imageNames;

    @Override
    public Server create(Server server) {
        server.setImgUrl(setServerImageUrl());
        return serverRepo.save(server);
    }


    @Override
    public Server ping(String ipAddress) throws IOException {
        Server server = serverRepo.findByIpAddress(ipAddress);
        InetAddress address = InetAddress.getByName(ipAddress);
        server.setStatus(address.isReachable(10000) ? Status.SERVER_UP : Status.SERVER_DOWN);
        return null;
    }

    @Override
    public Collection<Server> serverList(int limit) {
        return serverRepo.findAll(PageRequest.of(0, limit)).toList();
    }

    @Override
    public Server getServer(Long id) {
        return serverRepo.findById(id).orElse(null);
    }

    @Override
    public Server updateServer(Server server) {
        return serverRepo.save(server);
    }

    @Override
    public Boolean deleteServer(Long id) {
        serverRepo.deleteById(id);
        return Boolean.TRUE;
    }

    private String setServerImageUrl() {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/server/image/" +imageNames[new Random().nextInt(4)]).toUriString();
    }
}
