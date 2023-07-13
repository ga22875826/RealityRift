package com.teamsix.controller.game;

import java.io.IOException;
import java.io.File;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.teamsix.model.bean.GameBean;
import com.teamsix.service.GameService;

@Controller
public class GameController {

	@Autowired
	private GameService gService;

//  ===== ===== ===== 外頁 ===== ===== ===== ===== 	

	@GetMapping("/Index")
	public String gameGetAll(Model model) {
		List<GameBean> games = gService.gameSelectAll();
		model.addAttribute("games", games);
		return "game/gameIndex";
	}

//  ===== ===== ===== 外葉篩選器 ===== ===== ===== ===== 	

	@ResponseBody
	@GetMapping("/Index/filter")
	public List<GameBean> filterGames(@RequestParam(value = "level", required = false) String level,
			@RequestParam(value = "player", required = false) String player,
			@RequestParam(value = "maxPrice", required = false) String maxPrice,
			@RequestParam(value = "minPrice", required = false) String minPrice,
			@RequestParam(value = "city1", required = false) String city1,
			@RequestParam(value = "city2", required = false) String city2,
			@RequestParam(value = "city3", required = false) String city3) {

		List<GameBean> games = gService.gameSelectAll();

		if (city1 != "") {
			games = gService.selectByCity1(games, city1);
		}
		if (city2 != "") {
			games = gService.gameSelectAll();
			games = gService.selectByCity1(games, city1, city2);
		}
		if (city3 != "") {
			games = gService.gameSelectAll();
			games = gService.selectByCity1(games, city1, city2, city3);
		}
		if (level != "") {
			games = gService.selectByLevel(games, level);
		}
		if (player != "") {
			games = gService.selectByPlayer(games, player);
		}
		if (maxPrice != null) {
			games = gService.selectByPrice(games, maxPrice, minPrice);
		}

		return games;
	}

//  ===== ===== ===== 內頁 ===== ===== ===== ===== 

	@GetMapping("/game/view")
	public String gameView(@RequestParam("gameid") int gameid, Model model) {
		GameBean gameEach = gService.findById(gameid);
		model.addAttribute("gameEach", gameEach);
		return "game/gamePage";
	}

//  ===== ===== ===== Admin ===== ===== ===== ===== 

	@GetMapping("/db/GameSelectAll")
	public String gameSelectAll(Model model) {
		List<GameBean> games = gService.gameSelectAll();
		model.addAttribute("games", games);
		return "game/gameAdmin";
	}

	@PostMapping("/GameInsert")
	public String gameInsert(@ModelAttribute("newgame") GameBean newgame,
			@RequestParam("gameimg123") MultipartFile imgFile123) {

		try {

			if (imgFile123 != null && !imgFile123.isEmpty()) {

				// 檔名
				String uuid = UUID.randomUUID().toString();
				String extension = StringUtils.getFilenameExtension(imgFile123.getOriginalFilename());
				String fileName = "n-img-" + uuid + "." + extension;

				// 上傳位置
				File filePath = new File("C:/ProjectImages/game/upload/" + fileName);
				imgFile123.transferTo(filePath);

				// 將路徑存到 gameimg
				newgame.setGameimg("img/game/upload/" + fileName);

			} else {

				// 預設圖
				String defaultImg = "noimg.jpg";
				newgame.setGameimg("img/game/" + defaultImg);
			}

			gService.gameInsert(newgame);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return "redirect:/db/GameSelectAll";
	}

	@DeleteMapping("/GameDelete")
	public String gameDeleteById(@RequestParam("gameid") Integer gameid) {
		gService.gameDeleteById(gameid);
		return "redirect:/db/GameSelectAll";
	}

	@PutMapping("/GameUpdate")
	public String gameUpdateById(@ModelAttribute GameBean updategame,
			@RequestParam("updateimg") MultipartFile updateimg) {

		try {
			GameBean game = gService.findById(updategame.getGameid());
			String oldGameImg = game.getGameimg();

			if (updateimg != null && !updateimg.isEmpty()) {

				// 檔名
				String originalFileName = updateimg.getOriginalFilename();
				String extension = StringUtils.getFilenameExtension(originalFileName);
				String uuid = UUID.randomUUID().toString();
				String fileName = "n-img-" + uuid + "." + extension;

				// 上傳位置
				File filePath = new File("C:/ProjectImages/game/upload/" + fileName);
				updateimg.transferTo(filePath);

				updategame.setGameimg("img/game/upload/" + fileName);

			} else {
				updategame.setGameimg(oldGameImg);
			}
			gService.gameUpdateById(updategame.getGameid(), updategame);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "redirect:/db/GameSelectAll";
	}

//  ===== ===== ===== Admin篩選器 ===== ===== ===== ===== 

	@GetMapping("/SearchByFilter")
	public String gameSearchByFilter(@RequestParam("city") String city, @RequestParam("level") String level,
			Model model) {
		List<GameBean> games = gService.gameSearchByFilter(city, level);
		model.addAttribute("games", games);

		if (city.equals("all") && level.equals("allevel")) {

			return "redirect:/GameSelectAll";
		}

		return "game/gameAdmin";

	}

}
