package com.sunl19ht.client.mapper;

import com.sunl19ht.client.pojo.vo.SlideshowVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Mapper
public interface SlideshowMapper {
    @Select("select * from slideshow")
    List<SlideshowVO> getSlideshowList();

    @Insert("insert into slideshow(image) values(#{image})")
    void addSlideshow(String image);

    @Delete("delete from slideshow where id = #{id}")
    void deleteSlideshow(Integer id);
}
