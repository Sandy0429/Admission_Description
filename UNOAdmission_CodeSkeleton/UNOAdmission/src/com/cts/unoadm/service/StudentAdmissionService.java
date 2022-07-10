package com.cts.unoadm.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cts.unoadm.exception.StudentAdmissionException;
import com.cts.unoadm.util.ApplicationUtil;
import com.cts.unoadm.vo.StudentAdmission;
import com.cts.unoadm.dao.StudentAdmissionDAO;

import java.io.*;
public class StudentAdmissionService {
	
	/**
	 * @return List<StudentAdmission>
	 */
	public static List<StudentAdmission> buildStudentAdmissionsList(List<String> studentAdmissionRecords)throws Exception {
		List<StudentAdmission> studentAdmissionList = new ArrayList<StudentAdmission>();
		
		//Code here
		for(int i=0; i<studentAdmissionRecords.size(); i++)
		{
			String a [] = studentAdmissionRecords.get(i).split(",");
			String admissionId = a[0];
			String studentCode = a[1];
			Date d1 = ApplicationUtil.convertStringToDate(a[2].trim());
			String depName = a[3];
			Date d2 = ApplicationUtil.convertStringToDate(a[4].trim());
			String prefClgHstl = a[5];
			String firstG = a[6];
			String managApp = a[7];
			double[] fees = calculateTotalCollegeFee(prefClgHstl,firstG,depName);
			double admissionFee = fees[0];
			double tutionFee = fees[1];
			double hostelFee = fees[2];
			double totalCollegeFee = fees[3];
			String finalStatusOfAdmission = "AdmissionSuccessfull";
			
			StudentAdmission stdAdm = new StudentAdmission(admissionId,studentCode,d1,depName,d2,prefClgHstl,firstG,managApp,admissionFee,tutionFee,hostelFee,totalCollegeFee,finalStatusOfAdmission);
			studentAdmissionList.add(stdAdm);						
		}			
		
		
		return studentAdmissionList;
	}


	public boolean addStudentAdmissionDetails(String inputFeed) throws StudentAdmissionException, IOException {
		//text file name is send here|| added IOException
		//Code here
		
		try {
			List<String> parsedRecords = ApplicationUtil.readFile(inputFeed);
			List<StudentAdmission> studentAdmissionRecords = StudentAdmissionService.buildStudentAdmissionsList(parsedRecords);
			
			StudentAdmissionDAO dao = new StudentAdmissionDAO();
			return dao.addStudentAdmissionDetails(studentAdmissionRecords);			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (StudentAdmissionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}

	public static double[] calculateTotalCollegeFee(String preferCollegeHostel, String firstGraduate, String departmentName) {
		double[] studentAdmissionCosts = new double[4];
		
		//Code here..
		studentAdmissionCosts[0] = 30000d; //admission fee
		studentAdmissionCosts[1] = 0d; //for tuition fee
		studentAdmissionCosts[2] = 0d; // hostel fee
		studentAdmissionCosts[3] = 0d; //total college fee if graduate the -20000
		
			//System.out.println(departmentName);
		if(departmentName.equalsIgnoreCase("EEE") || departmentName.equalsIgnoreCase("CSE") || departmentName.equalsIgnoreCase("IT") )
		{
			studentAdmissionCosts[1] = 45000d;
		}
		else if (departmentName.equalsIgnoreCase("ECE") || departmentName.equalsIgnoreCase("CIVIL"))
		{
			studentAdmissionCosts[1] = 50000d;
		}
		else if(departmentName.equalsIgnoreCase("MECH"))
		{
			studentAdmissionCosts[1] = 55000d;
		}
		
		if(preferCollegeHostel.equalsIgnoreCase("YES"))
		{
			studentAdmissionCosts[2] = 75000d;
		}
		
		if(firstGraduate.equalsIgnoreCase("YES"))
		{
			studentAdmissionCosts[3] = studentAdmissionCosts[0] + studentAdmissionCosts[1]+studentAdmissionCosts[2] - 20000d;
		}
		else 
		{
			studentAdmissionCosts[3] = studentAdmissionCosts[0] + studentAdmissionCosts[1]+studentAdmissionCosts[2] ;
		}
			
		
		
		return studentAdmissionCosts;
	}

	public boolean searchStudentAdmission(String admissionId) throws StudentAdmissionException {
		boolean status = false;
		
		//Code here..
		List<StudentAdmission> admList = new  StudentAdmissionDAO().getAllStudentAdmissionDetails();
		
		for(int i =0 ;i< admList.size();i++)
		{
			if(admList.get(i).getAdmissionId().equalsIgnoreCase(admissionId))
			{
				status =true;
				System.out.println(admList.get(i).toString());
			}
		}
		
		
		return status;
	}
}
