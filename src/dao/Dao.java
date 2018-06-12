package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Dao {

    private static Dao instance = new Dao();
    //    private Context context;
    private Connection conn;
    private Statement statement;

    private Dao() {
//        try {
//            context = new InitialContext();
//            DataSource ds = (DataSource) context.lookup("java:comp/env/jdbc/oracleds");
//            conn = ds.getConnection();
//            statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (NamingException e) {
//            e.printStackTrace();
//        } finally {
//
//        }
    }

    public static Dao getInstance() {
        return instance;
    }

    //根据用户名密码登录
    public void login(String user, String pwd) throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");// 加载Oracle驱动程序
        System.out.println("开始尝试连接数据库！");
        String url = "jdbc:oracle:" + "thin:@127.0.0.1:1521:xe";// 127.0.0.1是本机地址，XE是精简版Oracle的默认数据库名
        conn = DriverManager.getConnection(url, user, pwd);// 获取连接
        System.out.println("连接成功！");
        statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
    }

    //创建表
    public void createTable(String table, String[] names, String[] type, String[] pk, String[] notnull,
                            String[] fk_name, String[] fk_table, String[] fk_col) throws SQLException {
        StringBuilder builder = new StringBuilder("create table " + table + "( ");
        for (int i = 0; i < names.length; i++) {
            builder.append(names[i] + " " + type[i] + " "
                    + (pk[i].equals("true") ? "primary key" : " ") + " "
                    + (notnull[i].equals("true") ? "not null" : "null")
                    + ",");
        }

        for (int i = 0; i < fk_name.length; i++) {
            if (fk_name[i].isEmpty())
                continue;

            builder.append("CONSTRAINT pk_" + fk_name[i] + fk_col[i] + i + " FOREIGN KEY ("
            + fk_name[i] + ") REFERENCES " + fk_table[i] + "(" + fk_col[i] + "),");
        }

        builder.deleteCharAt(builder.length() - 1);
        builder.append(")");

        String sql = builder.toString();
        statement.execute(sql);
    }

    //获取所有的表
    public List<String> listAllTables() throws SQLException {
        List<String> tables = new ArrayList<>();

        ResultSet set = statement.executeQuery("select table_name from user_tables");
        while (set.next()) {
            tables.add(set.getString(1));
        }

        return tables;
    }

    //获取制定表的所有属性
    public List<String> listAllColumnsByTable(String table) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement("select COLUMN_NAME from user_tab_cols where table_name = ?");
        preparedStatement.setString(1, table);
        ResultSet set = preparedStatement.executeQuery();

        List<String> cols = new ArrayList<>();
        while (set.next()) {
            cols.add(set.getString(1));
        }
        return cols;
    }

    //通过表名查询
    public ResultSet queryByTableName(String name) throws SQLException {
        ResultSet set = statement.executeQuery("select * from " + name.toLowerCase());

        return set;
    }

    //通过第一列的参数删除数据
    public int deleteByFirst(String table, String name, String first) throws SQLException {
        first = format(table, name, first);

        String sql = "delete from " + table + " where " + name + " = " + first;

        int deleteRow = statement.executeUpdate(sql);

        return deleteRow;
    }

    //根据表名和第一列的参数更新数据
    public int updateByTableFirst(String table, String firstName, String first, Map<String, String> paramMap) throws SQLException {
        //获取每一列的结构数据
        ResultSet set = conn.getMetaData().getColumns(null, "%", table, "%");

        //构造sql语句
        StringBuilder builder = new StringBuilder("update " + table + " set ");

        //格式化第一个参数
        set.next();
        String name = set.getString("COLUMN_NAME");
        String type = set.getString("TYPE_NAME");
        first = format(type, first);

        //添加参数
        String param = paramMap.get(name);
        param = format(type, param);
        builder.append(name + "=" + param);

        while (set.next()) {
            name = set.getString("COLUMN_NAME");
            type = set.getString("TYPE_NAME");
            param = paramMap.get(name);
            param = format(type, param);
            builder.append("," + name + "=" + param);
        }

        builder.append(" where " + firstName + " = " + first);

        //执行
        String sql = builder.toString();
        int updateRow = statement.executeUpdate(sql);

        return updateRow;
    }

    //根据表名和列名格式化字符串
    private String format(String table, String name, String value) throws SQLException {
        //获取每一列的结构数据
        ResultSet set = conn.getMetaData().getColumns(null, "%", table, "%");

        while (set.next()) {
            if (set.getString("COLUMN_NAME").equals(name)) {
                return format(set.getString("TYPE_NAME"), value);
            }
        }
        return value;
    }

    //根据类型格式化字符串
    private String format(String type, String value) {
        switch (type) {
            case "VARCHAR2":
            case "CHAR":
            case "VARCHAR":
            case "NCHAR":
            case "NVARCHAR2":
                return "'" + value + "'";
            case "NUMBER":
            case "INTEGER":
                return value;
            case "DATE":
                return "to_date('" + value.substring(0, value.length() - 2) + "' , 'yyyy-mm-dd hh24:mi:ss')";
            default:
                return value;
        }
    }

    //插入数据
    public int insertByTable(String table, Map<String, String> paramMap) throws SQLException {
        StringBuilder builder = new StringBuilder("insert into " + table + " (");
        StringBuilder builder1 = new StringBuilder(" values (");

        //获取每一列的结构数据
        ResultSet set = conn.getMetaData().getColumns(null, "%", table, "%");

        //添加参数
        while (set.next()) {
            String name = set.getString("COLUMN_NAME");
            String type = set.getString("TYPE_NAME");

            builder.append(name + ",");
            builder1.append(format(type, paramMap.get(name)) + ",");

        }

        builder.deleteCharAt(builder.length() - 1);
        builder1.deleteCharAt(builder1.length() - 1);
        builder.append(")");
        builder1.append(")");

        String sql = builder.toString() + builder1.toString();
        int insertRow = statement.executeUpdate(sql);

        return insertRow;
    }

    //创建表
    public void createTable() throws SQLException {
        String sql = "CREATE TABLE test (" +
                "id NUMBER NOT NULL PRIMARY KEY," +
                "first VARCHAR(8)" +
                ")";
        statement.executeUpdate(sql);
    }

    //增加字段
    public void addColumn() throws SQLException {
        String sql = "ALTER TABLE test ADD second VARCHAR(10);";
        statement.executeUpdate(sql);
    }

    //插入数据
    public void insert() throws SQLException {
        String sql = "INSERT INTO test VALUES (1, '1')";
        statement.executeUpdate(sql);
    }

    //更新数据
    public void update() throws SQLException {
        String sql = "UPDATE test SET first='2' WHERE first='1'";
        statement.executeUpdate(sql);
    }

    //删除数据
    public void delete() throws SQLException {
        String sql = "DELETE FROM test WHERE id=1";
        statement.executeUpdate(sql);
    }

    //查询操作
    public void select() throws SQLException {
        String sql = "SELECT * FROM test";
        ResultSet set = statement.executeQuery(sql);
        while (set.next()) {
            System.out.println(set.getInt(1));
            System.out.println(set.getString(2));
        }
    }

    //存储过程
    public void process() throws SQLException {
        String sql = "CREATE PROCEDURE proc_test" +
                "AS BEGIN" +
                "INSERT test VALUES (5, '5')" +
                "END;";
        statement.execute(sql);
        CallableStatement s = conn.prepareCall("proc_test");
        s.execute();
    }

    //触发器
    public void trigger() throws SQLException {
        String sql = "CREATE TRIGGER tri_test" +
                "AFTER UPDATE OF FIRST" +
                "ON test" +
                "FOR EACH ROW" +
                "WHEN (NEW.FIRST = '99')" +
                "BEGIN" +
                "   SELECT '1'" +
                "   INTO :NEW.FIRST" +
                "   FROM DUAL" +
                "END;";
        statement.executeUpdate(sql);
    }
}
