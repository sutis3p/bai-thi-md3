package controller;

import model.Staff;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class StaffControllerImpl implements StaffContrller {

    private String jdbcURL = "jdbc:mysql://localhost:3306/demo?useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "0398878422suti?";
    private static final String INSERT_STAFF = "INSERT INTO staff (name,email,address,phoneNumber,salary,department) VALUES (?, ?, ?, ?, ?, ?);";
    private static final String SELECT_STAFF_BY_ID = "select id,name,email,address,phoneNumber,salary,department from users where id =?";
    private static final String SELECT_ALL_STAFF = "select * from staff";
    private static final String DELETE_STAFF = "delete from staff where id = ?;";
    private static final String UPDATE_STAFF = "update users set name = ?,email= ?, address =?, phoneNumber= ?, salary=?, department =? where id = ?;";

    public StaffControllerImpl() {
    }

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    @Override
    public ArrayList<Staff> showAll() {
        ArrayList<Staff> staff = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_STAFF)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Staff staff1 = getStaff(resultSet);
                staff.add(staff1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return staff;
    }

    private Staff getStaff(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String email = resultSet.getString("email");
        String address = resultSet.getString("address");
        String phoneNumber = resultSet.getString("phoneNumber");
        double salary = resultSet.getDouble("salary");
        int department = resultSet.getInt("department");
        Staff staff = new Staff(id, name, email, address, phoneNumber, salary, department);
        return staff;
    }

    @Override
    public void create(Staff staff) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_STAFF)) {
            preparedStatement.setString(1, staff.getName());
            preparedStatement.setString(2, staff.getEmail());
            preparedStatement.setString(3, staff.getAddress());
            preparedStatement.setString(4, staff.getPhoneNumber());
            preparedStatement.setDouble(5, staff.getSalary());
            preparedStatement.setInt(6, staff.getDepartment());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Staff showByIndex(int index) {
        Staff staff = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_STAFF_BY_ID)) {
            preparedStatement.setInt(1, index);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            staff = getStaff(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return staff;
    }

    @Override
    public void update(Staff staff) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_STAFF)) {
            preparedStatement.setInt(1, staff.getId());
            preparedStatement.setString(2, staff.getName());
            preparedStatement.setString(3, staff.getEmail());
            preparedStatement.setString(4, staff.getAddress());
            preparedStatement.setString(5, staff.getPhoneNumber());
            preparedStatement.setDouble(6, staff.getSalary());
            preparedStatement.setInt(7, staff.getDepartment());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int index) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_STAFF)) {
            preparedStatement.setInt(1, index);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
