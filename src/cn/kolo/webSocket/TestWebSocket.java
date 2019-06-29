package cn.kolo.webSocket;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/websocketTest/{userId}")
public class TestWebSocket {
	private static int onlineCount = 0;
	private static Map<String, TestWebSocket> users = Collections.synchronizedMap(new HashMap());
	private static CopyOnWriteArrayList userslist = new CopyOnWriteArrayList<String>();
	private Session session;
	private String myname;
	// 自己的username

	// 连接时执行
	@OnOpen
	public void onOpen(@PathParam("userId") String username, Session session) throws IOException {
		this.session = session;
		users.put(username, this); // 加入map中,为了测试方便使用username做key
		addOnlineCount();
		userslist.add(username);
		myname = username;
		try {
			//this.session.getBasicRemote().sendText("连接成功");
			sendInfo("admin"+":加入到聊天室");
			StringBuffer listStringBuffer = new StringBuffer("#list:");
			Iterator<String> itr = userslist.iterator();
			while (itr.hasNext()) {
				String string = (String) itr.next();
				listStringBuffer.append(string);
				listStringBuffer.append(",");
			}
			
			listStringBuffer.deleteCharAt(listStringBuffer.length()-1);
			
			//session.getBasicRemote().sendText(listStringBuffer.toString());
			sendInfo("#onlineCount:"+String.valueOf(getOnlineCount()));
			sendInfo(listStringBuffer.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 关闭时执行
	@OnClose
	public void onClose(@PathParam("userId") String userId, Session session) throws IOException {
		users.remove(userId); // 从set中删除
		userslist.remove(userId);
		StringBuffer listStringBuffer = new StringBuffer("#list:");
		Iterator<String> itr = userslist.iterator();
		while (itr.hasNext()) {
			String string = (String) itr.next();
			listStringBuffer.append(string);
			listStringBuffer.append(",");
		}
		listStringBuffer.deleteCharAt(listStringBuffer.length()-1);
		sendInfo(listStringBuffer.toString());
		subOnlineCount(); // 在线数减1
		System.out.println(userId+"断开连接");
	}

	// 收到消息时执行
	@OnMessage
	public void onMessage(@PathParam("userId") String userId, String message, Session session) throws IOException {
		this.session = session;
		System.out.println(message);
		try {
			if (message == null) {
				return;
			}
			String[] split = message.split("#");
			if(split.length<2) return;
			if(split[0].equals("@ALL")) {
				sendInfoExceptMe(userId +": "+split[1]);
				//sendInfo(userId +": "+split[1]);
			}else {
				String user = split[0].substring(1);
				 sendMessageToSomeBody( user.trim(),split[1]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 连接错误时执行
	@OnError
	public void onError(@PathParam("userId") String userId, Session session, Throwable error) {
		error.printStackTrace();
	}

	public void sendMessageToSomeBody( String username, String message) throws IOException {
		if (users.get(username) == null) {
			return;
		}
		users.get(username).session.getBasicRemote().sendText("#private:"+myname+"@" +message);
		//this.session.getBasicRemote().sendText(myname + "@" + username + ": " + message);
		
	}

	public void sendInfo(String message) throws IOException {
		for (TestWebSocket item : users.values()) {
			try {
				//if(myname==item.myname) continue;
				item.session.getBasicRemote().sendText(message);
			} catch (IOException e) {
				continue;
			}
		}
	}
	
	public void sendInfoExceptMe(String message) throws IOException {
		for (TestWebSocket item : users.values()) {
			if(myname.equals(item.myname)) 
				continue;
			try {
				
				item.session.getBasicRemote().sendText(message);
			} catch (IOException e) {
				continue;
			}
		}
	}
	
	public static synchronized int getOnlineCount() {
		return onlineCount;
	}

	public static synchronized void addOnlineCount() {
		TestWebSocket.onlineCount++;
	}

	public static synchronized void subOnlineCount() {
		TestWebSocket.onlineCount--;
	}

}
