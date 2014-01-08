/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package z.car.db.factory;

import z.car.db.dao.IPkgAutor;
import z.car.db.mapper.PkgAutor;

/**
 *
 * @author ebarrientos
 */
public class PkgAutorFactory {
    private static PkgAutor dao = null;

    public static IPkgAutor create() {
        if(dao == null) dao = new PkgAutor();
        return dao;
    }
}
