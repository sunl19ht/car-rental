package com.sunl19ht.mapper;

import com.sunl19ht.pojo.entity.News;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NewsMapper {
    @Insert("insert into news(car_brand,license_plate,up_car_time,return_car_time,new_status,`read`,dingdan_id) values(#{carBrand},#{licensePlate},#{upCarTime},#{returnCarTime},#{newStatus},#{read},#{dingdanId})")
    void insertNews(News news);
}
