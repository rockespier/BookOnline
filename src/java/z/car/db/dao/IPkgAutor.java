/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package z.car.db.dao;

import java.sql.Connection;
import java.util.Map;
import z.car.db.mapper.PkgAutor;

/**
 *
 * @author ebarrientos
 */
public interface IPkgAutor {

    PkgAutor.Autor AutorInsert(Connection conn, Map map) throws Exception;

    void AutorInsert(Connection conn, PkgAutor.Autor param) throws Exception;

    PkgAutor.Autor newAutor();

    PkgAutor.Autor newAutor(Map map) throws Exception;

    PkgAutor.Autor AutorGetlist(Connection conn, Map map) throws Exception;

    void AutorGetlist(Connection conn, PkgAutor.Autor param) throws Exception;

    PkgAutor.Autor AutorDelete(Connection conn, Map map) throws Exception;

    void AutorDelete(Connection conn, PkgAutor.Autor param) throws Exception;

    PkgAutor.Autor AutorEdit(Connection conn, Map map) throws Exception;

    void AutorEdit(Connection conn, PkgAutor.Autor param) throws Exception;

    PkgAutor.Autor AutorGetlistById(Connection conn, Map map) throws Exception;

    void AutorGetlistById(Connection conn, PkgAutor.Autor param) throws Exception;
    
}
