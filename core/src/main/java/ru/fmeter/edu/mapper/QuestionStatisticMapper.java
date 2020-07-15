package ru.fmeter.edu.mapper;

import com.google.gson.Gson;
import org.mapstruct.Mapper;
import ru.fmeter.dao.model.QuestionStatistic;
import ru.fmeter.dto.QuestionStatisticDto;

import java.util.HashMap;

@Mapper(componentModel = "spring")
public interface QuestionStatisticMapper {
    QuestionStatisticDto questionStatisticToDto(QuestionStatistic questionStatistic);

    default HashMap answersStatisticToDto(String answersStatistic) {
        return new Gson().fromJson(answersStatistic, HashMap.class);
    }
}
