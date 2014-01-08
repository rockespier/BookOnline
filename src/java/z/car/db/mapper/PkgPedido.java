package z.car.db.mapper;


import z.car.db.dao.IPkgPedido;
import java.sql.CallableStatement;
import java.sql.Connection;
//import java.sql.Date;
import java.util.Date;
import java.sql.Types;
import java.util.Map;
import z.car.db.dao.ResourceManager;

public class PkgPedido implements IPkgPedido   {

    public PkgPedido.Pedido PedidoInsert(Connection conn, Map map) throws Exception {
        PkgPedido.Pedido param = newPedido(map);
        PedidoInsert(conn, param);
        return param;
    }
    
    public void PedidoInsert(Connection conn, PkgPedido.Pedido param) throws Exception {
        CallableStatement stmt = null;
        stmt = conn.prepareCall("{call dbo.PedidoInsert(?,?,?)}");
        stmt.setInt(1, param.getIdusuario());
        stmt.setDate(2, param.getFechaentrega()==null ? null : new java.sql.Date(param.getFechaentrega().getTime()));
        stmt.registerOutParameter(3, Types.INTEGER);
        stmt.execute();
        param.setIdpedido(stmt.getInt(3));
    }

    
        public PkgPedido.PedidoDetalle PedidoDetalleInsert(Connection conn, Map map) throws Exception {
        PkgPedido.PedidoDetalle param = newPedidoDetalle(map);
        PedidoDetalleInsert(conn, param);
        return param;
    }
    
    public void PedidoDetalleInsert(Connection conn, PkgPedido.PedidoDetalle param) throws Exception {
        
        CallableStatement stmt = null;
        stmt = conn.prepareCall("{call dbo.PedidoDetalleInsert(?,?,?,?)}");
        stmt.setInt(1, param.getIdpedido());
        stmt.setInt(2, param.getIdproducto());
        stmt.setInt(3, param.getCantidad());
        stmt.setFloat(4, param.getPrecio());
        stmt.execute();
    }

    public PkgPedido.Pedido newPedido() {
        return new PkgPedido.Pedido();
    }

    public PkgPedido.Pedido newPedido(Map map) throws Exception {
        return new PkgPedido.Pedido(map);
    }
    

    public class Pedido {
        
        private Integer idpedido;
        private Integer idusuario;
        private Date fechapedido;
        private Date fechaentrega;
        private String estado;

        public Integer getIdpedido() {
            return idpedido;
        }

        public void setIdpedido(Integer idpedido) {
            this.idpedido = idpedido;
        }

        public Integer getIdusuario() {
            return idusuario;
        }

        public void setIdusuario(Integer idusuario) {
            this.idusuario = idusuario;
        }

        public Date getFechapedido() {
            return fechapedido;
        }

        public void setFechapedido(Date fechapedido) {
            this.fechapedido = fechapedido;
        }

        public Date getFechaentrega() {
            return fechaentrega;
        }

        public void setFechaentrega(Date fechaentrega) {
            this.fechaentrega = fechaentrega;
        }

        public String getEstado() {
            return estado;
        }

        public void setEstado(String estado) {
            this.estado = estado;
        }
        
        public Pedido() {
        }
        
        public Pedido(Map map) throws Exception {
            ResourceManager.populateDtoFromMap(this, map);
        }
        
        public void setValuesFromMap(Map map) throws Exception {
            ResourceManager.populateDtoFromMap(this, map);
        }

    }
    

    public PkgPedido.PedidoDetalle newPedidoDetalle() {
        return new PkgPedido.PedidoDetalle();
    }

    public PkgPedido.PedidoDetalle newPedidoDetalle(Map map) throws Exception {
        return new PkgPedido.PedidoDetalle(map);
    }
    
    public class PedidoDetalle {
        
        private Integer idpedidodetalle;
        private Integer idpedido;
        private Integer idproducto;
        private Integer cantidad;
        private Float precio;
        private Float subtotal;

        public Integer getIdpedidodetalle() {
            return idpedidodetalle;
        }

        public void setIdpedidodetalle(Integer idpedidodetalle) {
            this.idpedidodetalle = idpedidodetalle;
        }

        public Integer getIdpedido() {
            return idpedido;
        }

        public Integer getIdproducto() {
            return idproducto;
        }

        public void setIdproducto(Integer idproducto) {
            this.idproducto = idproducto;
        }
        
        public void setIdpedido(Integer idpedido) {
            this.idpedido = idpedido;
        }

        public Integer getCantidad() {
            return cantidad;
        }

        public void setCantidad(Integer cantidad) {
            this.cantidad = cantidad;
        }

        public Float getPrecio() {
            return precio;
        }

        public void setPrecio(Float precio) {
            this.precio = precio;
        }

        public Float getSubtotal() {
            return this.cantidad*this.precio;
        }

        public void setSubtotal(Float subtotal) {
            this.subtotal = subtotal;
        }

        
        public PedidoDetalle() {
        }
        
        public PedidoDetalle(Map map) throws Exception {
            ResourceManager.populateDtoFromMap(this, map);
        }
        
        public void setValuesFromMap(Map map) throws Exception {
            ResourceManager.populateDtoFromMap(this, map);
        }

    }
    
}
