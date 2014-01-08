/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package z.car.db.factory;

import z.car.db.dao.IPkgUsuario;
import z.car.db.mapper.PkgUsuario;

/**
 *
 * @author ebarrientos
 */
public class PkgUsuarioFactory {
    private static PkgUsuario dao = null;

    public static IPkgUsuario create() {
        if(dao == null) dao = new PkgUsuario();
        return dao;
    }
}
