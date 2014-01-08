
package z.bi.security;

import javax.servlet.http.Cookie;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import z.bi.ServletBase;
import z.bi.utils.Utils;


public class Security {
   
    private static final String COOKIE_SECURITY_NAME = "z";
    private static final int COOKIE_SECURITY_MAXAGE = 60 * 60 * 24 * 7; // 1 semana
    private static final String COOKIE_SECURITY_PATH = "/";
    
    private Security() {
    }
    
    public static JSONObject getUserFromCookie(ServletBase.Parameters p) {
        try {
            p.idusuario = null;
            Cookie[] co = p.req.getCookies();
            if (co != null) {
                for (int i = 0; i < co.length; i++) {
                    Cookie c = co[i];
                    if (c.getName().equals(Security.COOKIE_SECURITY_NAME)) {

                        String value = c.getValue();

                        if (!Utils.isNullOrEmpty(value)) {
                            JSONObject user = getUserFromStringEncrypt(p, value);
                            return user;
                        }
                    }
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static void setUserToCookie(ServletBase.Parameters p, JSONObject value) {

        if (value == null)
            return;

        try {

            JSONObject jo = new JSONObject();

            jo.put("u", value.get("idusuario"));
            jo.put("r", value.get("rol"));

            String val = jo.toString();

            Cookie c = new Cookie(Security.COOKIE_SECURITY_NAME, val);

            c.setPath(Security.COOKIE_SECURITY_PATH);

            c.setMaxAge(Security.COOKIE_SECURITY_MAXAGE);

            p.res.addCookie(c);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static JSONObject getUserFromStringEncrypt(ServletBase.Parameters p, String value) throws Exception {

        JSONObject user = (JSONObject)JSONValue.parse(value);
        user.put("idusuario", user.get("u"));

        user.remove("u");

        return user;
    }
    
    
}
