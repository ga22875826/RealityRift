package com.teamsix.controller.item;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;

import com.teamsix.model.bean.Category;
import com.teamsix.model.bean.ItemDTO;
import com.teamsix.model.bean.ItemWithImages;
import com.teamsix.model.bean.Itemimg;
import com.teamsix.service.CategoryService;
import com.teamsix.service.ItemService;

@Controller
public class ItemController {

	@Autowired
	private ItemService iService;

	@Autowired
	private CategoryService categoryService;

	@GetMapping("/db/manager.showItemList.do")
	public String getAllItems(Model model) {
		List<ItemDTO> itemList = iService.listItem();
		model.addAttribute("itemList", itemList);
		return "Item/Itemadmin";

	}

/////postman測試用	
	@ResponseBody
	@GetMapping("/manager.showItemList.test")
	public List<ItemDTO> getAllItemsTest(Model model) {
		List<ItemDTO> itemList = iService.listItem();
		model.addAttribute("itemList", itemList);
		return itemList;

	}

	@ResponseBody
	@PostMapping("/addItem.do")
	public String addItem(@RequestParam("itemname") String itemname, @RequestParam("price") BigDecimal price,
			@RequestParam("salescount") int salescount, @RequestParam("stock") int stock,
			@RequestParam("itemstatus") String itemstatus, @RequestParam("itemdetail") String itemdetail,
			@RequestParam("categoryid") Integer categoryid,
			@RequestParam(value = "images", required = false) List<MultipartFile> images) throws IOException {

		ItemDTO item = new ItemDTO();
		item.setItemname(itemname);
		item.setPrice(price);
		item.setSalescount(salescount);
		item.setStock(stock);
		item.setItemstatus(itemstatus);
		item.setItemdetail(itemdetail);

		Category category = categoryService.getCategoryById(categoryid);
		item.setCategory(category);

		if (images != null) {
			for (MultipartFile image : images) {
				if (!image.isEmpty()) {
					Itemimg itemImage = new Itemimg();
					itemImage.setImagename(UUID.randomUUID().toString() + "_" + image.getOriginalFilename());
					byte[] imageData = image.getBytes();

					File file = new File("C:\\ProjectImages\\itemimgs\\" + itemImage.getImagename());
					FileUtils.writeByteArrayToFile(file, imageData);

					itemImage.setImage(imageData);
					itemImage.setItem(item);
					item.addImage(itemImage);
				}
			}
		}

		iService.insertItem(item);

		return "新增成功";
	}

	@GetMapping("/covertToAddItem.do")
	public String covertToAddItem() {
		return "Item/addItemPage";
	}

	@PutMapping("/updateItemAjax.do")
	@ResponseBody
	public ItemDTO editItem(@RequestParam("itemid") int itemId, @RequestParam("itemname") String itemName,
			@RequestParam("stock") int stock, @RequestParam("salescount") int salesCount,
			@RequestParam("itemstatus") String itemStatus, @RequestParam("itemdetail") String itemDetail,
			@RequestParam("price") BigDecimal price, @RequestParam("categoryid") int categoryId) {

		ItemDTO bean = iService.findItemById(itemId);

		bean.setItemdetail(itemDetail);
		bean.setItemname(itemName);
		bean.setItemstatus(itemStatus);
		bean.setPrice(price);
		bean.setSalescount(salesCount);
		bean.setStock(stock);

		Category category = categoryService.getCategoryById(categoryId); // 透過服務從ID取得Category對象
		bean.setCategory(category); // 將Category對象設定到bean中

		ItemDTO newItem = iService.updateItemById(bean);

		return newItem;
	}

	@PutMapping("/switchStatus.do")
	@ResponseBody
	public ItemDTO editStatusById(@RequestParam("itemid") int itemId, @RequestParam("itemstatus") String itemStatus) {

		ItemDTO bean = iService.findItemById(itemId);

		bean.setItemstatus(itemStatus);

		ItemDTO newItem = iService.updateItemById(bean);

		return newItem;
	}

	@GetMapping("/getItemById.do")
	@ResponseBody
	public ItemDTO getItemById(@RequestParam("itemid") int itemid) {

		return iService.findItemById(itemid);
	}

	@PostMapping("getItemByCategoryId.do")
	@ResponseBody
	public Page<ItemWithImages> getItemByCategoryId(@RequestParam("categoryId") int categoryId,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "8") int size) {
		Page<ItemDTO> itemPage = iService.getItemsByCategoryId(categoryId, page, size);
		List<ItemWithImages> content = new ArrayList<>();
		for (ItemDTO item : itemPage.getContent()) {
			List<Itemimg> list = iService.getImgsByItemid(item.getItemid());
			content.add(new ItemWithImages(item, list));
		}
		return new PageImpl<>(content, itemPage.getPageable(), itemPage.getTotalElements());
	}

	@PostMapping("pageAllItems.do")
	@ResponseBody
	public Page<ItemWithImages> getAllItemsPage(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "8") int size) {

		Page<ItemDTO> itemPage = iService.listItemPage(page, size);
		List<ItemWithImages> content = new ArrayList<>();

		for (ItemDTO item : itemPage.getContent()) {
			List<Itemimg> list = iService.getImgsByItemid(item.getItemid());
			content.add(new ItemWithImages(item, list));
		}

		return new PageImpl<>(content, itemPage.getPageable(), itemPage.getTotalElements());
	}

}
