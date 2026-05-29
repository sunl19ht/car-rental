package com.sunl19ht.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PickUpInfoMapper {
    @Insert("UPDATE dingdan SET id_card_photo = #{resourceUrl} WHERE order_number = #{orderNumber};")
    void saveIdCardPhoto(String resourceUrl, String orderNumber);

    @Insert("UPDATE dingdan SET id_card_back_photo = #{resourceUrl} WHERE order_number = #{orderNumber};")
    void saveIdCardBackPhoto(String resourceUrl, String orderNumber);

    @Insert("UPDATE dingdan SET driving_license_photo = #{resourceUrl} WHERE order_number = #{orderNumber};")
    void saveDrivingLicensePhoto(String resourceUrl, String orderNumber);

    @Insert("UPDATE dingdan SET driving_license_back_photo = #{resourceUrl} WHERE order_number = #{orderNumber};")
    void saveDrivingLicenseBackPhoto(String resourceUrl, String orderNumber);

    @Insert("UPDATE dingdan SET vehicle_photos = #{resourceUrl} WHERE order_number = #{orderNumber};")
    void saveVehiclePhotos(String resourceUrl, String orderNumber);

    @Insert("UPDATE dingdan SET vehicle_videos = #{resourceUrl} WHERE order_number = #{orderNumber};")
    void saveVehicleVideos(String resourceUrl, String orderNumber);

    @Insert("UPDATE dingdan SET return_car_image = #{resourceUrl} WHERE order_number = #{orderNumber};")
    void saveReturnCarImage(String resourceUrl, String orderNumber);

    @Insert("UPDATE dingdan SET return_car_videos = #{resourceUrl} WHERE order_number = #{orderNumber};")
    void saveReturnCarVideos(String resourceUrl, String orderNumber);

    @Insert("UPDATE dingdan SET exterior_damage_photos = #{resourceUrl} WHERE order_number = #{orderNumber};")
    void saveExteriorDamagePhotos(String resourceUrl, String orderNumber);

    @Insert("UPDATE dingdan SET exterior_damage_videos = #{resourceUrl} WHERE order_number = #{orderNumber};")
    void saveExteriorDamageVideos(String resourceUrl, String orderNumber);

    @Insert("UPDATE dingdan SET interior_damage_photos = #{resourceUrl} WHERE order_number = #{orderNumber};")
    void saveInteriorDamagePhotos(String resourceUrl, String orderNumber);

    @Insert("UPDATE dingdan SET interior_damage_videos = #{resourceUrl} WHERE order_number = #{orderNumber};")
    void saveInteriorDamageVideos(String resourceUrl, String orderNumber);
}
