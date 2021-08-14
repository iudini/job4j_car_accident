<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<body>
<div class="container">
    <div>
        <a href="<c:url value='/'/>">Главная</a>
    </div>
    <form  action="<c:url value='/save'/>" method='POST'>
        <table>
            <tr>
                <td>Название:</td>
                <td><input type='text' name='name'></td>
            </tr>
            <tr>
                <td>Текст:</td>
                <td><input type='text' name='text'></td>
            </tr>
            <tr>
                <td>Адрес:</td>
                <td><input type='text' name='address'></td>
            </tr>
            <tr>
                <td colspan='2'><input name="submit" type="submit" value="Сохранить" /></td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>