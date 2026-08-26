<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head><title>Danh sách Category</title></head>
<body>
    <h2>Quản Lý Danh Mục (JPA)</h2>
    <a href="<c:url value='/admin/category/add'/>">Add Category</a><br><hr>
    <table border="1" width="100%" cellpadding="5">
        <tr>
            <th>STT</th><th>Hình ảnh</th><th>Tên Category</th><th>Trạng thái</th><th>Hành động</th>
        </tr>
        <c:forEach items="${listcate}" var="cate" varStatus="STT">
            <tr>
                <td>${STT.index+1}</td>
                <c:choose>
                    <c:when test="${fn:startsWith(cate.images, 'https')}">
                        <c:url value="${cate.images}" var="imgUrl" />
                    </c:when>
                    <c:otherwise>
                        <c:url value="/image?fname=${cate.images}" var="imgUrl" />
                    </c:otherwise>
                </c:choose>
                <td><img height="100" width="120" src="${imgUrl}" /></td>
                <td>${cate.categoryname}</td>
                <td>${cate.status == 1 ? 'Hoạt động' : 'Khóa'}</td>
                <td>
                    <a href="<c:url value='/admin/category/edit?id=${cate.categoryid}'/>">Sửa</a> | 
                    <a href="<c:url value='/admin/category/delete?id=${cate.categoryid}'/>" onclick="return confirm('Chắc chắn xóa?');">Xóa</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>