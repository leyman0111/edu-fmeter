package ru.fmeter.edu.mapper;

import com.google.gson.Gson;
import org.mapstruct.Mapper;
import ru.fmeter.dao.model.Question;
import ru.fmeter.dto.ExamQuestionDto;

import java.util.HashMap;

@Mapper(componentModel = "spring")
public interface ExamQuestionMapper {
    ExamQuestionDto questionToExamQuestionDto(Question question);

    default HashMap variantsToDto(String variants) {
        return new Gson().fromJson(variants, HashMap.class);
    }
}
