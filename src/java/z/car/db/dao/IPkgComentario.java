/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package z.car.db.dao;

import java.sql.Connection;
import java.util.Map;
import z.car.db.mapper.PkgComentario;

/**
 *
 * @author ebarrientos
 */
public interface IPkgComentario {

    PkgComentario.Comentario ComentarioInsert(Connection conn, Map map) throws Exception;

    void ComentarioInsert(Connection conn, PkgComentario.Comentario param) throws Exception;

    PkgComentario.Comentario newComentario();

    PkgComentario.Comentario newComentario(Map map) throws Exception;
    
}
