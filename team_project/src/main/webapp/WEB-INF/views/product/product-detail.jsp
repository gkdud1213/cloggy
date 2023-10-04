<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<style type="text/css">
.button-container {
	display: flex;
	align-items: center;
	justify-content: center;
}

.button-container input[type="button"] {
	margin: 0;
}

.review-container {
	display: flex;
	justify-content: center;
	align-items: center;
	height: 100vh;
	/* Optionally, set the height to fill the entire viewport */
}
</style>
<script type="text/javascript">
	function count(type) {
		// 결과를 표시할 element
		const resultElement = document.getElementById('result');

		// 현재 화면에 표시된 값
		let number = parseInt(resultElement.innerText);

		// 더하기/빼기
		if (type === 'plus') {
			number = number + 1;
		} else if (type === 'minus') {
			// Check if the number is greater than or equal to the minimum value
			if (number > 1) {
				number = number - 1;
			} else {
				// Set a minimum value (e.g., 0)
				number = 1;
			}
		}

		// 결과 출력
		resultElement.innerText = number;
	}
</script>
<script type="text/javascript">
function addcart() {
    const productId = document.querySelector('input[name="product_id"]').value;
    const quantity = document.getElementById('result').innerText;

    // Create a JSON object with the data to send
    const data = {
        product_id: productId,
        quantity: quantity
    };

    // Send the data to the server using AJAX
   fetch('/addcart', {
    method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
    })
    .then(response => {
        // Handle the response as needed (e.g., show a success message)
        alert('상품이 장바구니에 추가되었습니다.');
    })
    .catch(error => {
    	alert('Error', error);
    });
}

</script>

<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<form action="addcart" method="post">
		<c:forEach items="${list}" var="pdetail">
			<table align="center" width="1000px" style="margin-top: 130px;">
				<tr>
					<td><input type="hidden" name="product_id"
						value="${pdetail.product_id}"></td>
				</tr>
				<tr>
					<td><img alt="상품썸네일" src="/dog/image/${pdetail.p_thumbnail }"
						width="350px;" height="400px;"></td>
				</tr>
				<tr>
					<td align="center" style="font-size: 40px; padding-left: 10px;">${pdetail.p_name }</td>
				</tr>
				<tr>
					<td align="right" style="font-size: 30px; padding-left: 10px;">
						<fmt:formatNumber pattern="#,##0원">${pdetail.p_price}</fmt:formatNumber>
					</td>
				</tr>
				<tr>
					<td>
						<div class="button-container">
							<input type="button" onclick='count("minus")' value='-' />
							<div id="result" name="quantity">1</div>
							<input type="button" onclick='count("plus")' value='+' />
						</div>
					</td>
				</tr>

				<tr>
					<td>Total Price: <span id="totalPrice">0</span></td>
				</tr>
				<tr>
					<td colspan="2"><input type="button" value="장바구니 추가"
						onclick="addcart()"></td>
				</tr>
			</table>

			<!--             리뷰 -->
			<div class="review-container">
				<div class="review">
					<table width="500px">
						<tr>
							<td colspan="2"><a
								href="review-out?product_id=${pdetail.product_id}" align="right">리뷰
									전체보기</a></td>
						</tr>

						<tr>
							<td rowspan="2" width="120px"><img
								src="/dog/review-img/${rdto.r_photo}" width="100px"
								height="100px"></td>
							<td height="30px">${rdto.userId}</td>
						</tr>
						<tr>
							<td>${rdto.r_content}</td>
						</tr>
					</table>
				</div>
			</div>

			<!-- 		다시 내용	 -->
			<table>
				<tr>
					<td colspan="2" style="padding-top: 150px;">${pdetail.p_info }<br>
						<img alt="상세페이지" src="/dog/image/${pdetail.p_image}"
						width="700px;">
					</td>
				</tr>
				<tr>
					<td colspan="2" style="text-align: right;"><input
						type="button" value="수정"
						onclick="location.href='product-modifyForm?product_id=${pdetail.product_id}'">
					</td>
			</table>
		</c:forEach>
	</form>



</body>
</html>
