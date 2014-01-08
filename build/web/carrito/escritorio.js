Ext.ns('z.carrito.escritorio');

z.carrito.escritorio.show = function () {

    return new Ext.Panel(Ext.apply({
        title: '',
        frmDetalle:null,
        frmCarritoLista:null,
        frmCarritoDetalle:null,
        store:null,
        me:null,
        frmAdministrador:null,
        opti:null,
        initComponent: function () {
            me=this;

            //var requerido = '<span style="color:red;font-weight:bold" data-qtip="Requerido">*</span>';

            me.frmDetalle = Ext.widget({
                xtype: 'form',
                layout: 'form',
                autoScroll: true,
                region: 'north',
                collapsible: false,
                id: 'Formdetalle',
                frame: false,
                split:true,
                title: '',
                bodyPadding: '5 5 0',
                fieldDefaults: {
                    msgTarget: 'side',
                    labelWidth: 75
                },
                /*plugins: {
                    ptype: 'datatip'
                },*/
                defaultType: 'textfield',
                
                items: [{
                    xtype: 'displayfield',
                    fieldLabel: ''
                    //name: 'articuloid',
                    //tpl: Ext.create('Ext.XTemplate','<tpl for="."><p align="center"><img width="64" height="64" src="carrito/imagenes/{[values.articuloid]}.jpg" /></p></tpl>')
                },{
                    xtype: 'hiddenfield',
                    name: 'idproducto',
                    value: ''
                },{
                    xtype: 'displayfield',
                    fieldLabel: 'Articulo:'
                },{
                    xtype: 'displayfield',
                    name: 'nombre',
                    fieldLabel: '',
                    value: ''
                },{
                    xtype: 'displayfield',
                    fieldLabel: 'Detalle:'
                },{
                    xtype: 'displayfield',
                    name: 'detalle',
                    fieldLabel: '',
                    value: ''
                },{
                    xtype: 'displayfield',
                    fieldLabel: 'Precio:',
                    name: 'precio',
                    value: ''
                }],
                buttons: [{
                    text: 'Agregame',
                    handler: me.onArticuloAgregarClic
                }]
            });
            me.dataview = Ext.create('Ext.view.View', {
                deferInitialRefresh: false,
                //store: SIMPLESTORE([], 'idproducto,nombre,detalle,foto,categoria,autor,precio,stock'),
                store: STORE('Producto', { C: 'PRODUCTO', S: 'GETLISTALL', estado:3 }, 'idproducto,nombre,detalle,foto,categoria,autor,precio,stock'),
                tpl  : Ext.create('Ext.XTemplate',
                    '<tpl for=".">',
                        '<div class="phone">',
                            '<img width="120" height="120" src="carrito/imagenes/{[values.foto]}.jpg" />',
                            '<strong>{nombre}</strong>',
                            '<span style="color:red">S/. {precio} </span>',
                        '</div>',
                    '</tpl>'
                ),
/*
                plugins : [
                    Ext.create('Ext.ux.DataView.Animated', {
                        duration  : 550,
                        idProperty: 'id'
                    })
                ],*/
                id: 'pnlcenter',
                itemSelector: 'div.phone',
                overItemCls : 'phone-hover',
                multiSelect : false,
                autoScroll  : true,
                listeners: {
                        scope: me,
                        selectionchange: me.onArticuloSelect
                    }
            });
            
            me.cellEditing = new Ext.grid.plugin.CellEditing({
                clicksToEdit: 1
            });

            me.frmCarritoLista = Ext.create('Ext.grid.Panel', {
                title: '',
                region: 'north',
                autoScroll: true,
                loadMask: true,
                plugins: [me.cellEditing],
                selModel: {
                    pruneRemoved: false
                },
                multiSelect: false,
                columns:[{
                    text: "Articulo",
                    dataIndex: 'nombre',
                    width: 150,
                    sortable: true
                },{
                    text: "Precio",
                    dataIndex: 'precio',
                    align: 'right',
                    width: 55,
                    sortable: false
                },{
                    id: 'cantidad',
                    text: "Cant.",
                    dataIndex: 'cantidad',
                    width: 55,
                    sortable: true,
                    groupable: true,
                    editor: {
                        xtype: 'numberfield',
                        allowBlank: false,
                        minValue: 0,
                        maxValue: 100
                    }/*,
                    listeners: {
                        scope : me,
                        change: me.onProductosFiltrar
                    }*/
                },{
                    text: "Total",
                    align: 'right',
                    width: 55,
                    sortable: false,
                    renderer: function(value, metaData, record, rowIdx, colIdx, store, view) {
                        me.onCarritoGeneraMonto(store);
                        return (record.get('cantidad') * record.get('precio')).toFixed(2);
                    }
                },{
                    xtype: 'actioncolumn',
                    width: 30,
                    sortable: false,
                    menuDisabled: true,
                    items: [{
                            iconCls: 'tb-delete',
                            tooltip: 'Noooo!!!',
                            scope: this,
                            handler: this.onCarritoEliminar
                           }]
                }]
            });
        
            me.frmCarritoDetalle = Ext.widget({
                xtype: 'form',
                //layout: 'form',
                region: 'center',
                autoScroll: true,
                collapsible: false,
                frame: false,
                split: true,
                title: '',
                //store: me.store,
                //height: 200,
                bodyPadding: '0 0 0 5',
                
                /*fieldDefaults: {
                    msgTarget: 'side',
                    labelWidth: 75
                },*/
                /*plugins: {
                    ptype: 'datatip'
                },*/
                /*defaultType: 'textfield',*/
                
                items: [{
                    xtype: 'displayfield',
                    fieldLabel: 'Total:',
                    id: 'NetoTotal',
                    value: '0.00'
                }],
                buttons: [{
                    text: 'Lo quiero',
                    handler: me.onCarritoPagoGenerar
                }]
            });
        
        
            var PnlInicioSession = {
                //title: 's',
                id:'PnlInicioSession',
                layout: 'card',
                region:'center',
                autoScroll: false,
                frame: false,
                region: 'south',
                split: true,
                border:false,
                bodyStyle: 'padding-bottom:15px;background:#eee;',
                items:[{
                    id:'pnlIniciarSesion',
                    autoScroll: false,
                    frame: false,
                    border:false,
                    layout: 'form',
                    bodyStyle: 'background:#eee;',
                    bodyPadding: '5 5 5 5',
                    items: [{
                        xtype: 'button',
                        iconCls: 'tb-casa',
                        text: 'Inicie sesión',
                        iconAlign: 'right',
                        width: '100%',
                        listeners: {
                            //scope : me,
                            click: function(){me.onCarritoPagoGenerar('logeo',this);}
                        }
                        //handler: me.onCarritoPagoGenerar//me.onIniciarSessionClick
                    },{
                        xtype: 'displayfield',
                        width: '90%',
                        value: '<p align="center">7A6567616E6574</p>'
                    }]
                },{
                    id:'pnlCerrarSession',
                    autoScroll: false,
                    frame: false,
                    border:false,
                    layout: 'form',
                    bodyStyle: 'background:#eee;',
                    bodyPadding: '5 5 5 5',
                    items: [{
                        xtype: 'button',
                        iconCls: 'tb-casa',
                        text: 'Cerrar sesión',
                        iconAlign: 'right',
                        width: '100%',
                        listeners: {
                            //scope : me,
                            click: function(){
                                Ext.Msg.confirm('Carrito', '¿Desea cerrar su sesion?', function(button) {
                                    if (button === 'yes') {
                                        Ext.util.Cookies.clear('z', '');me.INIT();
                                    }
                                });
                            }
                        }
                    },{
                        xtype: 'displayfield',
                        width: '90%',
                        value: '<p align="center">7A6567616E6574</p>'
                    }]
                },{
                    id:'pnlCerrarSessionA',
                    autoScroll: false,
                    frame: false,
                    border:false,
                    layout: 'form',
                    bodyStyle: 'background:#eee;',
                    bodyPadding: '5 5 5 5',
                    items: [{
                        xtype: 'button',
                        iconCls: 'tb-casa',
                        text: 'Cerrar sesión',
                        iconAlign: 'right',
                        width: '100%',
                        listeners: {
                            //scope : me,
                            click: function(){
                                Ext.Msg.confirm('Carrito', '¿Desea cerrar su sesion?', function(button) {
                                    if (button === 'yes') {
                                        Ext.util.Cookies.clear('z', '');me.INIT();
                                    }
                                });
                            }
                        }
                    },{
                        xtype: 'button',
                        iconCls: 'tb-casa',
                        text: 'Admintrar',
                        iconAlign: 'right',
                        width: '100%',
                        listeners: {
                            //scope : me,
                            click: function(){me.onLogeado();}
                        }
                    },{
                        xtype: 'displayfield',
                        width: '90%',
                        value: '<p align="center">7A6567616E6574</p>'
                    }]
                }]
            };
            
            var PnlProductoFiltro = {
                title: '',
                autoScroll: false,
                frame: false,
                border:false,
                layout: 'form',
                bodyStyle: 'background:#eee;',
                bodyPadding: '10 10 10 10',
                items: [{
                    xtype: 'textfield',
                    name: 'productofiltro',
                    width: '90%',
                    fieldLabel: 'Filtrar',
                    value: '',
                    listeners: {
                        scope : me,
                        change: me.onProductosFiltrar
                    }
                }
                ]
            };
            
            var PnlCarrito = {
                id: 'pnlCarrito',
                title: 'Carro de compras',
                region: 'center',
                autoScroll: true,
                activeItem: 3,
                //margins: '0 0 0 0',
                //bodyStyle: 'padding-bottom:15px;background:#eee;',
                items:[me.frmCarritoLista,me.frmCarritoDetalle]
            };
            
            /*
            var PnlPrincipalProducto = {
                frame: false,
                border:false,
                autoScroll: true
                //items:[me.dataview]
            };
            */
            var PnlPrincipal = {
                 id: 'pnlPrincipal',
                 region: 'center',
                 layout: '',
                 margins: '2 0 5 5',
                 title: 'Catalogo',
                 border: false,
                 autoScroll: true,
                 items:[PnlProductoFiltro,me.dataview]
            };

            me.agrupar = Ext.create('Ext.grid.feature.Grouping',{
                groupHeaderTpl: '{name}'
            });
            var PnlCategoria = Ext.create('Ext.grid.Panel', {
                store: STORE('Categoria', { C: 'CATEGORIA', S: 'GETLIST', estado:3 }, 'idcategoria,nombre,padre',{groupField:'padre'}),
                region:'center',
                loadMask: true,
                hideHeaders: true,
                features: [me.agrupar],
                columns:[{
                    id: 'icon',
                    dataIndex: '',
                    text: '+',
                    width: 30,
                    sortable: false,
                    renderer: function (v, m, r) {
                            return '<div style="white-space:normal;">&nbsp;&nbsp;&nbsp;+</div>';
                    }
                },{
                    id: 'nombre',
                    dataIndex: 'nombre',
                    flex: 1,
                    sortable: false,
                    renderer: function (v, m, r) {
                            return '<div style="white-space:normal">' + (v === null ? '' : v) + '</div>';
                    }
                }],
                listeners: {
                    scope: me,
                    'select': me.onCategoriaSelect
                }
            });

            me.opti = Ext.create('Ext.view.View', {
                deferInitialRefresh: false,
                //store: SIMPLESTORE([], 'idproducto,nombre,detalle,foto,categoria,autor,precio,stock'),
                store: STORE('Producto', { C: 'PRODUCTO', S: 'GETLISTNEW', estado:3 }, 'idproducto,nombre,detalle,foto,categoria,autor,precio,stock'),
                tpl  : Ext.create('Ext.XTemplate',
                    '<tpl for=".">',
                        '<div class="phone">',
                            '<table>',
                                '<tr>',
                                    '<td>',
                                        '<img width="120" height="120" src="carrito/imagenes/{[values.foto]}.jpg" />',
                                    '<td>',
                                    '<td>',
                                        '<strong>Nombre:</strong>{nombre}<br>',
                                        '<strong>Categoria:</strong>{categoria}<br>',
                                        '<span style="color:red">S/. {precio} </span><br>',
                                    '<td>',
                                '<tr>',
                            '</table>',
                        '</div>',
                    '</tpl>'
                ),
                id:'pnlnovedades',
                height:300,
                itemSelector: 'div.phone',
                overItemCls : 'phone-hover',
                multiSelect : false,
                autoScroll  : true,
                listeners: {
                        scope: me,
                        selectionchange: me.onArticuloSelect
                    }
            });

    
            var PnlNovedades = {
                title: 'Novedades',
                autoScroll: true,
                frame: false,
                region: 'south',
                split: true,
                border:false,
                config: { scrollable: true },
                bodyStyle: 'padding-bottom:15px;background:#eee;',
                //html:'<p align="center">7A6567616E6574</p>',
                items:[me.opti]
            };


            new Ext.Viewport({
                layout: 'border',
                title: '',
                items: [{
                    xtype: 'box',
                    id: 'Titulo',
                    region: 'north',
                    html: '<h1> BookShop </h1>',
                    height: 50
                },{
                    layout: 'border',
                    id: 'Informacion',
                    title: 'Mi detalle',
                    region:'east',
                    border: false,
                    split:true,
                    margins: '2 5 5 5',
                    width: 345,
                    minSize: 100,
                    maxSize: 500,
                    collapsible: true,
                    items: [me.frmDetalle, PnlCarrito, PnlInicioSession]
                },{
                    layout: 'border',
                    id: 'Categoria',
                    title: 'Categorias',
                    region:'west',
                    border: false,
                    split:true,
                    margins: '2 0 5 5',
                    width: 290,
                    minSize: 100,
                    maxSize: 500,
                    collapsible: true,
                    items: [PnlCategoria,PnlNovedades]
                }, 
                    PnlPrincipal
                ]
            });
            
            me.INIT();
            
        },
        onCategoriaSelect: function(d, s) {
            if (s.data.idcategoria){
                var me = this,
                    p = Ext.apply({ C: 'PRODUCTO', S: 'GETLIST', idcategoria: s.data.idcategoria } );
                postServer('Producto', p, function (v) {
                        v = Ext.decode(v);
                        me.dataview.store.loadData(v);
                }, me);
            }
        },
        onArticuloSelect: function(dataview, selections) {
             var selected = selections[0];
             if (selected) {
                 me.frmDetalle.loadRecord(selected);
             }
        },
        onArticuloAgregarClic: function(b) {
            var form = b.up('form').getForm();
            var values = form.getFieldValues();
            //me.frmCarritoLista.store.insert(me.frmCarritoLista.store.data.lenght, form);
            var veri='0';
            if (values.idproducto){

                me.frmCarritoLista.store.each(function (r) {
                    if (r.data.idproducto==values.idproducto){
                        Ext.z.msg("", "Ya me agregaste si quieres mas solo aumenta la cantidad :-D");
                        veri='1';
                        return false;
                    }
                });

                if (veri=='0'){
                    var idx = me.frmCarritoLista.store.getCount();

                    var a = SIMPLESTORE([],'idproducto,nombre,precio,cantidad');

                    var r = Ext.create(a.model.getName(), {
                        idproducto: values.idproducto,
                        nombre: values.nombre,
                        precio: values.precio,
                        cantidad: 1
                    });
                    me.frmCarritoLista.store.insert(idx, r); 
                }   
            }else{
                Ext.z.msg("", "Primero debe seleccionar un producto");
            }
        },
        onCarritoEliminar: function(a, b) {
            a.select(b);
            me.frmCarritoLista.store.remove(a.getSelectionModel().getSelection());
            me.onCarritoGeneraMonto(me.frmCarritoLista.store);
         },
        onCarritoGeneraMonto: function(store) {
            
           var n=0;
           store.each(function (r) {
               n = n + (r.data.cantidad*r.data.precio);
           });
           
           me.frmCarritoDetalle.items.getByKey('NetoTotal').setRawValue('<b>S/. '+(n).toFixed(2)+'</b>');
                        
         },         
        onCarritoPagoGenerar: function(a, b) {
            if (a!='logeo'){
                var idx = me.frmCarritoLista.store.getCount();
                if (idx==0){
                    Ext.z.msg("", "Primero debe agregar una producto al carrito");return;
                }
            }
            if (me.frmPago==null){
                Ext.syncRequire('global.pago');
                me.frmPago = Ext.create('z.Pago', {
                    //id: '',
                    listeners: {
                        Generarpago: me.onPagarOk
                    }
                });
            }
            
            if (a=='logeo'){
                me.frmPago.setTitle('Iniciar sesion');
                Ext.getCmp('PnlUsuarioPrincipal').layout.setActiveItem('PnlUsuarioLogeo');
            }
            else{
                me.frmPago.setTitle('Pagar');
                if (!zt||!zt.u){
                    Ext.getCmp('PnlUsuarioPrincipal').layout.setActiveItem('PnlUsuarioLogeo');    
                }else{
                    
                    var p = Ext.apply({ C: 'USUARIO', S: 'GETLISTBYID' ,idusuario:zt.u } );
                    postServer('Usuario', p, function (v) {
                            v = Ext.decode(v);
                            pago.PnlUsuarioPago.form.setValues(v);
                    });
                    
                    if (!zt||!zt.r||zt.r==1){Ext.getCmp('PnlUsuarioPrincipal').layout.setActiveItem('PnlUsuarioPago'); }
                    else{Ext.getCmp('PnlUsuarioPrincipal').layout.setActiveItem('PnlUsuarioPago'); }
                }
            }

            me.frmPago.show(this);

         },
        onPagarOk: function(a, b) {
            Ext.MessageBox.show({
               msg: 'Ahora espere mientras procesamos su pedido ...',
               progressText: 'Almacenando...',
               width:300,
               wait:true,
               waitConfig: {interval:50},
               icon:'ext-mb-download',
               iconHeight: 50/*,
               animateTarget: 'mb7'*/
           });
            setTimeout(function(){
                Ext.MessageBox.hide();
            }, 500);
         },
        onProductosFiltrar: function(field, newValue) {
            var store = me.dataview.store,
                view = me.dataview

            store.suspendEvents();
            store.clearFilter();
            store.filter({
                property: 'nombre',
                anyMatch: true,
                value   : newValue
            });
            store.resumeEvents();
            view.refresh();

        },
        onIniciarSessionClick: function (){
            if (me.frmLogea==null){
                Ext.syncRequire('global.logeo');
                me.frmLogea = Ext.create('z.Logeo', {
                    id: 'frmLogea',
                    listeners: {
                        onLogea: me.onLogeado
                    }
                });
            }
            me.frmLogea.show(this);
        },
        onRecargarTodo: function(){
            /*
            me.frmDetalle=null;
            me.frmCarritoLista=null;
            me.frmCarritoDetalle=null;
            me.store=null;
            me=null;
            */
            //Ext.destroy('frmAdministrador');
            //alert(Ext.getBody().getHeight());
        },
        onLogeado: function(){

            if (me.frmAdministrador==null){
                Ext.syncRequire('carrito.administrador');
                me.frmAdministrador = Ext.create('z.carrito.administrador', {
                    id: 'frmAdministrador',
                    listeners: {
                        onAdministradorCerrar: me.onRecargarTodo
                    }
                });
            }
            me.frmAdministrador.show();
            
        },
        INIT: function(){
            zt = Ext.JSON.decode(Ext.decode(Ext.util.Cookies.get('z')));
            if (!zt||!zt.u){
                Ext.getCmp('PnlInicioSession').layout.setActiveItem('pnlIniciarSesion');    
            }else{
                if (!zt||!zt.r||zt.r==1){Ext.getCmp('PnlInicioSession').layout.setActiveItem('pnlCerrarSessionA'); }
                else{Ext.getCmp('PnlInicioSession').layout.setActiveItem('pnlCerrarSession'); }
            }
        }
    }));
}
