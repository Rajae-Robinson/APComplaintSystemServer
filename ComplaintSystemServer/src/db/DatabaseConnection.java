/**
 * Authors: Rajae Robinson and Odane Walters
 */
package db;

import model.Advisor;
import model.Complaint;
import model.Query;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnection {

    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/student_services";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "";

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(CONNECTION_URL, DB_USERNAME, DB_PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Query> fetchOutstandingQueriesList() {
        List<Query> queries = new ArrayList<>();
        String query = "SELECT queryID, studentID, details FROM Query WHERE response IS NULL";

        try (Connection connection = getConnection()) {
            if (connection != null) {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Query q = new Query();
                    q.setQueryID(resultSet.getInt("queryID"));
                    q.setStudentID(Integer.parseInt(resultSet.getString("studentID")));
                    q.setDetails(resultSet.getString("details"));
                    queries.add(q);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return queries;
    }
    
    public static List<Query> fetchResolvedQueriesList() {
        List<Query> queries = new ArrayList<>();
        String query = "SELECT queryID, studentID, details FROM Query WHERE response IS NOT NULL";

        try (Connection connection = getConnection()) {
            if (connection != null) {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Query q = new Query();
                    q.setQueryID(resultSet.getInt("queryID"));
                    q.setStudentID(Integer.parseInt(resultSet.getString("studentID")));
                    q.setDetails(resultSet.getString("details"));
                    queries.add(q);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return queries;
    }


    public static List<Complaint> fetchOutstandingComplaintsList() {
        List<Complaint> complaints = new ArrayList<>();
        String query = "SELECT complaintID, studentID, details FROM Complaint WHERE response IS NULL";

        try (Connection connection = getConnection()) {
            if (connection != null) {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Complaint c = new Complaint();
                    c.setComplaintID(resultSet.getInt("complaintID"));
                    c.setStudentID(Integer.parseInt(resultSet.getString("studentID")));
                    c.setDetails(resultSet.getString("details"));
                    complaints.add(c);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return complaints;
    }
    
    public static List<Complaint> fetchResolvedComplaintsList() {
        List<Complaint> complaints = new ArrayList<>();
        String query = "SELECT complaintID, studentID, details FROM Complaint WHERE response IS NOT NULL";

        try (Connection connection = getConnection()) {
            if (connection != null) {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Complaint c = new Complaint();
                    c.setComplaintID(resultSet.getInt("complaintID"));
                    c.setStudentID(Integer.parseInt(resultSet.getString("studentID")));
                    c.setDetails(resultSet.getString("details"));
                    complaints.add(c);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return complaints;
    }

    public static boolean assignAdvisorToQuery(int advisorID, int queryID) {
        String query = "UPDATE Query SET responderID = ? WHERE queryID = ?";

        try (Connection connection = getConnection()) {
            if (connection != null) {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, advisorID);
                preparedStatement.setInt(2, queryID);
                preparedStatement.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean assignAdvisorToComplaint(int advisorID, int complaintID) {
        String query = "UPDATE Complaint SET responderID = ? WHERE complaintID = ?";

        try (Connection connection = getConnection()) {
            if (connection != null) {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, advisorID);
                preparedStatement.setInt(2, complaintID);
                preparedStatement.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return false;
    }

    public static List<Query> fetchQueriesCategoryList() {
        List<Query> queries = new ArrayList<>();
        String query = "SELECT category FROM Query";

        try (Connection connection = getConnection()) {
            if (connection != null) {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Query q = new Query();
                    q.setCategory(resultSet.getString("category"));
                    queries.add(q);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return queries;
    }

    public static List<Complaint> fetchComplaintsCategoryList() {
        List<Complaint> complaints = new ArrayList<>();
        String query = "SELECT category FROM Complaint ";

        try (Connection connection = getConnection()) {
            if (connection != null) {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Complaint c = new Complaint();
                    c.setCategory(resultSet.getString("category"));
                    complaints.add(c);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return complaints;
    }
}


