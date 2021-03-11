package Server;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * c3p0是最常用的JDBC连接池
 */
public class ConnectionPool {
    private static DataSource ds;

    static {
        ds = new ComboPooledDataSource();
    }

    /**
     * 获得一个连接
     * @return
     * @throws SQLException
     */
    public static Connection getConn() throws SQLException {
        return ds.getConnection();
    }

    public static void close(PreparedStatement ps, Connection conn) {
        close(null, ps, conn);
    }

    /**
     * close一个连接
     * @param rs
     * @param ps
     * @param conn
     */
    public static void close(ResultSet rs, PreparedStatement ps, Connection conn) {
        if (rs != null) {
            try {
                {
                    rs.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
