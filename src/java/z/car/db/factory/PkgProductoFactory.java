
package z.car.db.factory;

import z.car.db.dao.IPkgProducto;
import z.car.db.mapper.PkgProducto;

public class PkgProductoFactory {
    
    private static PkgProducto dao = null;

    public static IPkgProducto create() {
        if(dao == null) dao = new PkgProducto();
        return dao;
    }
    
}
