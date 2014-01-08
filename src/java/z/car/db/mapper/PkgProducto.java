package z.car.db.mapper;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import z.car.db.dao.*;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.Map;


public class PkgProducto implements IPkgProducto {

    
    public PkgProducto.Producto ProductoInsert(Connection conn, Map map) throws Exception {
        PkgProducto.Producto param = newProducto(map);
        ProductoInsert(conn, param);
        return param;
    }
    
    public void ProductoInsert(Connection conn, PkgProducto.Producto param) throws Exception {
        CallableStatement stmt = null;
        
        stmt = conn.prepareCall("{call dbo.ProductoInsert(?,?,?,?,?,?,?,?,?)}");
        stmt.setString(1, param.getNombre());
        stmt.setString(2, param.getDetalle());
        stmt.setString(3, param.getFoto());
        stmt.setInt(4, param.getIdcategoria());
        stmt.setInt(5, param.getIdautor());
        stmt.setFloat(6, param.getPrecio());
        stmt.setInt(7, param.getStock());
        stmt.setInt(8, param.getIdusuario());
        stmt.setString(9, param.getEstado());
        
        stmt.execute();
    }

    
    public PkgProducto.Producto ProductoGetlistById(Connection conn, Map map) throws Exception {
        PkgProducto.Producto param = newProducto(map);
        ProductoGetlistById(conn, param);
        return param;
    }
    
    public void ProductoGetlistById(Connection conn, PkgProducto.Producto param) throws Exception {
        CallableStatement stmt = null;
        stmt = conn.prepareCall("{call dbo.ProductoGetlistById(?)}");
        stmt.setInt(1, param.getIdproducto());
        param.setCurOut((ResultSet) stmt.executeQuery());
    }
    
    
    public PkgProducto.Producto ProductoDelete(Connection conn, Map map) throws Exception {
        PkgProducto.Producto param = newProducto(map);
        ProductoDelete(conn, param);
        return param;
    }
    
    public void ProductoDelete(Connection conn, PkgProducto.Producto param) throws Exception {
        CallableStatement stmt = null;
        stmt = conn.prepareCall("{call dbo.ProductoDelete(?)}");
        stmt.setInt(1, param.getIdproducto());
        stmt.execute();           
    }    
    
    
    public PkgProducto.Producto ProductoGetlist(Connection conn, Map map) throws Exception {
        PkgProducto.Producto param = newProducto(map);
        ProductoGetlist(conn, param);
        return param;
    }
    
    public void ProductoGetlist(Connection conn, PkgProducto.Producto param) throws Exception {
        CallableStatement stmt = null;
        stmt = conn.prepareCall("{call dbo.ProductoGetlist(?)}");
        stmt.setInt(1, param.getIdcategoria());
        param.setCurOut((ResultSet) stmt.executeQuery());
    }
    
    public PkgProducto.Producto ProductoGetlistNew(Connection conn, Map map) throws Exception {
        PkgProducto.Producto param = newProducto(map);
        ProductoGetlistNew(conn, param);
        return param;
    }
    
    public void ProductoGetlistNew(Connection conn, PkgProducto.Producto param) throws Exception {
        CallableStatement stmt = null;
        stmt = conn.prepareCall("{call dbo.ProductoGetlistNew()}");
        param.setCurOut((ResultSet) stmt.executeQuery());
    }
    
    public PkgProducto.Producto ProductoGetlistAll(Connection conn, Map map) throws Exception {
        PkgProducto.Producto param = newProducto(map);
        ProductoGetlistAll(conn, param);
        return param;
    }
    
    public void ProductoGetlistAll(Connection conn, PkgProducto.Producto param) throws Exception {
        CallableStatement stmt = null;
        stmt = conn.prepareCall("{call dbo.ProductoGetlistAll()}");
        param.setCurOut((ResultSet) stmt.executeQuery());
    }
    
    public PkgProducto.Producto newProducto() {
        return new Producto();
    }

    public PkgProducto.Producto newProducto(Map map) throws Exception {
        return new Producto(map);
    }
    
    public class Producto {

        private Integer idproducto;
        private String nombre;
        private String detalle;
        private String foto;
        private Integer idcategoria;
        private Integer idautor;
        private float precio;
        private Integer stock;
        private Integer idusuario;
        private Date fecharegistro;
        private String estado;
        private ResultSet curOut;

        public Integer getIdproducto() {
            return idproducto;
        }

        public void setIdproducto(Integer idproducto) {
            this.idproducto = idproducto;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getDetalle() {
            return detalle;
        }

        public void setDetalle(String detalle) {
            this.detalle = detalle;
        }

        public String getFoto() {
            return foto;
        }

        public void setFoto(String foto) {
            this.foto = foto;
        }

        public Integer getIdcategoria() {
            return idcategoria;
        }

        public void setIdcategoria(Integer idcategoria) {
            this.idcategoria = idcategoria;
        }

        public Integer getIdautor() {
            return idautor;
        }

        public void setIdautor(Integer idautor) {
            this.idautor = idautor;
        }

        public float getPrecio() {
            return precio;
        }

        public void setPrecio(float precio) {
            this.precio = precio;
        }

        public Integer getStock() {
            return stock;
        }

        public void setStock(Integer stock) {
            this.stock = stock;
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

        public Producto() {
        }

        public Producto(Map map) throws Exception {
            ResourceManager.populateDtoFromMap(this, map);
        }

        public void setValuesFromMap(Map map) throws Exception {
            ResourceManager.populateDtoFromMap(this, map);
        }

    }
    
}


