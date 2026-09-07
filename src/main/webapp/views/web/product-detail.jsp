<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${product.productName} - Chi tiết</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark mb-4">
        <div class="container">
            <a class="navbar-brand fw-bold" href="<c:url value='/home'/>">SHOP ONLINE</a>
            <div class="navbar-nav me-auto">
                <a class="nav-link" href="<c:url value='/home'/>">Trang chủ</a>
                <a class="nav-link" href="<c:url value='/product'/>">Tất cả sản phẩm</a>
            </div>
        </div>
    </nav>

    <div class="container my-5">
        <div class="card shadow p-4">
            <div class="row">
                <!-- Cột hình ảnh -->
                <div class="col-md-5 text-center">
                    <c:choose>
                        <c:when test="${product.images.startsWith('http')}">
                            <img src="${product.images}" class="img-fluid rounded border" style="max-height: 380px;">
                        </c:when>
                        <c:otherwise>
                            <img src="<c:url value='/image?fname=${product.images}'/>" class="img-fluid rounded border" style="max-height: 380px;">
                        </c:otherwise>
                    </c:choose>
                </div>

                <!-- Cột thông tin -->
                <div class="col-md-7">
                    <h2 class="fw-bold">${product.productName}</h2>
                    <p class="badge bg-success fs-6">${product.category.categoryname}</p>
                    <h3 class="text-danger fw-bold my-3">${product.price} VNĐ</h3>

                    <p><b>Trạng thái:</b> 
                        <c:if test="${product.status == 1}"><span class="text-success fw-bold">Còn hàng</span></c:if>
                        <c:if test="${product.status != 1}"><span class="text-danger fw-bold">Hết hàng</span></c:if>
                    </p>
                    <p><b>Số lượng trong kho:</b> ${product.quantity}</p>
                    <p><b>Ngày đăng:</b> ${product.createDate}</p>

                    <hr>
                    <h5>Mô tả sản phẩm:</h5>
                    <div class="p-3 bg-light rounded">
                        <c:choose>
                            <c:when test="${not empty product.description}">${product.description}</c:when>
                            <c:otherwise><p class="text-muted fst-italic">Chưa có mô tả chi tiết.</p></c:otherwise>
                        </c:choose>
                    </div>

                    <div class="mt-4">
                        <a href="<c:url value='/product'/>" class="btn btn-outline-secondary">« Quay lại danh sách</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>