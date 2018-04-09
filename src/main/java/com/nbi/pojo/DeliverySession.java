package com.nbi.pojo;

import java.util.Date;

/**
 * @author 17764
 *
 */
public class DeliverySession {
	
	private String deliverySessionId;
	private String actionType;
	private Date createTime;
	private Date startTime;
	
	public int getActionCount() {
		return actionCount;
	}
	public void setActionCount(int actionCount) {
		this.actionCount = actionCount;
	}
	private Date stopTime;
	private int actionCount;
	public Date getCreateTime() {
		return createTime;
	}
	public Date getStartTime() {
		return startTime;
	}
	public Date getStopTime() {
		return stopTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public void setStopTime(Date stopTime) {
		this.stopTime = stopTime;
	}
	public String getDeliverySessionId() {
		return deliverySessionId;
	}
	public void setDeliverySessionId(String deliverySessionId) {
		this.deliverySessionId = deliverySessionId;
	}
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
}
