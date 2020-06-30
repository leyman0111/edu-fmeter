package ru.fmeter.edu.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.fmeter.dao.model.Test;
import ru.fmeter.dao.repo.TestDao;
import ru.fmeter.dto.TestDto;
import ru.fmeter.edu.mapper.TestMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TestService {
    private final TestDao testDao;
    private final TestMapper testMapper;

    public TestService(TestDao testDao, TestMapper testMapper) {
        this.testDao = testDao;
        this.testMapper = testMapper;
    }

    public ResponseEntity<String> create(TestDto test) {
        if (testDao.findByName(test.getName()).isPresent())
            return new ResponseEntity<>("The name already exists", HttpStatus.OK);
        return new ResponseEntity<>(testDao.save(testMapper.testDtoToTest(test)).getId().toString(), HttpStatus.OK);
    }

    public ResponseEntity<TestDto> find(Long id) {
        Optional<Test> test = testDao.findById(id);
        return test.map(value -> new ResponseEntity<>(testMapper.testToTestDto(value), HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.of(Optional.empty()));
    }

    public ResponseEntity<List<TestDto>> findAll() {
        List<TestDto> tests = new ArrayList<>();
        for (Test test : testDao.findAll()) {
            tests.add(testMapper.testToTestDto(test));
        }
        return new ResponseEntity<>(tests, HttpStatus.OK);
    }

    public ResponseEntity<String> update(Long id, TestDto testDto) {
        Optional<Test> test = testDao.findById(id);
        if (test.isPresent()) {
            testDao.save(testMapper.testDtoToTest(testDto));
            return new ResponseEntity<>("OK!", HttpStatus.OK);
        }
        return new ResponseEntity<>("Test is not exist", HttpStatus.OK);
    }

    public ResponseEntity<String> block(Long id) {
        Optional<Test> testDb = testDao.findById(id);
        if (testDb.isPresent()) {
            Test test = testDb.get();
            test.setBlocked(!test.isBlocked());
            testDao.save(test);
            return new ResponseEntity<>("OK!", HttpStatus.OK);
        }
        return new ResponseEntity<>("Test is not exist", HttpStatus.OK);
    }

    public ResponseEntity<String> delete(Long id) {
        testDao.deleteById(id);
        return new ResponseEntity<>("OK!", HttpStatus.OK);
    }
}
