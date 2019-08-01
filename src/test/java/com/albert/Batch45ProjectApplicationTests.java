package com.albert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.albert.service.EmployeeMailSender;
import com.fasterxml.jackson.databind.Module.SetupContext;


@RunWith(SpringRunner.class)
@SpringBootTest
public class Batch45ProjectApplicationTests {
	private static final Logger log = LoggerFactory.getLogger(EmployeeMailSender.class);
	@Autowired
	EmployeeMailSender sender;
	
	
	
	@Test
	public void assertNotSenderNull() {
		assertNotNull(sender);
	}
	
	@Test
	public void assertMessageSent() {
		log.info("sender is null:" + (sender==null));
		String output = sender.sendingMail("anderson_ac@lynchburg.edu", "Test", "This is a test");
		assertEquals("Mail sent successfully!!", output);
	}
	@Test
	public void assertMessageFail() {
		String output = sender.sendingMail("", "Test", "This is a test");
		assertEquals("Exceptin while sending mail..", output);
		assertNotEquals("Mail sent successfully!!",output);
	}

}
