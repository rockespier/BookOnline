package z.car.servlets;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Date;
import java.util.HashMap;
import z.bi.ServletBase;
import z.bi.utils.Utils;

import z.car.db.dao.IPkgProducto;
import z.car.db.factory.PkgProductoFactory;
import z.car.db.mapper.PkgProducto;


import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import z.bi.AppConfig;



public class Producto extends ServletBase {

    private IPkgProducto DAO = PkgProductoFactory.create();

    protected void doCommand(ServletBase.Parameters p) throws Exception {
        if (p.command.equals("PRODUCTO"))
            PRODUCTO(p);
    }

    private void PRODUCTO(ServletBase.Parameters p) throws Exception {
        if ("GETLIST".equals(p.subcommand)) {
            PkgProducto.Producto param = DAO.ProductoGetlist(p.conn, p.getMap());
            p.print(Utils.getJSONArrayArray(param.getCurOut()));
            param.getCurOut().close();
        }else if ("GETLISTBYID".equals(p.subcommand)) {
            PkgProducto.Producto param = DAO.ProductoGetlistById(p.conn, p.getMap());
            p.print(Utils.getJSONObject(param.getCurOut()));
            param.getCurOut().close();
        }else if ("GETLISTNEW".equals(p.subcommand)) {
            PkgProducto.Producto param = DAO.ProductoGetlistNew(p.conn, p.getMap());
            p.print(Utils.getJSONArrayArray(param.getCurOut()));
            param.getCurOut().close();
        }else if ("GETLISTALL".equals(p.subcommand)) {
            PkgProducto.Producto param = DAO.ProductoGetlistAll(p.conn, p.getMap());
            p.print(Utils.getJSONArrayArray(param.getCurOut()));
            param.getCurOut().close();
        }else if ("INSERT".equals(p.subcommand)) {
            INSERT(p);
        }
    }
    
    protected void INSERT(ServletBase.Parameters p) throws Exception {

        try {
            //String pathTarget = "D:\\Upload";
            String pathTarget = AppConfig.getPath(p, "upload");

            File yourTempDirectory = new File(pathTarget);
            int yourMaxMemorySize = 10000000;
            int yourMaxRequestSize = 10000000;

            // Crear una fabrica de items de archivo
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setSizeThreshold(yourMaxMemorySize);
            factory.setRepository(yourTempDirectory);

            // Crear un nuevo manejador de UPLOAD de archivo
            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setSizeMax(yourMaxRequestSize);

            // Guardar los Archivos
            FileItemIterator fii = upload.getItemIterator(p.req);

            HashMap params = new HashMap();
            File file = null;
            String fileName = "";
            int size = 0;

            while (fii.hasNext()) {
                FileItemStream item = fii.next();

                if (item.isFormField()) {
                    InputStreamReader in = new InputStreamReader(item.openStream());
                    char[] b = new char[4096];
                    int len;
                    StringBuilder sb = new StringBuilder();
                    len = in.read(b);
                    while (len > 0) {
                        sb.append(b, 0, len);
                        len = in.read(b);
                    }
                    in.close();
                    params.put(item.getFieldName(), sb.toString());

                } else {

                    // Guardar el archivo al DD
                    InputStream is = null;
                    FileOutputStream os = null;
                    try {
                        file = File.createTempFile("upload", "file", yourTempDirectory);

                        // Guardar el archivo en el Servidor
                        byte[] b = new byte[4096];
                        int len;
                        is = item.openStream();
                        os = new FileOutputStream(file);
                        len = is.read(b);
                        while (len > 0) {
                            size += len;
                            os.write(b, 0, len);
                            len = is.read(b);
                        }

                    } catch (Exception ex) {
                        p.print("{\"success\": false, \"message\": \"Error al guardar el archivo (" + ex.getMessage() + 
                                ")\"}");
                        return;
                    } finally {
                        if (os != null) {
                            os.flush();
                            os.close();
                        }
                        if (is != null)
                            is.close();
                    }

                    // Generar el registro en la Base de Datos
                    fileName = (new File(item.getName())).getName();
                }
            }
           
            // Obtener la extension del archivo
            String ext = "";
            int cnt = fileName.lastIndexOf(".");
            if (cnt > 0) {
                ext = fileName.substring(cnt + 1);
            }
            ext = ext.toLowerCase();
            
            
            String realFileName = "A_" + ext + "." + ext;
            file.renameTo(new File(pathTarget + realFileName));
            
            PkgProducto.Producto param = DAO.newProducto(p.getMap());
            param.setIdusuario(p.idusuario);
            param.setFoto(fileName);
            DAO.ProductoInsert(p.conn, param);
            
            //p.print(ext);
            p.print("{\"success\": true, \"message\": \"Guardado correctamente\"}");


        } catch (Exception ex) {
            throw ex;
        }

    }

    
}
