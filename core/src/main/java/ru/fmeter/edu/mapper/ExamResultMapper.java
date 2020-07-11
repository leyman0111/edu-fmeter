package ru.fmeter.edu.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.fmeter.dao.model.ExamResult;
import ru.fmeter.dto.ExamResultDto;

@Mapper(componentModel = "spring", uses = ExamAnswerMapper.class)
public interface ExamResultMapper {
    @Mapping(target = "exam", ignore = true)
    ExamResultDto examResultToDto(ExamResult examResult);
}
