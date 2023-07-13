package com.teamsix.config;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.teamsix.model.bean.item.SaleDTO;
import com.teamsix.model.dao.SaleRepository;

@Component
public class SaleStatusUpdater {

    private final SaleRepository saleRepository;

    public SaleStatusUpdater(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    @Scheduled(fixedRate = 60000)
    public void updateSaleStatuses() {
        List<SaleDTO> sales = saleRepository.findAll();
        
        System.out.println("更新");

        for (SaleDTO sale : sales) {
            if (sale.shouldBeStarted()) {
                sale.start();
                saleRepository.save(sale);
            } else if (sale.shouldBeEnded()) {
                sale.end();
                saleRepository.save(sale);
            }
        }
    }
}