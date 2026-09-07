<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head><title>Sửa Danh Mục</title></head>
<body>
    <h2>Chỉnh Sửa Danh Mục</h2>
    <form action="<c:url value='/admin/category/update'/>" method="post" enctype="multipart/form-data">
        <input type="hidden" name="categoryid" value="${cate.categoryid}">
        
        <label>Tên danh mục:</label><br>
        <input type="text" name="categoryname" value="${cate.categoryname}" required><br><br>
        
        <label>Ảnh hiện tại:</label><br>
        <c:choose>
            <c:when test="${fn:startsWith(cate.images, 'https')}">
                <c:url value="${cate.images}" var="imgUrl" />
            </c:when>
            <c:otherwise>
                <c:url value="/image?fname=${cate.images}" var="imgUrl" />
            </c:otherwise>
        </c:choose>
        <img height="100" width="120" src="${imgUrl}" /><br>
        <input type="hidden" name="images" value="${cate.images}">
        
        <label>Upload ảnh mới (nếu muốn đổi):</label><br>
        <input type="file" name="images1" accept="image/*"><br><br>

        <label>Trạng thái:</label><br>
        <input type="radio" name="status" value="1" ${cate.status == 1 ? 'checked' : ''}> Hoạt động<br>
        <input type="radio" name="status" value="0" ${cate.status == 0 ? 'checked' : ''}> Khóa<br><br>

        <input type="submit" value="Update">
    </form>
    <a href="<c:url value='/admin/categories'/>">Quay lại danh sách</a>
</body>
</html>