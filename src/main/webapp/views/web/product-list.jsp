<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Danh sách sản phẩm</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<style>
.product-img {
	height: 220px;
	object-fit: cover;
}

.product-card {
	transition: transform 0.2s;
}

.product-card:hover {
	transform: translateY(-5px);
	box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
}
</style>
</head>
<body>
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark mb-4">
		<div class="container">
			<a class="navbar-brand fw-bold" href="<c:url value='/home'/>">SHOP
				ONLINE</a>
			<div class="navbar-nav me-auto">
				<a class="nav-link" href="<c:url value='/home'/>">Trang chủ</a> <a
					class="nav-link active" href="<c:url value='/product'/>">Tất cả
					sản phẩm</a>
			</div>
			<div class="navbar-nav">
				<c:if test="${empty sessionScope.account}">
					<a class="nav-link" href="<c:url value='/login'/>">Đăng nhập</a>
				</c:if>
				<c:if test="${not empty sessionScope.account}">
					<span class="nav-link text-warning">Xin chào,
						${sessionScope.account.fullname}</span>
					<a class="nav-link" href="<c:url value='/logout'/>">Đăng xuất</a>
				</c:if>
			</div>
		</div>
	</nav>

	<div class="container mb-5">
		<h2 class="mb-4 text-center">TẤT CẢ SẢN PHẨM</h2>

		<!-- Lưới 6 sản phẩm (3 cột x 2 hàng) -->
		<div class="row row-cols-1 row-cols-md-3 g-4">
			<c:forEach items="${productList}" var="p">
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
								<span class="badge bg-info text-dark mb-2">${p.category.categoryname}</span>
								<h5 class="card-title">${p.productName}</h5>
								<p class="card-text text-danger fs-5 fw-bold">${p.price}VNĐ</p>
								<p class="text-muted small">Số lượng còn: ${p.quantity}</p>
							</div>
						</a>
						<div class="card-footer bg-white border-0">
							<a href="<c:url value='/product/detail?id=${p.productId}'/>"
								class="btn btn-primary w-100">Bấm xem chi tiết</a>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>

		<!-- Phân trang -->
		<nav aria-label="Page navigation" class="mt-5">
			<ul class="pagination justify-content-center">
				<c:if test="${currentPage > 1}">
					<li class="page-item"><a class="page-link"
						href="<c:url value='/product?page=${currentPage - 1}'/>">«
							Trước</a></li>
				</c:if>

				<c:forEach begin="1" end="${totalPages}" var="i">
					<li class="page-item ${i == currentPage ? 'active' : ''}"><a
						class="page-link" href="<c:url value='/product?page=${i}'/>">${i}</a>
					</li>
				</c:forEach>

				<c:if test="${currentPage < totalPages}">
					<li class="page-item"><a class="page-link"
						href="<c:url value='/product?page=${currentPage + 1}'/>">Sau »</a>
					</li>
				</c:if>
			</ul>
		</nav>
	</div>
</body>
</html>