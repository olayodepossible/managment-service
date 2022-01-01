package com.possible.managementservice.service;

import com.possible.managementservice.model.Server;

import java.io.IOException;
import java.util.Collection;

public interface ServerService {
    Server create(Server server);
    Server ping(String ipAddress) throws IOException;
    Collection<Server> serverList(int limit);
    Server getServer(Long id);
    Server updateServer(Server server);
    Boolean deleteServer(Long id);
}
