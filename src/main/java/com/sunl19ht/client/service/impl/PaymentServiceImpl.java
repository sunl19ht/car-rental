package com.sunl19ht.client.service.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.sunl19ht.client.mapper.PaymentMapper;
import com.sunl19ht.client.pojo.dto.PaymentInfoDTO;
import com.sunl19ht.client.pojo.entity.Address;
import com.sunl19ht.client.pojo.entity.Orders;
import com.sunl19ht.client.pojo.entity.PaymentInfo;
import com.sunl19ht.client.pojo.vo.PaymentInfoVO;
import com.sunl19ht.client.pojo.vo.PriceDetailVO;
import com.sunl19ht.client.service.PaymentService;
import com.sunl19ht.context.BaseContext;
import com.sunl19ht.mapper.CarMapper;
import com.sunl19ht.mapper.DeactivateCarMapper;
import com.sunl19ht.mapper.NewsMapper;
import com.sunl19ht.pojo.dto.DeactivateCarDTO;
import com.sunl19ht.pojo.entity.News;
import com.sunl19ht.pojo.vo.CarVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentMapper paymentMapper;

    @Autowired
    private CarMapper carMapper;

    @Autowired
    private NewsMapper newsMapper;

    @Autowired
    private DeactivateCarMapper deactivateCarMapper;

    private final Set<String> uniqueTimestampSet = ConcurrentHashMap.newKeySet();

    @Value("${convexHull.host}")
    private String pointHost;

    /**
     * 用户下单
     * @param paymentInfo
     * @return
     */
    @Override
    @Transactional
    public PaymentInfoVO submit(PaymentInfo paymentInfo) {

        Timestamp upCarTime = paymentInfo.getUpCarTime();
        if (upCarTime.before(new Timestamp(System.currentTimeMillis()))) {
            throw new IllegalArgumentException("取车时间不能小于当前时间！");
        }

        PaymentInfoVO paymentInfoVO = new PaymentInfoVO();
        CarVO carById = carMapper.getCarById(paymentInfo.getCarId());
        PriceDetailVO detail = detail(paymentInfo.getCarId(), paymentInfo.getUpCarTime().toLocalDateTime(), paymentInfo.getReturnCarTime().toLocalDateTime(), paymentInfo.getServicePolicy(), paymentInfo.getCarWay());
        PaymentInfoDTO paymentInfoDTO = new PaymentInfoDTO();
        BeanUtils.copyProperties(paymentInfo, paymentInfoDTO);

        if (paymentInfo.getCarWay().equals("商家送取") && paymentInfo.getAddress().size() == 1) {
            String response = HttpUtil.get(pointHost + "/?x=" + paymentInfo.getAddress().get(0).getLongitude() + "&y=" + paymentInfo.getAddress().get(0).getLatitude());
            if (response == null || response.isEmpty())
                response = "10000";
            BigDecimal serverPrice = new BigDecimal(response).multiply(new BigDecimal(2));
            detail.setServicePrice(serverPrice);
            detail.setTotalPrice(detail.getTotalPrice().add(serverPrice));
            detail.setOriginalPrice(detail.getOriginalPrice().add(serverPrice));
        }

        if (paymentInfo.getCarWay().equals("自行取还") && paymentInfo.getAddress().size() == 1) {
            detail.setRentalPrice(detail.getRentalPrice().subtract(new BigDecimal("20")));
            detail.setTotalPrice(detail.getTotalPrice().subtract(new BigDecimal("20")));
            detail.setServicePrice(new BigDecimal(0));
        }

        if (paymentInfo.getCarWay().equals("异地还车") && paymentInfo.getAddress().size() == 2) {
            String s1 = HttpUtil.get(pointHost + "/?x=" + paymentInfo.getAddress().get(0).getLongitude() + "&y=" + paymentInfo.getAddress().get(0).getLatitude());
            String s2 = HttpUtil.get(pointHost + "/?x=" + paymentInfo.getAddress().get(1).getLongitude() + "&y=" + paymentInfo.getAddress().get(1).getLatitude());
            if (s1 == null || s1.isEmpty()) {
                s1 = "10000";
            }
            if (s2 == null || s2.isEmpty()) {
                s2 = "10000";
            }
            BigDecimal address1 = new BigDecimal(s1);
            BigDecimal address2 = new BigDecimal(s2);
            BigDecimal serverPrice = address1.add(address2);
            detail.setServicePrice(serverPrice);
            detail.setTotalPrice(detail.getTotalPrice().add(serverPrice));
            detail.setOriginalPrice(detail.getOriginalPrice().add(serverPrice));
        }
        // todo 获取userId
        paymentInfoDTO.setUserId(BaseContext.getCurrentId());

        // 订单号: 时间戳 + 3位随机数，避免并发重复
        String orderNumber = String.valueOf(System.currentTimeMillis())
                + String.format("%03d", (int)(Math.random() * 1000));
        paymentInfoDTO.setOrderNumber(orderNumber);

        // 设置总价
        paymentInfoDTO.setPrice(detail.getTotalPrice());

        // 总时间
        paymentInfoDTO.setTotalTimes(detail.getTotalTime());

        // 设置订单状态
        paymentInfoDTO.setCarStatus("待支付");

        // 设置起始时间
        paymentInfoDTO.setStartTime(paymentInfo.getUpCarTime());

        // 设置结束时间
        paymentInfoDTO.setEndTime(paymentInfo.getReturnCarTime());

        // 设置车牌号
        paymentInfoDTO.setLicensePlate(carById.getLicensePlate());

        // 车辆品牌
        paymentInfoDTO.setCarBrand(carById.getCarBrand());

        // 可退款
        paymentInfoDTO.setRefundableAmount(new BigDecimal("0"));

        // 总价
        paymentInfoDTO.setTotalFee(detail.getTotalPrice());

        // 服务保障
        paymentInfoDTO.setServiceGuarantee(paymentInfo.getServicePolicy());

        // 地址
        paymentInfoDTO.setAddress(JSONObject.toJSONString(paymentInfo.getAddress()));

        // 基本租车费
        paymentInfoDTO.setBaseRentalFee(detail.getRentalPrice());

        // 优享价格
        paymentInfoDTO.setPremiumInsuranceFee(detail.getGuaranteePrice());

        // 用户订单状态
        paymentInfoDTO.setUserOrderStatus("待支付");

        // 车辆照片
        paymentInfoDTO.setCarImageUrl(carById.getImageUrl());

        // 支付状态
        paymentInfoDTO.setPaymentStatus("待支付");

        // 上门取车费
        paymentInfoDTO.setPickupFee(detail.getServicePrice());

        paymentMapper.insertDingDan(paymentInfoDTO);

        // 防止用户重复下单
        log.error(uniqueTimestampSet.toString());
        if (uniqueTimestampSet.contains(paymentInfo.getUniqueTimestamp())) {
            throw new IllegalArgumentException("请勿重复下单！");
        }
        uniqueTimestampSet.add(paymentInfo.getUniqueTimestamp());

        // 返回给前端订单信息
        Orders order = paymentMapper.queryOrder(paymentInfoDTO.getId());

        paymentInfoVO.setId(Long.valueOf(order.getId()));
        paymentInfoVO.setOrderNumber(order.getOrderNumber());
        paymentInfoVO.setOrderAmount(order.getTotalFee());
        paymentInfoVO.setRentalPrice(order.getBaseRentalFee());
        paymentInfoVO.setServicePrice(order.getAgencyFee());
        paymentInfoVO.setGuaranteePrice(order.getPremiumInsuranceFee());
        paymentInfoVO.setOrderTime(LocalDateTime.now());

        News news = News.builder()
                .carBrand(order.getCarBrand())
                .read(0)
                .licensePlate(order.getLicensePlate())
                .upCarTime(order.getStartTime())
                .returnCarTime(order.getEndTime())
                .newStatus("待取订单")
                .dingdanId(order.getId()).build();
        newsMapper.insertNews(news);

        // 锁定车辆，防止其他用户下单
        carMapper.updateCarStatus(order.getCarId());
        // 更新车辆下线时间
        DeactivateCarDTO deactivateCarDTO = DeactivateCarDTO.builder()
                .carId(order.getCarId())
                .desc("订单取消")
                .startTime(order.getStartTime())
                .endTime(order.getEndTime())
                .user("预定订单")
                .orderNumber(paymentInfoDTO.getOrderNumber())
                .status(0).build();
        deactivateCarMapper.insertLogOfTime(deactivateCarDTO);
        return paymentInfoVO;
    }

    public PriceDetailVO detail(Integer carId, LocalDateTime upCarTime, LocalDateTime returnCarTime, String guaranteePrice, String carWay, List<Address> address) {
        PriceDetailVO detail = detail(carId, upCarTime, returnCarTime, guaranteePrice, carWay);

        if (carWay.equals("商家送取") && address.size() == 1) {
            String response = HttpUtil.get(pointHost + "/?x=" + address.get(0).getLongitude() + "&y=" + address.get(0).getLatitude());
            if (response == null || response.isEmpty()) {
                response = "10000";
            }
            BigDecimal serverPrice = new BigDecimal(response).multiply(new BigDecimal(2));
            detail.setServicePrice(serverPrice);
            detail.setTotalPrice(detail.getTotalPrice().add(serverPrice));
            detail.setOriginalPrice(detail.getOriginalPrice().add(serverPrice));
        }
        if (carWay.equals("自行取还") && address.size() == 1) {
            detail.setServicePrice(new BigDecimal(0));
            detail.setRentalPrice(detail.getRentalPrice().subtract(new BigDecimal("20")));
        }

        if (carWay.equals("异地还车") && address.size() == 2) {
            String s1 = HttpUtil.get(pointHost + "/?x=" + address.get(0).getLongitude() + "&y=" + address.get(0).getLatitude());
            String s2 = HttpUtil.get(pointHost + "/?x=" + address.get(1).getLongitude() + "&y=" + address.get(1).getLatitude());
            if (s1 == null || s1.isEmpty()) {
                s1 = "10000";
            }
            if (s2 == null || s2.isEmpty()) {
                s2 = "10000";
            }
            BigDecimal address1 = new BigDecimal(s1);
            BigDecimal address2 = new BigDecimal(s2);
            BigDecimal serverPrice = address1.add(address2);
            detail.setServicePrice(serverPrice);
            detail.setTotalPrice(detail.getTotalPrice().add(serverPrice));
            detail.setOriginalPrice(detail.getOriginalPrice().add(serverPrice));
        }

        return detail;
    }

    @Override
    public PriceDetailVO detail(Integer carId, LocalDateTime upCarTime, LocalDateTime returnCarTime, String guaranteePrice, String carWay) {
        CarVO car = carMapper.getCarById(carId);
        BigDecimal totalPrice = new BigDecimal(0);  // 优惠价格
        BigDecimal originalPrice = new BigDecimal(0); // 原价

        PriceDetailVO priceDetailVO = new PriceDetailVO();
        // 计算总时长
        Integer totalHours = calculateTotalHours(Timestamp.valueOf(upCarTime), Timestamp.valueOf(returnCarTime));
        if (totalHours >= 5 && totalHours < 24) {
            // 超过5小时 不满24小时 按照一天计算
            totalHours = 24;
        }
        priceDetailVO.setTotalTime(totalHours);


        // 计算时间差
        Duration duration = Duration.between(upCarTime, returnCarTime);

        // 获取总小时数
        long difference = duration.toHours();

        // 计算天数和剩余小时
        long days = difference / 24;
        long hours = difference % 24;

        // 判断是否为周末
        if (isWeekend(Timestamp.valueOf(upCarTime))) {
            // 周末
            priceDetailVO.setRentalPrice(car.getWeekendPrice());
            if (days > 0) {
                totalPrice = totalPrice.add(car.getWeekendPrice().multiply(new BigDecimal(days)));
            }
            if (hours >= 5) {
                totalPrice = totalPrice.add(car.getWeekendPrice());
            }
            totalPrice = totalPrice.add(car.getWeekendPrice());
            // 原价
            originalPrice = originalPrice.add(car.getWeekendPriceFake());
        } else {
            // 工作日
            priceDetailVO.setRentalPrice(car.getNormalPrice());
            if (days > 0) {
                totalPrice = totalPrice.add(car.getNormalPrice().multiply(new BigDecimal(days)));
            }
            if (hours >= 5) {
                totalPrice = totalPrice.add(car.getNormalPrice());
            }
//            totalPrice = totalPrice.add(car.getNormalPrice());
            originalPrice = originalPrice.add(car.getNormalPriceFake());
        }

        // 自行取环在车辆租金上减去20
        if (carWay.equals("自行取还")) {
            priceDetailVO.setRentalPrice(priceDetailVO.getRentalPrice().subtract(new BigDecimal(20)));
        }

        // todo 假设商家上门送取车 一趟路费 80
        totalHours = totalHours - 24;
        if (guaranteePrice.equals("优享服务"))
            priceDetailVO.setGuaranteePrice(new BigDecimal(40));
        else
            priceDetailVO.setGuaranteePrice(new BigDecimal(0));

        // 一小时 服务保障免费 租车费不额外收取
        if (totalHours >= 0 && totalHours < 2) {
            BigDecimal t = priceDetailVO.getRentalPrice().multiply(new BigDecimal("0")); // 额外租车费 75
            priceDetailVO.setRentalPrice(t);
            totalPrice = totalPrice.add(t);
            originalPrice = originalPrice.add(t);
            priceDetailVO.setRentalPrice(totalPrice);

            totalPrice = totalPrice.add(priceDetailVO.getGuaranteePrice().multiply(new BigDecimal("0"))); // 超出一天的服务费
            priceDetailVO.setGuaranteePrice(priceDetailVO.getGuaranteePrice().add(priceDetailVO.getGuaranteePrice().multiply(new BigDecimal("0"))));

            originalPrice = originalPrice.add(priceDetailVO.getGuaranteePrice().multiply(new BigDecimal("0")));
        }
        if (totalHours >= 2 && totalHours < 3) {
            // 收取百分之25
            BigDecimal t = priceDetailVO.getRentalPrice().multiply(new BigDecimal("0.25")); // 额外租车费

            totalPrice = totalPrice.add(t);
            originalPrice = originalPrice.add(t);

            priceDetailVO.setRentalPrice(totalPrice);

            totalPrice = totalPrice.add(priceDetailVO.getGuaranteePrice().multiply(new BigDecimal("0.25"))); // 超出一天的服务费
            priceDetailVO.setGuaranteePrice(priceDetailVO.getGuaranteePrice().add(priceDetailVO.getGuaranteePrice().multiply(new BigDecimal("0.25"))));

            originalPrice = originalPrice.add(priceDetailVO.getGuaranteePrice().multiply(new BigDecimal("0")));

        }
        if (totalHours >= 3 && totalHours < 4) {
            // 收取百分之50

            BigDecimal t = priceDetailVO.getRentalPrice().multiply(new BigDecimal("0.5")); // 额外租车费
            totalPrice = totalPrice.add(t);
            originalPrice = originalPrice.add(t);

            priceDetailVO.setRentalPrice(totalPrice);

            totalPrice = totalPrice.add(priceDetailVO.getGuaranteePrice().multiply(new BigDecimal("0.5"))); // 超出一天的服务费
            priceDetailVO.setGuaranteePrice(priceDetailVO.getGuaranteePrice().add(priceDetailVO.getGuaranteePrice().multiply(new BigDecimal("0.5"))));

            originalPrice = originalPrice.add(priceDetailVO.getGuaranteePrice().multiply(new BigDecimal("0")));

        }
        if (totalHours >= 4 && totalHours < 5) {
            // 收取百分之75

            BigDecimal t = priceDetailVO.getRentalPrice().multiply(new BigDecimal("0.75")); // 额外租车费 75
            totalPrice = totalPrice.add(t);
            originalPrice = originalPrice.add(t);

            priceDetailVO.setRentalPrice(totalPrice);

            totalPrice = totalPrice.add(priceDetailVO.getGuaranteePrice().multiply(new BigDecimal("0.75"))); // 超出一天的服务费
            priceDetailVO.setGuaranteePrice(priceDetailVO.getGuaranteePrice().add(priceDetailVO.getGuaranteePrice().multiply(new BigDecimal("0.75"))));

            originalPrice = originalPrice.add(priceDetailVO.getGuaranteePrice().multiply(new BigDecimal("0")));

        }
        if (totalHours >= 5) {
            // 收取百分之100

            BigDecimal t = priceDetailVO.getRentalPrice().multiply(new BigDecimal("1")); // 额外租车费
//            totalPrice = totalPrice.add(t);
            originalPrice = originalPrice.add(t);

            priceDetailVO.setRentalPrice(totalPrice);

            totalPrice = totalPrice.add(priceDetailVO.getGuaranteePrice().multiply(new BigDecimal("1"))); // 超出一天的服务费
            priceDetailVO.setGuaranteePrice(priceDetailVO.getGuaranteePrice().add(priceDetailVO.getGuaranteePrice().multiply(new BigDecimal("1"))));

            originalPrice = originalPrice.add(priceDetailVO.getGuaranteePrice().multiply(new BigDecimal("0")));

        }
        totalPrice = priceDetailVO.getRentalPrice().add(priceDetailVO.getGuaranteePrice());
        priceDetailVO.setTotalPrice(totalPrice);



        priceDetailVO.setDiscountPrice(originalPrice.subtract(totalPrice));
        priceDetailVO.setOriginalPrice(originalPrice);
        priceDetailVO.setDiscountPrice(originalPrice.subtract(totalPrice));

        PriceDetailVO res = new PriceDetailVO();
        res.setTotalTime(priceDetailVO.getTotalTime());
        res.setDiscountPrice(priceDetailVO.getDiscountPrice());
        res.setOriginalPrice(priceDetailVO.getOriginalPrice());
        res.setRentalPrice(priceDetailVO.getRentalPrice());
        res.setServicePrice(priceDetailVO.getServicePrice());
        res.setGuaranteePrice(priceDetailVO.getGuaranteePrice());
        res.setTotalPrice(priceDetailVO.getTotalPrice());
        return res;
    }

    private static Boolean isWeekend(Timestamp dateTime) {
        // 将 Timestamp 转换为 LocalDateTime
        LocalDateTime localDateTime = dateTime.toLocalDateTime();

        // 获取星期几
        DayOfWeek dayOfWeek = localDateTime.getDayOfWeek();

        // 判断是否为周六或周日
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }

    /**
     * 计算时间差
     * @param start 开始时间
     * @param end 结束时间
     * @return
     */
    private static Integer calculateTotalHours(Timestamp start, Timestamp end) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("时间不能为空");
        }

        LocalDateTime startDateTime = start.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime endDateTime = end.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        // 计算小时数
        return Math.toIntExact(Duration.between(startDateTime, endDateTime).toHours());
    }
}
