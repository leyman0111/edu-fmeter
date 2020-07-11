package ru.fmeter.edu.mapper;

import org.mapstruct.Mapper;
import ru.fmeter.dao.model.ExamAnswer;
import ru.fmeter.dto.ExamAnswerDto;

@Mapper(componentModel = "spring")
public interface ExamAnswerMapper {
    ExamAnswerDto examAnswerToDto(ExamAnswer answer);
}
