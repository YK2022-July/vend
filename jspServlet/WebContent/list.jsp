<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!-- パッケージ名を指定してItemBeanをインポート -->
<%@ page import="drinkMachine.add.*"%>

<!-- ↓リストをインポートする文章 -->
<%@ page import="java.io.*,java.util.*,java.sql.*,javax.naming.*"%>

<!-- リストselectedItmを取り出すgetAttributeするときの型宣言は<Servlet>でリストを宣言した型に合わせる -->
<!-- セッションの利用 -->
<%List<ItemBean> list = (List<ItemBean>)request.getSession().getAttribute("sItm"); %>
<!-- エラー表示のための変数設定 -->
<%String errormessage = (String)request.getAttribute("error"); %>

<IDOCTYPE html PUBLIC "-//W3C//DIDXHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DID/xhtml1-Transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ja" lang="ja">

<!-- 遷移先のコントローラーを指定するformタグ -->
<!-- 0番目のform -->

<form name="edit" method="Post" action="EditController"></form>

<!-- 1番目のform -->
<form name="delete" method="Post" action="DeleteController"></form>

<!-- 2番目のform -->
<form name="view" method="Post" action="ViewController"></form>

<!-- edit.jspに商品データを送る -->
<SCRIPT type="text/javascript" language=JavaScript>
<!--
function edit(code){
	if(confirm("■商品データを変更しますか？") == false){
		return false;
	}else{
		var Code = document.createElement('input');
		Code.type = 'hidden';
		Code.name = 'code';
		Code.value = code;
alert(code);
		document.forms[0].appendChild(Code);
		document.forms[0].submit();
	}
}
-->
</SCRIPT>



<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>商品一覧</title>
</head>
<body>

	<h1>商品一覧</h1>

	<%
  	//受け取ったlist2の値がnullの場合
    if(errormessage != null){
		    		%>
	<br>
	<b><font color="#ff0000"> 検索条件に一致するデータがありません。<br>もう一度入力してください
	</font></b>
	<br>
	<br>
	<%
    	//}
    }
    %>

	>
	<a href="./add.jsp">追加</a>
	<br> >
	<a href="./cart.html">購入者画面をチェックする</a>
	<br>
	<br>

	<form action="ListController" method="post">


		<table cellspacing="1" cellpadding="8" border="0" bgcolor="#999999">
			<tbody>
				<tr>
					<th width="100" bgcolor="#EBEBEB">商品コード</th>
					<td width="250" bgcolor="#FFFFFF"><input type="text"
						name="code" value=""></td>
				</tr>
				<tr>
					<th width="100" bgcolor="#EBEBEB">商品名<sup><font
							color="#FF0000">*</font></sup></th>

					<td width="250" bgcolor="#FFFFFF"><input type="text"
						name="name" value=""></td>
				</tr>
				<tr>
					<th bgcolor="#EBEBEB">他、検索条件</th>
					<td bgcolor="#FFFFFF"><input type="checkbox" name="isPR"
						value="True" {% ifitem.isPR %} checked{% endif %}>おすすめ商品</input>
						<input type="checkbox" name="isSoldout" value="True" {% ifitem.isSoldout %} checked{% endif %}>売切れ商品</input>
					</td>

				</tr>
			</tbody>
		</table>
		<br>



		<div border="0" bgcolor="#999999">
			<div>
				<input type="submit" value="検索">
			</div>
			<br>
			<div>
				<input type="submit" value="一括登録"> <input type="submit"
					value="一覧の情報をCSV出力">
			</div>
		</div>

		<br> <br>
		<table cellspacing="1" cellpadding="8" border="0" bgcolor="#999999">
			<tbody>
				<tr bgcolor="#EBEBEB">
					<th></th>
					<th></th>
					<th></th>
					<th align="center">商品コード</th>
					<th align="center">商品名</th>

					<th align="center">金額</th>
					<th align="center">数量</th>
				</tr>


				<%
	//
	if(list != null){
		for(int i=0;i<list.size();i++){
			// 取得したListから１件ずつ商品データ（ItemBean）を取り出します
			ItemBean selectedItm2 = list.get(i);
			System.out.println(list);
%>

				<tr bgcolor="#FFFFFF">
					<td><a href="./view.jsp?{{ person.key }}">詳細</a></td>





					<!-- JavaScriptのeditファンクションを呼ぶ -->
					<td><a href="javascript:edit(<%=selectedItm2.getCode()%>);">編集</a></td>


					<td><a href="./delete.jsp?{{ item.key }}">削除</a></td>

					<!--各セル（tdタグ）に検索結果（商品コード等）を入れます -->
					<td align="center"><%= selectedItm2.getCode() %></td>
					<td align="center"><%= selectedItm2.getName() %></td>
					<td align="center"><%= selectedItm2.getPrice() %></td>
					<td align="center"><%= selectedItm2.getCount() %></td>
				</tr>
				<%
		}
	}
%>



				</tr>
			</tbody>
		</table>
</body>
</html>