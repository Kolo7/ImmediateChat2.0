/**
 * index.js kolo
 */

function login() {
	$.ajax({
		url : "/ImmediateChat2.0/LoginServlet",
		data : $("#loginForm").serialize(),
		type : "POST",
		dataType : "json",
		success : function(msg) {
			console.log(msg);
			if (msg.message=="true") {
				console.log(msg.username);
				window.location.href = "http://"+ domain +":8080/ImmediateChat2.0/list.html";
			}else {
				alert("密码错误");
				$("#inpassword").val("");
			}
		}
	});
}

function register() {
	$.ajax({
		url : "/ImmediateChat2.0/RegisterServlet",
		data : $("#registerForm").serialize(),
		type : "POST",
		dataType : "json",
		success : function(msg) {
			if(msg.stute=="true"){
				console.log(msg.stute);
				window.location.href = "http://"+ domain +":8080/ImmediateChat2.0/list.html";
			}else{
				alert("账号已存在");
				document.getElementById("registerForm").reset();
			}
			
		}
	});
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
		}
	});
}
var domain = document.domain;
username();
