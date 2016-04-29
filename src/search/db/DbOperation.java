package search.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbOperation
{
    private static Connection conn = DbConnector.getconnection();
    
    public static void insert(String sql) throws SQLException
    {
       Statement statement = conn.createStatement();
       
       int num = statement.executeUpdate(sql);
       
       System.out.println(num + "rows records have been influence");
       
    }
    
    public static void update(String sql) throws SQLException
    {
    	PreparedStatement preparedStatement = conn.prepareStatement(sql);
    	
    	int i = preparedStatement.executeUpdate();
    	
    	System.out.println("result :"+ i);
    	
    }
    
    public static String select(String sql,String param) throws SQLException
    {
    	String url=null;
    	
        Statement statement = conn.createStatement();
        
        ResultSet resultSet = statement.executeQuery(sql);
        
        while(resultSet.next())
        {
		    url = resultSet.getString(param);
            
        }
        return url;
    }
    
    public static void delete(String sql) throws SQLException
    {
    	PreparedStatement preparedStatement = conn.prepareStatement(sql);
    	
    	int i = preparedStatement.executeUpdate();
    	
    	System.out.println("result"+i);
  
    }
}
