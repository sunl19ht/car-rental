package com.sunl19ht.client.mapper;

import com.sunl19ht.client.pojo.dto.PaymentInfoDTO;
import com.sunl19ht.client.pojo.entity.Orders;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface PaymentMapper {

//    @Insert("INSERT INTO dingdan (user_id, car_id, price, total_times, up_car_time, car_status, start_time, end_time, " +
//            "car_style, vehicle_photos, id_card_photo, driving_license_photo, refundable_amount, contract_url, " +
//            "vehicle_videos, exterior_damage, exterior_damage_photos, exterior_damage_videos, interior_damage, " +
//            "interior_damage_photos, interior_damage_videos, total_fee, service_guarantee, status, return_car_time, " +
//            "car_way, address, name, phone_number, id_card, service_policy, base_rental_fee, agency_fee, " +
//            "basic_insurance_fee, premium_insurance_fee) " +
//            "VALUES (#{userId}, #{carId}, #{price}, #{totalTimes}, #{upCarTime}, #{carStatus}, #{startTime}, #{endTime}, " +
//            "#{carStyle}, #{vehiclePhotos}, #{idCardPhoto}, #{drivingLicensePhoto}, #{refundableAmount}, #{contractUrl}, " +
//            "#{vehicleVideos}, #{exteriorDamage}, #{exteriorDamagePhotos}, #{exteriorDamageVideos}, #{interiorDamage}, " +
//            "#{interiorDamagePhotos}, #{interiorDamageVideos}, #{totalFee}, #{serviceGuarantee}, #{status}, #{returnCarTime}, " +
//            "#{carWay}, #{address}, #{name}, #{phoneNumber}, #{idCard}, #{servicePolicy}, #{baseRentalFee}, #{agencyFee}, " +
//            "#{basicInsuranceFee}, #{premiumInsuranceFee})")
    Integer insertDingDan(PaymentInfoDTO paymentInfoDTO);

    @Select("SELECT * FROM dingdan WHERE id = #{id}")
    Orders queryOrder(Integer id);
}
