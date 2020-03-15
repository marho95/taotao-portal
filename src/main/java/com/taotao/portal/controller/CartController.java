package com.taotao.portal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.service.CartService;

/**
 * 购物车Controller
 * <p>Title: CartController</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.com</p> 
 * @author	入云龙
 * @date	2015年9月15日下午3:12:59
 * @version 1.0
 */
@Controller
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private CartService cartService;
	
	@RequestMapping("/add/{itemId}")
	public String addCartItem(@PathVariable Long itemId, 
								@RequestParam(defaultValue="1")Integer num, 
								HttpServletRequest request, 
								HttpServletResponse response) {
		TaotaoResult result = cartService.addCartItem(itemId, num, request, response);
		return "redirect:/cart/success.html";//重定向到另一个方法是可以改变url，这样可以防止每次刷新添加成功页面都再执行一次这个方法，再加一件商品
	}
	
	@RequestMapping("/success")
	public String showSuccess() {
		return "cartSuccess";
	}
	
	@RequestMapping("/cart")
	public String showCart(HttpServletRequest request, 
							HttpServletResponse response, 
							Model model) {
		List<CartItem> list = cartService.getCartItemList(request, response);
		model.addAttribute("cartList", list);
		return "cart";
	}
	
	@RequestMapping("/delete/{itemId}")
	public String deleteCartItem(@PathVariable Long itemId, 
								HttpServletRequest request, 
								HttpServletResponse response) {
		cartService.deleteCartItem(itemId, request, response);
		return "redirect:/cart/cart.html";////重定向到另一个方法是为了防止每次刷新删除成功页面都再执行一次删除
	}
	
}
