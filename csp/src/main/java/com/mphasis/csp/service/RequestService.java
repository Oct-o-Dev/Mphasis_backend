package com.mphasis.csp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mphasis.csp.model.Request;
import com.mphasis.csp.repository.RequestRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RequestService {

    @Autowired
    private RequestRepository repo;

    // ✅ ✅ 1. CUSTOMER → Create request
    public Request createRequest(Request req) {

        req.setStatus("OPEN");
        req.setDateOfSubmission(LocalDateTime.now());

        return repo.save(req);
    }

    // ✅ ✅ 2. ADMIN → Get all requests
    public List<Request> getAllRequests() {
        return repo.findAll();
    }

    // ✅ ✅ 3. AGENT / ADMIN → Update request
    public Request updateRequest(Long id, Request req) {

        // ✅ Find existing request
        Request existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        // ✅ Update fields (example)
        existing.setStatus(req.getStatus());

        return repo.save(existing);
    }
}
