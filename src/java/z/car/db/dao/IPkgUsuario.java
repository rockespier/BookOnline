package z.car.db.dao;

import java.sql.Connection;
import java.util.Map;
import z.car.db.mapper.PkgUsuario;


public interface IPkgUsuario {

    PkgUsuario.Usuario UsuarioInsert(Connection conn, Map map) throws Exception;

    void UsuarioInsert(Connection conn, PkgUsuario.Usuario param) throws Exception;

    PkgUsuario.Usuario newUsuario();

    PkgUsuario.Usuario newUsuario(Map map) throws Exception;

    PkgUsuario.Usuario UsuarioLogeo(Connection conn, Map map) throws Exception;

    void UsuarioLogeo(Connection conn, PkgUsuario.Usuario param) throws Exception;

    PkgUsuario.Usuario UsuarioRecuperar(Connection conn, Map map) throws Exception;

    void UsuarioRecuperar(Connection conn, PkgUsuario.Usuario param) throws Exception;

    PkgUsuario.UsuarioDetalle UsuarioDetalleInsert(Connection conn, Map map) throws Exception;

    void UsuarioDetalleInsert(Connection conn, PkgUsuario.UsuarioDetalle param) throws Exception;

    PkgUsuario.UsuarioDetalle newUsuarioDetalle();

    PkgUsuario.UsuarioDetalle newUsuarioDetalle(Map map) throws Exception;

    PkgUsuario.UsuarioDetalle UsuarioDetalleGetById(Connection conn, Map map) throws Exception;

    void UsuarioDetalleGetById(Connection conn, PkgUsuario.UsuarioDetalle param) throws Exception;
    
}
