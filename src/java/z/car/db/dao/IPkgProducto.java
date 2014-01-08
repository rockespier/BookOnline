package z.car.db.dao;

import java.sql.Connection;
import java.util.Map;
import z.car.db.mapper.PkgProducto;

public interface IPkgProducto {

    void ProductoGetlist(Connection conn, PkgProducto.Producto param) throws Exception;

    PkgProducto.Producto ProductoGetlist(Connection conn, Map map) throws Exception;

    PkgProducto.Producto newProducto();

    PkgProducto.Producto newProducto(Map map) throws Exception;

    PkgProducto.Producto ProductoGetlistNew(Connection conn, Map map) throws Exception;

    void ProductoGetlistNew(Connection conn, PkgProducto.Producto param) throws Exception;

    PkgProducto.Producto ProductoGetlistAll(Connection conn, Map map) throws Exception;

    void ProductoGetlistAll(Connection conn, PkgProducto.Producto param) throws Exception;

    PkgProducto.Producto ProductoGetlistById(Connection conn, Map map) throws Exception;

    void ProductoGetlistById(Connection conn, PkgProducto.Producto param) throws Exception;

    PkgProducto.Producto ProductoInsert(Connection conn, Map map) throws Exception;

    void ProductoInsert(Connection conn, PkgProducto.Producto param) throws Exception;
    
}
