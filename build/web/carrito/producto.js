Ext.ns('z.carrito.administrador.producto');

Ext.define('z.carrito.administrador.producto', {
    extend: 'Ext.window.Window',
    height: 430,
    width : 500,
    title : '',
    closeAction: 'hide',
    layout: 'border',
    modal: true,
    border: false,
    bodyBorder: false,
    closable:true,
    initComponent: function() {
        producto = this;
        producto.PnlfrmProducto = new Ext.form.Panel({
            id: 'PnlfrmProducto',
            region: 'center',
            layout: 'form',
            //title: '',
            bodyPadding: '10 10 10 10',
            //margins: '2 5 5 0',
            border: false,
            defaultType: 'textfield',
            items: [{
                xtype: 'hiddenfield',
                name: 'idproducto',
                value:'0'
            },{
                name: 'nombre',
                fieldLabel: 'Nombre',
                emptyText: 'Nombre del producto'
            },{
                xtype: 'fieldcontainer',
                layout: 'hbox',
                width: '100%',
                defaultType: 'numberfield',
                items:[{
                            xtype: 'combo',
                            forceSelection: true,
                            editable:       false,
                            fieldLabel: 'Categoria',
                            name: 'categoria',
                            displayField: 'categoria',
                            valueField: 'idcategoria',
                            flex: 1
                        },{
                            margins: '0 0 0 10',
                            text: '---',
                            width: 20,
                            xtype: 'button'
                }]
            },{
                xtype: 'fieldcontainer',
                layout: 'hbox',
                width: '100%',
                defaultType: 'numberfield',
                items:[{
                            xtype: 'combo',
                            forceSelection: true,
                            editable:       false,
                            fieldLabel: 'Producto',
                            name: 'autor',
                            displayField: 'autor',
                            valueField: 'idautor',
                            iconCls: 'tb-new',
                            flex: 1
                        },{
                            margins: '0 0 0 10',
                            text: '---',
                            width: 20,
                            xtype: 'button'
                }]
            },{
                xtype: 'fieldcontainer',
                layout: 'hbox',
                width: '100%',
                defaultType: 'numberfield',
                items:[{
                            name: 'precio',
                            fieldLabel: 'Precio',
                            flex: 1
                        },{
                            name: 'stock',
                            margins: '0 0 0 10',
                            fieldLabel: 'Stock',
                            flex: 1
                }]
            },{
                name: 'foto',
                fieldLabel: 'Foto',
                xtype: 'filefield',
                buttonText: 'Buscar foto...'
            },{
                xtype: 'displayfield',
                fieldLabel: 'Detalle'
            }, {
                xtype: 'htmleditor',
                height: 120,
                enableAlignments: false,
                //enableColors: true,
                defaultFont: "Arial",
                enableFont: false,
                enableFontSize : false,
                enableLinks: false,
                enableSourceEdit: false
            },{
                width: 65,
                xtype: 'combo',
                allowBlank: false,
                forceSelection: true,
                editable:       false,
                fieldLabel: 'Estado',
                name: 'estado',  
                displayField: 'nombre',
                valueField: 'idestado',
                queryMode: 'local',
                store: Ext.create('Ext.data.Store', {
                    fields : ['idestado', 'nombre'],
                    data   : [
                        {idestado : "1", nombre : 'Activo'},
                        {idestado : "2", nombre : 'Inactivo'}
                    ]
                })
            }]
        });

        this.items = [
            {
                xtype: 'panel',
                region: 'center',
                layout: 'fit',
                items: [
                      producto.PnlfrmProducto
                ]
            }
        ];
        
        this.buttons = [
            {
                text: 'Guardar',
                scope: this,
                handler: function(){this.onProductoCerrarClic('Guardar');}
            },{
                text: 'Cerrar',
                scope: this,
                handler: function(){this.onProductoCerrarClic('Cerrar');}
            }
        ];
        
        this.callParent(arguments);
 
    },
    
    Actualizar: function(a,d){
        
        
        
        if (a=='edit'){
            var p = Ext.apply({ C: 'PRODUCTO', S: 'GETLISTBYID' ,idproducto:d } );
            postServer('Producto', p, function (v) {
                    v = Ext.decode(v);
                    producto.PnlfrmProducto.form.setValues(v);
            });
        }else if(a=='new'){
            producto.PnlfrmProducto.form.reset();
        }
        
    },
    onProductoCerrarClic: function(c) {
        if (c=='Guardar'){
            
            if (producto.PnlfrmProducto.isValid()){
                if (producto.PnlfrmProducto.form.getFieldValues().idproducto==0){
                    
                    producto.PnlfrmProducto.form.submit({
                        url: 'Producto?C=PRODUCTO&S=INSERT',
                        waitMsg: 'Subiendo archivo...',
                        success: function (fp, o) {
                            Ext.z.msg("", "Guardado correctamente");
                            //Ext.Msg.message('Archivo subido');
                            //me.fireEvent('upload', me, me.form.getValues());
                            //me.onEsc();
                        },
                        failure: function () {
                            Ext.z.msg("", "Error al subir archivo muy grande");
                        }
                    });
                    /*
                    
                    var p = Ext.apply({ C: 'PRODUCTO', S: 'UPLOAD' }, producto.PnlfrmProducto.getValues());

                    postServer('Producto', p, function (v) {
                        admin.frmProductoLista.store.reload()
                        producto.hide();     
                    });
                    */
                }else{
                    var p = Ext.apply({ C: 'PRODUCTO', S: 'EDIT' }, producto.PnlfrmProducto.getValues());
                    postServer('Producto', p, function (v) {
                        admin.frmProductoLista.store.reload()
                        producto.hide();     
                    });
                }

            }
            
        }else if (c=='Cerrar'){
            this.fireEvent('onProductoCerrar', this);
            this.hide();                
        }

    }
    
});