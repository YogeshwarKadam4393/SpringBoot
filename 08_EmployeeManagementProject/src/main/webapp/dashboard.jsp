<%@page import="com.example.entity.Employee"%>
<%@page import="java.util.List"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Employee List</title>
</head>

<body>

    <table width="700" height="50" border="1">

        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>City</th>
            <th>Email Address</th>
            <th>UserName</th>
            <th>Password</th>
        </tr>

        <%
            List<Employee> listOfEmp =
                (List<Employee>) request.getAttribute("listOfEmp");

            for (Employee emp : listOfEmp) {
        %>

        <tr>
            <td><%= emp.getId() %></td>
            <td><%= emp.getName() %></td>
            <td><%= emp.getCity() %></td>
            <td><%= emp.getEmailid() %></td>
            <td><%= emp.getUsername() %></td>
            <td><%= emp.getPassword() %></td>
        </tr>

        <%
            }
        %>

    </table>

</body>
</html>
