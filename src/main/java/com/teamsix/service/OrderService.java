package com.teamsix.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.HashMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;

import com.teamsix.config.NgrokUtil;
import com.teamsix.model.bean.Member;
import com.teamsix.model.bean.item.CartItem;
import com.teamsix.model.bean.item.ItemDTO;
import com.teamsix.model.bean.item.OrderDetail;
import com.teamsix.model.bean.item.OrderRequest;
import com.teamsix.model.bean.item.Orders;
import com.teamsix.model.dao.ItemRepository;
import com.teamsix.model.dao.OrderDetailRepository;
import com.teamsix.model.dao.OrderRepository;

import ecpay.payment.integration.AllInOne;
import ecpay.payment.integration.domain.AioCheckOutALL;
import jakarta.persistence.criteria.Join;
import jakarta.transaction.Transactional;

@Service
public class OrderService {

	private static final String LINE_PAY_API_URL = "https://sandbox-api-pay.line.me/v2/payments/request";
	private static final String X_LINE_ChannelId = "2000061352";
	private static final String X_LINE_ChannelSecret = "aea25491631106811c5456e50c82b528";

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderDetailRepository orderDetailRepository;

	@Autowired
	private MemberService memberService;

	@Autowired
	private ItemRepository itemRepository;

	@Transactional
	public void createOrderAndDetails(OrderRequest oq) {
		Orders order = new Orders();
		order.setOrderStatus(oq.getOrderStatus());
		order.setTotalAmount(oq.getTotalAmount());
		order.setMember(memberService.findById(oq.getMemno()));

		order = orderRepository.save(order); // 保存order

		for (CartItem cartItem : oq.getCartItems()) {
			OrderDetail detail = new OrderDetail();
			System.out.println(cartItem.getItem().getItemid());
			detail.setItemid(cartItem.getItem().getItemid());
			detail.setQuantity(cartItem.getQuantity());
			detail.setItemname(cartItem.getItem().getItemname());

			detail.setPrice(cartItem.getItem().getPrice());
			if (cartItem.getImages().size() == 0) {
				detail.setImageName("defaultPic.jpg");
			} else {
				detail.setImageName(cartItem.getImages().get(0).getImagename());
			}
			detail.setOrders(order);

			orderDetailRepository.save(detail); // 保存detail
		}

	}

