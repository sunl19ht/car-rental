package com.sunl19ht.client.service;

import com.sunl19ht.client.pojo.vo.SlideshowVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SlideshowService {
    List<SlideshowVO> getSlideshowList();

    void addSlideshow(MultipartFile image);

    void deleteSlideshow(Integer id);
}
