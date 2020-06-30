package ru.fmeter.edu.mapper;

import org.mapstruct.Mapper;
import ru.fmeter.dao.model.Test;
import ru.fmeter.dto.TestDto;

@Mapper(componentModel = "spring", uses = QuestionMapper.class)
public interface TestMapper {
    Test testDtoToTest(TestDto testDto);

    TestDto testToTestDto(Test test);
}
