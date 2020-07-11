package ru.fmeter.edu.mapper;

import org.mapstruct.Mapper;
import ru.fmeter.dao.model.Test;
import ru.fmeter.dto.ExamDto;

@Mapper(componentModel = "spring", uses = ExamQuestionMapper.class)
public interface ExamMapper {
    ExamDto testToExamDto(Test test);
}
