package z.car.servlets;

import java.util.ArrayList;
import z.bi.ServletBase;
import z.bi.security.Security;
import z.bi.utils.Utils;
import z.bi.mail.Send;
import z.car.db.dao.IPkgPedido;
import z.car.db.dao.IPkgUsuario;
import z.car.db.factory.PkgPedidoFactory;
import z.car.db.factory.PkgUsuarioFactory;
import z.car.db.mapper.PkgPedido;
import z.car.db.mapper.PkgUsuario;

public class Usuario extends ServletBase {
    private IPkgUsuario DAO = PkgUsuarioFactory.create();

    protected void doCommand(ServletBase.Parameters p) throws Exception {
        if (p.command.equals("USUARIO"))
            USUARIO(p);
    }
    
    private void USUARIO(ServletBase.Parameters p) throws Exception {
        if ("GETLISTBYID".equals(p.subcommand)) {
            PkgUsuario.UsuarioDetalle param = DAO.UsuarioDetalleGetById(p.conn, p.getMap());
            p.print(Utils.getJSONObject(param.getCurOut()));
            param.getCurOut().close();
        }else if ("INSERT".equals(p.subcommand)) {
            PkgUsuario.Usuario param = DAO.newUsuario(p.getMap());
            param.setRol("0");param.setEstado("1");
            DAO.UsuarioInsert(p.conn, param);
            p.subcommand="LOGEO";doCommand(p);
        }else if ("LOGEO".equals(p.subcommand)) {
            PkgUsuario.Usuario param = DAO.UsuarioLogeo(p.conn, p.getMap());
            Security.setUserToCookie(p, Utils.getJSONObject(param.getCurOut()));
            p.print(Utils.getJSONObject(param.getCurOut()));
            param.getCurOut().close();
        }else if ("RECUPERAR".equals(p.subcommand)) {
            Send s = new Send();
            
            //DAO.UsuarioRecuperar(p.conn, p.getMap());
        }else if ("PAGAR".equals(p.subcommand)) {
            
            PkgUsuario.UsuarioDetalle paramUsuariodetalle = DAO.newUsuarioDetalle(p.getMap());
            paramUsuariodetalle.setIdusuario(p.idusuario);
            DAO.UsuarioDetalleInsert(p.conn, paramUsuariodetalle);
            
            IPkgPedido DAOPedido = PkgPedidoFactory.create();
            
            PkgPedido.Pedido paramPedido = DAOPedido.newPedido(p.getMap());
            paramPedido.setIdusuario(paramUsuariodetalle.getIdusuario());
            paramPedido.setEstado("1");
            DAOPedido.PedidoInsert(p.conn, paramPedido);
            
           
            String[] car = ((String)(p.getMap().get("carrito"))).split("<->");
            
            for (int i = 0; i < car.length; i++) {
                String[] producto = car[i].split("]]");
                
                PkgPedido.PedidoDetalle paramPedidodetalle = DAOPedido.newPedidoDetalle();
                
                paramPedidodetalle.setIdpedido(paramPedido.getIdpedido());
                paramPedidodetalle.setIdproducto(Integer.parseInt(producto[0]));
                paramPedidodetalle.setCantidad(Integer.parseInt(producto[1]));
                paramPedidodetalle.setPrecio(Float.parseFloat(producto[2]));
                
                DAOPedido.PedidoDetalleInsert(p.conn, paramPedidodetalle);
            }
        } 
    }
}
