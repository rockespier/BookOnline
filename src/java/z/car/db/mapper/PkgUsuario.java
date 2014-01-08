package z.car.db.mapper;

import z.car.db.dao.IPkgUsuario;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.Map;
import z.car.db.dao.ResourceManager;

public class PkgUsuario implements IPkgUsuario {
    
    public PkgUsuario.UsuarioDetalle UsuarioDetalleGetById(Connection conn, Map map) throws Exception {
        PkgUsuario.UsuarioDetalle param = newUsuarioDetalle(map);
        UsuarioDetalleGetById(conn, param);
        return param;
    }
    
    public void UsuarioDetalleGetById(Connection conn, PkgUsuario.UsuarioDetalle param) throws Exception {
        CallableStatement stmt = null;
        stmt = conn.prepareCall("{call dbo.UsuarioDetalleGetById(?)}");
        stmt.setInt (1, param.getIdusuario());
        param.setCurOut((ResultSet) stmt.executeQuery());     
    }

    public PkgUsuario.UsuarioDetalle UsuarioDetalleInsert(Connection conn, Map map) throws Exception {
        PkgUsuario.UsuarioDetalle param = newUsuarioDetalle(map);
        UsuarioDetalleInsert(conn, param);
        return param;
    }
    
    public void UsuarioDetalleInsert(Connection conn, PkgUsuario.UsuarioDetalle param) throws Exception {
        CallableStatement stmt = null;
        stmt = conn.prepareCall("{call dbo.UsuarioDetalleInsert(?,?,?,?,?,?,?,?,?,?)}");
        stmt.setInt (1, param.getIdusuario());
        stmt.setString(2, param.getNombre());
        stmt.setString(3, param.getApellidos());
        stmt.setString(4, param.getTelefono());
        stmt.setString(5, param.getDireccion());
        stmt.setString(6, param.getDistrito());
        stmt.setString(7, param.getTarjetatipo());
        stmt.setString(8, param.getTarjetanumero());
        stmt.setString(9, param.getTarjetaexpiracionmes());
        stmt.setString(10, param.getTarjetaexpiracionano());
        stmt.execute();           
    }
    
    public PkgUsuario.Usuario UsuarioRecuperar(Connection conn, Map map) throws Exception {
        PkgUsuario.Usuario param = newUsuario(map);
        UsuarioRecuperar(conn, param);
        return param;
    }
    
    public void UsuarioRecuperar(Connection conn, PkgUsuario.Usuario param) throws Exception {
        CallableStatement stmt = null;
        stmt = conn.prepareCall("{call dbo.UsuarioRecuperar(?)}");
        stmt.setString(1, param.getCorreo());
        stmt.execute();           
    }

    
    public PkgUsuario.Usuario UsuarioLogeo(Connection conn, Map map) throws Exception {
        PkgUsuario.Usuario param = newUsuario(map);
        UsuarioLogeo(conn, param);
        return param;
    }
    
    public void UsuarioLogeo(Connection conn, PkgUsuario.Usuario param) throws Exception {
        CallableStatement stmt = null;
        stmt = conn.prepareCall("{call dbo.UsuarioLogeo(?,?)}");
        stmt.setString(1, param.getNombre());
        stmt.setString(2, param.getClave());
        //stmt.registerOutParameter(3, Types.VARCHAR);
        
        param.setCurOut((ResultSet) stmt.executeQuery());
/*
        if (stmt.getString(3).length()!=0){
            throw new Exception("Usuario o contraseña inválida.");
        }
        */
    }

    
    public PkgUsuario.Usuario UsuarioInsert(Connection conn, Map map) throws Exception {
        PkgUsuario.Usuario param = newUsuario(map);
        UsuarioInsert(conn, param);
        return param;
    }
    
    public void UsuarioInsert(Connection conn, PkgUsuario.Usuario param) throws Exception {
        CallableStatement stmt = null;
        stmt = conn.prepareCall("{call dbo.UsuarioInsert(?,?,?,?,?,?)}");
        stmt.setString(1, param.getNombre());
        stmt.setString(2, param.getClave());
        stmt.setString(3, param.getCorreo());
        stmt.setString(4, param.getRol());        
        stmt.setString(5, param.getEstado());        
        stmt.registerOutParameter(6, Types.VARCHAR); 
        stmt.execute();
        
        if (stmt.getString(6).length()!=0){
            throw new Exception(stmt.getString(6));
        }
    }

    
    public PkgUsuario.Usuario newUsuario() {
        return new PkgUsuario.Usuario();
    }

