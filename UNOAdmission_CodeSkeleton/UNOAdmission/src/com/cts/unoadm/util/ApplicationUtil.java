package com.cts.unoadm.util;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

//check its usage later neechu vala
import com.cts.unoadm.exception.StudentAdmissionException;

public class ApplicationUtil {

	/**
	 * @param fileName
	 * @return List<String>
	 * @throws Exception 
	 */
	public static List<String> readFile(String fileName) throws IOException ,Exception, StudentAdmissionException {
		List<String> studentAdmissionList = new ArrayList<String>();
		  //Code here..
		
		File f =new File(fileName);
		FileReader fr = new FileReader(f);
		BufferedReader br = new BufferedReader(fr);
		String s ;		
		while((s= br.readLine())!= null)
		{
				String a[] = s.split(",");
				Date d1 = ApplicationUtil.convertStringToDate(a[2].trim());
				Date d2 = ApplicationUtil.convertStringToDate(a[4].trim());
				String manager = a[7].trim();

				if(ApplicationUtil.checkIfValidAdmission(d1, d2, manager))
				{
					studentAdmissionList.add(s);
				}
		}
		
		fr.close();
		return studentAdmissionList;
	}

	/**
	 * @param util
	 *            Date
	 * @return sql Date
	 */
	public static java.sql.Date convertUtilToSqlDate(java.util.Date uDate) {
		
		java.sql.Date sDate = null;
		
		//Code here..		
		sDate =  new java.sql.Date(uDate.getTime()); // this is use to convert util date to sql date
		
		return sDate;
	}

	/**
	 * @param inDate
	 * @return Date
	 */
	public static Date convertStringToDate(String inDate) throws Exception {
		
		//Code here..
		
		Date date=new SimpleDateFormat("yyyy-MM-dd").parse(inDate);
		
		return date;
		//return new Date();//TODO change this return value
	}

	public static boolean checkIfValidAdmission(Date dtOfCounseling, Date dtOfAdmission, String manager) {
		boolean admissionValidity = false;
		
		//Code here..		
		long counseling = dtOfCounseling.getTime();
		long admission = dtOfAdmission.getTime();
		long days = TimeUnit.DAYS.convert(admission - counseling, TimeUnit.MILLISECONDS);
		
		if(days <= 10 && manager.equalsIgnoreCase("approved"))
		{
			admissionValidity = true;
		}
		
		return admissionValidity;
	}
}
