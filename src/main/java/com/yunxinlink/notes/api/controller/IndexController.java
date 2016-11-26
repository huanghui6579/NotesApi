package com.yunxinlink.notes.api.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunxinlink.notes.api.dto.ActionResult;
import com.yunxinlink.notes.api.dto.VersionInfoDto;
import com.yunxinlink.notes.api.dto.VersionLog;
import com.yunxinlink.notes.api.model.Platform;
import com.yunxinlink.notes.api.model.VersionInfo;
import com.yunxinlink.notes.api.service.IVersionService;
import com.yunxinlink.notes.api.util.AttachUsage;
import com.yunxinlink.notes.api.util.Constant;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

@Controller
@RequestMapping()
public class IndexController extends BaseController {
	
	@Autowired
	private IVersionService versionService;
	
	@RequestMapping(value = {"/", ""})
	public String index(Model model, HttpServletRequest request) {
		VersionInfo versionInfo = getVersionInfo(null);
		if (versionInfo != null) {
			String baseUrl = getBasePath(request);
			String downloadUrl = baseUrl + "app/download?platform=0";
			model.addAttribute("versionInfo", versionInfo);
			model.addAttribute("androidUrl", downloadUrl);
		}
		return "index";
	}
	
	/**
	 * 获取最新的版本信息
	 * @param versionInfo
	 * @return
	 */
	@RequestMapping(value = "app/check", method = RequestMethod.POST)
	@ResponseBody
	public ActionResult<VersionInfoDto> checkAppVersion(VersionInfo versionInfo) {
		ActionResult<VersionInfoDto> actionResult = new ActionResult<>();
		if (versionInfo == null || versionInfo.getPlatform() == null) {
			actionResult.setResultCode(ActionResult.RESULT_PARAM_ERROR);
			actionResult.setReason("参数错误");
			return actionResult;
		}
		versionInfo = versionService.getLastVersion(versionInfo);
		if (versionInfo != null) {
			VersionInfoDto infoDto = versionInfo.convert2Dto();
			actionResult.setResultCode(ActionResult.RESULT_SUCCESS);
			actionResult.setData(infoDto);
			actionResult.setReason("获取成功");
		} else {
			actionResult.setResultCode(ActionResult.RESULT_FAILED);
			actionResult.setReason("没有新版本");
		}
		return actionResult;
	}
	
	/**
	 * 下载APP包
	 * @param versionInfo
	 * @return
	 */
	@RequestMapping(value = "app/download")
	@ResponseBody
	public ResponseEntity<InputStreamResource> downloadApp(VersionInfo versionInfo, HttpServletRequest request) {
		InputStreamResource inputStreamResource = null;
		HttpHeaders headers = new HttpHeaders();
		HttpStatus httpStatus = null;
		if (versionInfo != null) {
			versionInfo = getVersionInfo(versionInfo);
			if (versionInfo != null && StringUtils.isNotBlank(versionInfo.getLocalPath())) {
				String localPath = versionInfo.getLocalPath();
				File file = getAppFile(versionInfo.getPlatform(), localPath);
				inputStreamResource = parseFile(file, null, headers);
			}
		}
		
		if (inputStreamResource != null) {
			httpStatus = HttpStatus.OK;
		} else {
			httpStatus = HttpStatus.NOT_FOUND;
		}
		return new ResponseEntity<InputStreamResource>(inputStreamResource, headers, httpStatus);
	}
	
	@RequestMapping(value = "updateLog", method = RequestMethod.GET)
	public String showVersionLog(VersionInfo versionInfo, Model model) {
		String url = "app-log";
		String cacheKey = "logList";
		List<VersionLog> logList = null;
		CacheManager cacheManager = CacheManager.getInstance();
		Cache cache = cacheManager.getCache(Constant.DEFAULT_CACHE);
		if (cache != null) {
			Element element = cache.get(cacheKey);
			if (element != null) {
				logList = (List<VersionLog>) element.getObjectValue();
				model.addAttribute("logList", logList);
				Calendar calendar = Calendar.getInstance();
				model.addAttribute("curYear", calendar.get(Calendar.YEAR));
				logger.info("show version log from cache");
				return url;
			}
		}
		List<VersionInfo> versionInfos = versionService.getVersionsByPlatform(versionInfo);
		if (!CollectionUtils.isEmpty(versionInfos)) {
			Calendar calendar = Calendar.getInstance();
			Map<Integer, List<VersionInfo>> versionMap = new HashMap<>();
			for (VersionInfo info : versionInfos) {
				Date createTime = info.getCreateTime();
				String content = info.getContent();
				if (createTime == null || content == null) {
					continue;
				}
				calendar.setTime(createTime);
				int year = calendar.get(Calendar.YEAR);
				List<VersionInfo> list = versionMap.get(year);
				if (list == null) {
					list = new ArrayList<>();
					versionMap.put(year, list);
				}
				
				info.setSubLineList(info.formatLog());
				
				list.add(info);
			}
			if (!versionMap.isEmpty()) {
				logList = new ArrayList<>();
				Set<Integer> keys = versionMap.keySet();
				List<Integer> keyList = new ArrayList<>(keys);
				if (keyList.size() > 1) {
					Collections.sort(keyList);
					Collections.reverse(keyList);
				}
				for (Integer key : keyList) {
					List<VersionInfo> list = versionMap.get(key);
					VersionLog log = new VersionLog();
					log.setYear(key);
					log.setVersionInfos(list);
					logList.add(log);
				}
				if (cache != null) {
					Element element = new Element(cacheKey, logList);
					cache.put(element);
				}
				model.addAttribute(cacheKey, logList);
				calendar.setTime(new Date());
				model.addAttribute("curYear", calendar.get(Calendar.YEAR));
			}
		}
		return url;
	}
	
	/**
	 * 根据条件查询版本信息
	 * @param versionInfo
	 * @return
	 */
	private VersionInfo getVersionInfo(VersionInfo param) {
		VersionInfo versionInfo = null;
		if (param == null || param.getId() <= 0) {
			param = new VersionInfo();
			//暂时只有Android平台
			param.setPlatform(Platform.PLATFORM_ANDROID);
			versionInfo = versionService.getLastVersion(param);
		} else {
			versionInfo = versionService.getVersionInfo(param.getId());
		}
		return versionInfo;
	}
	
	/**
	 * 根据软件的平台获取不同的文件夹
	 * @param platform
	 * @return
	 */
	private String getAppPrefix(int platform) {
		String prefix = null;
		switch (platform) {
		case Platform.PLATFORM_ANDROID:	//Android的软件包
			prefix = Constant.ANDROID;
			break;
		case Platform.PLATFORM_IOS:	//ios的软件包
			prefix = Constant.IOS;
			break;

		default:
			break;
		}
		return prefix;
	}
	
	/**
	 * 获取APP的本地路径
	 * @param platform
	 * @param filename
	 * @return
	 */
	private File getAppFile(int platform, String filename) {
		String prefix = getAppPrefix(platform);
		if (prefix == null) {
			return null;
		}
		filename = prefix + File.separator + filename;
		return getAttachFile(filename, AttachUsage.SOFT_ATTACH, false);
	}
}
