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

						<!-- Tên sản phẩm -->
						<div class="mb-3">
							<label class="form-label fw-bold">Tên sản phẩm: <span
								class="text-danger">*</span></label> <input type="text"
								name="productName" value="${param.productName}"
								class="form-control ${not empty errors.productName ? 'is-invalid' : ''}">
							<c:if test="${not empty errors.productName}">
								<div class="invalid-feedback">${errors.productName}</div>
							</c:if>
						</div>

						<!-- Danh mục -->
						<div class="mb-3">
							<label class="form-label fw-bold">Danh mục: <span
								class="text-danger">*</span></label> <select name="categoryId"
								class="form-select ${not empty errors.categoryId ? 'is-invalid' : ''}">
								<option value="0">-- Chọn danh mục --</option>
								<c:forEach items="${categories}" var="cat">
									<option value="${cat.categoryId}"
										${param.categoryId == cat.categoryId ? 'selected' : ''}>
										${cat.categoryname}</option>
								</c:forEach>
							</select>
							<c:if test="${not empty errors.categoryId}">
								<div class="invalid-feedback">${errors.categoryId}</div>
							</c:if>
						</div>

						<!-- Đơn giá & Số lượng -->
						<div class="row">
							<div class="col-md-6 mb-3">
								<label class="form-label fw-bold">Đơn giá (VNĐ): <span
									class="text-danger">*</span></label> <input type="number" name="price"
									value="${param.price}"
									class="form-control ${not empty errors.price ? 'is-invalid' : ''}">
								<c:if test="${not empty errors.price}">
									<div class="invalid-feedback">${errors.price}</div>
								</c:if>
							</div>
							<div class="col-md-6 mb-3">
								<label class="form-label fw-bold">Số lượng trong kho: <span
									class="text-danger">*</span></label> <input type="number"
									name="quantity" value="${param.quantity}"
									class="form-control ${not empty errors.quantity ? 'is-invalid' : ''}">
								<c:if test="${not empty errors.quantity}">
									<div class="invalid-feedback">${errors.quantity}</div>
								</c:if>
							</div>
						</div>

						<!-- Hình ảnh -->
						<div class="mb-3">
							<label class="form-label">Tải lên hình ảnh:</label> <input
								type="file" name="images" class="form-control" accept="image/*">
						</div>

						<!-- Mô tả -->
						<div class="mb-3">
							<label class="form-label">Mô tả sản phẩm:</label>
							<textarea name="description" class="form-control" rows="3">${param.description}</textarea>
						</div>

						<!-- Trạng thái -->
						<div class="mb-3">
							<label class="form-label">Trạng thái:</label><br>
							<div class="form-check form-check-inline">
								<input class="form-check-input" type="radio" name="status"
									value="1" checked> <label class="form-check-label">Đang
									bán</label>
							</div>
							<div class="form-check form-check-inline">
								<input class="form-check-input" type="radio" name="status"
									value="0"> <label class="form-check-label">Tạm
									ngưng</label>
							</div>
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