package com.monichev.springoptionalnestedinjections;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(properties = {"service.metrics=true", "service.logger=true"})
public class LoggerMetricsAndService {

	@Autowired
	private IService service;

	@Test
	public void contextLoads() {
		assertEquals("log, metrics, foo", service.getFoo());
	}
}
