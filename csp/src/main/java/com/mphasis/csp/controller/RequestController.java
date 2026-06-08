package com.mphasis.csp.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.mphasis.csp.model.Request;
import com.mphasis.csp.service.RequestService;

@RestController
@RequestMapping("/api/request")
public class RequestController {

    @Autowired
    private RequestService service;

    @PostMapping("/create")
    public Request create(@RequestBody Request req) {
        return service.createRequest(req);
    }
}
