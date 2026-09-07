<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Trang chủ - Cửa hàng</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<style>
.product-card {
	transition: transform 0.2s;
}

.product-card:hover {
	transform: scale(1.03);
	box-shadow: 0 4px 15px rgba(0, 0, 0, 0.15);
}

.product-img {
	height: 180px;
	object-fit: cover;
}
</style>
</head>
<body>
	<!-- Navbar -->
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
		<div class="container">
			<a class="navbar-brand fw-bold" href="<c:url value='/home'/>">SHOP
				ONLINE</a>
			<div class="navbar-nav me-auto">
				<a class="nav-link active" href="<c:url value='/home'/>">Trang
					chủ</a> <a class="nav-link" href="<c:url value='/product'/>">Tất cả
					sản phẩm</a>
			</div>
			<div class="navbar-nav">
				<c:choose>
					<c:when test="${not empty sessionScope.account}">
						<span class="nav-link text-warning">Xin chào,
							${sessionScope.account.fullname}</span>
						<c:if test="${sessionScope.account.role == 1}">
							<a class="nav-link" href="<c:url value='/admin/products'/>">Quản
								trị Sản phẩm</a>
							<a class="nav-link" href="<c:url value='/admin/categories'/>">Quản
								trị Danh mục</a>
						</c:if>
						<a class="nav-link" href="<c:url value='/logout'/>">Đăng xuất</a>
					</c:when>
					<c:otherwise>
						<a class="nav-link" href="<c:url value='/login'/>">Đăng nhập</a>
						<a class="nav-link" href="<c:url value='/register'/>">Đăng ký</a>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</nav>

	<!-- Banner -->
	<div class="bg-primary text-white py-4 text-center mb-4">
		<h2>HỆ THỐNG MUA SẮM TRỰC TUYẾN</h2>
		<p class="lead mb-0">10 SẢN PHẨM MỚI NHẤT VỪA LÊN KỆ</p>
	</div>

	<!-- Danh sách 10 sản phẩm mới nhất -->
	<div class="container mb-5">
		<h3 class="mb-4 text-uppercase border-bottom pb-2 text-primary">Top
			10 Sản Phẩm Mới Nhất</h3>
		<div class="row row-cols-1 row-cols-md-5 g-4">
			<c:forEach items="${top10Products}" var="p">
				<div class="col">
					<div class="card h-100 product-card shadow-sm">
						<a href="<c:url value='/product/detail?id=${p.productId}'/>"
							class="text-decoration-none text-dark"> <c:choose>
								<c:when test="${p.images.startsWith('http')}">
									<img src="${p.images}" class="card-img-top product-img"
										alt="${p.productName}">
								</c:when>
								<c:otherwise>
									<img src="<c:url value='/image?fname=${p.images}'/>"
										class="card-img-top product-img" alt="${p.productName}">
								</c:otherwise>
							</c:choose>
							<div class="card-body">
								<span class="badge bg-secondary mb-1">${p.category.categoryname}</span>
								<h6 class="card-title text-truncate">${p.productName}</h6>
								<p class="card-text text-danger fw-bold fs-5">${p.price}VNĐ</p>
							</div>
						</a>
						<div class="card-footer bg-white border-0 text-center">
							<a href="<c:url value='/product/detail?id=${p.productId}'/>"
								class="btn btn-outline-primary btn-sm w-100">Xem chi tiết</a>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
</body>
</html>