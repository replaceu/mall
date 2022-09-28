package com.gulimall.cart.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gulimall.cart.constant.MallCartConstants;
import com.gulimall.cart.service.CartService;
import com.gulimall.cart.vo.CartItemVo;
import com.gulimall.cart.vo.CartVo;

import io.swagger.annotations.Api;

/**
*@authoraqiang92020-09-08
*/
@RestController
@RequestMapping(value = MallCartConstants.mallCartInfo, produces = "application/json;charset=UTF-8")
@Api(value = "mallCartInfo", description = "用户购物车")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class CartController {
	@Autowired
	private CartService cartService;

	/**
	*浏览器有一个cookie:user-key，标识用户身份
	*登陆：session有
	*没登陆安装cookie里面带来user-key来做
	*第一次如果没有零食用户来创建临时用户
	*拦截器
	*/
	@GetMapping(MallCartConstants.MappingConstants.getUserShoppingCartList)
	public String getUserShoppingCart(Model model) {
		CartVo cartVo = cartService.getMallUserCartList();
		model.addAttribute(MallCartConstants.AttributeConstants.cart, cartVo);
		return MallCartConstants.MappingConstants.getUserShoppingCartList;
	}

	/**
	*改变勾选状态
	*@paramskuId
	*@paramcheck
	*@return
	*/
	@GetMapping(MallCartConstants.MappingConstants.checkUserShoppingCart)
	public String checkUserShoppingCart(@RequestParam("skuId") Long skuId, @RequestParam("check") int check) {
		cartService.checkUserShoppingCart(skuId, check);
		return MallCartConstants.RedirectConstants.cartListRedirect;
	}

	/**
	*改变商品数量
	*@paramskuId
	*@paramnum
	*@return
	*/
	@GetMapping(MallCartConstants.MappingConstants.changeUserShoppingCartNum)
	public String changeUserShoppingCartNum(@RequestParam("skuId") Long skuId, @RequestParam("num") int num) {
		cartService.changeUserShoppingCartNum(skuId, num);
		return MallCartConstants.RedirectConstants.cartListRedirect;
	}

	@GetMapping(MallCartConstants.MappingConstants.delUserShoppingCartItem)
	public String deleteItem(@RequestParam("skuId") Long skuId) {
		cartService.deleteItem(skuId);
		return MallCartConstants.RedirectConstants.cartListRedirect;
	}

	/**
	*model.addFlashAttribute()放在session中只能取一次
	*model.addAttribute放在url后
	*
	*@paramskuId
	*@paramnum
	*@return
	*/

	@GetMapping(MallCartConstants.MappingConstants.addItemUserShoppingCart)
	public String addToCart(@RequestParam("skuId") Long skuId, @RequestParam("num") Integer num, RedirectAttributes model) {
		cartService.addToCart(skuId, num);
		model.addAttribute(MallCartConstants.AttributeConstants.skuId, skuId);
		return MallCartConstants.RedirectConstants.addCartRedirect;
	}

	@GetMapping("/addToCartSuccess.html")
	public String addToCartSuccessPage(@RequestParam("skuId") Long skuId, Model model) {
		CartItemVo item = cartService.getCartItemInfo(skuId);
		model.addAttribute("item", item);
		return "success";
	}

	@GetMapping("/currentUserItems")
	public List<CartItemVo> getCurrentUserCartItems() {
		return cartService.getCurrentUserCartItems();
	}

}
