package z.car.db.mapper;

import z.car.db.dao.IPkgComentario;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.util.Map;
import z.car.db.dao.ResourceManager;

public class PkgComentario implements IPkgComentario {

    @Override
    public PkgComentario.Comentario ComentarioInsert(Connection conn, Map map) throws Exception {
        PkgComentario.Comentario param = newComentario(map);
        ComentarioInsert(conn, param);
        return param;
    }
    
    @Override
    public void ComentarioInsert(Connection conn, PkgComentario.Comentario param) throws Exception {
        
        CallableStatement stmt = null;
        stmt = conn.prepareCall("{call dbo.usp_ins_comentario(?,?,?,?)}");
        stmt.setString(1, param.getDetalle());
        stmt.setInt(4, 0);        
        stmt.execute();
        
    }

    @Override
    public PkgComentario.Comentario newComentario() {
        return new PkgComentario.Comentario();
    }

    @Override
    public PkgComentario.Comentario newComentario(Map map) throws Exception {
        return new PkgComentario.Comentario(map);
    }
    
    public class Comentario {
        
        private Integer idcomentario;
        private String idproducto;
        private String detalle;
        private String idusuario;
        private Date fecharegistro;
        private String estado;

        public Integer getIdcomentario() {
            return idcomentario;
        }

        public void setIdcomentario(Integer idcomentario) {
            this.idcomentario = idcomentario;
        }

        public String getIdproducto() {
            return idproducto;
        }

        public void setIdproducto(String idproducto) {
            this.idproducto = idproducto;
        }

        public String getDetalle() {
            return detalle;
        }

        public void setDetalle(String detalle) {
            this.detalle = detalle;
        }

        public String getIdusuario() {
            return idusuario;
        }

        public void setIdusuario(String idusuario) {
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


        public Comentario() {
        }
        
        public Comentario(Map map) throws Exception {
            ResourceManager.populateDtoFromMap(this, map);
        }
        
        public void setValuesFromMap(Map map) throws Exception {
            ResourceManager.populateDtoFromMap(this, map);
        }

    }
    
}
