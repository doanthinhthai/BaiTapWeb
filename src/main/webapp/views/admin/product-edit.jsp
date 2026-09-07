<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Sửa sản phẩm</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
</head>
<body class="container mt-4">
	<div class="row justify-content-center">
		<div class="col-md-8">
			<div class="card shadow">
				<div class="card-header bg-warning text-dark">
					<h4>CẬP NHẬT SẢN PHẨM</h4>
				</div>
				<div class="card-body">
					<form action="<c:url value='/admin/product/update'/>" method="post"
						enctype="multipart/form-data">
						<input type="hidden" name="productId" value="${product.productId}">
						<div class="mb-3">
							<label class="form-label">Tên sản phẩm:</label> <input
								type="text" name="productName" value="${product.productName}"
								class="form-control" required>
						</div>
						<div class="mb-3">
							<label class="form-label">Danh mục:</label> <select
								name="categoryId" class="form-select" required>
								<c:forEach items="${categories}" var="cat">
									<option value="${cat.categoryId}"
										${cat.categoryId == product.category.categoryId ? 'selected' : ''}>
										${cat.categoryname}</option>
								</c:forEach>
							</select>
						</div>
						<div class="row">
							<div class="col-md-6 mb-3">
								<label class="form-label">Đơn giá:</label> <input type="number"
									step="1000" name="price" value="${product.price}"
									class="form-control" required>
							</div>
							<div class="col-md-6 mb-3">
								<label class="form-label">Số lượng:</label> <input type="number"
									name="quantity" value="${product.quantity}"
									class="form-control" required>
							</div>
						</div>
						<div class="mb-3">
							<label class="form-label">Hình ảnh hiện tại:</label><br> <img
								src="<c:url value='/image?fname=${product.images}'/>"
								width="100" height="100" class="rounded mb-2"> <input
								type="file" name="images" class="form-control">
						</div>
						<div class="mb-3">
							<label class="form-label">Mô tả sản phẩm:</label>
							<textarea name="description" class="form-control" rows="4">${product.description}</textarea>
						</div>
						<div class="mb-3">
							<label class="form-label">Trạng thái:</label><br> <input
								type="radio" name="status" value="1"
								${product.status == 1 ? 'checked' : ''}> Đang bán <input
								type="radio" name="status" value="0"
								${product.status != 1 ? 'checked' : ''}> Tạm ngưng
						</div>
						<button type="submit" class="btn btn-warning">Cập nhật</button>
						<a href="<c:url value='/admin/products'/>"
							class="btn btn-secondary">Quay lại</a>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>