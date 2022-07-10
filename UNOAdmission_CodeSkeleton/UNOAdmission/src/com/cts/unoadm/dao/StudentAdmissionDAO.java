package com.cts.unoadm.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.cts.unoadm.exception.StudentAdmissionException;
import com.cts.unoadm.util.ApplicationUtil;
import com.cts.unoadm.util.DBConnectionManager;
import com.cts.unoadm.vo.StudentAdmission;

public class StudentAdmissionDAO {
	
	public boolean addStudentAdmissionDetails(List<StudentAdmission> stdAdmissions) throws StudentAdmissionException {
		boolean recordsAdded = false;
		
		//code here
		Connection con = null;
		try {
			//System.out.println("reached here at dao");
			 con = DBConnectionManager.getInstance().getConnection();
			for(int i =0;i<stdAdmissions.size();i++)
			{
				//System.out.println("reached here at dao1");
				PreparedStatement pst =con.prepareStatement("INSERT INTO students VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?);");
				pst.setString(1,stdAdmissions.get(i).getAdmissionId());
				pst.setString(2,stdAdmissions.get(i).getStudentCode());
				pst.setDate(3,ApplicationUtil.convertUtilToSqlDate(stdAdmissions.get(i).getDateOfCounseling()));
				pst.setString(4,stdAdmissions.get(i).getDepartmentName());
				pst.setDate(5,ApplicationUtil.convertUtilToSqlDate(stdAdmissions.get(i).getDateOfAdmission()));
				pst.setString(6,stdAdmissions.get(i).getPreferCollegeHostel());
				pst.setString(7,stdAdmissions.get(i).getFirstGraduate());
				pst.setString(8,stdAdmissions.get(i).getManagerApproval());
				pst.setDouble(9,stdAdmissions.get(i).getAdmissionFee());
				pst.setDouble(10,stdAdmissions.get(i).getTuitionFee());
				pst.setDouble(11,stdAdmissions.get(i).getHostelFee());
				pst.setDouble(12,stdAdmissions.get(i).getTotalCollegeFee());
				pst.setString(13,stdAdmissions.get(i).getFinalStatusOfAdmission());
				
				pst.executeUpdate();				
			}
			recordsAdded = true;
			
		}catch(SQLException e)
		{
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				throw new StudentAdmissionException("Exception in RollBack",e.getCause());
			}
			throw new StudentAdmissionException(e.getMessage(),e.getCause());
		}
		
		
		
		return recordsAdded; 
	}

	public List<StudentAdmission> getAllStudentAdmissionDetails() throws StudentAdmissionException {
		
		List<StudentAdmission> stdAdmissions = new ArrayList<StudentAdmission>();

		//code here
		Connection con = DBConnectionManager.getInstance().getConnection();
		try {
		Statement st = con.createStatement();
			String querry = "SELECT * FROM students";
			ResultSet rst = st.executeQuery(querry);
			while(rst.next())
			{
				String admission = rst.getString(1);
				String studentCode = rst.getString(2);
				Date dc = rst.getDate(3);
				String dept = rst.getString(4);
				Date da = rst.getDate(5);
				String prefClgHstl = rst.getString(6);
				String firstGraduate = rst.getString(7);
				String mngrAproo = rst.getString(8);
				double admFee = rst.getDouble(9);
				double tutionFee = rst.getDouble(10);
				double hostelFee = rst.getDouble(11);
				double totalClgFee = rst.getDouble(12);
				String finalAdmStatus = rst.getString(13);
				
				StudentAdmission stdAdm = new StudentAdmission(admission,studentCode,dc,dept,da,prefClgHstl,firstGraduate,mngrAproo,admFee,tutionFee,hostelFee,totalClgFee,finalAdmStatus);
				stdAdmissions.add(stdAdm);
				
			}
		}catch(SQLException e)
		{
			throw new StudentAdmissionException(e.getMessage(), e.getCause());
		}
		
				
		return stdAdmissions;

	}
}