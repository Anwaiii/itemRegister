<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="model.ItemBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*" session="false"%>
<%
	request.setCharacterEncoding("UTF-8");
	HttpSession sessionCheck = request.getSession(false);
	if (sessionCheck == null) {
		response.sendRedirect("Login.jsp");
		return;
	}
%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/UserBuy.css">
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

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Anson_Store</title>
<script type="text/javascript">
	function noBuy() {
		if (window.confirm('購買せずに一覧画面へ戻りますか？')) {
			window.location = "UserListCon"
		} else {
			return false;
		}
	}
</script>
<script type="text/javascript">
window.addEventListener('load', function() {
    document.getElementById('10+block').addEventListener('input', function() {
        var inputValue = this.value;
        document.getElementById('buyAmount').value = inputValue;
    });
});
</script>

<script type="text/javascript">
	function checkValue(val) {
		var value = document.getElementById('buyAmount');
		var text = document.getElementById('10+block');

		if (val == '10+') {
			value.style.display = 'none';
			text.style.display = 'block';

		} else {
			text.style.display = 'none';
			value.style.display = 'block';
		}
	}
</script>
</head>
<%
	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	response.setDateHeader("Expires", 0); //Causes the proxy cache to see the page as "stale"
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0 backward compatibility
%>
<body>

	<script defer src="js/click.js" type="text/javascript" charset="UTF-8"></script>
	<h1>商品購買画面</h1>
	<div class="message">
		&nbsp
		<%
			Integer message = (Integer) request.getAttribute("message");
			if (message != null) {
				if (message == -1) {
		%>
		<span class="fail">✖在庫不足です。購買数を入れ直してください。✖&nbsp</span>
		<%
			} else if (message == -2) {
		%>
		<span class="fail">✖購買数に0以下は入力できません。入れ直してください。✖&nbsp</span>

		<%
			} else if (message == -3) {
		%>
		<span class="fail">✖管理者アカウントは注文できないです。✖&nbsp</span>

		<%
			} else {
		%>
		<span class="success">●商品がカートに追加されました●&nbsp</span>
		<%
			}}
		%>

	</div>


	<div class="href">
		<a class="linkLeft" href="javascript:void(0)" onclick="noBuy();">一覧画面へ</a>
		<a></a>
	</div>

	<%
		ItemBean itemBean = (ItemBean) request.getAttribute("Item");
		Date date = new Date();
	%>

	<form action="BuyCon" method="post" id="buyFormID">
		<table border=1>
			<caption>♦購買する個数を入力してください♦</caption>

			<thead>
				<tr>
					<th colspan="2"><img
						src=".\files\<%=itemBean.getItemImage()%>" alt="image" width="400"
						height="260"></th>
				</tr>

			</thead>

			<tbody style="float:center">

				<tr>
					<td>商品NO</td>
					<td><input type="hidden" name="itemNo"
						value="<%=itemBean.getItemNo()%>" id="<%=itemBean.getItemNo()%>"><%=itemBean.getItemNo()%></td>
				</tr>

				<tr>
					<td>商品名</td>
					<td style="word-break: break-all"><%=itemBean.getItemName()%></td>
				</tr>

				<tr>
					<td>目玉/一般</td>
					<%
						if (itemBean.getSpecialItem() == 0) {
					%>
					<td>目玉商品</td>
					<%
						} else {
					%>
					<td>一般商品</td>
					<%
						}
					%>
				</tr>
				<tr>
					<td>値段</td>
					<td><%=itemBean.getItemPrice()%>円/個</td>
				</tr>
				<tr>
					<td>在庫数</td>
					<td><input type="hidden" name="stockCount"
						value="<%=itemBean.getStockCount()%>"
						id="<%=itemBean.getStockCount()%>"> <%=itemBean.getStockCount()%>個</td>
				</tr>

				<tr>
					<td>購買数</td>
					<td><p class="select"><select name="buyAmount" id="buyAmount"
						onchange='checkValue(this.value)'>
							<option value="1">1</option>
							<option value="2">2</option>
							<option value="3">3</option>
							<option value="4">4</option>
							<option value="5">5</option>
							<option value="6">6</option>
							<option value="7">7</option>
							<option value="8">8</option>
							<option value="9">9</option>
							<option value="10+">10+</option>
					</select><input type="text" name="buyAmount" id="10+block" size="5"
						style="display:none" class="validate[required],custom[integer],min[1]" style="float:center"></p></td>
				</tr>
			</tbody>
		</table>

		<br> <br>
		<div class="buttonall">
			<input type="submit" value="カートへ追加" class="button" id="buy">
		</div>
	</form>


</body>
</html>