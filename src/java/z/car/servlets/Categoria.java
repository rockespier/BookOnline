package z.car.servlets;

import z.bi.ServletBase;
import z.bi.utils.Utils;
import org.json.simple.JSONObject;

import z.car.db.dao.IPkgCategoria;
import z.car.db.factory.PkgCategoriaFactory;
import z.car.db.mapper.PkgCategoria;

public class Categoria extends ServletBase {

    private IPkgCategoria DAO = PkgCategoriaFactory.create();

    protected void doCommand(ServletBase.Parameters p) throws Exception {
        if (p.command.equals("CATEGORIA"))
            CATEGORIA(p);
    }

    private void CATEGORIA(ServletBase.Parameters p) throws Exception {
        if ("GETLIST".equals(p.subcommand)) {
            PkgCategoria.Categoria param = DAO.CategoriaGetlist(p.conn, p.getMap());
            p.print(Utils.getJSONArrayArray(param.getCurOut()));
            param.getCurOut().close();
        }else if ("INSERT".equals(p.subcommand)) {
            PkgCategoria.Categoria param = DAO.newCategoria(p.getMap());
            param.setIdusuario(p.idusuario);
            DAO.CategoriaInsert(p.conn, param);
        }else if ("GETLISTPADRE".equals(p.subcommand)) {
            PkgCategoria.Categoria param = DAO.CategoriaGetlistPadre(p.conn, p.getMap());
            p.print(Utils.getJSONArrayArray(param.getCurOut()));
            param.getCurOut().close();
        }else if ("EDIT".equals(p.subcommand)) {
            PkgCategoria.Categoria param = DAO.newCategoria(p.getMap());
            param.setIdusuario(p.idusuario);
            DAO.CategoriaEdit(p.conn, param);
        }else if ("GETLISTBYID".equals(p.subcommand)) {
            PkgCategoria.Categoria param = DAO.CategoriaGetlistById(p.conn, p.getMap());
            p.print(Utils.getJSONObject(param.getCurOut()));
            param.getCurOut().close();
        }else if("DELETE".equals(p.subcommand)){        
            DAO.CategoriaDelete(p.conn, p.getMap());
        }
    }
}
