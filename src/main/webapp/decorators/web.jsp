<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title><sitemesh:write property="title">Cửa Hàng Trực Tuyến</sitemesh:write></title>
<!-- Bootstrap 5 CSS -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<!-- FontAwesome Icons -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
<style>
body {
	min-height: 100vh;
	display: flex;
	flex-direction: column;
}

main {
	flex: 1;
}

.user-nav-avatar {
	width: 32px;
	height: 32px;
	object-fit: cover;
	border-radius: 50%;
}
</style>
<sitemesh:write property="head" />
</head>
<body>
	<!-- HEADER & NAVBAR BOOTSTRAP -->
	<header>
		<nav class="navbar navbar-expand-lg navbar-dark bg-dark shadow-sm">
			<div class="container">
				<a class="navbar-brand fw-bold text-warning"
					href="<c:url value='/home'/>"> <i
					class="fa-solid fa-store me-2"></i>SHOP ONLINE
				</a>
				<button class="navbar-toggler" type="button"
					data-bs-toggle="collapse" data-bs-target="#mainNav">
					<span class="navbar-toggler-icon"></span>
				</button>
				<div class="collapse navbar-collapse" id="mainNav">
					<ul class="navbar-nav me-auto">
						<li class="nav-item"><a class="nav-link"
							href="<c:url value='/home'/>"><i
								class="fa-solid fa-house me-1"></i>Trang chủ</a></li>
						<li class="nav-item"><a class="nav-link"
							href="<c:url value='/product'/>"><i
								class="fa-solid fa-box-open me-1"></i>Sản phẩm</a></li>
					</ul>

					<ul class="navbar-nav">
						<c:choose>
							<c:when test="${not empty sessionScope.account}">
								<li class="nav-item dropdown"><a
									class="nav-link dropdown-toggle text-white d-flex align-items-center"
									href="#" role="button" data-bs-toggle="dropdown"> <c:choose>
											<c:when test="${not empty sessionScope.account.images}">
												<img
													src="<c:url value='/image?fname=${sessionScope.account.images}'/>"
													class="user-nav-avatar me-2 border">
											</c:when>
											<c:otherwise>
												<i class="fa-solid fa-user-circle fa-xl me-2 text-warning"></i>
											</c:otherwise>
										</c:choose> <span>${sessionScope.account.fullname}</span>
								</a>
									<ul class="dropdown-menu dropdown-menu-end shadow">
										<li><a class="dropdown-item"
											href="<c:url value='/profile'/>"> <i
												class="fa-solid fa-id-badge me-2 text-primary"></i>Hồ sơ cá
												nhân
										</a></li>
										<c:if test="${sessionScope.account.role == 1}">
											<li><a class="dropdown-item"
												href="<c:url value='/admin/products'/>"> <i
													class="fa-solid fa-gear me-2 text-danger"></i>Trang Quản
													trị
											</a></li>
										</c:if>
										<li><hr class="dropdown-divider"></li>
										<li><a class="dropdown-item text-danger"
											href="<c:url value='/logout'/>"> <i
												class="fa-solid fa-right-from-bracket me-2"></i>Đăng xuất
										</a></li>
									</ul></li>
							</c:when>
							<c:otherwise>
								<li class="nav-item"><a class="nav-link text-white"
									href="<c:url value='/login'/>"> <i
										class="fa-solid fa-arrow-right-to-bracket me-1"></i>Đăng nhập
								</a></li>
								<li class="nav-item"><a class="btn btn-warning btn-sm ms-2"
									href="<c:url value='/register'/>">Đăng ký</a></li>
							</c:otherwise>
						</c:choose>
					</ul>
				</div>
			</div>
		</nav>
	</header>

	<!-- NỘI DUNG TRANG ĐƯỢC SITEMESH INJECT VÀO ĐÂY -->
	<main class="py-4">
		<sitemesh:write property="body" />
	</main>

	<!-- FOOTER BOOTSTRAP -->
	<footer class="bg-dark text-white py-4 mt-auto">
		<div class="container text-center">
			<p class="mb-1">© 2026 - Dự Án Quản Lý Bán Hàng JPA Servlet 6.0</p>
			<small class="text-muted">Phát triển với Jakarta Persistence
				API, SiteMesh 3 & Bootstrap 5</small>
		</div>
	</footer>

	<!-- Bootstrap 5 JS Bundle -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>