package com.teamsix.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.teamsix.model.bean.Category;
import com.teamsix.model.bean.ItemDTO;
import com.teamsix.model.bean.Itemimg;
import com.teamsix.model.dao.CategoryRepository;
import com.teamsix.model.dao.ImgRepository;
import com.teamsix.model.dao.ItemRepository;

import jakarta.transaction.Transactional;

@Service
public class ItemService {

	@Autowired
	private ItemRepository iRepo;

	@Autowired
	private ImgRepository imgRepo;

	@Autowired
	private CategoryRepository cRepo;

	public List<Itemimg> findAllImgsById(int id) {

		Itemimg ref = imgRepo.getReferenceById(id);

		return ref.getItem().getImages();

	}

	public void deleteImgById(int imgid) {
		imgRepo.deleteById(imgid);
	}

	// ---------------------------------------------------------------------
	public List<ItemDTO> listItem() {
		List<ItemDTO> items = iRepo.findAll();
		for (ItemDTO item : items) {
			String categoryPath = getCategoryPath(item.getCategory().getId());
			item.setCategoryPath(categoryPath);
		}
		return items;
	}

	private String getCategoryPath(int categoryId) {
		StringBuilder path = new StringBuilder();
		Category current = cRepo.findById(categoryId).orElse(null);

		while (current != null) {
			System.out.println("處理分類: " + current.getName());
			if (path.length() > 0) {
				path.insert(0, " > ");
			}
			path.insert(0, current.getName());
			current = current.getParent();
		}
		System.out.println("生成路徑: " + path.toString());
		return path.toString();
	}

//--------------------------------------------------------------------
	public ItemDTO findItemById(Integer id) {
		Optional<ItemDTO> rs = iRepo.findById(id);

		if (rs.isEmpty()) {
			return new ItemDTO();
		} else {
			return rs.get();
		}

	}

	public void insertItem(ItemDTO item) {
		iRepo.save(item);
	}

	public void saveItemImage(Itemimg itemImage) {
		imgRepo.save(itemImage);
	}

	public void saveAllItemImage(List<Itemimg> itemImages) {
		imgRepo.saveAll(itemImages);

	}

	@Transactional
	public ItemDTO updateItemById(ItemDTO item) {
		Optional<ItemDTO> optional = iRepo.findById(item.getItemid());
		String categoryPath = getCategoryPath(item.getCategory().getId());

		if (optional.isPresent()) {
			ItemDTO Item = optional.get();

			Item.setItemdetail(item.getItemdetail());
			Item.setItemname(item.getItemname());
			Item.setItemstatus(item.getItemstatus());
			Item.setPrice(item.getPrice());
			Item.setSalescount(item.getSalescount());
			Item.setStock(item.getStock());
			Item.setCategoryPath(categoryPath);

			return Item;

		}

		ItemDTO itemDTO = new ItemDTO();
		itemDTO.setItemname("更新失敗");
		return itemDTO;

	}

	public List<Itemimg> getImgsByItemid(int itemid) {

		return imgRepo.findByItem_Itemid(itemid);

	}

////////////////////////////////前台API/////////////////////////
	public Page<ItemDTO> getItemsByCategoryId(int categoryId, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<ItemDTO> items = iRepo.findByCategoryIdAndItemstatusNot(categoryId, "停售", pageable);
		for (ItemDTO item : items) {
			String categoryPath = getCategoryPath(item.getCategory().getId());
			item.setCategoryPath(categoryPath);
		}
		return items;
	}

	public Page<ItemDTO> listItemPage(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<ItemDTO> items = iRepo.findByItemstatusNot("停售", pageable);

		for (ItemDTO item : items) {
			String categoryPath = getCategoryPath(item.getCategory().getId());
			item.setCategoryPath(categoryPath);
		}

		return items;
	}

}
