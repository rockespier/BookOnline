package z.car.db.mapper;

//import z.car.db.dao.IPkgCategoria;
//import java.sql.CallableStatement;
import java.sql.CallableStatement;
import java.sql.Connection;
import z.car.db.dao.*;
import java.sql.ResultSet;
import java.sql.SQLOutput;
//import java.sql.SQLOutput;
import java.sql.Statement;
import java.sql.Types;
import java.util.Map;

public class PkgCategoria implements IPkgCategoria {

    
    
    public PkgCategoria.Categoria CategoriaGetlistPadre(Connection conn, Map map) throws Exception {
        PkgCategoria.Categoria param = newCategoria(map);
        CategoriaGetlistPadre(conn, param);
        return param;
    }
    
    public void CategoriaGetlistPadre(Connection conn, PkgCategoria.Categoria param) throws Exception {
        CallableStatement stmt = null;
        stmt = conn.prepareCall("{call dbo.CategoriaGetlistPadre()}");
        param.setCurOut((ResultSet) stmt.executeQuery());
    }
    
    public PkgCategoria.Categoria CategoriaGetlist(Connection conn, Map map) throws Exception {
        PkgCategoria.Categoria param = newCategoria(map);
        CategoriaGetlist(conn, param);
        return param;
    }
    
    public void CategoriaGetlist(Connection conn, PkgCategoria.Categoria param) throws Exception {
        CallableStatement stmt = null;
        stmt = conn.prepareCall("{call dbo.CategoriaGetlist(?)}");
        stmt.setString(1, param.getEstado());
        param.setCurOut((ResultSet) stmt.executeQuery());
    }

    public PkgCategoria.Categoria CategoriaInsert(Connection conn, Map map) throws Exception {
        PkgCategoria.Categoria param = newCategoria(map);
        CategoriaInsert(conn, param);
        return param;
    }
    
    public void CategoriaInsert(Connection conn, PkgCategoria.Categoria param) throws Exception {
        CallableStatement stmt = null;
        stmt = conn.prepareCall("{call dbo.CategoriaInsert(?,?,?,?,?)}");
        stmt.setString(1, param.getNombre());
        stmt.setString(2, param.getPadre());
        stmt.setInt(3, param.getIdusuario());
        stmt.setString(4, param.getEstado());
        
        stmt.registerOutParameter(5, Types.VARCHAR);

        stmt.execute();           

        if (stmt.getString(5).length()!=0){
            throw new Exception(stmt.getString(5));
        }

    }
    
    public PkgCategoria.Categoria CategoriaGetlistById(Connection conn, Map map) throws Exception {
        PkgCategoria.Categoria param = newCategoria(map);
        CategoriaGetlistById(conn, param);
        return param;
    }
    
    public void CategoriaGetlistById(Connection conn, PkgCategoria.Categoria param) throws Exception {
        CallableStatement stmt = null;
        stmt = conn.prepareCall("{call dbo.CategoriaGetlistById(?)}");
        stmt.setInt(1, param.getIdcategoria());
        param.setCurOut((ResultSet) stmt.executeQuery());
    }
    
    public PkgCategoria.Categoria CategoriaEdit(Connection conn, Map map) throws Exception {
        PkgCategoria.Categoria param = newCategoria(map);
        CategoriaEdit(conn, param);
        return param;
    }
    
    public void CategoriaEdit(Connection conn, PkgCategoria.Categoria param) throws Exception {
        CallableStatement stmt = null;
        stmt = conn.prepareCall("{call dbo.CategoriaEdit(?,?,?,?,?,?)}");
        stmt.setInt(1, param.getIdcategoria());
        stmt.setString(2, param.getNombre());
        stmt.setString(3, param.getPadre());
        stmt.setInt(4, param.getIdusuario());
        stmt.setString(5, param.getEstado());
        
        stmt.registerOutParameter(6, Types.VARCHAR);

        stmt.execute();           

        if (stmt.getString(6).length()!=0){
            throw new Exception(stmt.getString(6));
        }

    }
    
    public PkgCategoria.Categoria CategoriaDelete(Connection conn, Map map) throws Exception {
        PkgCategoria.Categoria param = newCategoria(map);
        CategoriaDelete(conn, param);
        return param;
    }
    
    public void CategoriaDelete(Connection conn, PkgCategoria.Categoria param) throws Exception {
        CallableStatement stmt = null;
        stmt = conn.prepareCall("{call dbo.CategoriaDelete(?)}");
        stmt.setInt(1, param.getIdcategoria());
        stmt.execute();           
    }
    
    
    public PkgCategoria.Categoria newCategoria() {
        return new Categoria();
    }

    public PkgCategoria.Categoria newCategoria(Map map) throws Exception {
        return new Categoria(map);
    }
    
    public class Categoria {
        private int idcategoria;
        private String nombre;
        private String padre;
        private int idusuario;
        private String estado;
        private ResultSet curOut;

        public int getIdcategoria() {
            return idcategoria;
        }

        public void setIdcategoria(int idcategoria) {
            this.idcategoria = idcategoria;
        }
        
        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getPadre() {
            return padre;
        }

        public void setPadre(String padre) {
            this.padre = padre;
        }

        public int getIdusuario() {
            return idusuario;
        }

        public void setIdusuario(int idusuario) {
            this.idusuario = idusuario;
        }
        
        public String getEstado() {
            return estado;
        }

        public void setEstado(String value) {
            this.estado = value;
        }

        public ResultSet getCurOut() {
            return curOut;
        }

        public void setCurOut(ResultSet value) {
            this.curOut = value;
        }
   
        public Categoria() {
        }

        public Categoria(Map map) throws Exception {
            ResourceManager.populateDtoFromMap(this, map);
        }
        
        public void setValuesFromMap(Map map) throws Exception {
            ResourceManager.populateDtoFromMap(this, map);
        }


    }
    
    
    
}


