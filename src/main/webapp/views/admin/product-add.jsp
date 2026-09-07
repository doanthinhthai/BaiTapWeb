<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Thêm sản phẩm mới</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
</head>
<body class="container mt-4">
	<div class="row justify-content-center">
		<div class="col-md-8">
			<div class="card shadow">
				<div class="card-header bg-primary text-white">
					<h4>THÊM SẢN PHẨM MỚI</h4>
				</div>
				<div class="card-body">
					<form action="<c:url value='/admin/product/insert'/>" method="post"
						enctype="multipart/form-data">
						<div class="mb-3">
							<label class="form-label">Tên sản phẩm:</label> <input
								type="text" name="productName" class="form-control" required>
						</div>
						<div class="mb-3">
							<label class="form-label">Danh mục:</label> <select
								name="categoryId" class="form-select" required>
								<c:forEach items="${categories}" var="cat">
									<option value="${cat.categoryId}">${cat.categoryname}</option>
								</c:forEach>
							</select>
						</div>
						<div class="row">
							<div class="col-md-6 mb-3">
								<label class="form-label">Đơn giá (VNĐ):</label> <input
									type="number" step="1000" name="price" class="form-control"
									required>
							</div>
							<div class="col-md-6 mb-3">
								<label class="form-label">Số lượng:</label> <input type="number"
									name="quantity" class="form-control" required>
							</div>
						</div>
						<div class="mb-3">
							<label class="form-label">Tải lên hình ảnh:</label> <input
								type="file" name="images" class="form-control">
						</div>
						<div class="mb-3">
							<label class="form-label">Mô tả sản phẩm:</label>
							<textarea name="description" class="form-control" rows="4"></textarea>
						</div>
						<div class="mb-3">
							<label class="form-label">Trạng thái:</label><br> <input
								type="radio" name="status" value="1" checked> Đang bán <input
								type="radio" name="status" value="0"> Tạm ngưng
						</div>
						<button type="submit" class="btn btn-success">Thêm mới</button>
						<a href="<c:url value='/admin/products'/>"
							class="btn btn-secondary">Quay lại</a>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>