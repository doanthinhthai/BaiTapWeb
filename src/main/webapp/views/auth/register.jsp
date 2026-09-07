<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Đăng ký</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
</head>
<body class="bg-light">
	<div class="container mt-5">
		<div class="row justify-content-center">
			<div class="col-md-6">
				<div class="card shadow">
					<div class="card-header bg-success text-white text-center">
						<h4>ĐĂNG KÝ TÀI KHOẢN</h4>
					</div>
					<div class="card-body">
						<c:if test="${not empty error}">
							<div class="alert alert-danger">${error}</div>
						</c:if>
						<form action="<c:url value='/register'/>" method="post">
							<div class="mb-3">
								<label class="form-label">Tên đăng nhập:</label> <input
									type="text" name="username" class="form-control" required>
							</div>
							<div class="mb-3">
								<label class="form-label">Email (Nhận mã OTP):</label> <input
									type="email" name="email" class="form-control" required>
							</div>
							<div class="mb-3">
								<label class="form-label">Mật khẩu:</label> <input
									type="password" name="password" class="form-control" required>
							</div>
							<div class="mb-3">
								<label class="form-label">Họ và tên:</label> <input type="text"
									name="fullname" class="form-control" required>
							</div>
							<div class="mb-3">
								<label class="form-label">Số điện thoại:</label> <input
									type="text" name="phone" class="form-control">
							</div>
							<button type="submit" class="btn btn-success w-100">Đăng
								ký ngay</button>
						</form>
						<div class="text-center mt-3">
							<a href="<c:url value='/login'/>">Đã có tài khoản? Đăng nhập</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>