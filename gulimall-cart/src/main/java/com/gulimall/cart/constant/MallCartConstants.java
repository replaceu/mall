package com.gulimall.cart.constant;

public interface MallCartConstants {
	String mallCartInfo = "/mallCartInfo";

	interface MappingConstants {
		String	getUserShoppingCartList		= "/getUserShoppingCartList";
		String	checkUserShoppingCart		= "/checkUserShoppingCart";
		String	changeUserShoppingCartNum	= "/changeUserShoppingCartNum";
		String	delUserShoppingCartItem		= "/delUserShoppingCartItem";
		String	addItemUserShoppingCart		= "/addItemUserShoppingCart";
		String	getCurrentUserCartItems		= "/getCurrentUserCartItems";
	}

	interface AttributeConstants {
		String	cart	= "cart";
		String	skuId	= "skuId";
	}

	interface RedirectConstants {
		String	cartListRedirect	= "redirect:http://cart.gulimall.com/list.html";
		String	addCartRedirect		= "redirect:http://cart.gulimall.com/addToCartSuccess.html";
	}
}
