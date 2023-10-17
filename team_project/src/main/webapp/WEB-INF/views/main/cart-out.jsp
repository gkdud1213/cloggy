<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<style type="text/css">
th {
	width: 100px;
	text-align: center;
}

.row-description {
	width: 400px;
}

#chk-all {
	width: 20px;
	text-align: center;
}
</style>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<!-- 	<!-- 진행상태바 -->
<!-- 	<ul class="order-status"> -->
<!-- 		<li>장바구니</li> -->
<!-- 		<li>주문/결제</li> -->
<!-- 		<li>완료</li> -->
<!-- 	</ul> -->
<!-- 	<form action="order" method="post"> -->
		<table align="center">
			<caption>CART 장바구니에 담긴 상품은 30일 동안 보관됩니다.</caption>

			<tr>
				<td colspan="7"><button type="button" id="deletethis" >선택
						삭제</button>
			</tr>
			<tr>
				<th><input type="checkbox" name="chk-all" id="chk-all"></th>
				<th>이미지</th>
				<th class="row-description">상품명</th>
				<th>가격</th>
				<th>수량</th>
				<th>상품구매금액</th>
			</tr>
			<c:set var="totalprice" value="0" />
			<c:forEach items="${list}" var="cart" varStatus="loop">
				<tr>
					<td><input type="checkbox" name="check-one"></td>
					<td><img src="/dog/image/${cart.p_thumbnail}" width="100px"></td>
					<td>${cart.p_name}<input type="hidden" name="product_id"
						value="${cart.product_id}"></td>
					<td><fmt:formatNumber pattern="#,##0원">${cart.p_price}</fmt:formatNumber>
					<span id="price-${loop.index}"
						style="display: none;">${cart.p_price}</span>
					</td>
					<td>
						<div class="button-container">
							<button type="button" class="decrease" data-id="${loop.index}">-</button>
							<span id="quantity-${loop.index}">${cart.cart_quantity}</span>개
							<button type="button" class="increase" data-id="${loop.index}">+</button>
						</div>
					</td>
					<td><span id="subtotal-${loop.index}"> <fmt:formatNumber
								pattern="#,##0원">
            ${cart.cart_quantity * cart.p_price}
        </fmt:formatNumber>
					</span></td>

				</tr>
			</c:forEach>

		</table>
		<table align="center">
			<tr>
				<th>총 상품금액</th>
				<th>총 배송비</th>
				<th>총 결제금액</th>
			</tr>
			<tr>
				<td class="totalprice"><fmt:formatNumber pattern="#,##0원">${totalprice}</fmt:formatNumber></td>
				<c:set var="shipping" value="${totalprice < 30000 ? 2500 : 0}" />
				<td class="shipping"><fmt:formatNumber pattern="#,##0원">${shipping}</fmt:formatNumber></td>
				<c:set var="finalprice" value="${totalprice + shipping}" />
				<td class="finalprice"><fmt:formatNumber pattern="#,##0원">${finalprice}</fmt:formatNumber>
				</td>
			</tr>
		</table>
		<div>
			<button type="button" id="order">결제하기</button>
		</div>
