package com.vm.readTextDrools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.drools.compiler.compiler.DroolsParserException;
import org.drools.template.ObjectDataCompiler;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import com.vm.readTextDrools.Vrp;

public class ReadVrpTextDrools {
	public static void main(String[] args) throws DroolsParserException, IOException {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
		VrpDaoImpl testDaoImpl = (VrpDaoImpl) context.getBean("vrpDaoImpl");

		String ruleFile = "/home/sys-user/CM/newWorkspace/Drools/src/main/resources/rules/VRP.txt";

		List<String> list = Files.readAllLines(new File(ruleFile).toPath(), Charset.defaultCharset());
		list.removeAll(Arrays.asList("", null));
		list.remove("end");

		Vrp vrpobj = new Vrp();
		for (int i = 0; i < list.size(); i++) {

			if (list.get(i).contains("when") || list.get(i).contains("vrp.")) {
				if (list.get(i).contains("when")) {

					String test = list.get(i+2);
					String split = "VendorRiskProfile";
					
					Pattern p = Pattern.compile("\"([^\"]*)\"");
					Matcher m = p.matcher(test);
					while (m.find()) {
					  System.out.println(m.group(1));
					  String testString=m.group(1);
						vrpobj.setType(testString);
					}

					

					if (list.get(i + 3).contains("then")) {
						for (int j = i + 4; j < i + 7; j++) {
							String sub = list.get(j);
							String testThen = sub.substring(sub.indexOf("(") + 1, sub.indexOf(")"));
							if (list.get(j).contains("Base")) {
								vrpobj.setBasePrice(Double.parseDouble(testThen));
							} else if (list.get(j).contains("Customer")) {
								vrpobj.setCustomerPrice(Double.parseDouble(testThen));
							} else if (list.get(j).contains("Vendor")) {
								vrpobj.setVendorPrice(Double.parseDouble(testThen));
							}
							
						}
						testDaoImpl.createtest(vrpobj);
					}
					
				}

			}
			
		}

		List<Vrp> TestList1 = testDaoImpl.getAll();

		/*
		 * for (Vrp testObj : TestList1) { System.out.println(testObj.getBasePrice());
		 * 
		 * }
		 */
		ObjectDataCompiler dataCompiler = new ObjectDataCompiler();

		final String drl = dataCompiler.compile(TestList1, getRulesStream());
		KieServices kieServices = KieServices.Factory.get();
		KieFileSystem kfs = kieServices.newKieFileSystem();
		kfs.write("src/main/resources/samp.drl", kieServices.getResources().newReaderResource(new StringReader(drl)));

		KieBuilder kieBuilder = kieServices.newKieBuilder(kfs).buildAll();
		KieModule kieModule = kieBuilder.getKieModule();
		KieContainer kContainer = kieServices.newKieContainer(kieModule.getReleaseId());
		KieSession kieSession = kContainer.newKieSession();

		Vrp vrpTest = new Vrp("customer pricing");
		kieSession.insert(vrpTest);
	
		kieSession.fireAllRules();

		kieSession.destroy();
		kieSession.dispose();

	}

	private static InputStream getRulesStream() throws FileNotFoundException {
		return new FileInputStream("/home/sys-user/CM/newWorkspace/Drools/src/main/resources/rules/VrpRules1.drt");
	}
}
