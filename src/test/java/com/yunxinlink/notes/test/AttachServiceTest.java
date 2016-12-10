package com.yunxinlink.notes.test;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yunxinlink.notes.api.init.IdGenerator;
import com.yunxinlink.notes.api.model.Attach;
import com.yunxinlink.notes.api.model.DeleteState;
import com.yunxinlink.notes.api.model.Folder;
import com.yunxinlink.notes.api.service.IAttachService;
import com.yunxinlink.notes.api.service.IFolderService;

/**
 * 
 * @author tiger
 * @date 2016年10月6日 上午10:00:39
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})
public class AttachServiceTest {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IAttachService attachService;
	
	@Autowired
	private IFolderService folderService;

	@Test
	public void testAddAttach() {
		Attach attach = new Attach();
		Date date = new Date();
		attach.setCreateTime(date);
		attach.setDeleteState(DeleteState.DELETE_NONE);
		attach.setFilename("fdsgfdg.png");
		attach.setLocalPath("45453/1455/fdsgfdg.png");
		attach.setMimeType("image/png");
		attach.setModifyTime(date);
		attach.setNoteSid("1");
		attach.setSid(IdGenerator.generateUUID());
		attach.setSize(274575L);
		attach.setUserId(1);
		
		attachService.addAttach(attach);
		logger.info("attach:" + attach);
	}

	@Test
	public void testAddAttachs() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateAttach() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteAttach() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteAttachs() {
		List<Attach> list = new ArrayList<>();
		Attach attach = new Attach();
		attach.setId(1);
		list.add(attach);
		
		attach = new Attach();
		attach.setId(2);
		list.add(attach);
		attachService.deleteAttachs(list);
	}

	@Test
	public void testDeleteByNote() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetById() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetByNote() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testSortFolder() {
		List<Folder> list = new ArrayList<>();
		Folder folder = new Folder();
		folder.setSid("1570021667785146371");
		folder.setSort(2);
		
		list.add(folder);
		
		folder = new Folder();
		folder.setSid("1570025077284536322");
		folder.setSort(4);
		
		list.add(folder);
		
		folder = new Folder();
		folder.setSid("1570026417356275719");
		folder.setSort(3);
		
		list.add(folder);
		
		int count = folderService.updateSort(list);
		logger.info("update folder sort count:" + count);
		
	}

}
