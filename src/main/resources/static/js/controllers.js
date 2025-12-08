function addToCart(bookid)
{

	if (confirm("장바구니에 도서를 추가합니까?")){

		document.addForm.action="/BookMarket/cart/book/"+bookid;
		document.addForm.submit();
	}


}
function removeFromCart(bookid, cartId) {

	document.removeForm.action = "/BookMarket/cart/book/"+bookid;
	document.removeForm.submit();
	setTimeout('location.reload()',10);
}

function clearCart(cartId) {
	if (confirm("모든 도서를 장바구니에서 삭제합니까?")==true){

		document.clearForm.submit();
		setTimeout('location.reload()',10);

	}
}

function deleteConfirm(id){
	if (confirm("정말로 삭제하시겠습니까?") == true) location.href ="/BookMarket/books/delete?id="+id;
	else  return;
}