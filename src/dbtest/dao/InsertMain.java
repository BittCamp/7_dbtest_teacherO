package dbtest.dao;//[ 김찬영  2023-07-28 오전 11:16:19 ]

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class InsertMain {
	private Connection conn;
	private PreparedStatement  pstmt;
	
	//자르는 과정. 환경변수로 빼버리는 과정을 할거라
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url ="jdbc:oracle:thin:@localhost:1521:xe";
	private String username ="c##java";
	private String password = "1234";

	public InsertMain() {	// 생성자 . 딱 1번밖에 수행안함.
		try {
			Class.forName(driver);
			System.out.println("드라이버 로딩 성공");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} // 앞에 패키지명까지 완벽하게 써라.
	}
	
	public void getConnection() {
		try { //오라클은 1521 // 지포스 엔비디아 그래픽카드 드라이버명. 오라클은 thin으로 쓴다.
			conn = DriverManager.getConnection(url,username,password);
			//mysql 밑에꺼 Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/boarddb", "root", "12341234!");
			System.out.println("접속 성공");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void insertArticle() {
		Scanner scan = new Scanner(System.in);
		System.out.println("이름 입력 :");
		String name = scan.next();
		System.out.println("나이 입력 :");
		int age = scan.nextInt();
		System.out.println("키 입력 :");
		double height = scan.nextDouble();
		//PreparedStatement얘는 인터페이스라 생성이 안된다. 쟤를 생성할수있는애는 Connection 안에 있다. Connection안에 prepareStatement (~~)에 있다.
		try {																		//?,?,?, 1 2 3 순이다 0부터 아님. 자바는 시작이 0 디비는 1
			
			this.getConnection(); // this. 기본생성자 호출
			
			pstmt = conn.prepareStatement("insert into dbtest values (?,?,?,sysdate)"); //생성
			
			//?에 데이터 대입
			pstmt.setString(1, name);
			pstmt.setInt(2, age);
			pstmt.setDouble(3, height);
			
			//실행
			int su = pstmt.executeUpdate(); // 개수 리턴
			
			System.out.println(su + "개의 행이 삽입되었습니다.");
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally { // 에러가있든 없든 무조건 수행해라.
			try {
				if(pstmt != null) pstmt.close(); // 열어놓은거 안닫으면 메모리에 계속쌓인다. 무조건 닫아야.
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
	}
	public static void main(String[] args) {
		InsertMain im = new InsertMain();
		im.insertArticle();
	}
}
