package com.sunl19ht.client.service.impl;

import com.sunl19ht.client.mapper.SlideshowMapper;
import com.sunl19ht.client.pojo.vo.SlideshowVO;
import com.sunl19ht.client.service.SlideshowService;
import com.sunl19ht.utils.TencentCOSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class SlideshowServiceImpl implements SlideshowService {
    @Autowired
    private SlideshowMapper slideshowMapper;

    @Autowired
    private TencentCOSUtils tencentCOSUtils;

    @Value("${cloud.tencent.cos.base-url}")
    private String baseUrl;
    @Override
    public List<SlideshowVO> getSlideshowList() {
        List<SlideshowVO> slideshowList = slideshowMapper.getSlideshowList();
        int index = 1;
        for (SlideshowVO slideshowVO : slideshowList) {
            slideshowVO.setIndex(index++);
        }
        return slideshowList;
    }

    @Override
    public void addSlideshow(MultipartFile image) {
        if (image.isEmpty()) {
            throw new RuntimeException("图片为空");
        }
        String imageUrl;
        try {
            imageUrl = baseUrl + tencentCOSUtils.simpleUpload(image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        slideshowMapper.addSlideshow(imageUrl);
    }

    @Override
    public void deleteSlideshow(Integer id) {
        slideshowMapper.deleteSlideshow(id);
    }
}
