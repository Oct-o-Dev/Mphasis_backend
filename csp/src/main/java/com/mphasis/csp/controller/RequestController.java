package com.mphasis.csp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.mphasis.csp.model.Request;
import com.mphasis.csp.service.RequestService;

import java.util.List;

@RestController
@RequestMapping("/api/request")
public class RequestController {

    @Autowired
    private RequestService service;

    // ✅ ✅ 1. CUSTOMER: Create Request
    @PostMapping("/customer/create")
    public Request create(@RequestBody Request req) {
        return service.createRequest(req);
    }

    // ✅ ✅ 2. ADMIN: View all requests
    @GetMapping("/admin/all")
    public List<Request> getAllRequests() {
        return service.getAllRequests();
    }

    // ✅ ✅ 3. AGENT / ADMIN: Update request
    @PutMapping("/agent/update/{id}")
    public Request updateRequest(@PathVariable Long id,
                                 @RequestBody Request req) {
        return service.updateRequest(id, req);
    }

}
