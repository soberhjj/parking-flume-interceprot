package com.newland.publicopinion.spider;

import com.newland.framework.common.date.DateUtils;
import com.newland.framework.common.parser.JSON;
import com.newland.publicopinion.conf.Constant;
import com.newland.publicopinion.model.parking.AccessParkingArea;
import com.newland.publicopinion.model.parking.AccessParkingLot;
import com.newland.publicopinion.model.parking.Datareport;
import com.newland.publicopinion.model.parking.ParkingPlaceRealTimeStatus;
import com.newland.publicopinion.util.StringUtils;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description: 解析上报的停车场数据
 * @Author Ljh
 * @Date 2020/5/6 9:22
 *
 * @return
 */
public class ParkingParseInterceptor implements Interceptor {

	private static final Logger logger = LoggerFactory.getLogger(ParkingParseInterceptor.class);

	/**
	 * 逐条解析
	 */
	@Override
	public Event intercept(Event event) {
		// 获取文本信息
		String eventData = null;
		try {
			eventData = new String(event.getBody(), Constant.CHARSET_UTF8);
		} catch (UnsupportedEncodingException e1) {
			logger.error("转码失败 ",e1);
			return null;
		}
		// 修改headers信息,可以在sink的时候,写入到特定目录
		Map<String, String> headers = event.getHeaders();

		String subclasspath = null;
		String[] dataArr = null;
		try {
			dataArr = parse(eventData);
			if(StringUtils.isEmpty(dataArr[0]) || StringUtils.isEmpty(dataArr[1])) {
				throw new Exception("解析失败，解析结果为空");
			}
			subclasspath = "/" + dataArr[0];
			event.setBody(dataArr[1].getBytes());

		} catch (Exception e) {
			e.printStackTrace();
			subclasspath = "/error";
			event.setBody(eventData.getBytes());
		}

		headers.put("subclasspath", subclasspath);
		return event;
	}

	/**
	 * 批量解析
	 */
	@Override
	public List<Event> intercept(List<Event> events) {
		List<Event> intercepted = new ArrayList<Event>(events.size());
		for (Event event : events) {
			Event interceptedEvent = intercept(event);
			if (interceptedEvent != null) {
				intercepted.add(interceptedEvent);
			}
		}
		return intercepted;
	}

	/**
	 * 构建拦截器
	 */
	public static class Builder implements Interceptor.Builder {
		@Override
		public void configure(Context arg0) {
		}

		@Override
		public Interceptor build() {
			return new ParkingParseInterceptor();
		}
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void close() {
		
	}

	/**
	 *  转csv格式，\001分隔
	 * @param eventData
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private String[] parse(String eventData) throws Exception {
		String sepFeed = "\n";
		String[] resultArr = new String[3];
		Datareport datareport = JSON.toBean(StringUtils.replace(eventData, Constant.REGEX_SPECIAL_CHAR, Constant.HTML_BR), Datareport.class);

		//获取FIELD_CRAWL_TIME的长度，超过10位的就默认10位(因为int长度只能为10位)
		int tmpLength = datareport.getTimestamp().toString().length();
		int length = tmpLength>10 ? 10 : tmpLength;

		//获取json里的数据
		int reportType = datareport.getReportType();
		StringBuilder sb = new StringBuilder();
		if(reportType == 100400){
			for(Object data:datareport.getReportData()){
				AccessParkingLot accessParkingLot = JSON.toBean(data.toString(),AccessParkingLot.class);
				sb.append(accessParkingLot.getId()).append(Constant.SEP_ONE)
						.append(accessParkingLot.getParkId()).append(Constant.SEP_ONE)
						.append(accessParkingLot.getParkNo()).append(Constant.SEP_ONE)
						.append(accessParkingLot.getCarPlate()).append(Constant.SEP_ONE)
						.append(accessParkingLot.getEnterTime()).append(Constant.SEP_ONE)
						.append(accessParkingLot.getIsLeft()).append(Constant.SEP_ONE)
						.append(accessParkingLot.getLeaveTime()).append(Constant.SEP_ONE)
						.append(accessParkingLot.getParkPay()).append(sepFeed);
			}
			resultArr[0] = "my_ods_access_parking_lot";
		}else if(reportType == 100600){
			for(Object data:datareport.getReportData()){
				ParkingPlaceRealTimeStatus parkingPlaceRealTimeStatus = JSON.toBean(data.toString(),ParkingPlaceRealTimeStatus.class);
				sb.append(parkingPlaceRealTimeStatus.getParkId()).append(Constant.SEP_ONE)
						.append(parkingPlaceRealTimeStatus.getTotalPlace()).append(Constant.SEP_ONE)
						.append(parkingPlaceRealTimeStatus.getFreePlace()).append(Constant.SEP_ONE)
						.append(parkingPlaceRealTimeStatus.getReservedPlace()).append(Constant.SEP_ONE)
						.append(parkingPlaceRealTimeStatus.getParkedPlace()).append(Constant.SEP_ONE)
						.append(parkingPlaceRealTimeStatus.getUpdateTime()).append(Constant.SEP_ONE)
						.append(datareport.getTimestamp()).append(sepFeed);


			}
			resultArr[0] = "my_ods_parking_place_realtime_status";
		}else {
			logger.error(String.format("error reportType: %s",reportType));
		}

		// 删除最后一个字符\n
		resultArr[1] = sb.deleteCharAt(sb.length() - 1).toString();

		return resultArr;
	}


}
