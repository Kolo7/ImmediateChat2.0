package cn.kolo.Ex_7.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Message {
	private int messageId;
	private String content;
	private Date senTime;
	private User sendUser;
	@Id
	public int getMessageId() {
		return messageId;
	}
	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getSenTime() {
		return senTime;
	}
	public void setSenTime(Date senTime) {
		this.senTime = senTime;
	}
	@ManyToOne
	@JoinColumn(name="userId")
	public User getSendUser() {
		return sendUser;
	}
	public void setSendUser(User sendUser) {
		this.sendUser = sendUser;
	}
	@Override
	public String toString() {
		return "Message [messageId=" + messageId + ", content=" + content + ", senTime=" + senTime + ", sendUser="
				+ sendUser + "]";
	}
	
	
}
