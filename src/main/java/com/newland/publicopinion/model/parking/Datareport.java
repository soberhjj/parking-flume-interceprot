package com.newland.publicopinion.model.parking;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.List;

/**
 * @Description: 数据上报格式的bean
 * @Author: Ljh
 * @Date 2020/5/6 9:50
 */
@Data
public class Datareport {
    private String appId;
    private Integer timestamp;
    private String version;
    private String signMethod;
    private String sign;
    private Integer reportType;
    private List<Object> reportData;
}
