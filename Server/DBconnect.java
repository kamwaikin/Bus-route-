import java.sql.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author AsusPC
 */
public class DBconnect {
         Connection connection = null;
         private PreparedStatement ViewAllRouteNo = null;
         private PreparedStatement ViewNumberOfRoute = null;
         private PreparedStatement ViewRouteFare = null;
         private PreparedStatement abc = null;
         //private PreparedStatement statement = null;
         
    public void connect() {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            System.out.println("JDBC driver loaded");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        try {
            String dbUrl = "jdbc:oracle:thin:@localhost:1521:XE";
	    String username = "HR";
	    String password = "password";
            connection = DriverManager.getConnection(dbUrl, username, password);
            System.out.println("Connection successful!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
        public void disconnect() {
        try {
            if (connection != null){
                connection.close();
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
        
        public ResultSet viewAllRouteNo() {
        ResultSet rs = null;
        try {
            String queryString = "select routeNo from Routes";
            ViewAllRouteNo = connection.prepareStatement(queryString);
            rs = ViewAllRouteNo.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }
        
        public ResultSet viewRouteFare(String a) {
        ResultSet rs = null;
        String queryString=null;
        try {
            /*switch(a){
                case "1":queryString = "select fares from Routes where routeno = '1'";break;
                case "1A":queryString = "select fares from Routes where routeno = '1A'";break;
                case "2":queryString = "select fares from Routes where routeno = '2'";break;
                case "5":queryString = "select fares from Routes where routeno = '5'";break;
                case "5A":queryString = "select fares from Routes where routeno = '5A'";break;
                case "5C":queryString = "select fares from Routes where routeno = '5C'";break;
                case "6":queryString = "select fares from Routes where routeno = '6'";break;
                case "7":queryString = "select fares from Routes where routeno = '7'";break;
                case "8":queryString = "select fares from Routes where routeno = '8'";break;
                case "8A":queryString = "select fares from Routes where routeno = '8A'";break;
                case "8P":queryString = "select fares from Routes where routeno = '8P'";break;
                case "9":queryString = "select fares from Routes where routeno = '9'";break;
                case "28":queryString = "select fares from Routes where routeno = '28'";break;
                case "N21":queryString = "select fares from Routes where routeno = 'N21'";break;
                case "N21A":queryString = "select fares from Routes where routeno = 'N21A'";break;
            }*/
            PreparedStatement statement = connection.prepareStatement("select fares from Routes where routeno = ?");
            statement.setString(1, a); 
            rs = statement.executeQuery();
            //System.out.println("QueryString is " + queryString);
            //abc = connection.prepareStatement(statement);
            //rs = abc.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }
}

