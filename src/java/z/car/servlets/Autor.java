package z.car.servlets;

import z.bi.ServletBase;
import z.bi.utils.Utils;
import z.car.db.dao.IPkgAutor;
import z.car.db.factory.PkgAutorFactory;
import z.car.db.mapper.PkgAutor;

public class Autor extends ServletBase {
        private IPkgAutor DAO = PkgAutorFactory.create();

    protected void doCommand(ServletBase.Parameters p) throws Exception {
        if (p.command.equals("AUTOR"))
            AUTOR(p);
    }
    
    private void AUTOR(ServletBase.Parameters p) throws Exception {
        if ("GETLIST".equals(p.subcommand)) {
            PkgAutor.Autor param = DAO.AutorGetlist(p.conn, p.getMap());
            p.print(Utils.getJSONArrayArray(param.getCurOut()));
            param.getCurOut().close();
        }else if ("GETLISTBYID".equals(p.subcommand)) {
            PkgAutor.Autor param = DAO.AutorGetlistById(p.conn, p.getMap());
            p.print(Utils.getJSONObject(param.getCurOut()));
            param.getCurOut().close();
        }else if ("INSERT".equals(p.subcommand)) {
            PkgAutor.Autor param = DAO.newAutor(p.getMap());
            param.setIdusuario(p.idusuario);
            DAO.AutorInsert(p.conn, param);
        }else if ("EDIT".equals(p.subcommand)) {
            PkgAutor.Autor param = DAO.newAutor(p.getMap());
            param.setIdusuario(p.idusuario);
            DAO.AutorEdit(p.conn, param);
        }else if("DELETE".equals(p.subcommand)){        
            DAO.AutorDelete(p.conn, p.getMap());
        }
    }
}
