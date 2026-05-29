package com.sunl19ht.client.pojo.entity;

import com.sunl19ht.client.pojo.vo.UseCarVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UseCarResultVO implements Serializable {
    private List<UseCarVO> carList = new ArrayList<>();
    private List<CarTypePrice> carTypePrices = new ArrayList<>();
}
