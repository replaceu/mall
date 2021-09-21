package com.gulimall.common.to.mq;

/**
 * @author Carter
 * @date 2021-09-17 00:32
 * @description:
 * @version:
 */
public class StockLockedTo {
	private Long id;//库存工作单的id

	private Long detailId;//库存工作单详情的id

	/**
	 * sku_id
	 */
	private Long	skuId;
	/**
	 * sku_name
	 */
	private String	skuName;
	/**
	 * 购买个数
	 */
	private Integer	skuNum;
	/**
	 * 工作单id
	 */
	private Long	taskId;

	/**
	 * 仓库id
	 */
	private Long wareId;

	/**
	 * 锁定状态
	 */
	private Integer lockStatus;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDetailId() {
		return detailId;
	}

	public void setDetailId(Long detailId) {
		this.detailId = detailId;
	}

	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName == null ? null : skuName.trim();
	}

	public Integer getSkuNum() {
		return skuNum;
	}

	public void setSkuNum(Integer skuNum) {
		this.skuNum = skuNum;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public Long getWareId() {
		return wareId;
	}

	public void setWareId(Long wareId) {
		this.wareId = wareId;
	}

	public Integer getLockStatus() {
		return lockStatus;
	}

	public void setLockStatus(Integer lockStatus) {
		this.lockStatus = lockStatus;
	}
}
