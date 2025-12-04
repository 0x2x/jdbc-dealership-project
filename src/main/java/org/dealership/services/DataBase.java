package org.dealership.services;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

public class DataBase {
    private static Connection connection;
    private static DataSource ds;
    public static boolean running = false;

    private String host;
    private int port;
    private String database;
    private String user;
    private String password;

    // constructors
    public DataBase(String host, int port, String database, String user, String password) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.user = user;
        this.password = password;
        establish();
    }



    // methods

    public Object initalize(String SQL, Object... Args) {
        // this function is meant to run at startup; Such as creating tables
        return Execute(SQL, Args);
    }

    public Connection getConnection() {
        return connection;
    }

    public DataSource getDataSource() {
        return ds;
    }

    // custom methods

    private List<Map<String, Object>> SetToList(ResultSet rs) throws SQLException {
        List<Map<String, Object>> rows = new ArrayList<>();
        ResultSetMetaData meta = rs.getMetaData();
        int columnCount = meta.getColumnCount();
        while(rs.next()) {
            Map<String, Object> row = new LinkedHashMap<>();
            for (int i = 1; i <= columnCount ; i++) {
                String colName = meta.getColumnName(i);
                Object value = rs.getObject(i);
                row.put(colName, value);
            }
            rows.add(row);
        }
        return rows;
    }
    public boolean establish() {
        if (!running) {
            BasicDataSource dataSource = new BasicDataSource();
            dataSource.setUsername(this.user);
            dataSource.setPassword(this.password);
            dataSource.setUrl("jdbc:mysql://" + this.host + ":" + this.port + "/"+ this.database);

            try(Connection cn = dataSource.getConnection()) {
                System.out.println("Connected to Database");
                connection = cn;
                ds = dataSource;
                running = true;
            }catch (SQLException e) {
                System.out.println(e);
            }
            return true;
        }
        return false;
    }
    public Object Execute(String SQL, Object... Args) {
        DataSource dataSource = getDataSource();
        try(Connection conn = dataSource.getConnection();
            PreparedStatement statement = conn.prepareStatement(SQL)
        ) {
            // allowing args
            for (int i = 0; i < Args.length; i++) {
                statement.setObject(i+ 1, Args[i]);
            }

            boolean hasResultSet = statement.execute();
            statement.closeOnCompletion();

            if (hasResultSet) {
                try(ResultSet rs = statement.getResultSet()) {
                    return SetToList(rs);
                }
            } else {
                return statement.getUpdateCount();
            }
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }
    public ArrayList<HashMap<String, String>> fetchAll(String table) {
        /**
         *Grab all rows from specific mentioned Table:
         *   database db = new database("localhost", 3306, "DB", "USER", "PASS");
         *   db.establish();
         *   db.fetchAll(db.getDataSource(), "table");
         **/
        DataSource dataSource = getDataSource();
        // query
        String query = "SELECT * FROM " + table;
        ArrayList<HashMap<String, String>> columns = new ArrayList<>();
        try(Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            //execute
            preparedStatement.execute();
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                // process
                while(resultSet.next()) {
                    ResultSetMetaData metaData = resultSet.getMetaData();
                    int columnCount = metaData.getColumnCount();
                    for (int i = 1; i <= columnCount; i++) {
                        HashMap<String, String> Pairs = new HashMap<>();

                        String columnName = metaData.getColumnName(i);
                        String value = resultSet.getString(i);
                        Pairs.put("Key", columnName);
                        Pairs.put("Value", value);
                        columns.add(Pairs);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error occured: " + e);
            return columns;
        }
        return columns;
    }
    public boolean Update(String tableName, String column, String value, String WhereCondition) {
        String query = "UPDATE %s SET %s = %s WHERE %s".formatted(tableName, column, value, WhereCondition);
        Execute( query);
        return true;

    }
    public boolean Insert(String[] ColumnNames, Object[] Values, String TableName) {
        if(ColumnNames.length != Values.length) {
            throw new IllegalArgumentException("Length of Columns and Values must match");
        }

        for( String col : ColumnNames) {
            if(!col.matches("[A-Za-z0-9_]+"))
                throw new IllegalArgumentException("Invalid column name: " + col);
        }

        if (!TableName.matches("[A-Za-z0-9_]+"))
            throw new IllegalArgumentException("Invalid table name: " + TableName);

        String columns = String.join(", ", ColumnNames);
        String placeholders = String.join(", ", Values.toString());

        String query = "INSERT INTO " + TableName +
                " (" + columns + ") VALUES (" + placeholders + ");";

        Execute(query);
        return true;
    }

    public Object InsertOne(String tableName, String columnName, Object Value) {
        String query = "INSERT INTO %s(%s) VALUES (?)".formatted(tableName, columnName);
        Object exec = Execute(query, Value);
        return exec;
    }

    public Object UpdateOne(String table, String setColumn, Object setValue, String whereColumn, Object whereValue) {
        String query = "UPDATE %s SET %s = ? WHERE %s = ?".formatted(table, setColumn, whereColumn);
        return Execute(query, setValue, whereValue);
    }

    public Object DeleteAll(String tableName) {
        String query = "DELETE FROM %s".formatted(tableName);
        return Execute(query);
    }

    public Object DeleteWhere(String tableName, String key, String Value) {
        String query = "DELETE FROM %s WHERE %s = ?".formatted(tableName, key);
        return Execute(query, Value);
    }


}
