package z.car.db.dao;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.*;
import java.util.Map;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
public class ResourceManager {
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
    public static Integer parseInteger(Object value) {
        try {
            return Integer.parseInt(value.toString());
        } catch (Exception e) {
            return null;
        }
    }
    public static BigDecimal parseBigDecimal(Object value) {
        try {
            return new BigDecimal(Double.parseDouble(value.toString()));
        } catch (Exception e) {
            return null;
        }
    } /* public static void populateDtoFromRequest(Object dto, ServletBase.Parameters p) throws Exception { Class cls = dto.getClass(); Field m[] = cls.getDeclaredFields(); String tipoDato, fName, paramReq; Method set; Class params[] = new Class[1]; Object args[] = new Object[1]; for (int i = 0; i < m.length; i++) { fName = m[i].getName(); paramReq = p.getString(fName.toLowerCase()); if (paramReq != null) { fName = fName.substring(0, 1).toUpperCase() + fName.substring(1); params[0] = m[i].getType(); set = cls.getMethod("set" + fName, params); tipoDato = params[0].getName(); if ("java.lang.Integer".equals(tipoDato)) args[0] = parseInteger(paramReq); else if ("java.lang.String".equals(tipoDato)) args[0] = paramReq; else if ("java.math.BigDecimal".equals(tipoDato)) args[0] = parseBigDecimal(paramReq); else if ("java.util.Date".equals(tipoDato) || "java.sql.Date".equals(tipoDato)) args[0] = parseDate(paramReq); else if ("int".equals(tipoDato)) args[0] = Integer.parseInt(paramReq); else if ("boolean".equals(tipoDato)) args[0] = Boolean.parseBoolean(paramReq); else continue; set.invoke(dto, args); } } } */
    public static void populateDtoFromMap(Object dto, Map json) throws Exception {
        Class cls = dto.getClass();
        Field m[] = cls.getDeclaredFields();
        String tipoDato, fName;
        Object parametros;
        String paramReq;
        Method set = null;
        Class params[] = new Class[1];
        Object args[] = new Object[1];
        args[0] = null;
        for (int i = 0; i < m.length; i++) {
            fName = m[i].getName()
                .toLowerCase();
            if (json.containsKey(fName)) {
                parametros = json.get(fName);
                fName = m[i].getName();
                fName = fName.substring(0, 1)
                    .toUpperCase() + fName.substring(1);
                params[0] = m[i].getType();
                set = cls.getMethod("set" + fName, params);
                if (parametros != null) {
                    if (parametros.getClass()
                        .isArray()) {
                        if (((String[]) parametros)
                            .length == 0) {
                            args[0] = null;
                            set.invoke(dto, args);
                            continue;
                        }
                        paramReq = ((String[]) parametros)[0];
                    } else {
                        paramReq = parametros.toString();
                    }
                    tipoDato = params[0].getName();
                    if ("java.lang.Integer".equals(tipoDato)) args[0] = parseInteger(paramReq);
                    else if ("java.lang.String".equals(tipoDato)) args[0] = paramReq;
                    else if ("java.math.BigDecimal".equals(tipoDato)) args[0] = parseBigDecimal(paramReq);
                    else if ("java.util.Date".equals(tipoDato) || "java.sql.Date".equals(tipoDato)) args[0] = parseDate((String) paramReq);
                    else if ("int".equals(tipoDato)) args[0] = parseInteger(paramReq);
                    else if ("boolean".equals(tipoDato)) args[0] = Boolean.parseBoolean((String) paramReq);
                    else args[0] = null;
                } else {
                    args[0] = null;
                }
                set.invoke(dto, args);
            }
        }
    }
}