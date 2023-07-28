package dbtest.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SelectMain {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url ="jdbc:oracle:thin:@localhost:1521:xe";
	private String username ="c##java";
	private String password = "1234";
	
	public SelectMain() {
		try {
			Class.forName(driver);
			System.out.println("드라이버 로딩 성공");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
	}
	
	public void getConnection() {
		try {
			conn = DriverManager.getConnection(url,username,password);
			System.out.println("접속성공");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void selectArticle() {
		this.getConnection(); // 접속
		String sql = "select * from dbtest";
		
		try {
			pstmt = conn.prepareStatement(sql); // 오라클 문장을 번역해줄 통역가 임. pstmt
			rs = pstmt.executeQuery(); //실행 - ResultSet 리턴
			
			//rs.next() - 현재 위치의 레코드가 있으면 true, 없으면 false ==> for 이 아니라 while써줘야함.
			// 			- 다음 레코드로 이동
			// rs.getString("name") / rs.getString(1)
			// rs.getInt("age") / rs.getInt(2)
			// rs.getDouble("age") / rs.getDouble(3)
			// rs.getDate("logtime")  또는 rs.getString("logtime") 스트링타입으로 꺼내와라도 된다./ rs.getDate(4)
			while(rs.next()) { // 레코드 없을때 까지 반복
				System.out.println(rs.getString("name")+"\t"
						+ rs.getInt("age")+"\t"
						+ rs.getDouble("height")+"\t"
						+ rs.getString("logtime"));
			}//while
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null) pstmt.close(); // 열어놓은거 안닫으면 메모리에 계속쌓인다. 무조건 닫아야.
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	} 
	
	public static void main(String[] args) {
		SelectMain sm = new SelectMain();
		sm.selectArticle();
				
		
	}
}
