package org.ashfan.controller;

import org.ashfan.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class resource {

    @GetMapping("/api/auth/resource")
    public String getResource() {
        return "You have accessed a protected resource! 🎉";
    }
}
