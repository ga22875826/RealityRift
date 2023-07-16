package com.teamsix.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.teamsix.model.bean.item.Notification;

@Service
public class NotificationService {

	
	@Autowired
    private  SimpMessagingTemplate template;

    
	public void sendNotification(Notification notification) {
	    System.out.println("Sending notification: " + notification); // Add this line
	    template.convertAndSend("/topic/notifications", notification);
	}
}