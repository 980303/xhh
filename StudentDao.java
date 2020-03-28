package com.cn.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import com.cn.pojo.Student;
import com.cn.pojo.Studentinfo;
import com.cn.pojo.Teacher;

public class StudentDao {
	/** 创建Statement对象 */
	Statement stmt;
	/** 创建结果集 */
	ResultSet rs;

	public Statement getStmt() {
		return stmt;
	}

	/**
	 * 
	 * 建立到eps_management.sql的连接
	 * 
	 * @param con 连接数据库
	 * 
	 */
	public StudentDao(Connection con) {
		try {
			stmt = con.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public int AddStudent(Connection con, Student student) throws SQLException {
		String sql = "insert into student value(?, ?, ?, ?, ?)";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, student.getStudent_id());
		pstmt.setString(2, student.getStu_name());
		pstmt.setString(3, student.getStu_sex());
		pstmt.setString(4, student.getStu_pro());
		pstmt.setString(5, student.getStu_city());
		return pstmt.executeUpdate();

	}

	
	public int AddStudentInfo(Connection con, Studentinfo sinfo) throws SQLException {
		String sql = "insert into stu_info value(?, ?, ?,?, ?)";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, sinfo.getStudent_id());
		pstmt.setString(2, sinfo.getSymptom());
		pstmt.setString(3, sinfo.getCheck());
		pstmt.setBigDecimal(4, sinfo.getTemperature());
		pstmt.setDate(5, (java.sql.Date) sinfo.getDate());
		return pstmt.executeUpdate();
	}

	/** 显示所有学生信息 */
	public void selectAllStudent() throws SQLException {
		String sql = "select * from student s, stu_info s1 where s.student_id = s1.s_id order by s.student_id";
		rs = stmt.executeQuery(sql);
		System.out.println("学生信息");
		System.out.println("学号\t姓名\t性别\t所处省份\t市区\t当日温度\t症状\t填报时间\t\t是否确诊\t");
		display();

	}

	

	public void selectStudentById(Connection con, int student_id) throws SQLException {
		String sql = "select * from student s, stu_info s1 where s.student_id = s1.s_id and s.student_id=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, student_id);
		rs = pstmt.executeQuery();
		display();
	}
	
	public void SelectByDateIdStudent(Connection con, Date date, int student_id) throws SQLException {
		String sql = "select * from student s, stu_info s1 where s.student_id = s1.s_id and s1.date = ? and s.student_id=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setDate(1, (java.sql.Date) date);
		pstmt.setInt(2, student_id);
		rs = pstmt.executeQuery();
		display();
	}

	
	private void display() throws SQLException {
		// TODO Auto-generated method stub
		while (rs.next()) {
			String id = rs.getString("s_id");
			String name = rs.getString("student_name");
			String stu_sex = rs.getString("stu_sex");
			String student_pro = rs.getString("student_pro");
			String student_city = rs.getString("student_city");
			BigDecimal temprature = rs.getBigDecimal("temperature");
			Date date = rs.getDate("date");
			String symptom = rs.getString("symptom");
			String check = rs.getString("check1");
			System.out.print(id + "\t" + name + "\t" + stu_sex + "\t" + student_pro + "\t" + student_city + "\t"
					+ temprature + "\t" + symptom + "\t" + date + "\t" + check + "\n" + "\n");
		}

	}
}
