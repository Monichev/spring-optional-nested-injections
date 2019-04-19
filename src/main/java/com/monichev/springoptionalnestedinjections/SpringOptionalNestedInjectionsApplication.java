package com.monichev.springoptionalnestedinjections;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.Priority;

interface IService {
	String getFoo();
}

@Service
@Qualifier("main")
@Priority(Integer.MAX_VALUE)
class ServiceA implements IService {
	@Override
	public String getFoo() {
		return "foo";
	}
}

@Service
@ConditionalOnProperty(prefix = "service", value = "metrics")
@Priority(Integer.MAX_VALUE - 1)
class ServiceAMetrics implements IService {
	private final IService service;

	// using this @Qualifier prevents circular dependency
	public ServiceAMetrics(@Qualifier("main") IService service) {
		this.service = service;
	}

	@Override
	public String getFoo() {
		return "metrics, " + service.getFoo();
	}
}

@Service
@ConditionalOnProperty(prefix = "service", value = "logger")
@Priority(Integer.MAX_VALUE - 2)
class ServiceALogger implements IService {
	private final IService service;

	public ServiceALogger(IService service) {
		this.service = service;
	}

	@Override
	public String getFoo() {
		return "log, " + service.getFoo();
	}
}

@SpringBootApplication
public class SpringOptionalNestedInjectionsApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(SpringOptionalNestedInjectionsApplication.class, args);
		context.getBean(IService.class).getFoo();
	}
}
