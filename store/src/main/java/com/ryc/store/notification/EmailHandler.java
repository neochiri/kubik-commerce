package com.ryc.store.notification;

import com.ryc.store.entity.ProductEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

@Component
public class EmailHandler {

	private final JavaMailSender mailSender;

	public EmailHandler(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void sendEmailProductRemoved(String email, List<ProductEntity> productsLeft){
		String emailContent = createEmailContent(productsLeft);
		sendEmail(emailContent, email, "One product has been removed");
	}

	private void sendEmail(String textMessage, String email, String subject) {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		try {
			helper.setTo(email);
			helper.setText(textMessage, true);
			helper.setSubject(subject);
			mailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	private String createEmailContent(List<ProductEntity> productsLeft){
		String content = "";
		for(ProductEntity product : productsLeft){
			content += product.getAvailability() + " " + product.getName();
		}
		return content;
	}
}
