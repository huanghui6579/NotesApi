package com.yunxinlink.notes.api.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 设备信息的实体
 * @author huanghui1
 *
 */
@JsonInclude(Include.NON_NULL)
public class DeviceInfo implements Serializable {
	private static final long serialVersionUID = 5179390087353766133L;
	
	private Integer id;
	
	/**
	 * 设备唯一编号
	 */
	private String imei;
	
	/**
	 * 设备平台，如Android、IOS、Windows等
	 */
	private String os;
	
	/**
	 * 系统的版本号，如Android 6.0
	 */
	private String osVersion;
	
	/**
	 * 手机型号，如1505-A02
	 */
	private String phoneModel;
	
	/**
	 * 手机的厂商，如360 、小米、华为
	 */
	private String brand;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 修改时间
	 */
	private Date modifyTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getOsVersion() {
		return osVersion;
	}

	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}

	public String getPhoneModel() {
		return phoneModel;
	}

	public void setPhoneModel(String phoneModel) {
		this.phoneModel = phoneModel;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	/**
	 * 检查设备信息是否为空，imei不存在则为空
	 * @return
	 */
	public boolean checkInfo() {
		return StringUtils.isNotBlank(imei) && !"0".equals(imei);
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	@Override
	public String toString() {
		return "DeviceInfo [id=" + id + ", imei=" + imei + ", os=" + os + ", osVersion=" + osVersion + ", phoneModel="
				+ phoneModel + ", brand=" + brand + ", createTime=" + createTime + ", modifyTime=" + modifyTime + "]";
	}
}
