package z.car.db.dao;

import java.sql.Connection;
import java.util.Map;
import z.car.db.mapper.PkgCategoria;

public interface IPkgCategoria {

    PkgCategoria.Categoria newCategoria();

    PkgCategoria.Categoria newCategoria(Map map) throws Exception;
    
    void CategoriaGetlist(Connection conn, PkgCategoria.Categoria param) throws Exception;

    PkgCategoria.Categoria CategoriaGetlist(Connection conn, Map map) throws Exception;

    void CategoriaInsert(Connection conn, PkgCategoria.Categoria param) throws Exception;

    PkgCategoria.Categoria CategoriaInsert(Connection conn, Map map) throws Exception;

    PkgCategoria.Categoria CategoriaGetlistPadre(Connection conn, Map map) throws Exception;

    void CategoriaGetlistPadre(Connection conn, PkgCategoria.Categoria param) throws Exception;

    PkgCategoria.Categoria CategoriaEdit(Connection conn, Map map) throws Exception;

    void CategoriaEdit(Connection conn, PkgCategoria.Categoria param) throws Exception;

    PkgCategoria.Categoria CategoriaGetlistById(Connection conn, Map map) throws Exception;

    void CategoriaGetlistById(Connection conn, PkgCategoria.Categoria param) throws Exception;

    PkgCategoria.Categoria CategoriaDelete(Connection conn, Map map) throws Exception;

    void CategoriaDelete(Connection conn, PkgCategoria.Categoria param) throws Exception;
    
}
