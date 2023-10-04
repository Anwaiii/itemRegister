<%@page import="model.PurchaseRecordDao"%>
<%@page import="model.PurchaseRecordBean"%>
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
<link rel="stylesheet" type="text/css"
	href="css/mouseHoverDropDownDesign.css">
	<link rel="stylesheet" type="text/css"
	href="css/PurchaseRecord.css">
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
function Logout(){
	if(window.confirm("ログアウトしますか？")){
		window.location.replace('Logout');
		return false;
	}
}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Anson_Store</title>
<style>
th, td{
    border-right: none !important;
    border-left: none !important;
}
</style>
</head>

<body>

	<h1>注文履歴</h1>

	<%
		sessionCheck = request.getSession(false);
	%>
	<ul>
		<%
			System.out.println("sessionCheck");
			 	UreservationBean user= (UreservationBean)sessionCheck.getAttribute("user");
		%>
		<li><a>Hello,<%= user.getUserName() %>さん 残額:<%= user.getMoney() %>円
		</a>

			<ul class="dropdown">
				<li><a href="UserListCon">商品一覧</a></li>
				<li><a href="Charge.jsp">チャージ</a></li>
				<li><a href="javascript:void(0)" onclick="Logout();">ログアウト</a></li>
			</ul></li>
	</ul>
	<%
				PurchaseRecordDao purchaseRecordDao = new PurchaseRecordDao();
				ArrayList<Integer> orderID = purchaseRecordDao.getAllOrderID(user);

	%>



	<%
					for(int i = 0;i<orderID.size();i++){
						ArrayList<PurchaseRecordBean> itemList = purchaseRecordDao.getPurchasedItemByEachOderID(orderID.get(i));
						int sum =0;  %>
	<table border=1 style="width: 850px">
		<thead>
			<tr>

				<th colspan="5"><span style="float:left">注文日:<%= itemList.get(0).getPurchaseDate() %></span>
					<span style="float:right">注文番号:#<%= itemList.get(0).getID() %></span>
				</th>
			</tr>
		</thead>
		<tbody>


			<% for(int j = 0;j<itemList.size();j++){%>
			<tr style="height : 120px;">
				<td><img src=".\files\<%= itemList.get(j).getImagePath() %>"
					alt="image" width="180" height="120"></td>
				<td colspan="2" style="width: 300px"><%= itemList.get(j).getItemName() %></td>
				<td style="width: 150px">単価:<%= itemList.get(j).getPrice() %><br>
					数量:<%= itemList.get(j).getAmount() %></td>
				<td style="width: 150px"><%= itemList.get(j).getTotalPrice() %>円<% sum += itemList.get(j).getTotalPrice(); %></td>

			</tr>

			<%}%>
			<tr>
				<td colspan="5">
				<span style="float:left">注文者ID:<%= itemList.get(0).getUserID() %></span>
			<span style="float:right">	(<%= itemList.size() %> items) 合計金額:  <%= sum %>円</span>
			</td>
			</tr>

		</tbody>

	</table>
	<br>
	<%}%>






</body>
</html>
