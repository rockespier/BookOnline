package z.bi;

import z.bi.config.AppProperties;
import z.bi.config.IConfig;

import java.sql.Connection;

public class AppConfig {

    private static IConfig config;

    private static IConfig getInstance(ServletBase.Parameters p) throws Exception {
        if (config == null)
            config = new AppProperties(p.req);
        return config;
    }

    public static Connection getConnection(ServletBase.Parameters p, String nombre) throws Exception {
        return AppConfig.getInstance(p).getConnection(p, nombre);
    }

    public static String getServerPath(ServletBase.Parameters p) throws Exception {
        return AppConfig.getInstance(p).getServerPath(p);
    }

    public static String getPath(ServletBase.Parameters p, String pathName) throws Exception {
        return AppConfig.getInstance(p).getPath(p, pathName);
    }

}
