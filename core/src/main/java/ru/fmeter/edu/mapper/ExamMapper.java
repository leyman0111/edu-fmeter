package ru.fmeter.edu.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.fmeter.dao.model.Test;
import ru.fmeter.dto.ExamDto;

@Mapper(componentModel = "spring", uses = ExamQuestionMapper.class)
public interface ExamMapper {
    @Mapping(target = "access", ignore = true)
    ExamDto testToExamDto(Test test);
}
