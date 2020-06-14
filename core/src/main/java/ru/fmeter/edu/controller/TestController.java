package ru.fmeter.edu.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {

    @PostMapping("/name")
    public ResponseEntity<String> test() {
        return new ResponseEntity<>("ИЛЬНУР", HttpStatus.OK);
    }
}
