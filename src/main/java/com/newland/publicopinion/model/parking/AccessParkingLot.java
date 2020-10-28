package com.newland.publicopinion.model.parking;

import lombok.Data;

/**
 * @Description: 车辆进出场信息
 * @Author: Ljh
 * @Date 2020/5/6 10:42
 */
@Data
public class AccessParkingLot {
    private String id;
    private String parkId;
    private String parkNo;
    private String carPlate;
    private String enterTime;
    private Integer isLeft;
    private String leaveTime;
    private Integer parkPay;
}
