<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head><title>Thêm Danh Mục</title></head>
<body>
    <h2>Thêm Danh Mục Mới</h2>
    <form action="<c:url value='/admin/category/insert'/>" method="post" enctype="multipart/form-data">
        <label>Tên danh mục:</label><br>
        <input type="text" name="categoryname" required><br><br>
        
        <label>Link mạng (nếu có):</label><br>
        <input type="text" name="images"><br><br>

        <label>Hoặc Upload ảnh từ máy:</label><br>
        <input type="file" name="images1" accept="image/*"><br><br>

        <label>Trạng thái:</label><br>
        <input type="radio" name="status" value="1" checked> Hoạt động<br>
        <input type="radio" name="status" value="0"> Khóa<br><br>

        <input type="submit" value="Insert">
    </form>
    <a href="<c:url value='/admin/categories'/>">Quay lại danh sách</a>
</body>
</html>