package ru.fmeter.edu.mapper;

import org.mapstruct.Mapper;
import ru.fmeter.dao.model.QuestionStatistic;
import ru.fmeter.dto.QuestionStatisticDto;
import ru.fmeter.utils.DateTimeUtility;

import java.util.Date;

@Mapper(componentModel = "spring")
public interface QuestionStatisticMapper {
    QuestionStatisticDto questionStatisticToDto(QuestionStatistic questionStatistic);

    default String createDateToDto(Date createDate) {
        return DateTimeUtility.dateToString(createDate);
    }
}
