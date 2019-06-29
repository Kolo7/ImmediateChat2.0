/**
 * list.js
 */

function initSocket() {
	if (typeof (WebSocket) == "undefined") {
		console.log("您的浏览器不支持WebSocket");
	} else {
		console.log("您的浏览器支持WebSocket/websocket");
	}
	// socket连接地址: 注意是ws协议
	socket = new WebSocket(
			"ws://"+ domain +":8080/ImmediateChat2.0/websocketTest/" + userid);
	socket.onopen = function() {
		console.log("Socket 已打开");
	};
	// 获得消息事件
	socket.onmessage = function(msg) {
		var data = msg.data;
		var date = new Date();
		if (data.indexOf("#onlineCount") == 0) {
			console.log($("#onlineCount").html());
			$("#onlineCount").text(data.split(':')[1]);
		} else if(data.indexOf("#list") == 0){
			var list = data.split(':')[1];
			if(list==undefined)
				return ;
			list = list.split(',');
			var len = list.length;
			document.getElementById("users").innerHTML="";
			for(var i=0;i<len;i++){
				adduser(list[i], date.toLocaleString( ));
			}
		}else if(msg.data.indexOf("#private")==0){
			var name = msg.data.slice(9, msg.data.indexOf("@"));
			var message = msg.data.slice(msg.data.indexOf("@")+1);
			showother(name+":"+message, "home");
		}else{
			showother(msg.data,"menu1");
		}
	};
	// 关闭事件
	socket.onclose = function() {
		console.log("Socket已关闭");
	};
	// 错误事件
	socket.onerror = function() {
		alert("Socket发生了错误");
	};
	window.addEventListener('unload', function() {
		socket.close();
	}, false);

}
function send() {
	var privateuser = document.getElementById("privateuser").innerHTML;
	var result = $("#privatemsg").val();
	showme(result, "home");
	socket.send("@"+privateuser+"#"+result);
}
function sendall() {
	var result = "@ALL#"+$("#msg").val();
	socket.send(result);
	console.log(result);
	showme($("#msg").val(), "menu1");
	$("#msg").val("");
}
function username() {
	
	$.ajax({
		url : "/ImmediateChat2.0/UserNameServlet",
		data : "",
		type : "GET",
		dataType : "json",
		success : function(msg) {
			console.log(msg);
			$("#usernameTag").html(msg.username);
			userid=msg.username;
			initSocket();
		}
	});
}

function adduser(name, time){
	var users = document.getElementById("users");
	var trtag = document.createElement("tr");
	trtag.setAttribute("style", "background-color:#FF8F63;");
	var nametdtag = document.createElement("td");
	nametdtag.appendChild(document.createTextNode(name));
	var timetdtag = document.createElement("td");
	timetdtag.appendChild(document.createTextNode(time));
	var opertdtag = document.createElement("td");
	var atag = document.createElement("input");
	atag.setAttribute("class","btn btn-primary");
	atag.setAttribute("type","button");
	atag.addEventListener('click', function(e){selectuser(e);} , false);
	atag.setAttribute("id",name);
	atag.setAttribute("value", "私聊");
	opertdtag.appendChild(atag);
	trtag.appendChild(nametdtag);
	trtag.appendChild(timetdtag);
	trtag.appendChild(opertdtag);
	users.appendChild(trtag);
}
function showme(message, id){
	if(message==undefined) return;
	console.log("id:"+message);
	var home = document.getElementById(id).innerHTML;
	var cardlist = home.split("<br>");
	var newnode = '<div style="float: right;">'+
	'<div class="card" style="width: 600px; float: left; background-color: #B7F4D1; margin: 5px;">'+
	'<div class="card-body row"><div class="col-md-10"><p class="card-text">'+message +'</p>'+
	'</div><div class="col-md-2"><text style="font-size:10px" class="card-link font-italic">'+(new Date()).toLocaleString()+'</text>'+
	'</div></div></div><div class="form-group" style="float: right; margin-right: 10px;">'+
	'<small style="margin-left: 3px;" id="helpId" class="form-text text-muted">'+userid+'</small>'+ 
	'<input class="btn btn-secondary" type="button" value="我"   style="border-radius: 50%;" /></div></div>';
	document.getElementById(id).innerHTML = cardlist[0] +'<br>'+ newnode + cardlist[1];
}
function showother(message, id){
	if(message==undefined) return;
	console.log(message);
	var home = document.getElementById(id).innerHTML;
	var cardlist = home.split("<br>");
	var name = message.slice(0, message.indexOf(":")); 
	message = message.slice(message.indexOf(":")+1);
	var newnode = '<div style="float: left;"><div class="form-group" style="float: left; margin-right: 10px;">'+
		'<small style="margin-left: 3px;" id="helpId" class="form-text text-muted">'+name+'</small>'+ 
		'<input class="btn btn-secondary" type="button" value="'+name[0]+'" style="border-radius: 50%;background-color:#8295C9;" id="selectid">'+
		'</div><div class="card" style="float: right; width: 600px; background-color: #F4C2B7; margin: 5px;">'+
		'<div class="card-body row"><div class="col-md-10"><p class="card-text">'+message+'</p>'+
		'</div><div class="col-md-2"><text style="font-size:10px" class="card-link font-italic">'+(new Date()).toLocaleString()+'</text>'+
		'</div></div></div></div>';
	document.getElementById(id).innerHTML = cardlist[0] +'<br>'+ newnode + cardlist[1];
	document.getElementById("selectid").addEventListener('click', function(e){selecthead(e,name);} , false);
}
function selectuser(e){
	var privateuser = e.srcElement.getAttribute("id");
	console.log(privateuser);	
	document.getElementById("privateuser").innerHTML=privateuser;
	document.getElementById("navitem1").click();
}

function selecthead(e, name){
	console.log(name);
	document.getElementById(name).click();
}
var userid;
var socket;
var domain = document.domain;
username();
//document.getElementById("insend").addEventListener('click', sendall , false);