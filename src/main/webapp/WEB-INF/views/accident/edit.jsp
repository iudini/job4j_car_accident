<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<body>
<div class="container">
    <div>
        <a href="<c:url value='/'/>">Главная</a>
    </div>
    <form  action="<c:url value='/update?id=${accident.id}'/>" method='POST'>
        <table>
            <tr>
                <td>Название:</td>
                <td><input type='text' name='name' value="${accident.name}"></td>
            </tr>
            <tr>
                <td>Текст:</td>
                <td><input type='text' name='text' value="${accident.text}"></td>
            </tr>
            <tr>
                <td>Адрес:</td>
                <td><input type='text' name='address' value="${accident.address}"></td>
            </tr>
            <tr>
                <td>Тип:</td>
                <td>
                    <select name="type.id">
                        <c:forEach var="type" items="${types}" >
                            <option value="${type.id}" <c:if test="${accident.type.id == type.id}">selected</c:if>>
                                    ${type.name}
                            </option>
                        </c:forEach>
                    </select>
            </tr>
            <tr>
                <td>Статьи:</td>
                <td>
                    <select name="rIds" multiple>
                        <c:forEach var="rule" items="${rules}" >
                            <option value="${rule.id}" <c:if test="${accident.ruleInside(rule.id)}">selected</c:if>>
                                    ${rule.name}
                            </option>
                        </c:forEach>
                    </select>
            </tr>
            <tr>
                <td colspan='2'><input name="submit" type="submit" value="Сохранить" /></td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>