package com.Backend_Code;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/FetchData")
public class FetchData extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
			response.setContentType("text/html");
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/spottabl","root","12345");
			
			PreparedStatement ps=conn.prepareStatement("SELECT DISTINCT clientcode from clientuserinvites ;");
			PreparedStatement ps1=conn.prepareStatement("SELECT COUNT(email) FROM  `registrations` WHERE email LIKE '%flexmoney.in%' ;");
			PreparedStatement ps2=conn.prepareStatement("SELECT COUNT(`inviter`) FROM  `clientuserinvites` WHERE inviter LIKE '%spottabl.com%' AND  email LIKE '%flexmoney.in%' ;");
			PreparedStatement ps3=conn.prepareStatement("SELECT COUNT(`accepted`) FROM  `clientuserinvites` WHERE (accepted = 'TRUE' AND  email LIKE '%flexmoney.in%') ;");
			
			ResultSet rs=ps.executeQuery();
			ResultSet rs1=ps1.executeQuery();
			ResultSet rs2=ps2.executeQuery();
			ResultSet rs3=ps3.executeQuery();
			
			PrintWriter out=response.getWriter();
			
			out.println("<html><body><table border='1'style=\"border-collapse: collapse;width: 800px;\"><tr style=\"color: blue;\"><td>ClientCode</td><td>Number of users on spottabl</td><td>Number of users invited from spottabl</td><td>Number of users who have accepted invite</td><td style=\"color: orange;\">Number of users invited from spottabl user</td></tr>");
			
			while(rs.next()&&rs1.next()&&rs2.next()&& rs3.next()) {
							
				out.println("<tr><td>"+rs.getString(1)+"</td><td>"+rs1.getString(1)+"</td><td>"+rs2.getString(1)+"</td><td>"+rs3.getString(1)+"</td><td>"+rs2.getString(1)+"</td></tr>");
				
			}
			
			out.println("</table></body></html>");
		}
		
		catch(Exception e) {
		
			e.printStackTrace();
		}
	}
}
