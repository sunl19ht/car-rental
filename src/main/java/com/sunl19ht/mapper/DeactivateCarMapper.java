package com.sunl19ht.mapper;

import com.sunl19ht.pojo.dto.DeactivateCarDTO;
import com.sunl19ht.pojo.vo.DeactivateCarVO;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface DeactivateCarMapper {

    @Select("select * from deactivate_cars where status = 0 ORDER BY end_time ASC")
    List<DeactivateCarVO> getDeactivateCar();

    @Update("update deactivate_cars SET status = 1 WHERE id = #{id};")
    void delCar(Integer id);

    @Select("select * from deactivate_cars where car_id = #{id}")
    List<DeactivateCarVO> getTimeById(Integer id);

    @Delete("delete from deactivate_cars where id = #{id}")
    void delTime(Integer id, LocalDateTime startTime, LocalDateTime endTime);

    @Insert("insert into deactivate_cars(car_id, `desc`, start_time, end_time, user, status, order_number) values(#{carId}, #{desc}, #{startTime}, #{endTime}, #{user}, #{status}, #{orderNumber})")
    void insertLogOfTime(DeactivateCarDTO deactivateCarDTO);

    @Delete("delete from deactivate_cars where order_number = #{orderNumber}")
    void delLogOfTime(String orderNumber);
}
