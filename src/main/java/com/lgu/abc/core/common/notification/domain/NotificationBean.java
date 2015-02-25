package com.lgu.abc.core.common.notification.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * Desc :
 * FileName : NotificationRequest.java
 * ClassName : NotificationRequest
 * Date : 2013. 4. 16.
 * Author : leechanwoo
 */
public @Data class NotificationBean {
	private List<String> sendUsrKey = new ArrayList<String>();
	private List<String> recvUsrKey = new ArrayList<String>();
	private List<String> recvDeptKey = new ArrayList<String>();
	private String recvCorpKey;
	private String recvWsKey;
	private String message;
	private String title;
	private String contentId;
	
	private String boardName;
	private String time;
	private String remainingTime;
	private String link;
	private String type;
	private String targetDiv;
	private String boardID;
	private String count;
	private String writerName;
	private String folderName;
	private String fileName;
	private String apprLine;
	private String drftDtime;
	private String drftUserName;
	private String aprvUserName;
	private String aprvDtime;
	private String apprOpn;
	private String labelName;
	
	
	public void addSender(String userId) {
		this.sendUsrKey.add(userId);
	}
	
	public void addReceiver(String userId) {
		this.recvUsrKey.add(userId);
	}
	
	public void addReceivingDepartment(String deptKey) {
		this.recvDeptKey.add(deptKey);
	}	
	
}

