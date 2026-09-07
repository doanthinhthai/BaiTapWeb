<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<head>
<title>Hồ sơ cá nhân - ${user.fullname}</title>
</head>
<body>
	<div class="container my-4">
		<div class="row justify-content-center">
			<div class="col-md-8">
				<div class="card shadow border-0">
					<div class="card-header bg-primary text-white py-3">
						<h4 class="mb-0">
							<i class="fa-solid fa-user-pen me-2"></i>HỒ SƠ CÁ NHÂN
						</h4>
					</div>
					<div class="card-body p-4">

						<!-- Thông báo Thành công -->
						<c:if test="${not empty message}">
							<div class="alert alert-success alert-dismissible fade show"
								role="alert">
								<i class="fa-solid fa-circle-check me-2"></i>${message}
								<button type="button" class="btn-close" data-bs-dismiss="alert"></button>
							</div>
						</c:if>

						<form action="<c:url value='/profile/update'/>" method="post"
							enctype="multipart/form-data" class="needs-validation">
							<div class="row">
								<!-- Cột hiển thị Avatar -->
								<div class="col-md-4 text-center border-end">
									<label class="form-label fw-bold d-block">Ảnh đại diện</label>
									<div class="mb-3">
										<c:choose>
											<c:when test="${not empty user.images}">
												<img id="previewImg"
													src="<c:url value='/image?fname=${user.images}'/>"
													class="rounded-circle img-thumbnail shadow-sm"
													style="width: 140px; height: 140px; object-fit: cover;">
											</c:when>
											<c:otherwise>
												<img id="previewImg"
													src="https://via.placeholder.com/140?text=No+Avatar"
													class="rounded-circle img-thumbnail shadow-sm"
													style="width: 140px; height: 140px; object-fit: cover;">
											</c:otherwise>
										</c:choose>
									</div>
									<div class="mb-3">
										<input type="file" name="imageFile" id="imageFile"
											class="form-control form-control-sm ${not empty errors.images ? 'is-invalid' : ''}"
											accept="image/*" onchange="previewFile(this)">
										<c:if test="${not empty errors.images}">
											<div class="invalid-feedback text-start">${errors.images}</div>
										</c:if>
										<small class="text-muted d-block mt-1">Định dạng: JPG,
											PNG, WEBP (Tối đa 5MB)</small>
									</div>
								</div>

								<!-- Cột thông tin chi tiết -->
								<div class="col-md-8 ps-md-4">
									<!-- Username (Chỉ đọc) -->
									<div class="mb-3">
										<label class="form-label text-muted">Tên đăng nhập:</label> <input
											type="text" class="form-control bg-light"
											value="${user.username}" readonly>
									</div>

									<!-- Email (Chỉ đọc) -->
									<div class="mb-3">
										<label class="form-label text-muted">Email:</label> <input
											type="text" class="form-control bg-light"
											value="${user.email}" readonly>
									</div>

									<!-- Họ và tên -->
									<div class="mb-3">
										<label class="form-label fw-bold">Họ và tên: <span
											class="text-danger">*</span></label> <input type="text"
											name="fullname"
											class="form-control ${not empty errors.fullname ? 'is-invalid' : ''}"
											value="${user.fullname}" required>
										<c:if test="${not empty errors.fullname}">
											<div class="invalid-feedback">${errors.fullname}</div>
										</c:if>
									</div>

									<!-- Số điện thoại -->
									<div class="mb-3">
										<label class="form-label fw-bold">Số điện thoại:</label> <input
											type="text" name="phone"
											class="form-control ${not empty errors.phone ? 'is-invalid' : ''}"
											value="${user.phone}" placeholder="Ví dụ: 0987654321">
										<c:if test="${not empty errors.phone}">
											<div class="invalid-feedback">${errors.phone}</div>
										</c:if>
									</div>

									<hr class="my-4">
									<div class="d-flex justify-content-end">
										<a href="<c:url value='/home'/>"
											class="btn btn-secondary me-2">Hủy bỏ</a>
										<button type="submit" class="btn btn-primary px-4">
											<i class="fa-solid fa-floppy-disk me-2"></i>Lưu thay đổi
										</button>
									</div>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script>
    // Preview ảnh trước khi upload
    function previewFile(input) {
        var file = input.files[0];
        if (file) {
            var reader = new FileReader();
            reader.onload = function(e) {
                document.getElementById('previewImg').src = e.target.result;
            }
            reader.readAsDataURL(file);
        }
    }
</script>
</body>