	@Transactional
	public Page<Orders> getOrders(Optional<Integer> memno, Optional<String> period, Optional<String> status, int page,
	        int size) {
	    Pageable pageable = PageRequest.of(page, size, Sort.by("orderId").descending());

	    Specification<Orders> spec = Specification.where(null);

	    // 新增一個條件，確保訂單日期不超過當前時間
	    Date now = new Date();
	    spec = spec.and((root, query, cb) -> cb.lessThanOrEqualTo(root.get("orderDate"), now));

	    if (memno.isPresent()) {
	        spec = spec.and((root, query, cb) -> {
	            Join<Orders, Member> join = root.join("member");
	            return cb.equal(join.get("memno"), memno.get());
	        });
	    }

	    if (period.isPresent() && !period.get().equals("all")) {
	        Date startDate = calculateStartDate(period.get());
	        spec = spec.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("orderDate"), startDate));
	    }

	    if (status.isPresent() && !status.get().equals("all")) {
	        spec = spec.and((root, query, cb) -> cb.equal(root.get("orderStatus"), status.get()));
	    }

	    return orderRepository.findAll(spec, pageable);
	}


	private Date calculateStartDate(String period) {
		LocalDate now = LocalDate.now();
		LocalDateTime nowTime = LocalDateTime.now();

		switch (period) {
		case "hour":
			return Date.from(nowTime.minusHours(1).atZone(ZoneId.systemDefault()).toInstant());
		case "day":
			return Date.from(now.minusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
		case "week":
			return Date.from(now.minusWeeks(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
		case "month":
			return Date.from(now.minusMonths(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
		case "quarter":
			return Date.from(now.minusMonths(3).atStartOfDay(ZoneId.systemDefault()).toInstant());
		case "year":
			return Date.from(now.minusYears(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
		case "all":
		default:
			return null;
		}
	}

	public List<OrderDetail> getOrderDetailsByOrderId(Long orderId) {
		return orderDetailRepository.findByOrdersOrderId(orderId);
	}

	public Long getOriginalOrderId(String merchantTradeNo) {
		// Assuming the UUID part is always 8 characters long
		return Long.parseLong(merchantTradeNo.substring(0, merchantTradeNo.length() - 8));
	}

	// 綠界金流service
	public String ecpayCheckout(Long orderid) {
		Optional<Orders> optional = orderRepository.findById(orderid);

		NgrokUtil util = new NgrokUtil();
		String ngrokUrl = util.getNgrokUrl();
		if (optional.isEmpty()) {
			return null;
		} else {
			Orders order = optional.get();

			String uuId = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8);
			AllInOne all = new AllInOne("");
			AioCheckOutALL obj = new AioCheckOutALL();
			obj.setMerchantTradeNo(order.getOrderId() + uuId);

			// Convert Date to LocalDateTime
			LocalDateTime ldt = order.getOrderDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

			// format the LocalDateTime to the required format
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			String formattedDateTime = ldt.format(formatter);

			obj.setMerchantTradeDate(formattedDateTime);

			BigDecimal totalAmount = order.getTotalAmount();
			String totalAmountStr = totalAmount.setScale(0, RoundingMode.UNNECESSARY).toString();

			obj.setTotalAmount(totalAmountStr);
			obj.setTradeDesc("test Description");

			obj.setReturnURL(ngrokUrl + "/rr/api/orders/payment/callback.test");
			obj.setNeedExtraPaidInfo("N");

			List<OrderDetail> list = orderDetailRepository.findByOrdersOrderId(orderid);
			StringBuilder itemNames = new StringBuilder();

			for (OrderDetail detail : list) {
				String itemNameWithDetails = detail.getItemname() + "    數量:    " + detail.getQuantity() ;
				itemNames.append(itemNameWithDetails).append("#");
			}

			// remove the last "#"
			if (itemNames.length() > 0) {
				itemNames.setLength(itemNames.length() - 1);
			}

			obj.setItemName(itemNames.toString());
			obj.setCustomField1(totalAmountStr);
			obj.setClientBackURL(ngrokUrl + "/rr/managePage/orderManager.do");

			String form = all.aioCheckOut(obj, null);
			System.out.println("form" + form);
			return form;
		}
	}

	public boolean payCallBackTest(String merchantTradeNo) {

		Long originalOrderId = getOriginalOrderId(merchantTradeNo);
		Orders order = orderRepository.findById(originalOrderId).orElse(null);
		if (order == null) {
			return false;
		}

		order.setOrderStatus("完成付款");
		orderRepository.save(order);

		List<OrderDetail> details = orderDetailRepository.findByOrdersOrderId(originalOrderId);
		for (OrderDetail detail : details) {
			int itemid = detail.getItemid();
			ItemDTO item = itemRepository.findById(itemid).orElse(null);
			if (item == null) {
				continue;
			}

			int salescount = item.getSalescount() + detail.getQuantity();
			int stock = item.getStock() - detail.getQuantity();

			if (stock <= 0) {
				item.setItemstatus("售完");
			}
			item.setSalescount(salescount);
			item.setStock(stock);

			itemRepository.save(item);
		}

		return true;
	}

	public boolean payCallBack(Long orderId) {

		Orders order = orderRepository.findById(orderId).orElse(null);
		if (order == null) {
			return false;
		}

		order.setOrderStatus("完成付款");
		orderRepository.save(order);

		List<OrderDetail> details = orderDetailRepository.findByOrdersOrderId(orderId);
		for (OrderDetail detail : details) {
			int itemid = detail.getItemid();
			ItemDTO item = itemRepository.findById(itemid).orElse(null);
			if (item == null) {
				continue;
			}

			int salescount = item.getSalescount() + detail.getQuantity();
			int stock = item.getStock() - detail.getQuantity();

			if (stock <= 0) {
				item.setItemstatus("售完");
			}
			item.setSalescount(salescount);
			item.setStock(stock);

			itemRepository.save(item);
		}

		return true;
	}

	public List<Map<String, Object>> getSalesByYear(int year) {
		return orderRepository.findSalesByYear(year);
	}

	public ResponseEntity<String> makePayment(Long orderId) {
		RestTemplate restTemplate = new RestTemplate();

		Orders order = orderRepository.getReferenceById(orderId);
		List<OrderDetail> orderDetails = orderDetailRepository.findByOrdersOrderId(orderId);

		StringBuilder productNameBuilder = new StringBuilder();
		for (OrderDetail orderDetail : orderDetails) {
			String productName = orderDetail.getItemname();
			BigDecimal quantity = new BigDecimal(orderDetail.getQuantity());
			productNameBuilder.append(
					  String.format("%s * %s\n", productName, quantity.toPlainString()));
		}

		// Remove the trailing comma
		if (productNameBuilder.length() > 0) {
			productNameBuilder.setLength(productNameBuilder.length() - 2);
		}

		// Create headers
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("X-LINE-ChannelId", X_LINE_ChannelId);
		headers.set("X-LINE-ChannelSecret", X_LINE_ChannelSecret);

		// Create the request body
		Map<String, Object> requestBody = new HashMap<>();
		requestBody.put("productName", productNameBuilder.toString());
		requestBody.put("amount", order.getTotalAmount().toString());
		requestBody.put("currency", "TWD");
		NgrokUtil util = new NgrokUtil();
		requestBody.put("productImageUrl",util.getNgrokUrl()+"/rr/img/images/newlogo.png");
		requestBody.put("confirmUrl", util.getNgrokUrl() + "/rr/managePage/paySucceed.do");
		requestBody.put("orderId", orderId.toString());

		// Build the request
		HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

		// Send the request
		ResponseEntity<String> response = restTemplate.postForEntity(LINE_PAY_API_URL, entity, String.class);

		// Print the response
		System.out.println(response.getBody());

		return response;
	}

}
