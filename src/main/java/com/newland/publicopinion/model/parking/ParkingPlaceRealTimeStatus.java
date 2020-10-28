package com.newland.publicopinion.model.parking;

import lombok.Data;

/**
 * @Description: 停车场车位实时状态(kafka)
 * @Author: Ljh
 * @Date 2020/9/22 15:05
 */
@Data
public class ParkingPlaceRealTimeStatus {
    private String parkId;
    private Integer totalPlace;
    private Integer freePlace;
    private Integer reservedPlace;
    private Integer parkedPlace;
    private Long updateTime;
}
