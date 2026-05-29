package com.sunl19ht.mapper;

import com.sunl19ht.pojo.dto.CarDTO;
import com.sunl19ht.pojo.dto.DisableCarDTO;
import com.sunl19ht.pojo.vo.CarVO;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface CarMapper {

    @Select("select * from cars where is_delete = 0")
    @Results({
            @Result(property = "state", column = "status")
    })
    List<CarVO> getCar();

    @Update("update cars set status = #{status} where id = #{id}")
    void setStatus(Integer id, Integer status);

    @Insert("insert into cars(car_brand, license_plate, car_type, weekend_price, normal_price, image_url, seat, gearbox, car_year, trait, weekend_price_fake, normal_price_fake, status, is_delete) " +
            "values(#{carBrand}, #{licensePlate}, #{carType}, #{weekendPrice}, #{normalPrice}, #{imageUrl}, #{seat}, #{gearbox}, #{carYear}, #{trait}, #{weekendPriceFake}, #{normalPriceFake}, #{status}, #{isDelete})")
    void addCar(CarDTO carDTO);

    // 逻辑删除
    @Update("update cars SET is_delete = 1 WHERE id = #{id};")
    void delCar(Integer id);

    @Select("select * from cars where id = #{id} and is_delete = 0")
    CarVO getCarById(Integer id);

//    @Update("update cars set car_brand = #{carBrand},license_plate = #{licensePlate},car_type = #{carType},weekend_price = #{weekendPrice},normal_price = #{normalPrice}, image_url = #{imageUrl}, seat = #{seat}, gearbox = #{gearbox}, car_year = #{carYear}, `option` = #{option}, trait = #{trait} where id = #{id}")
    void updateCar(CarDTO carDTO);

    @Update("update cars set weekend_price = #{weekendPrice},normal_price = #{normalPrice} where id = #{id}")
    void updateCarPrice(Integer id, BigDecimal weekendPrice, BigDecimal normalPrice);

    @Insert("insert into deactivate_cars(car_id, `desc`, start_time, end_time, user, status) values(#{carId}, #{desc}, #{startTime}, #{endTime}, #{user}, 1)")
    void addTempDisableCar(DisableCarDTO disableCarDTO);

    @Update("update cars set status = 1 where id = #{carId}")
    void updateCarStatus(Integer carId);

    @Update("update cars set status = 0 where id = #{carId}")
    void enableCar(Integer carId);
}
