<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="com.sun.xml.internal.bind.v2.schemagen.xmlschema.List"%>
<%@page import="model.ItemBean"%>
<%@page import="model.UreservationBean"%>
<%@page import="java.util.ArrayList"%>
<%@ page import="java.util.*" session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
HttpSession sessionCheck = request.getSession(false);
if (sessionCheck == null){
	response.sendRedirect("Login.jsp");
	return;
}
%>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/mouseHoverDropDownDesign.css">
<link rel="stylesheet" type="text/css" href="css/Charge.css">
<link rel="stylesheet" type="text/css" href="css/Mutual.css">
<link rel="stylesheet" type="text/css"
	href="css/validationEngine.jquery.css">


<script src="js/jquery-1.8.2.min.js" type="text/javascript"></script>
<script src="js/jquery.validationEngine.js" type="text/javascript"
	charset="UTF-8"></script>
<script src="js/languages/jquery.validationEngine-ja.js"
	type="text/javascript" charset="UTF-8"></script>
<script type="text/javascript">
	jQuery(document).ready(function() {
		jQuery("#ChargeFormID").validationEngine();
	});
</script>

<script type="text/javascript">
function Logout(){
	if(window.confirm("ログアウトしますか？")){
		window.location.replace('Logout');
		return false;
	}
}
</script>
<script type="text/javascript">
window.addEventListener('load', function() {
    document.getElementById('textBox').addEventListener('input', function() {
        var inputValue = this.value;
        document.getElementById('other').value = inputValue;
    });
});
</script>

<script>
function showText(value) {
	var other = document.getElementById("other");
	var textBox =  document.getElementById("textBox");

    if (other.checked) {
    	textBox.style.display = "block";
    	other.value = textBox.value;
    }else{
    	textBox.style.display = "none";
    }
}

</script>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Anson_Store</title>

</head>
<body>

<h1>チャージ</h1>

	<%
		sessionCheck = request.getSession(false);
	%>
	<ul>
	<%
		if (sessionCheck != null) {
			System.out.println("sessionCheck");
		 	UreservationBean user= (UreservationBean)sessionCheck.getAttribute("user");
	%>
	<li><a>Hello,<%= user.getUserName() %>さん 残額:<%= user.getMoney() %>円</a>
		<ul class="dropdown">
		<li><a href="UserListCon">商品一覧</a></li>
		<li><a href="PurchaseRecord.jsp">注文履歴</a></li>
		<li><a href="javascript:void(0)" onclick="Logout();">ログアウト</a></li>
		</ul>
		</li>
	</ul>
	<%} %>



	<div class="message">
	&nbsp
	<!-- このspaceキーは詳細/更新/削除の出力結果メッセージの位置を確保するために据えるものです %-->
		<%
			Integer message = (Integer) request.getAttribute("message");
		if(message != null){
			if(message == -1){%>

		<span class="fail">チャージ金額を入力してください</span>
			<%}}%>

	</div>

	<div class="href">
		<a href="CartCon">カートへ</a> <a
			href=""></a>
	</div>
	<br>


	<form action="ChargeCon" method="post" id="ChargeFormID" name="ChargeForm">
		<table border=1>
			<caption></caption>
			<thead>
				<tr>

					<th style="width:300px">チャージ金額を選んでください</th>

				</tr>
			</thead>

			<tbody>


				<tr style="text-align:left">

					<td> <input type="radio" name="balance" value="1000" class="validate[required]"
					onclick="showText(this.value);">1000円<br>
					<input type="radio" name="balance" value="2000" class="validate[required]"
					onclick="showText(this.value);">2000円<br>
					<input type="radio" name="balance" value="5000" class="validate[required]"
					onclick="showText(this.value);">5000円<br>
					<input type="radio" name="balance" value="10000" class="validate[required]"
					onclick="showText(this.value);">10000円<br>
					<input type="radio" name="balance" value="30000" class="validate[required]"
					onclick="showText(this.value);">30000円<br>
					<input type="radio" name="balance" value="50000" class="validate[required]"
					onclick="showText(this.value);">50000円<br>
					<input type="radio" name="balance" value="" id="other" onclick="showText(this.value);"
					 class="validate[required]">Other

					<input type="text" name="balance" id="textBox" size="5"
						style="display:none" class="validate[required],custom[integer],min[1]">

					</td>

				</tr>



			</tbody>

		</table>

		<br><br>
		<div class="buttonall">
			<input type="submit" value="チャージ" class="button" id="register">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
			<input type="reset" value="リセット" class="button"> <br> <br>
		</div>
		</form>




</body>
</html>