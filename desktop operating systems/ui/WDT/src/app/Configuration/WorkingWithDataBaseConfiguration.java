package app.Configuration;

import app.Common.Options;
import com.WDTComponents.DataBase.IWorkingWithDataBase;

import java.sql.*;
import java.util.ArrayList;

public class WorkingWithDataBaseConfiguration implements IWorkingWithDataBase {

    private Connection connection;
    private Statement statement;

    @Override
    public boolean createDataBase() {
        try {
            connection = DriverManager.getConnection(
                    "jdbc:sqlite:"+ Options.INSTANCE.getCOMMON_DIRECTORY() + "/" +
                            Options.APPLICATION_TITLE + "_Database.db"
            );
            statement = connection.createStatement();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public synchronized boolean executeQuery(String SQLQuery) {
        try {
            statement.executeUpdate(SQLQuery);
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public synchronized ArrayList<String[]> executeRowQuery(String SQLQuery) {
        ArrayList<String[]> arrayList = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery(SQLQuery);
            while (resultSet.next()) {
                String[] tempArrayString = new String[resultSet.getMetaData().getColumnCount()];
                for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++)
                    tempArrayString[i - 1] = resultSet.getString(i);
                arrayList.add(tempArrayString);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return arrayList;
    }

    @Override
    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}