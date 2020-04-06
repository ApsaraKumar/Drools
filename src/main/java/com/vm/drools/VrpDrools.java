package com.vm.drools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.drools.template.ObjectDataCompiler;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class VrpDrools {
	public static void main(String args[]) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
		DroolsDaoImpl droolsDaoImpl = (DroolsDaoImpl) context.getBean("droolsDaoImpl");

		try {
			readExcel(droolsDaoImpl);

		} catch (

		Exception e) {
			e.printStackTrace();
		}

	}

	private static void readExcel(DroolsDaoImpl droolsDaoImpl) throws FileNotFoundException, IOException {
		FileInputStream file = new FileInputStream(new File("/home/sys-user/Backup/CM/VRP_Test.xls"));

		HSSFWorkbook workbook = new HSSFWorkbook(file);

		HSSFSheet sheet = workbook.getSheetAt(0);
		int end = sheet.getLastRowNum();

		ArrayList<Vrp> vrpList = new ArrayList<>();

		for (int i = 9; i <= end; i++) {

			Vrp vrpObj = new Vrp();

			Cell c = workbook.getSheetAt(0).getRow(i).getCell(1);
			vrpObj.setSpend(Double.parseDouble(c.getStringCellValue()));

			Cell c1 = workbook.getSheetAt(0).getRow(i).getCell(15);
			vrpObj.setBasePrice(Double.parseDouble(c1.getStringCellValue()));

			Cell c2 = workbook.getSheetAt(0).getRow(i).getCell(16);
			vrpObj.setCustomerPrice(Double.parseDouble(c2.getStringCellValue()));

			Cell c3 = workbook.getSheetAt(0).getRow(i).getCell(17);
			vrpObj.setVendorPrice(c3.getNumericCellValue());

			vrpList.add(vrpObj);

			droolsDaoImpl.insertVrp(vrpObj);
		}

		file.close();

		List<Vrp> VrpGetList = new ArrayList<Vrp>();

		VrpGetList = droolsDaoImpl.getVrp();

		for (Vrp vrpObj : VrpGetList) {
			System.out.println(vrpObj.getVendorPrice());

		}
		ObjectDataCompiler dataCompiler = new ObjectDataCompiler();

		final String drl = dataCompiler.compile(VrpGetList, getRulesStream());
		KieServices kieServices = KieServices.Factory.get();
		KieFileSystem kfs = kieServices.newKieFileSystem();
		kfs.write("src/main/resources/samp.drl", kieServices.getResources().newReaderResource(new StringReader(drl)));

		KieBuilder kieBuilder = kieServices.newKieBuilder(kfs).buildAll();
		KieModule kieModule = kieBuilder.getKieModule();
		KieContainer kContainer = kieServices.newKieContainer(kieModule.getReleaseId());
		KieSession kieSession = kContainer.newKieSession();

		Vrp vrpTest = new Vrp(2000.0);

		kieSession.insert(vrpTest);

		kieSession.fireAllRules();

		kieSession.destroy();
		kieSession.dispose();

	}

	private static InputStream getRulesStream() throws FileNotFoundException {
		return new FileInputStream("/home/sys-user/Backup/CM/VrpRules.drt");
	}
}
