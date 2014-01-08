
package z.car.db.factory;

import z.car.db.dao.IPkgCategoria;
import z.car.db.mapper.PkgCategoria;

public class PkgCategoriaFactory {
    
    private static PkgCategoria dao = null;

    public static IPkgCategoria create() {
        if(dao == null) dao = new PkgCategoria();
        return dao;
    }
    
}
