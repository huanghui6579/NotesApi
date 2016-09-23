package com.yunxinlink.notes.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.util.AlternativeJdkIdGenerator;
import org.springframework.util.IdGenerator;
import org.springframework.util.SimpleIdGenerator;

public class TestUUID {

	@Test
	public void test() {
		IdGenerator idGenerator = new SimpleIdGenerator();
		String uuid = idGenerator.generateId().toString();
		System.out.println("uuid:" + uuid);
		
		idGenerator = new AlternativeJdkIdGenerator();
		uuid = idGenerator.generateId().toString();
		System.out.println("uuid:" + uuid);
		
		idGenerator = new AlternativeJdkIdGenerator();
		uuid = idGenerator.generateId().toString();
		System.out.println("uuid:" + uuid);
	}

}
