package com.yunxinlink.notes.api.controller;

import org.apache.log4j.Logger;

public class BaseController {
	protected static Logger logger = null;
	
	public BaseController() {
		logger = Logger.getLogger(getClass());
	}
}
