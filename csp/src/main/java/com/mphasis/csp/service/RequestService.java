package com.mphasis.csp.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mphasis.csp.model.Request;
import com.mphasis.csp.repository.RequestRepository;

import java.time.LocalDateTime;

@Service
public class RequestService {

    @Autowired
    private RequestRepository repo;

    public Request createRequest(Request req) {

        req.setStatus("OPEN");
        req.setDateOfSubmission(LocalDateTime.now());

        return repo.save(req);
    }
}