    public PkgUsuario.Usuario newUsuario(Map map) throws Exception {
        return new PkgUsuario.Usuario(map);
    }

   
    public class Usuario {
        
        private Integer idusuario;
        private String nombre;
        private String clave;
        private String correo;
        private String rol;
        private String estado;
        private ResultSet curOut;

        public Integer getIdusuario() {
            return idusuario;
        }

        public void setIdusuario(Integer idusuario) {
            this.idusuario = idusuario;
        }
        
        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getClave() {
            return clave;
        }

        public void setClave(String clave) {
            this.clave = clave;
        }

        public String getCorreo() {
            return correo;
        }

        public void setCorreo(String correo) {
            this.correo = correo;
        }

        public String getRol() {
            return rol;
        }

        public void setRol(String rol) {
            this.rol = rol;
        }
        
        public String getEstado() {
            return estado;
        }

        public void setEstado(String estado) {
            this.estado = estado;
        }        
        
        public ResultSet getCurOut() {
            return curOut;
        }

        public void setCurOut(ResultSet curOut) {
            this.curOut = curOut;
        }

        
        public Usuario() {
        }
        
        public Usuario(Map map) throws Exception {
            ResourceManager.populateDtoFromMap(this, map);
        }
        
        public void setValuesFromMap(Map map) throws Exception {
            ResourceManager.populateDtoFromMap(this, map);
        }

    }
    
    public PkgUsuario.UsuarioDetalle newUsuarioDetalle() {
        return new PkgUsuario.UsuarioDetalle();
    }

    public PkgUsuario.UsuarioDetalle newUsuarioDetalle(Map map) throws Exception {
        return new PkgUsuario.UsuarioDetalle(map);
    }
    
    public class UsuarioDetalle {
        
        private Integer idusuario;
        private String nombre;
        private String apellidos;
        private String telefono;
        private String direccion;
        private String distrito;
        private String tarjetatipo;
        private String tarjetanumero;
        private String tarjetaexpiracionmes;
        private String tarjetaexpiracionano;
        private ResultSet curOut;

        public Integer getIdusuario() {
            return idusuario;
        }

        public void setIdusuario(Integer idusuario) {
            this.idusuario = idusuario;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getApellidos() {
            return apellidos;
        }

        public void setApellidos(String apellidos) {
            this.apellidos = apellidos;
        }

        public String getTelefono() {
            return telefono;
        }

        public void setTelefono(String telefono) {
            this.telefono = telefono;
        }

        public String getDireccion() {
            return direccion;
        }

        public void setDireccion(String direccion) {
            this.direccion = direccion;
        }

        public String getDistrito() {
            return distrito;
        }

        public void setDistrito(String distrito) {
            this.distrito = distrito;
        }

        public String getTarjetatipo() {
            return tarjetatipo;
        }

        public void setTarjetatipo(String tarjetatipo) {
            this.tarjetatipo = tarjetatipo;
        }

        public String getTarjetanumero() {
            return tarjetanumero;
        }

        public void setTarjetanumero(String tarjetanumero) {
            this.tarjetanumero = tarjetanumero;
        }
       
        public String getTarjetaexpiracionmes() {
            return tarjetaexpiracionmes;
        }

        public void setTarjetaexpiracionmes(String tarjetaexpiracionmes) {
            this.tarjetaexpiracionmes = tarjetaexpiracionmes;
        }

        public String getTarjetaexpiracionano() {
            return tarjetaexpiracionano;
        }

        public void setTarjetaexpiracionano(String tarjetaexpiracionano) {
            this.tarjetaexpiracionano = tarjetaexpiracionano;
        }
        
        public ResultSet getCurOut() {
            return curOut;
        }

        public void setCurOut(ResultSet curOut) {
            this.curOut = curOut;
        }
        
        public UsuarioDetalle() {
        }
        
        public UsuarioDetalle(Map map) throws Exception {
            ResourceManager.populateDtoFromMap(this, map);
        }
        
        public void setValuesFromMap(Map map) throws Exception {
            ResourceManager.populateDtoFromMap(this, map);
        }

    }
    
    
    
}
