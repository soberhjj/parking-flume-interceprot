package com.newland.publicopinion.model.parking;

import lombok.Data;

/**
 * @Description: 车辆进出区域信息
 * @Author: Ljh
 * @Date 2020/5/6 10:51
 */
@Data
public class AccessParkingArea {
    private String id;
    private String parkId;
    private String areaId;
    private String parkNo;
    private String carPlate;
    private String enterTime;
    private Integer isLeft;
    private String leaveTime;
}
