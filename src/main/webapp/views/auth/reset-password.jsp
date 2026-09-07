<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Đặt lại mật khẩu</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
</head>
<body class="bg-light">
	<div class="container mt-5">
		<div class="row justify-content-center">
			<div class="col-md-5">
				<div class="card shadow">
					<div class="card-header bg-dark text-white text-center">
						<h4>ĐẶT LẠI MẬT KHẨU</h4>
					</div>
					<div class="card-body">
						<c:if test="${not empty error}">
							<div class="alert alert-danger">${error}</div>
						</c:if>
						<form action="<c:url value='/reset-password'/>" method="post">
							<div class="mb-3">
								<label class="form-label">Mã xác thực OTP:</label> <input
									type="text" name="otp" class="form-control" required>
							</div>
							<div class="mb-3">
								<label class="form-label">Mật khẩu mới:</label> <input
									type="password" name="newPassword" class="form-control"
									required>
							</div>
							<button type="submit" class="btn btn-dark w-100">Cập
								nhật mật khẩu</button>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>