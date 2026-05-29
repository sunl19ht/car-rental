package com.sunl19ht.client.controller;

import com.sunl19ht.client.pojo.vo.SlideshowVO;
import com.sunl19ht.client.service.SlideshowService;
import com.sunl19ht.pojo.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@Tag(name = "用户端-轮播图接口")
@RequestMapping("api/user/slideshow")
public class SlideshowController {
    @Autowired
    private SlideshowService slideshowService;

    @Operation(summary = "获取轮播图列表")
    @GetMapping("")
    public Result<List<SlideshowVO>> getSlideshow(){
        log.info("获取轮播图列表");
        List<SlideshowVO> slideshowList = slideshowService.getSlideshowList();
        return Result.success(slideshowList);
    }

    @PostMapping("")
    @Operation(summary = "添加轮播图")
    @Parameter(name = "image",description = "file",required = true,in= ParameterIn.QUERY)
    public Result addSlideshow(@RequestParam MultipartFile image){
        log.info("添加轮播图");
        slideshowService.addSlideshow(image);
        return Result.success();
    }

    @DeleteMapping("")
    @Operation(summary = "删除轮播图")
    @Parameter(name = "id",description = "轮播图id",required = true,in= ParameterIn.QUERY)
    public Result deleteSlideshow(@RequestParam Integer id){
        log.info("删除轮播图");
        slideshowService.deleteSlideshow(id);
        return Result.success();
    }
}
