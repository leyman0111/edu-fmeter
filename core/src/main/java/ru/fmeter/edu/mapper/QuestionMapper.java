package ru.fmeter.edu.mapper;

import org.mapstruct.Mapper;
import ru.fmeter.dao.model.Question;
import ru.fmeter.dto.QuestionDto;

@Mapper(componentModel = "spring")
public interface QuestionMapper {
    Question questionDtoToQuestion(QuestionDto questionDto);

    QuestionDto questionToQuestionDto(Question question);
}
