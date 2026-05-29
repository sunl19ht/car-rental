package com.sunl19ht.client.mapper;

import com.sunl19ht.client.pojo.dto.FeedbackDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FeedbackMapper {
    @Insert("INSERT INTO feedback(type, content, phone, image) VALUES (#{type}, #{content}, #{phone}, #{image})")
    void insertIntoFeedback(FeedbackDTO feedbackDTO);
}
