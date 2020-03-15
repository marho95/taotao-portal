package com.taotao.portal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.common.utils.ExceptionUtil;
import com.taotao.pojo.TbUser;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.pojo.Order;
import com.taotao.portal.service.CartService;
import com.taotao.portal.service.OrderService;

@Controller
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private OrderService orderService;
	
	//订单确认界面
	@RequestMapping("/order-cart")
	public String showOrderCart(HttpServletRequest request, HttpServletResponse response, Model model) {
		//取购物车商品列表
		List<CartItem> list = cartService.getCartItemList(request, response);
		//传递给页面
		model.addAttribute("cartList", list);
		
		return "order-cart";
	}
	
	//提交，创建订单
	@RequestMapping("/create")
	public String createOrder(Order order, Model model, HttpServletRequest request) {//Order继承自TbOrder，除了TbOrder表中的属性，还有订单商品明细对象列表和订单物流对象。order从页面的表单接收这三种数据
		try {
			//从Request中取用户信息
			TbUser user = (TbUser) request.getAttribute("user");
			//在order订单对象中补全用户信息
			order.setUserId(user.getId());
			order.setBuyerNick(user.getUsername());
			//调用服务，创建订单
			String orderId = orderService.createOrder(order);
			model.addAttribute("orderId", orderId);
			model.addAttribute("payment", order.getPayment());
			model.addAttribute("date", new DateTime().plusDays(3).toString("yyyy-MM-dd"));
			return "success";
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "创建订单出错。请稍后重试！");
			return "error/exception";
		}
	}
	
}
