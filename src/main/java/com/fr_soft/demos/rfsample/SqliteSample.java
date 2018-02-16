package com.fr_soft.demos.rfsample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.File;
//import com.twilio.*;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;


public class SqliteSample
{
	private Connection connection;
	private Statement statement;
	private String url = "jdbc:sqlite:H://ucl-nii-amakro/sample";
	
   // Find your Account Sid and Auth Token at twilio.com/console
    public static final String ACCOUNT_SID = "ACe5b0ea89d95a9095c2f35477de1a0a03";
    public static final String AUTH_TOKEN = "2f353ee3aa5ed7631855301f75617d77";
	public SqliteSample() {
	
	}
	
	public String getDbName(){
		return "H://ucl-nii-amakro/sample";
	}
	
	public String getTableName(){
		return "stock";
	}
	
	public String getPath(){
		return new File(".").getAbsoluteFile().getParent();
	}

public void createTable() throws ClassNotFoundException {
		String sql = "CREATE TABLE IF NOT EXISTS " + this.getTableName()
            + "	  (id          	  INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "   name            TEXT,"
            + "   amount          INTEGER,"
            + "   threshold       INTEGER,"
            + "   updated_at	  TimeStamp DATETIME DEFAULT CURRENT_TIMESTAMP,"
            + "   notified_at	  TimeStamp DATETIME DEFAULT CURRENT_TIMESTAMP"
            +");";
            this.sqlStatement(sql);
}

public void insertDemoData() throws ClassNotFoundException {
		String sql = "insert into "+ this.getTableName() 
	    +"(name, amount, threshold) values ('apple', 10, 11), ('orange', 100, 10), ('mango', 100, 9);";
            this.sqlStatement(sql);
}

  public void sqlStatement(String sql) throws ClassNotFoundException
  {
    Class.forName("org.sqlite.JDBC");
	
    try
    {
      	this.connection = DriverManager.getConnection(this.url);
      	this.statement = this.connection.createStatement();


      this.statement.execute(sql);
    }
    catch(SQLException e)
    {
      System.err.println(e.getMessage());
    }
    finally
    {
      try
      {
        if(this.connection != null)
          this.connection.close();
      }
      catch(SQLException e)
      {
        System.err.println(e);
      }
    }
  }
  
  public void countStock() throws ClassNotFoundException
  {
    Class.forName("org.sqlite.JDBC");
	
    try
    {
      	this.connection = DriverManager.getConnection(this.url);
      	this.statement = this.connection.createStatement();

	    ResultSet rs = this.statement.executeQuery("select * from "+ this.getTableName());
	    while(rs.next()) {
	    	if (this.compareStock(rs.getInt("amount"), rs.getInt("threshold")))
	    	{
	    		System.out.println(rs.getString("name"));
	    	}
	    }
    }
    catch(SQLException e)
    {
      System.err.println(e.getMessage());
    }
    finally
    {
      try
      {
        if(this.connection != null)
          this.connection.close();
      }
      catch(SQLException e)
      {
        System.err.println(e);
      }
    }
  }
  
  public boolean compareStock(int amount, int threshold) {
  	return amount<=threshold;
  }
  
  public boolean sendNotification(String message){
	Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

    Message message = Message
        .creator(new PhoneNumber("+818065071193"),
                 new PhoneNumber("+19165071231"),
                 message)
        .create();

    System.out.println(message.getSid());
   return true:
  }
}