/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package z.car.db.dao;

import java.sql.Connection;
import java.util.Map;
import z.car.db.mapper.PkgPedido;

/**
 *
 * @author zega
 */
public interface IPkgPedido {

    PkgPedido.Pedido PedidoInsert(Connection conn, Map map) throws Exception;

    void PedidoInsert(Connection conn, PkgPedido.Pedido param) throws Exception;

    PkgPedido.Pedido newPedido();

    PkgPedido.Pedido newPedido(Map map) throws Exception;

    PkgPedido.PedidoDetalle PedidoDetalleInsert(Connection conn, Map map) throws Exception;

    void PedidoDetalleInsert(Connection conn, PkgPedido.PedidoDetalle param) throws Exception;

    PkgPedido.PedidoDetalle newPedidoDetalle();

    PkgPedido.PedidoDetalle newPedidoDetalle(Map map) throws Exception;
    
}
