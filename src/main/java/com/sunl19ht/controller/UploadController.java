package com.sunl19ht.controller;

import com.sunl19ht.mapper.PickUpInfoMapper;
import com.sunl19ht.pojo.Result;
import com.sunl19ht.service.OrdersService;
import com.sunl19ht.utils.AliOSSUtils;
import com.sunl19ht.utils.TencentCOSUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api")
public class UploadController {
    @Autowired
    private AliOSSUtils aliOSSUtils;

    @Autowired
    private TencentCOSUtils tencentCOSUtils;

    @Autowired
    private PickUpInfoMapper packUpInfoMapper;

    @Autowired
    private OrdersService ordersService;

    @Value("${cloud.tencent.cos.base-url}")
    private String baseUrl;

    @PostMapping("oss/upload")
    public Result upload(MultipartFile image) throws Exception {
        String url = aliOSSUtils.simpleUpload(image);
        return Result.success(url);
    }

    @PostMapping("cos/upload")
    public Result tencentUpload(MultipartFile image) throws IOException {
        String url = tencentCOSUtils.simpleUpload(image);
        return Result.success(url);
    }

    @PostMapping("cos/pickUpInfo")
    public Result pickUpInfo(MultipartFile image, String name, String orderNumber) throws IOException {
        String resourceUrl = "";
        if (name.equals("idCardPhoto")) {
            // 身份证正面照片
            log.info("上传身份证正面照片");
            resourceUrl = baseUrl + tencentCOSUtils.simpleUpload(image);
            packUpInfoMapper.saveIdCardPhoto(resourceUrl, orderNumber);
            ordersService.checkOrderImages(orderNumber);
        }
        if (name.equals("idCardBackPhoto")) {
            // 身份证反面
            log.info("上传身份证反面照片");
            tencentCOSUtils.simpleUpload(image);
            resourceUrl = baseUrl + tencentCOSUtils.simpleUpload(image);
            packUpInfoMapper.saveIdCardBackPhoto(resourceUrl, orderNumber);
            ordersService.checkOrderImages(orderNumber);
        }
        if (name.equals("drivingLicensePhoto")) {
            // 驾驶证正面
            log.info("上传驾驶证正面照片");
            tencentCOSUtils.simpleUpload(image);
            resourceUrl = baseUrl + tencentCOSUtils.simpleUpload(image);
            packUpInfoMapper.saveDrivingLicensePhoto(resourceUrl, orderNumber);
            ordersService.checkOrderImages(orderNumber);
        }
        if (name.equals("drivingLicenseBackPhoto")) {
            // 驾驶证反面
            log.info("上传驾驶证反面照片");
            tencentCOSUtils.simpleUpload(image);
            resourceUrl = baseUrl + tencentCOSUtils.simpleUpload(image);
            packUpInfoMapper.saveDrivingLicenseBackPhoto(resourceUrl, orderNumber);
            ordersService.checkOrderImages(orderNumber);
        }
        if (name.equals("vehiclePhotos")) {
            // 车辆图片
            log.info("上传车辆图片");
            tencentCOSUtils.simpleUpload(image);
            resourceUrl = baseUrl + tencentCOSUtils.simpleUpload(image);
            packUpInfoMapper.saveVehiclePhotos(resourceUrl, orderNumber);
            ordersService.checkOrderImages(orderNumber);
        }
        if (name.equals("vehicleVideos")) {
            // 车辆视频
            log.info("上传车辆视频");
            tencentCOSUtils.simpleUpload(image);
            resourceUrl = baseUrl + tencentCOSUtils.simpleUpload(image);
            packUpInfoMapper.saveVehicleVideos(resourceUrl, orderNumber);
            ordersService.checkOrderImages(orderNumber);
        }
        if (name.equals("returnCarImage")) {
            // 还车车辆照片
            log.info("上传还车车辆照片");
            tencentCOSUtils.simpleUpload(image);
            resourceUrl = baseUrl + tencentCOSUtils.simpleUpload(image);
            packUpInfoMapper.saveReturnCarImage(resourceUrl, orderNumber);
            ordersService.checkReturnImage(orderNumber);
        }
        if (name.equals("returnCarVideos")) {
            // 还车车辆视频
            log.info("上传还车车辆视频");
            tencentCOSUtils.simpleUpload(image);
            resourceUrl = baseUrl + tencentCOSUtils.simpleUpload(image);
            packUpInfoMapper.saveReturnCarVideos(resourceUrl, orderNumber);
            ordersService.checkReturnImage(orderNumber);
        }
        if (name.equals("exteriorDamagePhotos")) {
            // 外部损伤照片
            log.info("上传外部损伤照片");
            tencentCOSUtils.simpleUpload(image);
            resourceUrl = baseUrl + tencentCOSUtils.simpleUpload(image);
            packUpInfoMapper.saveExteriorDamagePhotos(resourceUrl, orderNumber);
            ordersService.checkReturnImage(orderNumber);
        }
        if (name.equals("exteriorDamageVideos")) {
            // 外部损伤视频
            log.info("上传外部损伤视频");
            tencentCOSUtils.simpleUpload(image);
            resourceUrl = baseUrl + tencentCOSUtils.simpleUpload(image);
            packUpInfoMapper.saveExteriorDamageVideos(resourceUrl, orderNumber);
            ordersService.checkReturnImage(orderNumber);
        }
        if (name.equals("interiorDamagePhotos")) {
            // 内部损伤照片
            log.info("上传内部损伤照片");
            tencentCOSUtils.simpleUpload(image);
            resourceUrl = baseUrl + tencentCOSUtils.simpleUpload(image);
            packUpInfoMapper.saveInteriorDamagePhotos(resourceUrl, orderNumber);
            ordersService.checkReturnImage(orderNumber);
        }
        if (name.equals("interiorDamageVideos")) {
            // 内部损伤视频
            log.info("上传内部损伤视频");
            tencentCOSUtils.simpleUpload(image);
            resourceUrl = baseUrl + tencentCOSUtils.simpleUpload(image);
            packUpInfoMapper.saveInteriorDamageVideos(resourceUrl, orderNumber);
            ordersService.checkReturnImage(orderNumber);
        }
        return Result.success(resourceUrl);
    }
}
