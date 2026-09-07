<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Quản lý sản phẩm</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
</head>
<body class="container mt-4">
	<div class="d-flex justify-content-between align-items-center mb-3">
		<h2>QUẢN LÝ SẢN PHẨM</h2>
		<div>
			<a href="<c:url value='/admin/categories'/>"
				class="btn btn-outline-secondary">Quản lý Category</a> <a
				href="<c:url value='/admin/product/add'/>" class="btn btn-primary">+
				Thêm Sản phẩm</a> <a href="<c:url value='/home'/>"
				class="btn btn-success">Xem Trang chủ</a> <a
				href="<c:url value='/logout'/>" class="btn btn-danger">Đăng xuất</a>
		</div>
	</div>

	<table class="table table-bordered table-hover align-middle">
		<thead class="table-dark">
			<tr>
				<th>ID</th>
				<th>Hình ảnh</th>
				<th>Tên sản phẩm</th>
				<th>Giá</th>
				<th>Số lượng</th>
				<th>Danh mục</th>
				<th>Trạng thái</th>
				<th>Hành động</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${listproduct}" var="p">
				<tr>
					<td>${p.productId}</td>
					<td><c:choose>
							<c:when test="${p.images.startsWith('http')}">
								<img src="${p.images}" width="70" height="70"
									class="object-fit-cover rounded">
							</c:when>
							<c:otherwise>
								<img src="<c:url value='/image?fname=${p.images}'/>" width="70"
									height="70" class="object-fit-cover rounded">
							</c:otherwise>
						</c:choose></td>
					<td><b>${p.productName}</b></td>
					<td class="text-danger fw-bold">${p.price}VNĐ</td>
					<td>${p.quantity}</td>
					<td><span class="badge bg-secondary">${p.category.categoryname}</span></td>
					<td><c:if test="${p.status == 1}">
							<span class="badge bg-success">Đang bán</span>
						</c:if> <c:if test="${p.status != 1}">
							<span class="badge bg-danger">Khóa</span>
						</c:if></td>
					<td><a
						href="<c:url value='/admin/product/edit?id=${p.productId}'/>"
						class="btn btn-sm btn-warning">Sửa</a> <a
						href="<c:url value='/admin/product/delete?id=${p.productId}'/>"
						class="btn btn-sm btn-danger"
						onclick="return confirm('Bạn có chắc muốn xóa?')">Xóa</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>