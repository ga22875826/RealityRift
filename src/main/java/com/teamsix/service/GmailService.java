package com.teamsix.service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Message;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.Properties;
import java.util.Set;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;

@Service
public class GmailService {

	private static final String FROM_EMAIL_ADDRESS = "realityriftweb.official@gmail.com";


	private static final String APPLICATION_NAME = "Gmail API Java Quickstart";
	private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
	private static final String CREDENTIALS_FILE_PATH = "/client_secret_746476049577-vhkoqb14e19c0beusb2j0dednunoeegf.apps.googleusercontent.com.json";
	
	private final Gmail service;

	public GmailService() throws GeneralSecurityException, IOException {
		NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
		service = new Gmail.Builder(httpTransport, JSON_FACTORY, getCredentials(httpTransport))
				.setApplicationName(APPLICATION_NAME).build();
	}

	private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
		// Load client secrets.
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(GmailService.class.getResourceAsStream(CREDENTIALS_FILE_PATH)));

		// Build flow and trigger user authorization request.
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
				clientSecrets, Set.of(GmailScopes.GMAIL_COMPOSE))
				.setDataStoreFactory(new FileDataStoreFactory(Paths.get("gmail_tokens").toFile()))
				.setAccessType("offline").build();
		LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
		return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
	}

	//純文字無檔案
//	public void sendMail(String subject, String message,String toEmailAddress)
//			throws GeneralSecurityException, IOException, AddressException, MessagingException {
//
//		// Create the email content
//
//		// Encode as MIME message
//		Properties props = new Properties();
//		Session session = Session.getDefaultInstance(props, null);
//		MimeMessage email = new MimeMessage(session);
//		email.setFrom(new InternetAddress(FROM_EMAIL_ADDRESS));
//		email.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(toEmailAddress));
//		email.setSubject(subject);
//		email.setText(message);
//
//		// Encode and wrap the MIME message into a gmail message
//		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
//		email.writeTo(buffer);
//		byte[] rawMessageBytes = buffer.toByteArray();
//		String encodedEmail = Base64.encodeBase64URLSafeString(rawMessageBytes);
//		Message msg = new Message();
//		msg.setRaw(encodedEmail);
//
//		try {
//			// Create send message
//			msg = service.users().messages().send("me", msg).execute();
//			System.out.println("Message id: " + msg.getId());
//			System.out.println(msg.toPrettyString());
//		} catch (GoogleJsonResponseException e) {
//			GoogleJsonError error = e.getDetails();
//			if (error.getCode() == 403) {
//				System.err.println("Unable to send message: " + e.getDetails());
//			} else {
//				throw e;
//			}
//		}
//
//	}
	public void sendMail(String subject, String message,String toEmailAddress)
			throws GeneralSecurityException, IOException, AddressException, MessagingException {
		
	    MimeMultipart multipart = new MimeMultipart();

	    MimeBodyPart textPart = new MimeBodyPart();
	    textPart.setText(message);

	    multipart.addBodyPart(textPart);

	    MimeBodyPart imagePart = new MimeBodyPart();
	    String imagePath = "C:/ProjectImages/images/newlogo.png";  
	    DataSource imageSource = new FileDataSource(imagePath);
	    imagePart.setDataHandler(new DataHandler(imageSource));
	    imagePart.setHeader("Content-ID", "<image>");

	    multipart.addBodyPart(imagePart);

	    Properties props = new Properties();
	    Session session = Session.getDefaultInstance(props, null);
	    MimeMessage email = new MimeMessage(session);
	    email.setFrom(new InternetAddress(FROM_EMAIL_ADDRESS));
	    email.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(toEmailAddress));
	    email.setSubject(subject);
	    email.setContent(multipart);

	    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
	    email.writeTo(buffer);
	    byte[] rawMessageBytes = buffer.toByteArray();
	    String encodedEmail = Base64.encodeBase64URLSafeString(rawMessageBytes);
	    Message msg = new Message();
	    msg.setRaw(encodedEmail);

	    try {
	        msg = service.users().messages().send("me", msg).execute();
	        System.out.println("Message id: " + msg.getId());
	        System.out.println(msg.toPrettyString());
	    } catch (GoogleJsonResponseException e) {
	        GoogleJsonError error = e.getDetails();
	        if (error.getCode() == 403) {
	            System.err.println("Unable to send message: " + e.getDetails());
	        } else {
	            throw e;
	        }
	    }
		
	}

}