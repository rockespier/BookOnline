package z.bi;


import z.bi.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.SQLException;

import java.util.HashMap;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.*;
import z.bi.security.Security;


public abstract class ServletBase extends HttpServlet {

    private static final String CONTENT_TYPE = "text/html; charset=windows-1252";

    protected abstract void doCommand(Parameters p) throws Exception;

    protected String getContentType() {
        return CONTENT_TYPE;
    }

    protected void init(Parameters p) throws Exception {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Cache-Control", "max-age=2592000"); // para los JavaScript
        Execute(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Execute(request, response);
    }

    protected Parameters Execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType(getContentType());
        response.addHeader("", "");

        Parameters params = null;

        try {

            params = createParameter(request, response);
            params.setConnection(getConnection(params));

            doCommand(params);

            params.end();

        } catch (Exception ex) {
            doError(params, ex);
        }

        return params;

    }

    protected void doError(Parameters p, Exception ex) {

        try {

            String error = "";
            //ex.printStackTrace(p.res.getWriter());

            error = ex.getMessage();

            if (Utils.isNullOrEmpty(error))
                error = ex.toString();


            p.json.put("error", true);
            p.json.put("message", error);
            p.abort(ex);

        } catch (Exception e) {
        }

    }

    protected Connection getConnection(Parameters p) throws Exception {
        // Leer la CONEXION
        Connection conn = null;

        String[] parts = p.req.getContextPath().split("/");
        for (int i = parts.length - 1; i >= 0; i--) {
            try {
                if (parts[i].length() > 0) {
                    conn = AppConfig.getConnection(p, parts[i]);
                    return conn;
                }
            } catch (Exception ex) {
            }
        }

        // Si no existe una conexion retornar la conexion por defecto
        conn = AppConfig.getConnection(p, "z");
        return conn;
    }

    protected ServletBase.Parameters createParameter(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return new ServletBase.Parameters(request, response);
    }

    public class Parameters {
        public String command;
        public String subcommand;
        public HttpServletRequest req;
        public HttpServletResponse res;
        public JSONObject json;
        private HashMap<String, String> parameters = null;
        public Connection conn;

        public Integer idusuario = null;

        private PrintWriter out = null;

        Parameters(HttpServletRequest request, HttpServletResponse response) throws Exception {
            req = request;
            res = response;
            json = new JSONObject();
            parameters = new HashMap<String, String>();
            setParameters(req.getParameterMap());
            initCookie();
        }

        private void initCookie() throws Exception {
            JSONObject user = Security.getUserFromCookie(this);
            if (user != null) {
                this.idusuario = Utils.parseInteger(user.get("idusuario"));
            }
        }
        
        public void setConnection(Connection conn) throws SQLException {
            this.conn = conn; 
            if (this.conn != null) {
                this.conn.setAutoCommit(false);
            }
        }

        public void abort(Exception ex) {
            try 
            {
                res.addHeader("X-JSON", json.toString());
                if (conn != null) {
                    conn.rollback();
                    conn.close();
                }
                //ex.printStackTrace(getWriter());
                res.flushBuffer();
                if (out != null) {
                    out.flush();
                    //out.close();
                }
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void end() {
            res.addHeader("X-JSON", json.toString());
            try {
                if (conn != null) {
                    conn.commit();
                    conn.close();
                }
                res.flushBuffer();
                if (out != null) {
                    out.flush();
                    //out.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public PrintWriter getWriter() throws Exception {
            if (out == null) {
                out = res.getWriter();
            }
            return out;
        }

        public void writeForStore(Object data) throws Exception {
            JSONObject jo = new JSONObject();
            jo.put("success", data != null);
            jo.put("data", data);
            getWriter().print(jo.toString());
        }

        public void print(String obj, Object... params) throws Exception {
            if (obj == null)
                return;
            getWriter().print(String.format(obj, params));
        }

        public void print(Object obj) throws Exception {
            if (obj == null)
                return;
            getWriter().print(obj.toString());
        }

        public Map getMap() {
            return parameters;
        }

        public String getString(String key) {
            return parameters.get(key);
        }

        public String getParameter(String key) {
            return parameters.get(key);
        }

        public void setParameters(Map value) {
            parameters.clear();

            Object vals;
            Iterator it = value.keySet().iterator();
            while (it.hasNext()) {
                String s = (String)it.next();
                if (!parameters.containsKey(s)) {
                    vals = value.get(s);
                    if (vals != null) {
                        if (vals.getClass().isArray()) {
                            if (((String[])vals).length == 0) {
                                vals = null;
                            } else {
                                vals = ((String[])vals)[0];
                            }
                        }
                    }
                    parameters.put(s, vals == null ? "" : vals.toString());
                }
            }

            initCommands();
        }

        private void initCommands() {
            String commandName = "";
            String subCommandName = "";

            // COMMAND
            if (parameters.containsKey("C"))
                commandName = "C";
            else if (parameters.containsKey("c"))
                commandName = "c";

            this.command = parameters.get(commandName);
            if (Utils.isNullOrEmpty(this.command))
                this.command = "";

            this.command = command.toUpperCase();

            // SUBCOMMAND
            if (parameters.containsKey("S"))
                subCommandName = "S";
            else if (parameters.containsKey("s"))
                subCommandName = "s";

            this.subcommand = parameters.get(subCommandName);
            if (Utils.isNullOrEmpty(this.subcommand))
                this.subcommand = "";

            this.subcommand = subcommand.toUpperCase();


            removeParameter(commandName);
            removeParameter(subCommandName);
        }

        public String removeParameter(String key) {
            return parameters.remove(key);
        }

        public void setParameter(String key, Object value) {
            if (value != null)
                parameters.put(key, value.toString());
        }

        public String getRealPath(String uri) {
            File f = new File(this.req.getSession().getServletContext().getRealPath("/"), uri);
            return f.getPath();
        }

    }

}

