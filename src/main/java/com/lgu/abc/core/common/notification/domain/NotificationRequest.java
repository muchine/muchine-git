package com.lgu.abc.core.common.notification.domain;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;
import lombok.ToString;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize.Inclusion;

/**
 * Desc :
 * FileName : NotificationRequest.java
 * ClassName : NotificationRequest
 * Date : 2013. 4. 16.
 * Author : leechanwoo
 */
//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
@ToString
@JsonSerialize(include=Inclusion.NON_NULL)
public @Data class NotificationRequest {
	private Map<String, Object> sendId;
	private Map<String, Object> recvId;
	
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
	
	public NotificationRequest() {}
	
	public NotificationRequest(NotificationBean bean) {
		setTargetDiv(bean.getTargetDiv());
		
		setSenders(bean);
		setReceivers(bean);
		
		setMessage(bean.getMessage());
		setTitle(bean.getTitle());
		setContentId(bean.getContentId());
		setBoardName(bean.getBoardName());
		setTime(bean.getTime());
		setRemainingTime(bean.getRemainingTime());
		setLink(bean.getLink());
		setType(bean.getType());
		setWriterName(bean.getWriterName());
		setFolderName(bean.getFolderName());
		setFileName(bean.getFileName());
		setApprLine(bean.getApprLine());
		setDrftDtime(bean.getDrftDtime());
		setDrftUserName(bean.getDrftUserName());
		setAprvUserName(bean.getAprvUserName());
		setAprvDtime(bean.getAprvDtime());
		setApprOpn(bean.getApprOpn());
		setLabelName(bean.getLabelName());
		
		if (getApprOpn() == null) setApprOpn("");
	}
	
	private void setSenders(NotificationBean bean) {
		Map<String, Object> sendId = new HashMap<String, Object>();
		sendId.put("user", bean.getSendUsrKey());
		setSendId(sendId);
	}
	
	private void setReceivers(NotificationBean bean) {
		Map<String, Object> recvId = new HashMap<String, Object>();
		if (bean.getRecvUsrKey().size() > 0) {
			recvId.put("user", bean.getRecvUsrKey());
		}
		if (bean.getRecvDeptKey().size() > 0) {
			recvId.put("dept", bean.getRecvDeptKey());
		}
		if (bean.getRecvCorpKey() != null) {
			recvId.put("corp", bean.getRecvCorpKey());
		}
		if (bean.getRecvWsKey() != null) {
			recvId.put("ws", bean.getRecvWsKey());
		}
		
		setRecvId(recvId);
	}
	
}

