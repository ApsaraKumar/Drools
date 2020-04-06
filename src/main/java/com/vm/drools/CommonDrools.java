package com.vm.drools;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class CommonDrools {

	public static void main(String[] args) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
		DroolsDaoImpl droolsDaoImpl = (DroolsDaoImpl) context.getBean("droolsDaoImpl");
		DroolsServiceImpl droolsServiceImpl = (DroolsServiceImpl) context.getBean("droolsServiceImpl");

		String vrpFlow = "vrp";
		String rrpFlow = "rrp";
		
			try {
				droolsServiceImpl.checkFlow(vrpFlow, droolsDaoImpl);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		

	}

	
}
