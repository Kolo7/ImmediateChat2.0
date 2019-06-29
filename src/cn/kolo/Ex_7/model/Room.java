package cn.kolo.Ex_7.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Room {
	private int roomId;
	private List<User> userList;
	private String roomName;
	@Id
	public int getRoomId() {
		return roomId;
	}
	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}
	@OneToMany(mappedBy="account")
	public List<User> getUserList() {
		return userList;
	}
	public void setUserList(List<User> userList) {
		this.userList = userList;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	@Override
	public String toString() {
		return "Room [roomId=" + roomId + ", userList=" + userList + ", roomName=" + roomName + "]";
	}
	
}
