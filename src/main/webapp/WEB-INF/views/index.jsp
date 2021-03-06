<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns" crossorigin="anonymous"></script>

    <title>Accident</title>
</head>
<body>
    <div class="container">
        <div>
            Login as : ${user.username}
        </div>
        <div>
            <a href="${pageContext.request.contextPath}/logout">logout</a>
        </div>
        <div>
            <a href="<c:url value='/create'/>">Добавить инцидент</a>
        </div>
        <table class="table">
            <thead>
                <tr>
                    <th>id</th>
                    <th>Name</th>
                    <th>Text</th>
                    <th>Address</th>
                    <th>Type</th>
                    <th>Rules</th>
                    <th>Update</th>
                </tr>
            </thead>
            <c:forEach items="${accidents}" var="acc">
                <tr>
                    <td>${acc.id}</td>
                    <td>${acc.name}</td>
                    <td>${acc.text}</td>
                    <td>${acc.address}</td>
                    <td>${acc.type.name}</td>
                    <td>
                        <c:forEach var="rule" items="${acc.rules}">
                            <span id="${rule.id}">${rule.name}</span>
                        </c:forEach>
                    </td>
                    <td><span><a href="<c:url value='/update?id=${acc.id}'/>">Редактировать инцидент</a></span></td>
                </tr>
            </c:forEach>
        </table>
    </div>
</body>
</html>