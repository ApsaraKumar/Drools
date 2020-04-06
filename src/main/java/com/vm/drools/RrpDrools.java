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

public class RrpDrools {
	public static void main(String args[]) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
		DroolsDaoImpl rrpDaoImpl = (DroolsDaoImpl) context.getBean("droolsDaoImpl");

		try {
			readExcel(rrpDaoImpl);

		} catch (

		Exception e) {
			e.printStackTrace();
		}

	}

	private static void readExcel(DroolsDaoImpl rrpDaoImpl) throws FileNotFoundException, IOException {
		FileInputStream file = new FileInputStream(new File("/home/sys-user/CM/newWorkspace/Drools/src/main/resources/rules/RRP_Test.xls"));

		HSSFWorkbook workbook = new HSSFWorkbook(file);

		HSSFSheet sheet = workbook.getSheetAt(0);
		int end = sheet.getLastRowNum();

		ArrayList<Rrp> rrpList = new ArrayList<>();

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

			rrpList.add(rrpObj);

			rrpDaoImpl.insertRrp(rrpObj);
		}

		file.close();

		List<Rrp> RrpGetList = new ArrayList<Rrp>();

		RrpGetList = rrpDaoImpl.getAll();

		/*
		 * for (Rrp rrpObj : RrpGetList) {
		 * System.out.println(rrpObj.getCustomerLocations());
		 * 
		 * }
		 */
		ObjectDataCompiler dataCompiler = new ObjectDataCompiler();

		final String drl = dataCompiler.compile(RrpGetList, getRulesStream());
		KieServices kieServices = KieServices.Factory.get();
		KieFileSystem kfs = kieServices.newKieFileSystem();
		kfs.write("src/main/resources/samp.drl", kieServices.getResources().newReaderResource(new StringReader(drl)));

		KieBuilder kieBuilder = kieServices.newKieBuilder(kfs).buildAll();
		KieModule kieModule = kieBuilder.getKieModule();
		KieContainer kContainer = kieServices.newKieContainer(kieModule.getReleaseId());
		KieSession kieSession = kContainer.newKieSession();

		Rrp rrpTest = new Rrp();

		kieSession.insert(rrpTest);
		kieSession.fireAllRules();
		System.out.println( rrpTest.getComment());

		kieSession.destroy();
		kieSession.dispose();

	}

	private static InputStream getRulesStream() throws FileNotFoundException {
		return new FileInputStream("/home/sys-user/CM/newWorkspace/Drools/src/main/resources/rules/RrpRules.drt");
	}
}	
