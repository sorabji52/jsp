package music.data;

import java.sql.*;

import music.business.*;

public class UserDB
{
    public static int insert(User user)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        //This method adds a new record to the Users table in the database
        String query = 
                "INSERT INTO User (FirstName, LastName, EmailAddress, CompanyName, " + 
                "Address1, Address2, City, State, Zip, Country, " +
                "CreditCardType, CreditCardNumber, CreditCardExpirationDate) " +
                "VALUES (?, ?, ?, ?, " +
                "?, ?, ?, ?, ?, ?, " +
                "?, ?, ?)";
        try
        {        
            ps = connection.prepareStatement(query);
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getEmailAddress());
            ps.setString(4, user.getCompanyName());
            ps.setString(5, user.getAddress1());
            ps.setString(6, user.getAddress2());
            ps.setString(7, user.getCity());
            ps.setString(8, user.getState());
            ps.setString(9, user.getZip());
            ps.setString(10, user.getCountry());
            ps.setString(11, user.getCreditCardType());
            ps.setString(12, user.getCreditCardNumber());
            ps.setString(13, user.getCreditCardExpirationDate());
            return ps.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return 0;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    public static int update(User user)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        //This method updates the record with a matching email address.
        //It returns a value of 0 if the email address can't be found.
        String query = "UPDATE User SET " +
                "FirstName = ?, " +
                "LastName = ?, " +
                "CompanyName = ?, " +
                "Address1 = ?, " +
                "Address2 = ?, " +
                "City = ?, " +
                "State = ?, " +
                "Zip = ?, " +
                "Country = ?, " +
                "CreditCardType = ?, " +
                "CreditCardNumber = ?, " +
                "CreditCardExpirationDate = ? " +
                "WHERE EmailAddress = ?";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getCompanyName());
            ps.setString(4, user.getAddress1());
            ps.setString(5, user.getAddress2());
            ps.setString(6, user.getCity());
            ps.setString(7, user.getState());
            ps.setString(8, user.getZip());
            ps.setString(9, user.getCountry());
            ps.setString(10, user.getCreditCardType());
            ps.setString(11, user.getCreditCardNumber());
            ps.setString(12, user.getCreditCardExpirationDate());
            ps.setString(13, user.getEmailAddress());

            return ps.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return 0;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    public static boolean emailExists(String emailAddress)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String query = "SELECT EmailAddress FROM User " +
                "WHERE EmailAddress = ?";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setString(1, emailAddress);
            rs = ps.executeQuery();
            return rs.next();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return false;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    public static int selectUserID(User user)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        //This method returns 0 if invoiceID isn't found.
        String query = "SELECT UserID FROM User " +
                "WHERE EmailAddress = ?";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setString(1, user.getEmailAddress());
            rs = ps.executeQuery();
            if (rs.next())
                return rs.getInt("UserID");
            else
                return -1;
        }
        catch (SQLException e){
            e.printStackTrace();
            return -1;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }    

    //This method returns null if a record isn't found.
    public static User selectUser(String emailAddress)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String query = "SELECT * FROM User " +
                       "WHERE EmailAddress = ?";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setString(1, emailAddress);
            rs = ps.executeQuery();
            User user = null;
            if (rs.next())
            {
                user = new User();
                user.setFirstName(rs.getString("FirstName"));
                user.setLastName(rs.getString("LastName"));
                user.setEmailAddress(rs.getString("EmailAddress"));
                user.setCompanyName(rs.getString("CompanyName"));
                user.setAddress1(rs.getString("Address1"));
                user.setAddress2(rs.getString("Address2"));
                user.setCity(rs.getString("City"));
                user.setState(rs.getString("State"));
                user.setZip(rs.getString("Zip"));
                user.setCountry(rs.getString("Country"));
            }
            return user;
        }
        catch (SQLException e){
            e.printStackTrace();
            return null;
        }        
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
}