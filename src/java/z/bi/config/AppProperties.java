package z.bi.config;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.net.URLConnection;
import z.bi.ServletBase;
import java.sql.Connection;

import java.sql.Driver;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Properties;


import javax.servlet.http.HttpServletRequest;
import z.bi.utils.Utils;


public class AppProperties extends IConfig  {

    private String serverPath = null;
    private static final String CONFIGURATION_FILE = "app.properties";
    private static HashMap propiedades;
    
    public AppProperties(HttpServletRequest request) throws Exception {
        super(request);
    }

    public Connection getConnection(ServletBase.Parameters p, String nombre) throws Exception {
        Driver connectionDriver = null;

        nombre = nombre.toLowerCase();
        
        String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String url = "jdbc:sqlserver://db205.my-hosting-panel.com:1433;" +
            "databaseName=rtresweb_zdb;user=rtresweb_user; password=userdb1";  
        
        //String userName = "";
        //String password = "";

        try {
            connectionDriver = DriverManager.getDriver(url);
        } catch (Exception ex) {
            connectionDriver = null;
        }
        if (connectionDriver == null) {
            Class jdbcDriverClass = Class.forName(driver);
            connectionDriver = (Driver)jdbcDriverClass.newInstance();
            DriverManager.registerDriver(connectionDriver);
        }
        //return DriverManager.getConnection(url, userName, password);
        return DriverManager.getConnection(url);
    }

    public String getServerPath(HttpServletRequest request) throws Exception {
        if (Utils.isNullOrEmpty(serverPath)) {
            serverPath = request.getSession().getServletContext().getRealPath("/");
        }
        return serverPath;
    }

    
    private HashMap getProperties(ServletBase.Parameters p) throws Exception {
        if (propiedades == null) {
            File fi = new File(getServerPath(p.req), "WEB-INF" + File.separator + CONFIGURATION_FILE);
            FileInputStream f = new FileInputStream(fi);
            Properties propiedadesTemporales = new Properties();
            propiedadesTemporales.load(f);
            f.close();
            propiedades = new HashMap(propiedadesTemporales);
        }
        return propiedades;
    }
    
    public String getPath(ServletBase.Parameters p, String pathName) throws Exception {
        String s = (String)getProperties(p).get("path." + pathName);
        File f = new File(s, "");
        s = f.getAbsolutePath();
        if(!s.endsWith(File.separator))
            s = s + File.separator;
        return s;
    }


}
