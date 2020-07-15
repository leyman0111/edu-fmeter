package ru.fmeter.edu.mapper;

import com.google.gson.Gson;
import org.mapstruct.Mapper;
import ru.fmeter.dao.model.TestStatistic;
import ru.fmeter.dto.TestStatisticDto;
import ru.fmeter.utils.DateTimeUtility;

import java.util.Date;
import java.util.HashMap;

@Mapper(componentModel = "spring")
public interface TestStatisticMapper {
    TestStatisticDto testStatisticToDto(TestStatistic testStatistic);

    default String createDateToDto(Date createDate) {
        return DateTimeUtility.dateToString(createDate);
    }
}
