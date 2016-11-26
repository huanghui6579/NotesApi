package com.yunxinlink.notes.api.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.yunxinlink.notes.api.model.VersionInfo;

/**
 * 版本的更新日志
 * @author huanghui-iri
 * @date 2016年11月21日 上午10:38:27
 */
@JsonInclude(Include.NON_EMPTY)
public class VersionLog implements Serializable {
	private static final long serialVersionUID = -5642357110594683333L;

	private int year;
	
	private List<VersionInfo> versionInfos;

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public List<VersionInfo> getVersionInfos() {
		return versionInfos;
	}

	public void setVersionInfos(List<VersionInfo> versionInfos) {
		this.versionInfos = versionInfos;
	}

	@Override
	public String toString() {
		return "VersionLog [year=" + year + ", versionInfos=" + versionInfos + "]";
	}
}
