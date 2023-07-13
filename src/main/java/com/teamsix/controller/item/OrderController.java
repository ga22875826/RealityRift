package com.teamsix.controller.item;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.teamsix.model.bean.item.CheckoutRequest;
import com.teamsix.model.bean.item.OrderDetail;
import com.teamsix.model.bean.item.OrderRequest;
import com.teamsix.model.bean.item.Orders;
import com.teamsix.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@PostMapping
	public ResponseEntity<String> createOrder(@RequestBody OrderRequest orderRequest) {
		System.out.println(orderRequest.getCartItems().size());
		orderService.createOrderAndDetails(orderRequest);
		return new ResponseEntity<>("Order created successfully", HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<Page<Orders>> getOrders(
	        @RequestParam(required = false) Integer memno,
	        @RequestParam(required = false) String period,
	        @RequestParam(required = false) String status,
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "10") int size) {

	    Page<Orders> result = orderService.getOrders(Optional.ofNullable(memno), Optional.ofNullable(period), Optional.ofNullable(status), page, size);

	    return new ResponseEntity<>(result, HttpStatus.OK);
	}



	@GetMapping("/orderdetails/{orderId}")
	public ResponseEntity<List<OrderDetail>> getOrderDetails(@PathVariable Long orderId) {
		List<OrderDetail> details = orderService.getOrderDetailsByOrderId(orderId);
		return new ResponseEntity<>(details, HttpStatus.OK);
	}
	
	@PostMapping("/ecpayCheckout")
	public String ecpayCheckout(@RequestBody CheckoutRequest checkoutRequest) {
	    Long orderId = checkoutRequest.getOrderId();
	    String aioCheckOutALLForm = orderService.ecpayCheckout(orderId);
	    return aioCheckOutALLForm;
	}
	
	@PostMapping("/payment/callback.test")
	public ResponseEntity<String> handlePaymentCallback(@RequestParam Map<String, String> formData) {
	    // 将收到的所有表单数据打印到控制台
	    for (Map.Entry<String, String> entry : formData.entrySet()) {
	        System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
	    }

	    // 转换和验证数据
	    String string = formData.get("MerchantTradeNo");
	    
	    
	    boolean isSuccess = orderService.payCallBackTest(string);

	    if (isSuccess) {
	        System.out.println("Successfully handled payment callback");
	        return ResponseEntity.ok("1|OK");
	    } else {
	        System.out.println("Failed to handle payment callback");
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("0|Error");
	    }
	}
	
	@PostMapping("/payment/callback")
	public ResponseEntity<String> handlePaymentCallback(@RequestBody CheckoutRequest checkoutRequest) {
	    boolean isSuccess = orderService.payCallBack(checkoutRequest.getOrderId());

	    if (isSuccess) {
	        System.out.println("Successfully handled payment callback");
	        return ResponseEntity.ok("1|OK");
	    } else {
	        System.out.println("Failed to handle payment callback");
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("0|Error");
	    }
	}
	
	
	@PostMapping("/linepay")
	public ResponseEntity<String> createPaymentRequest(@RequestBody CheckoutRequest checkoutRequest) {
	    ResponseEntity<String> responseEntity = orderService.makePayment(checkoutRequest.getOrderId());
	    String responseBody = responseEntity.getBody();
	    return ResponseEntity.ok(responseBody);
	}

}

