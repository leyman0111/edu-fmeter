package ru.fmeter.edu.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.fmeter.dto.TestDto;
import ru.fmeter.edu.service.TestService;

import java.util.List;

@RestController
@RequestMapping("/admin/tests")
public class TestController {
    private final TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }
    @PostMapping("/new")
    public ResponseEntity<String> create(@RequestBody TestDto test) {
        return testService.create(test);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TestDto> get(@PathVariable Long id) {
        return testService.find(id);
    }

    @GetMapping("/all")
    public ResponseEntity<List<TestDto>> getAll() {
        return testService.findAll();
    }

    @GetMapping("/all/{local}")
    public ResponseEntity<List<TestDto>> getAllLocal(@PathVariable String local) {
        return testService.findByLocal(local);
    }

    @PostMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody TestDto test) {
        return testService.update(id, test);
    }

    @GetMapping("/blocks/{id}")
    public ResponseEntity<String> block(@PathVariable Long id) {
        return testService.block(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return testService.delete(id);
    }
}
