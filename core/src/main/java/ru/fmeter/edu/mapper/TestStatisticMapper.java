package ru.fmeter.edu.mapper;

import com.google.gson.Gson;
import org.mapstruct.Mapper;
import ru.fmeter.dao.model.TestStatistic;
import ru.fmeter.dto.TestStatisticDto;

import java.util.HashMap;

@Mapper(componentModel = "spring")
public interface TestStatisticMapper {
    TestStatisticDto testStatisticToDto(TestStatistic testStatistic);

    default HashMap<Long, Integer> hardestQuestionsToDto(String hardestQuestions) {
        return new Gson().fromJson(hardestQuestions, HashMap.class);
    }

    default HashMap<Integer, Integer> achievementsToDto(String achievements) {
        return new Gson().fromJson(achievements, HashMap.class);
    }
}
