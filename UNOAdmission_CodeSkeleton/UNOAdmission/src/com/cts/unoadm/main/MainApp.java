package com.cts.unoadm.main;

import com.cts.unoadm.skeletonvalidator.SkeletonValidator;
import com.cts.unoadm.service.StudentAdmissionService;
import java.util.*;

public class MainApp {

	public static void main(String[] args) {
		//Don't delete this code
		//Skeletonvalidaton starts
		new SkeletonValidator();
		//Skeletonvalidation ends

		//Write your code here..
		Scanner sc = new Scanner(System.in);
		StudentAdmissionService sas = new StudentAdmissionService();
		try {
			
			//Yehh ek br bs run krna h
//			if(sas.addStudentAdmissionDetails("E:\\COGNIZANT\\ICT Preparation\\UNOAdmission_CodeSkeleton\\UNOAdmission\\inputFeed.txt"))
//			{
//				System.out.println("Data has been inserted into database");				
//			}
//			else
//			{
//				System.out.println("Database insertion failed");
//			}
			
			System.out.println("Enter a admission Id to search status -");
			String admissionId =sc.nextLine();
			if(sas.searchStudentAdmission(admissionId))
			{
				System.out.println("Student Admission found");
			}
			else
			{
				System.out.println("Student Admission not found");
			}
			
		}catch(Exception e)
		{
			System.out.println(e);
		}
		sc.close();			
		
	}

}