<!-- 	</form> -->
	<!-- 	<script src="/dog/js/cart-out.js"></script> -->
	<script type="text/javascript">
	$(document).ready(function() {
				
				// 초기 로딩 시 전체박스, 개별체크박스 선택
				$("input[name='chk-all']").prop('checked', true);
				$("input[name='check-one']").prop('checked', $("input[name='chk-all']").prop('checked'));
				
				
				///이벤트들///
				
				// 1. 전체 선택 버튼 클릭 시 모든 체크박스 체크
				 $("#chk-all").click(function() {
			           $("input[name='check-one']").prop('checked', $(this).prop('checked'));
			           updateTotalPrice();
			     });
				
				// 2. 체크박스가 변경될 때 totalprice 업데이트
				 $("input[name='check-one']").change(function() {
				      updateTotalPrice();
				 });
				 
				
				// 3. 선택삭제
// 				 $("#deletethis").click(function() {
// 					    var selectedProducts = [];
// 					    // 체크된 상품의 product_id를 수집
// 					    $("input[name='check-one']:checked").each(function() {
// 					        var product_id = $(this).closest("tr").find("input[name='product_id']").val();
// 					        selectedProducts.push(product_id);
// 					    });
						
// 						 console.log("Selected product_ids: " + selectedProducts);

// 					    // AJAX 요청으로 선택된 상품을 컨트롤러로 전송
					    
// 						$.ajax({
// 						    type: "POST",
// 						    url: "/dog/deletefromcart",
// 						    data: { productIds: selectedProducts },
// 						    success: function(response) {
// 						        if (response === "success") {
// 						            alert("상품이 삭제되었습니다.");
// 						        } else {
// 						            alert("알 수 없는 응답: " + response);
// 						        }
// 						    },
// 						});
// 				});
				
// '선택 삭제' 버튼 클릭 이벤트를 처리합니다.
$("#deletethis").click(function() {
    const checkedBoxes = $('.chk:checked');
    let productIds = [];

    // 선택된 제품의 product_id를 수집해서 배열에 저장
    $("input[name='check-one']:checked").each(function() {
        const productId = $(this).closest("tr").find("input[name='product_id']").val();
        productIds.push(productId);
    });

    if (productIds.length === 0) {
        alert('선택한 상품이 없습니다.');
        return;
    } else {
        alert('선택한 상품이 삭제되었습니다.');
    }

    $.ajax({
        type: "POST",
        url: "/dog/deletefromcart",
        data: { productIds: productIds.join(",") },
        success: function(response) {
            // 서버에서의 응답을 처리할 수 있음
            // 예를 들어, 페이지 리로드 또는 필요한 작업 수행
            location.reload(); // 페이지 리로드 예시
        },
        error: function(xhr, status, error) {
            alert("오류 발생: " + error);
        }
    });
});

//'결제' 버튼 클릭 이벤트를 처리합니다.
$("#order").click(function() {
    const checkedBoxes = $('.chk:checked');
    let productIds = [];

    // 선택된 제품의 product_id를 수집해서 배열에 저장
    $("input[name='check-one']:checked").each(function() {
        const productId = $(this).closest("tr").find("input[name='product_id']").val();
        productIds.push(productId);
    });

    if (productIds.length === 0) {
        alert('선택한 상품이 없습니다.');
        return;
    } 

    $.ajax({
        type: "POST",
        url: "/dog/order",
        data: { productIds: productIds.join(",") },
    	 // 성공한 경우의 콜백 함수
        success: function(response) {
            // 서버에서의 응답을 처리한 후, URL로 이동
            // 예를 들어, 성공 메시지를 표시하고, 페이지를 이동
            alert('주문이 성공적으로 처리되었습니다.');
            window.location.href = '/dog/order'; // 이동할 URL을 지정
        },
        error: function(xhr, status, error) {
            alert("오류 발생: " + error);
        }
    });
});
			        
		  
				  
				// 4. 수량 증감
				$(".increase, .decrease").click(
					function() {
							var index = $(this).data("id");
							//증감버튼이 여러 개 있기 때문에 해당 행의 index값을 받아와야 원하는 행에 적용됨
							var quantityElement = $("#quantity-"+ index);
							//수량값을 숫자로 변환
							var quantity = parseInt(quantityElement.text(), 10);

							// 증가/감소 여부를 판단해 수량 변경
							if ($(this).hasClass("increase")) {
								quantity++;
							} else if (quantity > 1) {
								quantity--;
							}

							//화면에 표시되는 값 변경
							quantityElement.text(quantity);
							
							//DB로 변경된 quantity 값 보내서 수정하기
							
					        var product_id = $(this).closest("tr").find("input[name='product_id']").val();
							
							$.ajax({
							    type: "POST",
							    url: "/dog/changeqty",
							    data: {
							          product_id: product_id,
							          quantity: quantity
							        },
							    success: function(response) {
							        if (response === "success") {
							            alert("수량이 변경되었습니다.");
							        } else {
							            alert("알 수 없는 응답: " + response);
							        }
							    },
							});							
							
							
							// Recalculate subtotal, total price, and shipping
							updateSubtotal(index);
							updateTotalPrice();
							updateShipping();
				});

				// (1) 소계 업데이트 메서드
				function updateSubtotal(index) {
					var quantityElement = $("#quantity-" + index);
					var quantityValue = parseInt(quantityElement.text(), 10);
					var priceValue = parseFloat($("#price-" + index).text().replace(/[^0-9.-]+/g, ""));
					var subtotal = quantityValue * priceValue;
					$("#subtotal-" + index).text(formatNumberWithCommas(subtotal));
				}
				
				// (2) 총계 업데이트 메서드
				 function updateTotalPrice() {
			        var totalprice = 0;
			        $("input[name='check-one']:checked").each(function() {
			            var index = $(this).closest("tr").find(".decrease").data("id");
			            var subtotal = parseFloat($("#subtotal-" + index).text().replace(/[^0-9.-]+/g, ""));
			            totalprice += subtotal;
			        });
			
			        $(".totalprice").text(formatNumberWithCommas(totalprice)); // Update the total price element
			        updateShipping();
			    }

				// (3) 배송비 업데이트 메서드
				function updateShipping() {
				    var totalprice = parseFloat($(".totalprice")
				        .text().replace(/[^0-9.-]+/g, ""));
				    var shipping = totalprice == 0 ? 0 : totalprice < 30000 ? 2500 : 0;
				    var formattedShipping = formatNumberWithCommas(shipping);
				    $(".shipping").text(formattedShipping); // Update the shipping element

				    var finalprice = totalprice + shipping;
				    var formattedFinalPrice = formatNumberWithCommas(finalprice);
				    $(".finalprice").text(formattedFinalPrice); // Update the final price element

				    // Update the hidden input field for finalprice
				    $("#finalprice").val(finalprice);
				}

				// (4) 형식 적용 메서드
				function formatNumberWithCommas(number) {
					return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")+ '원';
				}

				// 초기값
				updateTotalPrice();
				updateShipping();
			
	});
		</script>
</body>
</html>
