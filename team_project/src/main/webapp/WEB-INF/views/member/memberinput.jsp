<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
<form action="membersave" method="post" align="center">
<table>
<tr><th>���̵�</th><td><input type="text" name="userId"></td></tr>
<tr><th>��й�ȣ</th><td><input type="text" name="password"></td></tr>
<tr><th>�̸�</th><td><input type="text" name="userName"></td></tr>
<tr><th>����</th><td><input type="text" name="age"></td></tr>
<tr><th>����</th><td><input type="radio" name="gender" value="����">����
					<input type="radio" name="gender" value="����">����</td></tr>
<tr><th>�̸���</th><td><input type="text" name="email"></td></tr>
<tr><th>��ȣ</th><td><input type="text" name="phone"></td></tr>
<tr><th>�ּ�</th><td><input type="text" name="address"></td></tr>
<tr><td colspan="2" align="center"><input type="submit" value="����"></td></tr>
</table>
</form>
</body>
</html>