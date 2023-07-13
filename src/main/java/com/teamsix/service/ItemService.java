package com.teamsix.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.teamsix.model.bean.item.Category;
import com.teamsix.model.bean.item.ItemDTO;
import com.teamsix.model.bean.item.Itemimg;
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
			if (path.length() > 0) {
				path.insert(0, " > ");
			}
			path.insert(0, current.getName());
			current = current.getParent();
		}
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
			Item.setAdded(item.getAdded());
			
		

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
	public Page<ItemDTO> getItemsByCategoryId(int categoryId, int page, int size, String sort) {
		Sort sortRequest = null;
		if (sort.equals("price_desc")) {
			sortRequest = Sort.by("price").descending();
		}else if(sort.equals("price_asc")) {
			sortRequest = Sort.by("price").ascending();
		}else if(sort.equals("added_asc")) {
			sortRequest = Sort.by("added").ascending();
		}else if(sort.equals("added_desc")){
			sortRequest = Sort.by("added").descending();
		}else if(sort.equals("salescount_asc")) {
			sortRequest = Sort.by("salescount").ascending();
		}else {
			sortRequest = Sort.by("salescount").descending();
			
		}
		
		Pageable pageable = PageRequest.of(page, size, sortRequest);
		Page<ItemDTO> items = iRepo.findByCategoryIdAndItemstatusNot(categoryId, "停售", pageable);
		for (ItemDTO item : items) {
			String categoryPath = getCategoryPath(item.getCategory().getId());
			item.setCategoryPath(categoryPath);
		}
		return items;
	}

	public Page<ItemDTO> listItemPage(int page, int size, String sort) {
		Sort sortRequest = null;
		if (sort.equals("price_desc")) {
			sortRequest = Sort.by("price").descending();
		}else if(sort.equals("price_asc")) {
			sortRequest = Sort.by("price").ascending();
		}else if(sort.equals("added_asc")) {
			sortRequest = Sort.by("added").ascending();
		}else if(sort.equals("added_desc")){
			sortRequest = Sort.by("added").descending();
		}else if(sort.equals("salescount_asc")) {
			sortRequest = Sort.by("salescount").ascending();
		}else {
			sortRequest = Sort.by("salescount").descending();
			
		}
		
		Pageable pageable = PageRequest.of(page, size, sortRequest);
		Page<ItemDTO> items = iRepo.findByItemstatusNot("停售", pageable);

		for (ItemDTO item : items) {
			String categoryPath = getCategoryPath(item.getCategory().getId());
			item.setCategoryPath(categoryPath);
		}

		return items;
	}

}
