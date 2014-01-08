/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package z.car.db.factory;

import z.car.db.dao.IPkgPedido;
import z.car.db.mapper.PkgPedido;

/**
 *
 * @author ebarrientos
 */
public class PkgPedidoFactory {
    private static PkgPedido dao = null;

    public static IPkgPedido create() {
        if(dao == null) dao = new PkgPedido();
        return dao;
    }
}
