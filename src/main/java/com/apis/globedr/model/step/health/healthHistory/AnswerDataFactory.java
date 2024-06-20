package com.apis.globedr.model.step.health.healthHistory;

import com.apis.globedr.constant.Text;
import com.apis.globedr.enums.QuestionItemType;
import com.apis.globedr.helper.Common;
import com.apis.globedr.model.response.health.healthHistory.QuestionItemsRS;
import com.apis.globedr.model.response.noti.PusherTeleCall;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;


public class AnswerDataFactory {
    private static DateFormat timeV1 = new SimpleDateFormat(Text.FTIME_FULL);
    private static DateFormat timeV2 = new SimpleDateFormat(Text.FTIME_FULL_v2);


    public static Object init(HealthHistoryInfoMS data) {

        if (data.getItemType() == QuestionItemType.Checkbox.value()) return data.getIsYes();

        if (data.getItemType() ==QuestionItemType.Tag.value()) return data.getIsYes();

        if (data.getItemType() == QuestionItemType.Text.value()) return data.getValue();

        if (data.getItemType() == QuestionItemType.OptionText.value()){
            return OptionTextItem.builder()
                    .isValue((data.getIsYes()))
                    .values(
                            (data.getValue() == null ? Arrays.asList("") : Arrays.asList(data.getValue().split(",")) )
                    ).build();
        }

        if (data.getItemType() == QuestionItemType.Number.value()) return data.getValue();

        if (data.getItemType() == QuestionItemType.DateTime.value()){
            try {
                Date date = timeV1.parse(data.getValue());
                return Common.format(date, Text.FTIME_V1);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if (data.getItemType() == QuestionItemType.Height.value()) return data.getValue();
        if (data.getItemType() == QuestionItemType.Weight.value()) return data.getValue();
        if (data.getItemType() == QuestionItemType.Head.value()) return data.getValue();

        return null;
    }

    public static Object init(QuestionItemsRS data) {

        if (data.getItemType() == QuestionItemType.OptionText.value()){
            return mapping(data.getAnswerData(), OptionTextItem.class);
        }

        if (data.getItemType() == QuestionItemType.DateTime.value()){
            try {
                Date date = timeV2.parse(data.getAnswerData().toString());
                return Common.format(date, Text.FTIME_V1);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return data.getAnswerData();
    }

    private static <T> T mapping(Object fromValue, Class<T> toClass) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper.convertValue(fromValue, toClass);
    }


}