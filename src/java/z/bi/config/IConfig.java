package z.bi.config;
import z.bi.ServletBase;
import java.sql.Connection;
import javax.servlet.http.HttpServletRequest;

public abstract class IConfig {
    IConfig(HttpServletRequest request) throws Exception{
    }

    public String getServerPath(ServletBase.Parameters p) throws Exception{
        return this.getServerPath(p.req);
    }
    
    abstract public Connection getConnection(ServletBase.Parameters p, String nombre) throws Exception;
    abstract public String getPath(ServletBase.Parameters p, String pathName) throws Exception;
    abstract public String getServerPath(HttpServletRequest request) throws Exception;
}
