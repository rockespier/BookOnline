package z.bi.utils;

import z.bi.ServletBase;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public final class Utils {

    private Utils() {
    }

    public static String concatIf(String s1, String s2) {
        return isNullOrEmpty(s1) || isNullOrEmpty(s2) ? "" : s1 + s2;
    }

    public static String padLeft(String str, char car, int maxlength) {
        if (str == null)
            str = "";
        int falta = maxlength - str.length();
        for (int i = 0; i < falta; i++) {
            str = car + str;
        }
        return str;
    }

    public static String padRight(String str, char car, int maxlength) {
        if (str == null)
            str = "";
        int falta = maxlength - str.length();
        for (int i = 0; i < falta; i++) {
            str = str + car;
        }
        return str;
    }
    /*
    public static String getRealPath(ServletBase.Parameters p, String path){
        String pathTarget = p.req.getSession().getServletContext().getRealPath(path);
        return pathTarget;
    }
*/

    public static String resolveClientURL(ServletBase.Parameters p, String URL) {
        return resolveClientURL(p.req.getContextPath(), p.req.getRequestURI(), URL);
    }

    public static String resolveClientURL(String contextPath, String requestUri, String URL) {
        URL = URL.replaceFirst("~", contextPath);
        int minlen = Math.min(URL.length(), requestUri.length());
        int c = 0;
        while ((c < minlen) && (URL.charAt(c) == requestUri.charAt(c)))
            c++;
        URL = URL.substring(c);
        requestUri = requestUri.substring(c);
        if (requestUri.charAt(0) == '/')
            requestUri = requestUri.substring(1);

        int count = 0;
        for (int i = 0; i < requestUri.length(); i++)
            if (requestUri.charAt(i) == '/')
                count++;
        requestUri = "";
        for (int i = 0; i < count; i++)
            requestUri += "../";
        requestUri += URL;

        return requestUri;
    }

    public static String decodeURIComponent(String encodedURI) {
        if (encodedURI == null || encodedURI.length() == 0) {
            return encodedURI;
        }
        char actualChar;

        StringBuffer buffer = new StringBuffer();

        int bytePattern, sumb = 0;

        for (int i = 0, more = -1; i < encodedURI.length(); i++) {
            actualChar = encodedURI.charAt(i);

            switch (actualChar) {
                case '%':
                    {
                        actualChar = encodedURI.charAt(++i);
                        int hb = 
                            (Character.isDigit(actualChar) ? actualChar - '0' : 10 + Character.toLowerCase(actualChar) - 'a') & 
                            0xF;
                        actualChar = encodedURI.charAt(++i);
                        int lb = 
                            (Character.isDigit(actualChar) ? actualChar - '0' : 10 + Character.toLowerCase(actualChar) - 'a') & 
                            0xF;
                        bytePattern = (hb << 4) | lb;
                        break;
                    }
                case '+':
                    {
                        bytePattern = '+';
                        break;
                    }
                default:
                    {
                        bytePattern = actualChar;
                    }
            }

            //* Decode byte bytePattern as UTF-8, sumb collects incomplete chars *//*
            if ((bytePattern & 0xc0) == 0x80) { // 10xxxxxx
                sumb = (sumb << 6) | (bytePattern & 0x3f);
                if (--more == 0)
                    buffer.append((char)sumb);
            } else if ((bytePattern & 0x80) == 0x00) { // 0xxxxxxx
                buffer.append((char)bytePattern);
            } else if ((bytePattern & 0xe0) == 0xc0) { // 110xxxxx
                sumb = bytePattern & 0x1f;
                more = 1;
            } else if ((bytePattern & 0xf0) == 0xe0) { // 1110xxxx
                sumb = bytePattern & 0x0f;
                more = 2;
            } else if ((bytePattern & 0xf8) == 0xf0) { // 11110xxx
                sumb = bytePattern & 0x07;
                more = 3;
            } else if ((bytePattern & 0xfc) == 0xf8) { // 111110xx
                sumb = bytePattern & 0x03;
                more = 4;
            } else { // 1111110x
                sumb = bytePattern & 0x01;
                more = 5;
            }
        }
        return buffer.toString();
    }

    public static String toUpperCase(String str) {
        if (str == null || str.length() == 0)
            return str;
        return str.toUpperCase();
    }


    public static String asString(String str) {
        if (str == null)
            str = "";
        return str.trim();
    }

    public static boolean isNullOrEmpty(String value) {
        return value == null || value.length() == 0;
    }

    public static Date parseDate(String value) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return format.parse(value);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String parseDate(Date value) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return format.format(value);
        } catch (Exception e) {
            return null;
        }
    }

    public static Integer parseInteger(Object value, Integer defaultValue) {
        try {
            return Integer.parseInt(value.toString());
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static Integer parseInteger(Object value) {
        try {
            return Integer.parseInt(value.toString());
        } catch (Exception e) {
            return null;
        }
    }

    public static BigDecimal parseBigDecimal(Object value) {
        try {
            return BigDecimal.valueOf(Double.parseDouble(value.toString()));
        } catch (Exception e) {
            return null;
        }
    }

    public static Double parseDouble(Object value) {
        try {
            return Double.parseDouble(value.toString());
        } catch (Exception e) {
            return null;
        }
    }

    public static java.sql.Date getSqlDate(String fecha) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            java.util.Date aDate = format.parse(fecha);
            java.text.SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.sql.Date sqlDate = java.sql.Date.valueOf(sdf.format(aDate));
            return sqlDate;
        } catch (ParseException e) {
            return null;
        }
    }

    public static String getSqlDate(java.sql.Date fecha) {
        if (fecha == null)
            return "";
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date jDate = new java.util.Date(fecha.getTime());
        return format.format(jDate);
    }

    public static String getSqlTimestamp(java.sql.Timestamp fecha) {
        if (fecha == null)
            return "";
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date jDate = new java.util.Date(fecha.getTime());
        return format.format(jDate);
    }


    public static String containsParse(String text) {
        if (text == null)
            return null;

        StringBuilder sb = new StringBuilder();
        String[] str = text.split(" ");
        String s;
        for (int i = 0, len = str.length; i < len; i++) {
            s = str[i].trim().toUpperCase();
            if (s.length() > 0) {

                if ("DE".equals(s))
                    continue;
                if ("LA".equals(s))
                    continue;
                /*
                if("Y".equals(s))
                    continue;
                */

                if (sb.length() > 0)
                    sb.append(" AND ");
                sb.append("\"" + s + "%\"");
            }
        }

        return sb.toString();
    }

    public static String callProcedure(String procedureName, int paramCount) {
        StringBuilder sb = new StringBuilder("{call " + procedureName + "(");
        for (int n = 1; n <= paramCount; n++) {
            sb.append("?");
            if (n < paramCount)
                sb.append(",");
        }
        return sb.append(")}").toString();
    }

    public static void setParameter(PreparedStatement stmt, int index, int type, Object value) throws Exception {
        try {
            if (value == null) {
                stmt.setNull(index, type);
                return;
            }
            switch (type) {
                case Types.VARCHAR:
                    stmt.setString(index, value.toString());
                    break;
                case Types.INTEGER:
                    stmt.setInt(index, parseInteger(value));
                    break;
                case Types.NUMERIC:
                    stmt.setBigDecimal(index, parseBigDecimal(value));
                    break;
                case Types.DATE:
                    stmt.setDate(index, getSqlDate(value.toString()));
                    break;
            }
        } catch (SQLException e) {
            throw e;
        } catch (NullPointerException e) {
            stmt.setNull(index, type);
        }
    }

    public static Object executeScalar(Connection conn, String sql, Object... params) {
        try {
            Statement stmt = conn.createStatement();
            //stmt.setMaxRows(1);
            ResultSet rs = stmt.executeQuery(String.format(sql, params));
            Object result = null;
            if (rs.next()) {
                result = rs.getObject(1);
            }
            rs.close();
            stmt.close();
            return result;

        } catch (Exception e) {
            return null;
        }
    }

    public static int executeNonQuery(Connection conn, String sql, Object... params) throws SQLException {
        Statement stmt = conn.createStatement();
        int result = stmt.executeUpdate(String.format(sql, params));
        stmt.close();
        return result;
    }

    public static ResultSet executeResultSet(Connection conn, String sql, Object... params) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(String.format(sql, params));
            return rs;

        } catch (SQLException e) {
            return null;
        }
    }

    public static void close(ResultSet rs, boolean closeStatement) {
        try {
            close(rs, closeStatement ? rs.getStatement() : null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close(ResultSet rs, Statement s) {
        try {
            if (rs != null)
                rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (s != null)
                s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String[] getColumns(ResultSetMetaData meta) throws SQLException {

        int numcols = meta.getColumnCount();

        // Construir el Nombre de las Columnas
        java.util.ArrayList<String> cl = new java.util.ArrayList<String>();
        String cname;
        for (int i = 1; i <= numcols; i++) {
            cname = meta.getColumnName(i);
            if (cname.charAt(0) == '!')
                cname = cname.substring(1);
            else
                cname = cname.toLowerCase().replace("_", "");
            cl.add(cname);
        }
        String[] aa = new String[0];
        String[] cols = cl.toArray(aa);
        return cols;
    }

    public static JSONObject getJSONMatriz(ResultSet rs) throws SQLException {
        JSONObject jo = new JSONObject();

        ResultSetMetaData meta = rs.getMetaData();
        int numcols = meta.getColumnCount();
        int sep;
        String[] cols = Utils.getColumns(meta);
        String id;

        // Crear la Cabecera
        JSONArray ja = new JSONArray();
        JSONArray jids = new JSONArray();
        for (int i = 0; i < numcols; i++) {
            JSONObject header = new JSONObject();

            sep = cols[i].indexOf("[[");
            if (sep > -1) {
                id = "pvt" + cols[i].substring(0, sep);
                header.put("header", cols[i].substring(sep + 2));
            } else {
                id = cols[i];
                header.put("header", cols[i]);
            }
            jids.add(id);
            header.put("dataIndex", id);
            header.put("width", 50);
            ja.add(header);
        }

        jo.put("header", ja);
        jo.put("store", jids);
        jo.put("data", getJSONArrayArray(rs));
        return jo;
    }


    public static JSONObject getJSONObject(Connection conn, String sql, Object... params) throws SQLException {
        try {
            sql = String.format(sql, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        JSONObject json = getJSONObject(rs);
        rs.close();
        stmt.close();
        return json;
    }
    
    public static Object getObjectFromTable(int type, Object value){
        if (value != null) {
            switch (type) {
                case Types.DATE:
                    value = Utils.getSqlDate((java.sql.Date)value);
                    break;
                case Types.TIMESTAMP:
                    value = Utils.getSqlTimestamp((Timestamp)value);
                    break;
                case -3: //2005
                    value = value.toString();
                    break;
            }
        } else {
            value = "";
        }
        return value;
    }

    public static JSONObject getJSONObject(ResultSet rs) throws SQLException {
        JSONObject json = new JSONObject();
        if (rs.getRow() == 0)
            if (!rs.next())
                return json;

        Object value;
        ResultSetMetaData meta = rs.getMetaData();
        int numcols = meta.getColumnCount();

        String[] cols = Utils.getColumns(meta);

        for (int i = 1; i <= numcols; i++) {
            json.put(cols[i - 1], getObjectFromTable(meta.getColumnType(i), rs.getObject(i)));
        }

        return json;
    }

    public static JSONArray getJSONArrayObject(ResultSet rs) throws SQLException {
        JSONArray ja = new JSONArray();
        Object value;

        ResultSetMetaData meta = rs.getMetaData();
        int numcols = meta.getColumnCount();
        String[] cols = Utils.getColumns(meta);

        while (rs.next()) {
            JSONObject jo = new JSONObject();
            for (int i = 1; i <= numcols; i++) {
                jo.put(cols[i - 1], getObjectFromTable(meta.getColumnType(i), rs.getObject(i)));
            }
            ja.add(jo);
        }
        return ja;
    }

    public static JSONArray getJSONArrayObject(Connection conn, String sql, Object... params) throws SQLException {
        try {
            sql = String.format(sql, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        JSONArray ja = getJSONArrayObject(rs);
        close(rs, stmt);
        return ja;
    }

    public static JSONArray getJSONArrayArray(ResultSet rs) throws SQLException {
        JSONArray ja = new JSONArray();
        Object value;
        ResultSetMetaData meta = rs.getMetaData();

        int numcols = meta.getColumnCount();

        while (rs.next()) {
            JSONArray jo = new JSONArray();
            for (int i = 1; i <= numcols; i++) {
                jo.add(getObjectFromTable(meta.getColumnType(i), rs.getObject(i)));
            }
            ja.add(jo);
        }
        return ja;
    }

    public static JSONArray getJSONArrayArray(Connection conn, String sql, Object... params) throws SQLException {
        try {
            sql = String.format(sql, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        JSONArray ja = getJSONArrayArray(rs);
        close(rs, stmt);
        return ja;
    }


    public static void populateDtoFromRequest(Object dto, ServletBase.Parameters p) throws Exception {
        Class cls = dto.getClass();
        Field m[] = cls.getDeclaredFields();
        String tipoDato, fName, paramReq;
        Method set;
        Class params[] = new Class[1];
        Object args[] = new Object[1];
        for (int i = 0; i < m.length; i++) {
            fName = m[i].getName();
            paramReq = p.getString(fName.toLowerCase());

            if (paramReq != null) {
                fName = fName.substring(0, 1).toUpperCase() + fName.substring(1);
                params[0] = m[i].getType();
                set = cls.getMethod("set" + fName, params);

                // formatear el Texto de acuerdo al tipo de parametro
                tipoDato = params[0].getName();
                if ("java.lang.Integer".equals(tipoDato))
                    args[0] = Utils.parseInteger(paramReq);
                else if ("java.lang.String".equals(tipoDato))
                    args[0] = paramReq;
                else if ("java.math.BigDecimal".equals(tipoDato))
                    args[0] = Utils.parseBigDecimal(paramReq);
                else if ("java.util.Date".equals(tipoDato) || "java.sql.Date".equals(tipoDato))
                    args[0] = Utils.parseDate(paramReq);
                else if ("int".equals(tipoDato))
                    args[0] = Integer.parseInt(paramReq);
                else if ("boolean".equals(tipoDato))
                    args[0] = Boolean.parseBoolean(paramReq);
                else
                    continue;
                set.invoke(dto, args);
            }
        }

    }

    public static void populateDtoFromJSON(Object dto, JSONObject json) throws Exception {
        Class cls = dto.getClass();
        Field m[] = cls.getDeclaredFields();
        String tipoDato, fName;
        Object paramReq;
        Method set = null;
        Class params[] = new Class[1];
        Object args[] = new Object[1];
        args[0] = null;
        for (int i = 0; i < m.length; i++) {
            fName = m[i].getName().toLowerCase();

            if (json.containsKey(fName)) {
                paramReq = json.get(fName);
                if (paramReq != null) {
                    fName = m[i].getName(); // nombre original
                    fName = fName.substring(0, 1).toUpperCase() + fName.substring(1);
                    params[0] = m[i].getType();
                    set = cls.getMethod("set" + fName, params);

                    // formatear el Texto de acuerdo al tipo de parametro
                    tipoDato = params[0].getName();
                    if ("java.lang.Integer".equals(tipoDato))
                        args[0] = Utils.parseInteger(paramReq);
                    else if ("java.lang.String".equals(tipoDato))
                        args[0] = paramReq;
                    else if ("java.math.BigDecimal".equals(tipoDato))
                        args[0] = Utils.parseBigDecimal(paramReq);
                    else if ("java.util.Date".equals(tipoDato) || "java.sql.Date".equals(tipoDato))
                        args[0] = Utils.parseDate((String)paramReq);
                    else if ("int".equals(tipoDato))
                        args[0] = Utils.parseInteger(paramReq);
                    else if ("boolean".equals(tipoDato))
                        args[0] = Boolean.parseBoolean((String)paramReq);
                    else
                        args[0] = null;
                } else {
                    args[0] = null;
                }

                set.invoke(dto, args);

            }
        }
    }

    static int inxId;
    static int inxText;
    static int inxParent;
    static Integer inxTag;

    private static void getSubTree(ResultSet rs, StringBuilder sb, String parent) throws SQLException {
        String par = rs.getString(inxParent);
        if (par == null)
            par = "";
        if (parent == null)
            parent = "";
        String id;
        String tag = "";
        boolean ponerComa = false, salir = false;
        while (!salir && parent.equals(par)) {
            if (ponerComa)
                sb.append(",");
            id = rs.getString(inxId);
            if (inxTag != null)
                tag = ", \"tag\":\"" + rs.getString(inxTag) + "\"";
            sb.append(String.format("{\"id\":\"%1$s\", \"text\":\"%2$s\" %3$s", id, rs.getString(inxText), tag));

            salir = !rs.next();
            if (!salir) {
                par = rs.getString(inxParent);
                if (par == null)
                    par = "";
                if (par.equals(id)) {
                    sb.append(", \"items\":[");
                    getSubTree(rs, sb, par);
                    sb.append("]");
                    try {
                        par = rs.getString(inxParent);
                    } catch (Exception ex) { // si se esta al final
                        par = "";
                        salir = true;
                    }
                    if (par == null)
                        par = "";
                }
            }
            sb.append("}");
            ponerComa = true;
        }
    }

    // select [ id, text, idparent ]

    public static String getJSONTree(ResultSet rs, int indexId, int indexText, int indexParent, 
                                     Integer indexTag) throws SQLException {
        inxId = indexId;
        inxText = indexText;
        inxParent = indexParent;
        inxTag = indexTag;
        StringBuilder sb = new StringBuilder("[");
        while (rs.next()) {
            try {
                // verificar si existe la Columna de Tag
                rs.getString(inxTag);
            } catch (Exception ex) {
                inxTag = null;
            }

            getSubTree(rs, sb, rs.getString(inxParent));
        }

        sb.append("]");
        return sb.toString();
    }

    public static String getJSONTree(ResultSet rs) throws SQLException {
        return getJSONTree(rs, 1, 2, 3, 4);
    }

    public static String BytesToHex(final byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b: bytes)
            sb.append(String.format("%02x", b & 0xff));
        return sb.toString();
    }

    public static byte[] HexToBytes(final String str) {
        int nBytes = str.length() / 2;
        byte[] bytes = new byte[nBytes];
        for (int i = 0; i < nBytes; i++) {
            bytes[i] = (byte)Integer.parseInt(str.substring(i * 2, i * 2 + 2), 16);
        }
        return bytes;
    }


}
