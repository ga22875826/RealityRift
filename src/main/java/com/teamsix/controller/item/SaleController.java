package com.teamsix.controller.item;

import java.math.BigDecimal;
import java.util.List;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.teamsix.config.SaleStatusUpdater;
import com.teamsix.model.bean.item.ItemDTO;
import com.teamsix.model.bean.item.Notification;
import com.teamsix.model.bean.item.SaleDTO;
import com.teamsix.model.bean.item.SaleItemDTO;
import com.teamsix.model.dao.SaleItemRepository;
import com.teamsix.service.ItemService;
import com.teamsix.service.SaleService;

@RestController
@RequestMapping("/api/sales")
public class SaleController {

	@Autowired
	private SaleService saleService;

	@Autowired
	private ItemService itemService;

	@Autowired
	private SaleItemRepository saleItemRepo;

	@Autowired
	private SaleStatusUpdater saleStatusUpdater;
	
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@PostMapping
	public ResponseEntity<SaleDTO> createSale(@RequestBody SaleDTO saleDTO) {
		Date now = new Date();
		if (saleDTO.getSaleStart().after(now)) {
			saleDTO.setSaleStatus("準備中");
		} else if (saleDTO.getSaleEnd().before(now)) {
			saleDTO.setSaleStatus("特賣結束");
		} else {
			saleDTO.setSaleStatus("進行中");
		}

		SaleDTO createdSale = saleService.createSale(saleDTO);
		
		Notification notification = new Notification();
		notification.setTitle("限時特賣新增");
		notification.setContent(saleDTO.getSaleName()+"開始囉");
		notification.setImgSrc("sale.jpg");
		  simpMessagingTemplate.convertAndSend("/topic/notifications",notification );
		return new ResponseEntity<>(createdSale, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<SaleDTO>> getAllSales() {
		List<SaleDTO> sales = saleService.getAllSales();
		return new ResponseEntity<>(sales, HttpStatus.OK);
	}

	/**
	 * 獲取進行中特賣
	 * 
	 * @return
	 */
	@GetMapping("/ongoingSales")
	public ResponseEntity<List<SaleDTO>> getOngoingSales() {
		List<SaleDTO> ongoingSales = saleService.getSalesByStatus("進行中");
		return new ResponseEntity<>(ongoingSales, HttpStatus.OK);
	}

	/**
	 * 用saleId找到saleItem
	 * 
	 * @param saleId
	 * @return
	 */
	@PostMapping("/getSaleItems.do")
	public ResponseEntity<List<SaleItemDTO>> getSaleItems(@RequestParam Integer saleId) {
		List<SaleItemDTO> items = saleService.findItemsBySaleId(saleId);
		return ResponseEntity.ok(items);
	}

	@PostMapping("/addItemsToSale")
	public ResponseEntity<Void> addItemsToSale(@RequestParam("saleId") int saleId,
			@RequestParam("discount") String discount, @RequestBody List<Integer> itemIds) {
		try {
			SaleDTO sale = saleService.getSaleById(saleId);
			if (sale == null) {
				throw new Exception("Sale not found");
			}

			BigDecimal discountValue = new BigDecimal(discount);

			for (Integer itemId : itemIds) {
				ItemDTO item = itemService.findItemById(itemId);
				if (item == null) {
					throw new Exception("Item not found");
				}

				SaleItemDTO saleItem = new SaleItemDTO();
				saleItem.setSale(sale);
				saleItem.setItem(item);
				saleItem.setDiscount(discountValue);
				saleItemRepo.save(saleItem);
			}

			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/deleteSaleItem.do")
	public ResponseEntity<String> deleteSaleItem(@RequestParam Integer saleItemId) {
		// 根據 saleItemId 刪除銷售項目的邏輯
		boolean success = saleService.deleteSaleItem(saleItemId);

		if (success) {
			return ResponseEntity.ok("銷售項目已成功刪除");
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("無法刪除銷售項目");
		}
	}

	@PostMapping("/start/{id}")
	public ResponseEntity<Void> startSale(@PathVariable int id) {
		SaleDTO sale = saleService.getSaleById(id);
		if (sale == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		sale.startSaleNow();
		saleService.updateSale(sale);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/{saleId}/actions/{action}")
	public ResponseEntity<Void> updateSaleStatus(@PathVariable Integer saleId, @PathVariable String action) {
		saleService.updateSaleStatus(saleId, action);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PostMapping("/trigger")
	public void updateSaleStatuses() {
		saleStatusUpdater.updateSaleStatuses();
	}

}
