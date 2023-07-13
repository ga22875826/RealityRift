package com.teamsix.controller.item;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.teamsix.model.bean.item.ItemDTO;
import com.teamsix.model.bean.item.Itemimg;
import com.teamsix.model.bean.item.ItemimgDTO;
import com.teamsix.service.ItemService;


@Controller
public class ImgController {
	
	@Autowired
	private ItemService iService;
	
	@DeleteMapping("api/deleteImg")
	@ResponseBody
	public List<ItemimgDTO> deleteAction(@RequestParam("id")int imgid) {
		List<Itemimg> newImages = iService.findAllImgsById(imgid);
		iService.deleteImgById(imgid);
		List<ItemimgDTO>dtos = new LinkedList<>();
		for(Itemimg image:newImages) {
			ItemimgDTO dto = new ItemimgDTO();
			dto.setId(image.getId());
			dto.setImagename(image.getImagename());
			dtos.add(dto);
		}
		
		return dtos;
	}	


	@PostMapping("/api/imgUpload")
	@ResponseBody
	public List<ItemimgDTO> uploadImgAction(
			@RequestParam("images") List<MultipartFile> images,
			@RequestParam("itemid") int itemid) throws IOException {

		ItemDTO newItem = iService.findItemById(itemid);
		ItemDTO item = newItem;

		if (images.size() != 0) {

			for (MultipartFile image : images) {
				Itemimg itemImage = new Itemimg();
				itemImage.setImagename(UUID.randomUUID().toString() + "_" + image.getOriginalFilename());
				try {
					byte[] imageData = image.getBytes();
					File file = new File("C:\\ProjectImages\\itemimgs\\" + itemImage.getImagename());
					FileUtils.writeByteArrayToFile(file, imageData);
				} catch (IOException e) {
					e.printStackTrace();
				}
				itemImage.setImage(image.getBytes());
				itemImage.setItem(item);
//				itemImage.setItemid(itemid);
				item.addImage(itemImage);
			
			
				iService.saveItemImage(itemImage);
			}
		}
		List<Itemimg> NewImages = item.getImages();
		List<ItemimgDTO>dtos = new LinkedList<>();
		for(Itemimg image:NewImages) {
			ItemimgDTO dto = new ItemimgDTO();
			dto.setId(image.getId());
			dto.setImagename(image.getImagename());
			dtos.add(dto);
		}
		
		return dtos;
		
		
	}

}
