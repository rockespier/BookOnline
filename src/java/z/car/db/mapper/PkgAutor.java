package z.car.db.mapper;

import z.car.db.dao.IPkgAutor;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.Map;
import z.car.db.dao.ResourceManager;

public class PkgAutor implements IPkgAutor  {

    public PkgAutor.Autor AutorGetlist(Connection conn, Map map) throws Exception {
        PkgAutor.Autor param = newAutor(map);
        AutorGetlist(conn, param);
        return param;
    }
    
    public void AutorGetlist(Connection conn, PkgAutor.Autor param) throws Exception {
        CallableStatement stmt = null;
        stmt = conn.prepareCall("{call dbo.AutorGetlist(?)}");
        stmt.setString(1, param.getEstado());
        param.setCurOut((ResultSet) stmt.executeQuery());
    }
    
    public PkgAutor.Autor AutorInsert(Connection conn, Map map) throws Exception {
        PkgAutor.Autor param = newAutor(map);
        AutorInsert(conn, param);
        return param;
    }
    
    public void AutorInsert(Connection conn, PkgAutor.Autor param) throws Exception {
        
        CallableStatement stmt = null;
        stmt = conn.prepareCall("{call dbo.AutorInsert(?,?,?,?,?,?)}");
        stmt.setString(1, param.getNombre());
        stmt.setString(2, param.getCorreo());
        stmt.setString(3, param.getBiografia());
        stmt.setString(4, param.getFoto());
        stmt.setInt(5, param.getIdusuario());
        stmt.setString(6, param.getEstado());
        
        //stmt.registerOutParameter(7,Types.VARCHAR);    
        
        stmt.execute();
        /*
        if (stmt.getString(7).length()!=0){
            throw new Exception(stmt.getString(7));
        }
        */
    }

    public PkgAutor.Autor AutorGetlistById(Connection conn, Map map) throws Exception {
        PkgAutor.Autor param = newAutor(map);
        AutorGetlistById(conn, param);
        return param;
    }
    
    public void AutorGetlistById(Connection conn, PkgAutor.Autor param) throws Exception {
        CallableStatement stmt = null;
        stmt = conn.prepareCall("{call dbo.AutorGetlistById(?)}");
        stmt.setInt(1, param.getIdautor());
        param.setCurOut((ResultSet) stmt.executeQuery());
    }
    
    public PkgAutor.Autor AutorEdit(Connection conn, Map map) throws Exception {
        PkgAutor.Autor param = newAutor(map);
        AutorEdit(conn, param);
        return param;
    }
    
    public void AutorEdit(Connection conn, PkgAutor.Autor param) throws Exception {
        CallableStatement stmt = null;

        stmt = conn.prepareCall("{call dbo.AutorEdit(?,?,?,?,?,?,?)}");
        stmt.setInt(1, param.getIdautor());
        stmt.setString(2, param.getNombre());
        stmt.setString(3, param.getCorreo());
        stmt.setString(4, param.getBiografia());
        stmt.setString(5, param.getFoto());
        stmt.setInt(6, param.getIdusuario());
        stmt.setString(7, param.getEstado());
        
        //stmt.registerOutParameter(8, Types.VARCHAR);

        stmt.execute();           
/*
        if (stmt.getString(8).length()!=0){
            throw new Exception(stmt.getString(8));
        }
*/
    }
    
    public PkgAutor.Autor AutorDelete(Connection conn, Map map) throws Exception {
        PkgAutor.Autor param = newAutor(map);
        AutorDelete(conn, param);
        return param;
    }
    
    public void AutorDelete(Connection conn, PkgAutor.Autor param) throws Exception {
        CallableStatement stmt = null;
        stmt = conn.prepareCall("{call dbo.AutorDelete(?)}");
        stmt.setInt(1, param.getIdautor());
        stmt.execute();           
    }    
    
    
    public PkgAutor.Autor newAutor() {
        return new PkgAutor.Autor();
    }

    public PkgAutor.Autor newAutor(Map map) throws Exception {
        return new PkgAutor.Autor(map);
    }
    
    public class Autor {
        
        private Integer idautor;
        private String nombre;
        private String correo;
        private String biografia;
        private String foto;
        private Integer idusuario;
        private Date fecharegistro;
        private String estado;
        private ResultSet curOut;

        public Integer getIdautor() {
            return idautor;
        }

        public void setIdautor(Integer idautor) {
            this.idautor = idautor;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getCorreo() {
            return correo;
        }

        public void setCorreo(String correo) {
            this.correo = correo;
        }

        public String getBiografia() {
            return biografia;
        }

        public void setBiografia(String biografia) {
            this.biografia = biografia;
        }

        public String getFoto() {
            return foto;
        }

        public void setFoto(String foto) {
            this.foto = foto;
        }

        public Integer getIdusuario() {
            return idusuario;
        }

        public void setIdusuario(Integer idusuario) {
            this.idusuario = idusuario;
        }

        public Date getFecharegistro() {
            return fecharegistro;
        }

        public void setFecharegistro(Date fecharegistro) {
            this.fecharegistro = fecharegistro;
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

        public Autor() {
        }
        
        public Autor(Map map) throws Exception {
            ResourceManager.populateDtoFromMap(this, map);
        }
        
        public void setValuesFromMap(Map map) throws Exception {
            ResourceManager.populateDtoFromMap(this, map);
        }

    }
    
}
