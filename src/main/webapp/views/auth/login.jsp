<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Đăng nhập</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
</head>
<body class="bg-light">
	<div class="container mt-5">
		<div class="row justify-content-center">
			<div class="col-md-5">
				<div class="card shadow">
					<div class="card-header bg-primary text-white text-center">
						<h4>ĐĂNG NHẬP</h4>
					</div>
					<div class="card-body">
						<c:if test="${not empty error}">
							<div class="alert alert-danger">${error}</div>
						</c:if>
						<c:if test="${not empty message}">
							<div class="alert alert-success">${message}</div>
						</c:if>

						<form action="<c:url value='/login'/>" method="post">
							<div class="mb-3">
								<label class="form-label">Tên đăng nhập hoặc Email:</label> <input
									type="text" name="username" class="form-control"
									value="${username}" required>
							</div>
							<div class="mb-3">
								<label class="form-label">Mật khẩu:</label> <input
									type="password" name="password" class="form-control" required>
							</div>
							<div class="mb-3 form-check">
								<input type="checkbox" name="remember" class="form-check-input"
									id="remember" ${remember ? 'checked' : ''}> <label
									class="form-check-label" for="remember">Ghi nhớ đăng
									nhập (Cookie)</label>
							</div>
							<button type="submit" class="btn btn-primary w-100 mb-2">Đăng
								nhập</button>
						</form>
						<div class="d-flex justify-content-between mt-3">
							<a href="<c:url value='/register'/>">Đăng ký tài khoản</a> <a
								href="<c:url value='/forgot-password'/>">Quên mật khẩu?</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>