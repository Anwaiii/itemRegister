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
<link rel="stylesheet" type="text/css" href="css/Cart.css">
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
		jQuery("#buyFormID").validationEngine();
	});
</script>

<script type="text/javascript">
function Detail(itemNo){
		var form = document.forms[0];
		var input = document.getElementById(itemNo)
		form.appendChild(input);
		document.body.appendChild(form);
		form.submit();
	}
</script>

<%-- 更新リンクのfunction --%>
<script type="text/javascript">
function UpdateLink(itemNo){
	if(window.confirm("この商品を更新しますか？")){
		var form = document.forms[1];
		var input = document.getElementById(itemNo);
		form.appendChild(input);
		document.body.appendChild(form);
		form.submit();
	}else{
		return false;
	}
		}
</script>

<script type="text/javascript">
function DeleteLink(itemNo){
	if(window.confirm("この商品を削除しますか？")){
		var form = document.forms[2];
		var input = document.getElementById(itemNo);
		form.appendChild(input);
		document.body.appendChild(form);
		form.submit();
}else{
	return false;
}
	}
</script>

<script type="text/javascript">
function clearResult(){
		document.getElementById("itemName").value=''
		var radio = document.getElementById("radioButton");
		var radio2 = document.getElementById("radioButton2");

			radio.checked=false;
			radio2.checked=false;
}

</script>

<script type="text/javascript">
function buyConfirm(){
	if(window.confirm("注文確定？")){
		var form = document.getElementById("buyFormID");

		form.submit();
}else{
	return false;
}
	}
</script>

<script type="text/javascript">
function Logout(){
	if(window.confirm("ログアウトしますか？")){
		window.location.replace('Logout');
		return false;
	}
}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Anson_Store</title>

</head>
<%
	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
response.setDateHeader("Expires", 0); //Causes the proxy cache to see the page as "stale"
response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility
%>
<body>

	<form method="post" action="ViewCon"></form>
	<form method="get" action="BuyCon"></form>
	<form method="get" action="CartCon"></form>
	<form method="post" action="CartCon"></form>

	<h1>カート一覧</h1>

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
		<li><a href="Charge.jsp">チャージ</a></li>
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

		<span class="fail">残額不足です。チャージしますか？</span><a href="Charge.jsp">こちらへ</a>

			<% }else if(message == -2){ %>

		<span class="fail">カートは空です</span>

			<% }else if(message == 0){ %>

		<span class="success">チャージが完了しました</span>

		<% }} %>

	</div>

	<div class="href">
		<a href="UserListCon">商品一覧画面へ</a> <a
			href=""></a>
	</div>


	<form action="CartCon" method="post" id="buyFormID" name="buyForm">
		<table border=1>
			<caption>♦カート情報♦</caption>
			<thead>
				<tr>

					<th>イメージ写真</th>
					<th>商品名</th>
					<th>注文数</th>
					<th>値段(合計)</th>
					<th>項目選択</th>
				</tr>
			</thead>

			<tbody>



				<%
					@SuppressWarnings("unchecked")
				ArrayList<ItemBean> itemList = (ArrayList<ItemBean>) request.getAttribute("cart");
				int sum=0;
					if(itemList != null){
					for (ItemBean item : itemList) {
						int subTotalAmount = item.getItemPrice() * item.getBuyAmount();
				%>

				<tr>

					<td> <img src=".\files\<%= item.getItemImage() %>" alt="image"  width="200" height="130"></td>
					 <td class="itemName" style="word-break: break-all">
					 <input type="hidden" name="itemNo"
					 value="<%= item.getItemNo() %> id="<%= item.getItemNo() %>"><%=item.getItemName()%></td>
					 <td class="buyAmount"><%=item.getBuyAmount()%>個</td>
					<td class="price"><input type="hidden" name="subTotalAmount"
					 value="<%= subTotalAmount %> id="<%= subTotalAmount %>"><%= subTotalAmount %>円</td>
					<% sum += subTotalAmount; %>

					<td class="choice"><input type="hidden" name="itemNo"
						value="<%=item.getItemNo()%>" id="<%=item.getItemNo()%>">
						<a href="javascript:void(0)"
						onclick="Detail(<%=item.getItemNo()%>);">詳細</a>　<a
						href="javascript:void(0)"
						onclick="UpdateLink(<%=item.getItemNo()%>);">更新</a>　<a
						href="javascript:void(0)"
						onclick="DeleteLink(<%=item.getItemNo()%>);">削除</a></td>
				</tr>
				<%
					}
				%>
				<td colspan="5" >合計(<%= itemList.size() %> items): <%= sum %>円</td>
				<%
					}
				%>
			</tbody>

		</table>



<br><br>
		<div class="serach" style="text-align:center">

			<%
				String name = (String) request.getAttribute("itemName");
				String specialItem = (String) request.getAttribute("specialItem");

				if (name == null) {
					name = "";
				}
			%>

		<div class="buttonall">
			<input type="button" value="注　文" class="" id="buttonDesign" onclick="buyConfirm();">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp

		</div>
		<br> <br>
	</form>
</body>
</html>