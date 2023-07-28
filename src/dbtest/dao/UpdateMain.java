package dbtest.dao;//[ 김찬영  2023-07-28 오전 11:42:23 ]

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

//과제
public class UpdateMain {
	private Connection conn;
	private PreparedStatement  pstmt;
	
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url ="jdbc:oracle:thin:@localhost:1521:xe";
	private String username ="c##java";
	private String password = "1234";
	
	
	public UpdateMain() {
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
	public void updateArticle() {
		Scanner scan = new Scanner(System.in);
		System.out.println("수정할 이름 입력 :");
		String name = scan.next();
		String sql = "update dbtest set age=(age+1) where name like ?";
	
		try {
			this.getConnection();
			pstmt = conn.prepareStatement(sql);
			// ?에 데이터 대입
			pstmt.setString(1, "%"+name+"%");
			int su = pstmt.executeUpdate(); // 개수가 리턴됨.
			System.out.println(su + "개의 행이 수정 되었습니다.");
			// 커밋안하면 접속까지만 되고 디비락이 걸린다.
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
		UpdateMain um = new UpdateMain();
		um.updateArticle();
	}
}

// 드라이버 로딩
//커넥션
//수정할 이름 입력 : 홍 
// ===>홍이 들어간 이름을 찾아서 나이를 1증가 시키시오. update
