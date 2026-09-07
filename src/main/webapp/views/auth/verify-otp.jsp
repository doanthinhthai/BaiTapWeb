<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Xác nhận OTP</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
</head>
<body class="bg-light">
	<div class="container mt-5">
		<div class="row justify-content-center">
			<div class="col-md-5">
				<div class="card shadow">
					<div class="card-header bg-warning text-dark text-center">
						<h4>KÍCH HOẠT TÀI KHOẢN</h4>
					</div>
					<div class="card-body">
						<p class="text-muted text-center">Mã OTP gồm 6 chữ số đã được
							gửi tới email của bạn.</p>
						<c:if test="${not empty error}">
							<div class="alert alert-danger">${error}</div>
						</c:if>
						<form action="<c:url value='/verify-otp'/>" method="post">
							<div class="mb-3">
								<label class="form-label">Nhập mã OTP (6 số):</label> <input
									type="text" name="otp" class="form-control text-center fs-4"
									maxlength="6" required>
							</div>
							<button type="submit" class="btn btn-warning w-100">Xác
								nhận kích hoạt</button>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>