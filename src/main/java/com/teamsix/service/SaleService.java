package com.teamsix.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teamsix.model.bean.item.ItemDTO;
import com.teamsix.model.bean.item.SaleDTO;
import com.teamsix.model.bean.item.SaleItemDTO;
import com.teamsix.model.dao.SaleItemRepository;
import com.teamsix.model.dao.SaleRepository;

import jakarta.transaction.Transactional;

@Service
public class SaleService {

	@Autowired
	private SaleRepository saleRepository;
	
	@Autowired
	private SaleItemRepository saleItemRepo;

	public SaleDTO createSale(SaleDTO saleDTO) {

		return saleRepository.save(saleDTO);
	}
	

    public List<SaleDTO> getAllSales() {
        List<SaleDTO> sales = saleRepository.findAll();
        List<SaleDTO> saleDTOs = new ArrayList<>();
        for(SaleDTO sale : sales) {
            SaleDTO saleDTO = new SaleDTO();
            BeanUtils.copyProperties(sale, saleDTO);
            saleDTOs.add(saleDTO);
        }
        return saleDTOs;
    }
    
    @Transactional
    public SaleItemDTO addSaleItem(SaleDTO sale, ItemDTO item, BigDecimal discount) {
        SaleItemDTO saleItem = new SaleItemDTO();
        saleItem.setSale(sale);
        saleItem.setItem(item);
        saleItem.setDiscount(discount);
        return saleItemRepo.save(saleItem);
    }
    
    @Transactional
    public List<SaleItemDTO> getAllSaleItems() {
        List<SaleItemDTO> findAll = saleItemRepo.findAll();
        
        return findAll;
    }
    
    public SaleDTO getSaleById(int saleId) {
    return saleRepository.getReferenceById(saleId);
    }
    
    
    public List<SaleItemDTO> findItemsBySaleId(int saleId) {
        return saleItemRepo.findBySale_SaleId(saleId);
    }
    
    public List<SaleDTO> getSalesByStatus(String status) {
    	
    	return  saleRepository.findBySaleStatus(status);
    	
    	
    }
        


	public boolean deleteSaleItem(Integer saleItemId) {
		 saleItemRepo.deleteById(saleItemId);
		return true;
	}

	  public void updateSale(SaleDTO sale) {
	        saleRepository.save(sale);
	    }
	  
	    
	    @Transactional
	    public void updateSaleStatus(Integer saleId, String action) {
	        SaleDTO existingSale = saleRepository.findById(saleId)
	                .orElseThrow(() -> new IllegalArgumentException("Invalid sale Id:" + saleId));
	        
	        switch (action) {
	            case "start":
	                existingSale.startSaleNow();
	                break;
	            case "end":
	                existingSale.endSaleNow();
	                break;
	            case "pause":
	                existingSale.pauseSale();
	                break;
	            case "resume":
	                existingSale.resumeSale();
	                break;
	            default:
	                throw new IllegalArgumentException("Invalid action: " + action);
	        }

	        saleRepository.save(existingSale);
	    }


    
    
}
