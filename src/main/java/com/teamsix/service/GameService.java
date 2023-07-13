package com.teamsix.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.teamsix.model.bean.GameBean;
import com.teamsix.model.dao.GameRepository;

@Service
public class GameService {
	
	@Autowired
	private GameRepository gameRepo;
	
	//   ===== ===== ===== ===== ===== ===== ===== ===== 
	
	public List<GameBean> gameSelectAll(){
		return gameRepo.findAll();
	}
	
	public GameBean findById(int gameId) {
		return gameRepo.getReferenceById(gameId);
	}

	public void gameInsert(GameBean newgame) {
		gameRepo.save(newgame);
	}
	
	public void gameDeleteById(Integer gameig) {
		gameRepo.deleteById(gameig);
	}

	
	@Transactional
	public GameBean gameUpdateById(Integer gameid, GameBean updategame) {
		Optional<GameBean> optional = gameRepo.findById(gameid);
		
	    if (optional.isPresent()) {
	    	
	        GameBean game = optional.get();
	        
	        game.setStudio(updategame.getStudio());
	        game.setGname(updategame.getGname());
	        game.setLevel(updategame.getLevel());
	        game.setGamestatus(updategame.getGamestatus());
	        game.setPlayer(updategame.getPlayer());
	        game.setTime(updategame.getTime());
	        game.setAddress(updategame.getAddress());
	        game.setPhone(updategame.getPhone());
	        game.setPriceper(updategame.getPriceper());
	        game.setTextdesc(updategame.getTextdesc());
	        game.setGameimg(updategame.getGameimg());
	        
	        return game;
	    } 
	    
	    return null;
		
	}
	
	//   ===== ===== ===== ===== ===== ===== ===== ===== 
	
    public List<GameBean> gameSearchByFilter(String city, String level) {
        if (city.equals("all")) {
            // 只難度
            return gameRepo.findByLevel(level);
        } else if (level.equals("allevel")) {
            // 只地區
            return gameRepo.findByAddress(city);
        } else {
            // 地區+難度
            return gameRepo.findByAddressAndLevel(city, level);
        }
    }
    
    public List<GameBean> findByLevel(String level) {
    	return gameRepo.findByLevel(level);
    }

    public GameBean findByGname(String gname) {
    	return gameRepo.findByGname(gname);
    }
	

	
}
