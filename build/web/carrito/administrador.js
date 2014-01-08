Ext.ns('z.carrito.administrador');

Ext.define('z.carrito.administrador', {
    extend: 'Ext.window.Window',
    height: Ext.getBody().getHeight()-20,
    width : Ext.getBody().getWidth()-40,
    title : 'Centro de administracion del sitio',
    closeAction: 'hide',
    layout: 'border',
    modal: true,
    border: false,
    bodyBorder: false,
    closable:false,
    titleAlign:'center',
    initComponent: function() {
        admin=this;
        Ext.define('CatAdmin',{
            extend: 'Ext.data.Model',
            proxy: {
                type: 'ajax',
                reader: 'xml'
            },
            fields: [
                {name: 'padre', mapping: '@menu.padre'},
                'nombre', 'menuid'            ]
        });
        
        var storeAdm = Ext.create('Ext.data.Store', {
            model: 'CatAdmin',
            autoLoad: true,
            groupField:'padre',
            proxy: {
                type: 'ajax',
                url: 'carrito/admin.xml',
                reader: {
                    type: 'xml',
                    record: 'Item',
                    idProperty: 'menuid'
                }
            }
        });

        var PnlAdminPrincipal = {
             id: 'PnlAdminPrincipal',
             region: 'center',
             layout: 'card',
             autoScroll: true,
             //margins: '2 5 5 0',
             activeItem: 0,
             border: false,
             items: []
        };
        
        this.admagrupar = Ext.create('Ext.grid.feature.Grouping',{
            groupHeaderTpl: '{name}'
        });
        var PnlAdminCategoria = Ext.create('Ext.grid.Panel', {
            store: storeAdm,
            region:'center',
            loadMask: true,
            hideHeaders: true,
            features: [this.admagrupar],
            columns:[{
                dataIndex: 'nombre',
                //text: '<b>*</b>',
                width: 30,
                sortable: false,
                renderer: function (v, m, r) {
                        return '<div style="white-space:normal;">&nbsp;&nbsp;&nbsp;<b>+</b></div>';
                }
            },{
                dataIndex: 'nombre',
                flex: 1,
                sortable: false,
                renderer: function (v, m, r) {
                        return '<div style="white-space:normal">' + (v === null ? '' : v) + '</div>';
                }
            }],
            listeners: {
                scope: this,
                'select': this.onAdminCategoriaSelect
            }
        });
        
        var portPrincipal = Ext.Viewport({
                layout: 'border',
                title: '',
                items: [{
                    layout: 'border',
                    title: 'Opciones',
                    region:'west',
                    border: true,
                    split:false,
                    width: 290,
                    minSize: 100,
                    maxSize: 500,
                    collapsible: false,
                    items: [PnlAdminCategoria]
                }, 
                    PnlAdminPrincipal
                ]
            });
        
        
        this.items = [
            {
                xtype: 'panel',
                region: 'center',
                layout: 'fit',
                items: [
                      portPrincipal
                ]
            }
        ];
        
        this.buttons = [
            {
                text: 'Cerrar',
                scope: this,
                handler: this.onAdministradorCerrarClic
            }
        ];
        
        this.callParent(arguments);

        /*this.addEvents(
            'onLogea'
        );*/   
    },
    
    onAdministradorCerrarClic: function() {
        this.fireEvent('onAdministradorCerrar', this);
        this.hide();
    },
    onAdminCategoriaSelect: function(a,b,c,d) {
        if (b.data.menuid==1){
            /*Categorias*/
            if (!Ext.getCmp('PnlAdminPrincipal').items.getByKey('PnlAdminManteCategoria')){

                admin.frmCategoriaLista = Ext.create('Ext.grid.Panel', {
                    title: '',region: 'north',loadMask: true,multiSelect: false,
                    store: STORE('Categoria', { C: 'CATEGORIA', S: 'GETLIST', estado:1 }, 'idcategoria,nombre,padre,estado,fecharegistro'),
                    
                    columns:[{
                        dataIndex: 'idcategoria',hidden: true
                    },{
                        text: "Nombre",dataIndex: 'nombre',sortable: true,flex: 1
                    },{
                        text: "Padre",dataIndex: 'padre',sortable: false,flex: 1
                    },{
                        text: "Fecha",dataIndex: 'fecharegistro',sortable: true
                    },{
                        text: "Estado",dataIndex: 'estado',sortable: true,
                        renderer : function(val) {
                                        switch (val) {
                                            case "1":
                                                return 'Activo';
                                            case "2":
                                                return 'Inactivo';
                                        }
                                    }
                    },{
                        xtype: 'actioncolumn',width: 30,sortable: false,menuDisabled: true,
                        items: [{
                                name:'edit',iconCls: 'tb-edit',tooltip: ':-D!!!',scope: this,handler: this.onCategoriaClic
                               }]
                    },{
                        xtype: 'actioncolumn',width: 30,sortable: false,menuDisabled: true,
                        items: [{
                                name:'delete',iconCls: 'tb-delete',tooltip: 'Noooo!!!',scope: this,
                                handler: this.onCategoriaClic
                               }]
                    }]
                });

                var PnlAdminManteCategoria= {
                    id: 'PnlAdminManteCategoria',title: 'Mantenimiento de Categorias',layout: 'form',bodyPadding: '10 10 10 10',
                    items:[{
                        name:'new',xtype: 'button',text : 'Nueva Categoria',handler: this.onCategoriaClic
                    },admin.frmCategoriaLista]
                }
                Ext.getCmp('PnlAdminPrincipal').add(PnlAdminManteCategoria);            
            }
            Ext.getCmp('PnlAdminPrincipal').layout.setActiveItem('PnlAdminManteCategoria');
        }
        else
        if (b.data.menuid==2){
            /*Autores*/
            if (!Ext.getCmp('PnlAdminPrincipal').items.getByKey('PnlAdminManteAutor')){

                admin.frmAutorLista = Ext.create('Ext.grid.Panel', {
                    title: '',autoScroll: true,loadMask: true,multiSelect: false,
                    store: STORE('Autor', { C: 'AUTOR', S: 'GETLIST', estado:1 }, 'idautor,nombre,correo,biografia,foto,idusuario,fecha,estado'),
                    
                    columns:[{
                        text: "Nombre",dataIndex: 'nombre',sortable: true
                    },{
                        text: "Correo",dataIndex: 'correo',align: 'right',sortable: false
                    },{
                        id: 'biografia',text: "Biografia",dataIndex: 'biografia',flex: 2,sortable: true
                    },{
                        id: 'foto',text: "Foto",dataIndex: 'foto',sortable: true
                    },{
                        text: "Estado",dataIndex: 'estado',sortable: true,
                        renderer : function(val) {
                                        switch (val) {
                                            case "1":
                                                return 'Activo';
                                            case "2":
                                                return 'Inactivo';
                                        }
                                    }
                    },{
                        xtype: 'actioncolumn',width: 30,sortable: false,menuDisabled: true,
                        items: [{
                                name:'edit',iconCls: 'tb-edit',tooltip: ':-D!!!',scope: this,handler: this.onAutorClic
                               }]
                    },{
                        xtype: 'actioncolumn',width: 30,sortable: false,menuDisabled: true,
                        items: [{
                                name:'delete',iconCls: 'tb-delete',tooltip: 'Noooo!!!',scope: this,
                                handler: this.onAutorClic
                               }]
                    }]
                });

                var PnlAdminManteAutor= {
                    id: 'PnlAdminManteAutor',title: 'Mantenimiento de Autores',layout: 'form',bodyPadding: '10 10 10 10',
                    items:[{
                        name:'new',xtype: 'button',text : 'Nuevo Autor',handler: this.onAutorClic
                    },admin.frmAutorLista]
                }
                Ext.getCmp('PnlAdminPrincipal').add(PnlAdminManteAutor);            
            }
            Ext.getCmp('PnlAdminPrincipal').layout.setActiveItem('PnlAdminManteAutor');
        }
        else
        if (b.data.menuid==3){
            /*Productos*/
            if (!Ext.getCmp('PnlAdminPrincipal').items.getByKey('PnlAdminManteProducto')){

                admin.frmProductoLista = Ext.create('Ext.grid.Panel', {
                    title: '',autoScroll: true,loadMask: true,multiSelect: false,
                    store: STORE('Producto', { C: 'PRODUCTO', S: 'GETLIST', idcategoria:2 }, 'idproducto, nombre, detalle, foto, idcategoria, idautor, precio, stock, idusuario, fecharegistro, estado'),
                    
                    columns:[{
                        text: "Foto",dataIndex: 'foto',sortable: true
                    },{
                        text: "Nombre",dataIndex: 'nombre',sortable: false,flex: 1
                    },{
                        text: "Autor",dataIndex: 'autor',sortable: true
                    },{
                        text: "Categoria",dataIndex: 'foto',sortable: true
                    },{
                        text: "Precio",dataIndex: 'precio',sortable: true
                    },{
                        text: "Stock",dataIndex: 'stock',sortable: true
                    },{
                        text: "Estado",dataIndex: 'estado',sortable: true,
                        renderer : function(val) {
                                        switch (val) {
                                            case "1":
                                                return 'Activo';
                                            case "2":
                                                return 'Inactivo';
                                        }
                                    }
                    },{
                        xtype: 'actioncolumn',width: 30,sortable: false,menuDisabled: true,
                        items: [{
                                name:'edit',iconCls: 'tb-edit',tooltip: ':-D!!!',handler: this.onProductoClic
                               }]
                    },{
                        xtype: 'actioncolumn',width: 30,sortable: false,menuDisabled: true,
                        items: [{
                                name:'delete',iconCls: 'tb-delete',tooltip: 'Noooo!!!', handler: this.onProductoClic
                               }]
                    }]
                });

                var PnlAdminManteProducto= {
                    id: 'PnlAdminManteProducto',title: 'Mantenimiento de Productos',layout: 'form',bodyPadding: '10 10 10 10',
                    items:[{
                        name:'new',xtype: 'button',text : 'Nuevo Producto',handler: this.onProductoClic
                    },admin.frmProductoLista]
                }
                Ext.getCmp('PnlAdminPrincipal').add(PnlAdminManteProducto);            
            }
            Ext.getCmp('PnlAdminPrincipal').layout.setActiveItem('PnlAdminManteProducto');
        }
        else
        if (b.data.menuid==4){
            /*Usuarios*/
            if (!Ext.getCmp('PnlAdminPrincipal').items.getByKey('PnlAdminManteUsuario')){

                var frmUsuarioLista = Ext.create('Ext.grid.Panel', {
                    title: '',autoScroll: true,loadMask: true,multiSelect: false,
                    columns:[{
                        text: "Foto",dataIndex: 'foto',sortable: true
                    },{
                        text: "Nombre",dataIndex: 'nombre',sortable: false,flex: 1
                    },{
                        text: "Autor",dataIndex: 'autor',sortable: true
                    },{
                        text: "Categoria",dataIndex: 'foto',sortable: true
                    },{
                        text: "Precio",dataIndex: 'precio',sortable: true
                    },{
                        text: "Stock",dataIndex: 'stock',sortable: true
                    },{
                        text: "Estado",dataIndex: 'estado',sortable: true
                    },{
                        xtype: 'actioncolumn',width: 30,sortable: false,menuDisabled: true,
                        items: [{
                                iconCls: 'tb-edit',tooltip: ':-D!!!',scope: this,handler: this.onUsuarioClic
                               }]
                    },{
                        xtype: 'actioncolumn',width: 30,sortable: false,menuDisabled: true,
                        items: [{
                                iconCls: 'tb-delete',tooltip: 'Noooo!!!',scope: this,
                                handler: this.onUsuarioClic
                               }]
                    }]
                });

                var PnlAdminManteUsuario= {
                    id: 'PnlAdminManteUsuario',title: 'Mantenimiento de Usuarios',layout: 'form',bodyPadding: '10 10 10 10',
                    defaults: {
                        bodyStyle: 'padding:15px;'
                    },
                    items:[{
                        xtype: 'button',text : 'Nuevo Usuario',handler: this.onUsuarioClic
                    },frmUsuarioLista]
                }
                Ext.getCmp('PnlAdminPrincipal').add(PnlAdminManteUsuario);            
            }
            Ext.getCmp('PnlAdminPrincipal').layout.setActiveItem('PnlAdminManteUsuario');
        }
        
        
    },
    onCategoriaClic: function(a,b,c,d,e,f,g){

        if (admin.frmCategoria==null){
            Ext.syncRequire('carrito.categoria');
            admin.frmCategoria = Ext.create('z.carrito.administrador.categoria', {
                id: 'frmCategoria',
                listeners: {
                    onCategoriaCerrar: admin.onRecargarTodo
                }
            });
        }
        
        if (!!c){
            if (d.name=='edit'){
                admin.frmCategoria.setTitle('Categoria: Editar');
                admin.frmCategoria.show();
                admin.frmCategoria.Actualizar('edit',f.data.idcategoria);  
            }else if (d.name=='delete'){
                Ext.Msg.confirm('Carrito', 'Desea eliminar la categoria seleccionada', function(button) {
                    if (button === 'yes') {
                        var p = Ext.apply({ C: 'CATEGORIA', S: 'DELETE' ,idcategoria:f.data.idcategoria } );
                        postServer('Categoria', p, function (v) {
                                admin.frmCategoriaLista.store.reload()
                        });
                    }
                });
            }  
        }else {
            admin.frmCategoria.setTitle('Categoria: Nuevo');
            admin.frmCategoria.show(this);
            admin.frmCategoria.Actualizar('new');             
        }
    },
    onAutorClic: function(a,b,c,d,e,f,g){
        
        if (admin.frmAutor==null){
            Ext.syncRequire('carrito.autor');
            admin.frmAutor = Ext.create('z.carrito.administrador.autor', {
                id: 'frmAutor',
                listeners: {
                    onAutorCerrar: admin.onRecargarTodo
                }
            });
        }
        
        if (!!c){
            if (d.name=='edit'){
                admin.frmAutor.setTitle('Autor: Editar');
                admin.frmAutor.show();
                admin.frmAutor.Actualizar('edit',f.data.idautor);  
            }else if (d.name=='delete'){
                Ext.Msg.confirm('Carrito', 'Desea eliminar el autor seleccionado', function(button) {
                    if (button === 'yes') {
                        var p = Ext.apply({ C: 'AUTOR', S: 'DELETE' ,idautor:f.data.idautor } );
                        postServer('Autor', p, function (v) {
                                admin.frmAutorLista.store.reload()
                        });
                    }
                });
            }  
        }else {
            admin.frmAutor.setTitle('Autor: Nuevo');
            admin.frmAutor.show(this);
            admin.frmAutor.Actualizar('new');             
        }
    },
    onProductoClic: function(a,b,c,d,e,f,g){
        
        if (admin.frmProducto==null){
            Ext.syncRequire('carrito.producto');
            admin.frmProducto = Ext.create('z.carrito.administrador.producto', {
                id: 'frmProducto',
                listeners: {
                    onProductoCerrar: admin.onRecargarTodo
                }
            });
        }
        
        if (!!c){
            if (d.name=='edit'){
                admin.frmProducto.setTitle('Producto: Editar');
                admin.frmProducto.show();
                admin.frmProducto.Actualizar('edit',f.data.idproducto);  
            }else if (d.name=='delete'){
                Ext.Msg.confirm('Carrito', 'Desea eliminar el producto seleccionado', function(button) {
                    if (button === 'yes') {
                        var p = Ext.apply({ C: 'PRODUCTO', S: 'DELETE' ,idproducto:f.data.idproducto } );
                        postServer('Producto', p, function (v) {
                                admin.frmProductoLista.store.reload()
                        });
                    }
                });
            }  
        }else {
            admin.frmProducto.setTitle('Producto: Nuevo');
            admin.frmProducto.show(this);
            admin.frmProducto.Actualizar('new');             
        }
        
    },
    onUsuarioClic: function(){
        
            if (me.frmUsuario==null){
                Ext.syncRequire('carrito.usuario');
                me.frmUsuario = Ext.create('z.carrito.administrador.usuario', {
                    id: 'frmUsuario',
                    listeners: {
                        onUsuarioCerrar: me.onRecargarTodo
                    }
                });
            }
            me.frmUsuario.setTitle('Usuario: Nuevo');
            me.frmUsuario.show();
            
    },
    onRecargarTodo: function(){
        if (!!admin.frmCategoriaLista){admin.frmCategoriaLista.store.reload()}
        if (!!admin.frmAutorLista){admin.frmAutorLista.store.reload()}
        
    }
    
});

