package com.yunxinlink.notes.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yunxinlink.notes.api.dto.NoteDto;
import com.yunxinlink.notes.api.init.IdGenerator;
import com.yunxinlink.notes.api.model.Attach;
import com.yunxinlink.notes.api.model.DeleteState;
import com.yunxinlink.notes.api.model.Folder;
import com.yunxinlink.notes.api.model.NoteInfo;
import com.yunxinlink.notes.api.model.NoteKind;
import com.yunxinlink.notes.api.model.User;
import com.yunxinlink.notes.api.util.SystemUtil;

public class NoteControllerTest {
	private static final String BASE_URL = "http://localhost:8080/noteapi/note/";

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Test
	public void testCreateNote() {
//		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		User user = new User();
		user.setSid("1569298489463013383");
		
		Date date = new Date();
		
		Folder folder = new Folder();
		folder.setCreateTime(date);
		folder.setModifyTime(date);
		folder.setDeleteState(DeleteState.DELETE_NONE);
		folder.setName("工作");
		folder.setSort(1);
		
		NoteInfo noteInfo = new NoteInfo();
		noteInfo.setContent("回应称，地方细则对户籍和车辆的严格限制将使得绝大多数滴滴平台上目前服务老百姓的车辆和司机被迫退出，无法继续为各地市民提供便利、实惠的移动出行服务。老百姓亦将重新面对痛恨已久的“打车难”、打车贵的旧况。而高排量的车辆准入也将增加城市环境压力");
		noteInfo.setCreateTime(date);
		noteInfo.setDeleteState(DeleteState.DELETE_NONE);
		noteInfo.setHash(DigestUtils.md2Hex(noteInfo.getContent()));
		noteInfo.setKind(NoteKind.TEXT);
		noteInfo.setSid(IdGenerator.generateUUID());
		noteInfo.setFolderSid(folder.getSid());
		
		Attach attach = new Attach();
		attach.setSid(IdGenerator.generateUUID());
		attach.setCreateTime(date);
		attach.setModifyTime(date);
		attach.setDeleteState(DeleteState.DELETE_NONE);
		attach.setDescription("附加费的技能加多少的文件");
		attach.setFilename("abc.png");
		attach.setLocalPath("2016/10/08/att/abc.png");
		attach.setMimeType("image/png");
		attach.setSize(25612L);
		attach.setNoteSid(noteInfo.getSid());
		
		List<Attach> attachs = new ArrayList<>();
		attachs.add(attach);
		
		noteInfo.setAttachs(attachs);
		
		NoteDto noteDto = new NoteDto();
		noteDto.setFolder(folder);
		
		List<NoteInfo> noteInfos = new ArrayList<>();
		noteInfos.add(noteInfo);
		
		noteDto.setNoteInfos(noteInfos);
		noteDto.setUserSid(user.getSid());
		
		String json = SystemUtil.obj2json(noteDto);
		logger.info("json:" + json);
	}

}
