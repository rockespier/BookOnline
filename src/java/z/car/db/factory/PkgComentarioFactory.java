/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package z.car.db.factory;

import z.car.db.dao.IPkgComentario;
import z.car.db.mapper.PkgComentario;

/**
 *
 * @author ebarrientos
 */
public class PkgComentarioFactory {
    private static PkgComentario dao = null;

    public static IPkgComentario create() {
        if(dao == null) dao = new PkgComentario();
        return dao;
    }
}
