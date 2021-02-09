package  com.pm717.recribe.beans;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;

public class C {
	//MyBatis�슜 SqlSession
	public static SqlSession sqlSession;
	
	public static ServletContext context;
	
	public static HttpSession session;
}
