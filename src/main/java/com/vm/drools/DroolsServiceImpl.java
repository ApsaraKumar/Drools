package com.vm.drools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.List;

import org.apache.log4j.Logger;
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
import org.springframework.stereotype.Service;

@Service
public class DroolsServiceImpl {

	final Logger log = Logger.getLogger(DroolsServiceImpl.class.getName());

	public void checkFlow(String flowType, DroolsDaoImpl droolsDaoImpl) throws FileNotFoundException, IOException {

		if (flowType == "vrp") {
			readExcelVrp(droolsDaoImpl);
		}
		if (flowType == "rrp") {
			readExcelRrp(droolsDaoImpl);
		}
	}

	public void readExcelVrp(DroolsDaoImpl droolsDaoImpl) throws FileNotFoundException, IOException {
		
		log.info("The chosen flow is Vrp sheet processing");
		
		FileInputStream file = new FileInputStream(
				new File("/home/sys-user/CM/newWorkspace/Drools/src/main/resources/rules/VRP_Test.xls"));
		log.info("Parsing the data from VRP_Test.xls and inserting into mongo collection:vrp");
		HSSFWorkbook workbook = new HSSFWorkbook(file);

		HSSFSheet sheet = workbook.getSheetAt(0);
		int end = sheet.getLastRowNum();

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

			Cell c4 = workbook.getSheetAt(0).getRow(i).getCell(2);
			vrpObj.setOnSite(c4.getStringCellValue());

			droolsDaoImpl.insertVrp(vrpObj);
		}
		log.info("Retrieving the data from vrp collection");
		List<Vrp> VrpGetList = droolsDaoImpl.getVrp();

		droolsImpl(VrpGetList);

	}

	public void readExcelRrp(DroolsDaoImpl droolsDaoImpl) throws FileNotFoundException, IOException {
		
		log.info("The chosen flow is Rrp sheet processing");
		FileInputStream file = new FileInputStream(
				new File("/home/sys-user/CM/newWorkspace/Drools/src/main/resources/rules/RRP_Test.xls"));
        log.info("Parsing the data from RRP_Test.xls and inserting into mongo collection:rrp");
		HSSFWorkbook workbook = new HSSFWorkbook(file);

		HSSFSheet sheet = workbook.getSheetAt(0);
		int end = sheet.getLastRowNum();

		for (int i = 10; i <= end; i++) {

			Rrp rrpObj = new Rrp();

			Cell c = workbook.getSheetAt(0).getRow(i).getCell(1);
			rrpObj.setOnSite(c.getBooleanCellValue());

			Cell c2 = workbook.getSheetAt(0).getRow(i).getCell(2);
			rrpObj.setTaxId(c2.getStringCellValue());

			Cell c3 = workbook.getSheetAt(0).getRow(i).getCell(3);
			rrpObj.setCustomerLocations(c3.getStringCellValue());

			Cell c4 = workbook.getSheetAt(0).getRow(i).getCell(4);
			rrpObj.setCustomerDepartments(c4.getStringCellValue());

			Cell c5 = workbook.getSheetAt(0).getRow(i).getCell(5);
			rrpObj.setQ1(c5.getBooleanCellValue());

			Cell c6 = workbook.getSheetAt(0).getRow(i).getCell(6);
			rrpObj.setQ2(c6.getBooleanCellValue());

			Cell c7 = workbook.getSheetAt(0).getRow(i).getCell(7);
			rrpObj.setQ3(c7.getBooleanCellValue());

			Cell c8 = workbook.getSheetAt(0).getRow(i).getCell(8);
			rrpObj.setQ4(c8.getBooleanCellValue());

			Cell c9 = workbook.getSheetAt(0).getRow(i).getCell(9);
			rrpObj.setQ5(c9.getBooleanCellValue());

			Cell c10 = workbook.getSheetAt(0).getRow(i).getCell(11);
			rrpObj.setType(c10.getStringCellValue());

			Cell c11 = workbook.getSheetAt(0).getRow(i).getCell(12);
			rrpObj.setDocVerificationReq(c11.getStringCellValue());

			Cell c12 = workbook.getSheetAt(0).getRow(i).getCell(13);
			rrpObj.setRequiredDocumentBundles(c12.getStringCellValue());

			Cell c13 = workbook.getSheetAt(0).getRow(i).getCell(14);
			rrpObj.setOptionalDocumentBUndles(c13.getStringCellValue());

			Cell c14 = workbook.getSheetAt(0).getRow(i).getCell(15);
			rrpObj.setComment(c14.getStringCellValue());

			droolsDaoImpl.insertRrp(rrpObj);
		}

		file.close();
		
		log.info("Retrieving the data from rrp collection");
		List<Rrp> RrpGetList = droolsDaoImpl.getAll();

		droolsImpl(RrpGetList);
	}

	public void droolsImpl(List<?> GetList) throws FileNotFoundException {
		ObjectDataCompiler dataCompiler = new ObjectDataCompiler();
		String drt = "";
		if (GetList.get(0).toString().contains("spend") == true) {
			drt = "vrp";
		} else {
			drt = "rrp";
		}
		log.info("Compiling the drools rules against rule template file");
		final String drl = dataCompiler.compile(GetList, getRulesStream(drt));
		
		log.info("Drools processing using kie api started");
		KieServices kieServices = KieServices.Factory.get();
		KieFileSystem kfs = kieServices.newKieFileSystem();
		kfs.write("src/main/resources/samp.drl", kieServices.getResources().newReaderResource(new StringReader(drl)));

		KieBuilder kieBuilder = kieServices.newKieBuilder(kfs).buildAll();
		KieModule kieModule = kieBuilder.getKieModule();
		KieContainer kContainer = kieServices.newKieContainer(kieModule.getReleaseId());
		KieSession kieSession = kContainer.newKieSession();
		
		log.info("Drools processing completed");
		Rrp rrpTest = new Rrp();
		Vrp vrpTest = new Vrp(2000.0);

		if (drt == "vrp") {
			kieSession.insert(vrpTest);
		} else {
			kieSession.insert(rrpTest);
		}
		kieSession.fireAllRules();
		log.info("Drools fact insertion and firing is completed");
		log.info("The vendor price set by vrp rules condition-action - "+vrpTest.getVendorPrice());
		log.info("The comment set by rrp rules condition-action - "+rrpTest.getComment());
		
		kieSession.destroy();
		kieSession.dispose();

	}

	public InputStream getRulesStream(String drt) throws FileNotFoundException {
		if (drt == "rrp") {
			return new FileInputStream("/home/sys-user/CM/newWorkspace/Drools/src/main/resources/rules/RrpRules.drt");
		} else
			return new FileInputStream("/home/sys-user/CM/newWorkspace/Drools/src/main/resources/rules/VrpRules.drt");
	}
}
