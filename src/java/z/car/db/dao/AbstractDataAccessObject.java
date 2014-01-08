package z.car.db.dao;
import java.io.*;
import java.sql.*;
public class AbstractDataAccessObject {
    protected byte[] getBlob(CallableStatement stmt, int columnIndex) throws SQLException {
        try {
            Blob blob = stmt.getBlob(columnIndex);
            if (blob == null) {
                return null;
            }
            InputStream is = blob.getBinaryStream();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            if (is == null) {
                return null;
            } else {
                byte buffer[] = new byte[64];
                int c = is.read(buffer);
                while (c > 0) {
                    bos.write(buffer, 0, c);
                    c = is.read(buffer);
                }
                return bos.toByteArray();
            }
        } catch (IOException e) {
            throw new SQLException("Fallo al leer columna BLOB con IOException: " + e.getMessage());
        }
    }
    protected void setBlob(CallableStatement stmt, int parameterIndex, byte[] value) throws SQLException {
        if (value == null) {
            stmt.setNull(parameterIndex, Types.BLOB);
        } else {
            stmt.setBinaryStream(parameterIndex, new ByteArrayInputStream(value), value.length);
        }
    }
    protected String getClob(CallableStatement stmt, int columnIndex) throws SQLException {
        try {
            Clob clob = stmt.getClob(columnIndex);
            if (clob == null) {
                return null;
            }
            StringBuffer ret = new StringBuffer();
            InputStream is = clob.getAsciiStream();
            if (is == null) {
                return null;
            } else {
                byte buffer[] = new byte[64];
                int c = is.read(buffer);
                while (c > 0) {
                    ret.append(new String(buffer, 0, c));
                    c = is.read(buffer);
                }
                return ret.toString();
            }
        } catch (IOException e) {
            throw new SQLException("Fallo al leer columna CLOB con IOException: " + e.getMessage());
        }
    }
    protected void setClob(CallableStatement stmt, int parameterIndex, String value) throws SQLException {
        if (value == null) {
            stmt.setNull(parameterIndex, Types.CLOB);
        } else {
            stmt.setAsciiStream(parameterIndex, new ByteArrayInputStream(value.getBytes()), value.length());
        }
    }
    protected void close(Statement stmt) {
        try {
            if (stmt != null) stmt.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }
    protected void close(ResultSet rs) {
        try {
            if (rs != null) rs.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }
}