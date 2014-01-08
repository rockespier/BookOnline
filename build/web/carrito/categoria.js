Ext.ns('z.carrito.administrador.categoria');

Ext.define('z.carrito.administrador.categoria', {
    extend: 'Ext.window.Window',
    height: 200,
    width : 400,
    title : '',
    closeAction: 'hide',
    layout: 'border',
    modal: true,
    border: false,
    bodyBorder: false,
    closable:true,
    initComponent: function() {
        categoria=this
        categoria.PnlfrmCategoria = new Ext.form.Panel({
            id: 'PnlfrmCategoria',
            region: 'center',
            layout: 'form',
            //title: '',
            xtype: 'form',
            bodyPadding: '10 10 10 10',
            //margins: '2 5 5 0',
            border: false,
            defaultType: 'textfield',
            items: [{
                xtype: 'hiddenfield',
                name: 'idcategoria',
                value:'0'
            },{
                name: 'nombre',
                fieldLabel: 'Nombre',
                emptyText: 'Nombre',
                allowBlank: false,
                blankText: 'Ingrese la Categoria ... '
            },categoria.padre={
                width: 65,
                xtype: 'combo',
                //mode:           'local',
                //value:          'mrs',
                //triggerAction:  'all',
                //forceSelection: true,
                //editable:       false,
                fieldLabel: 'Padre',
                name: 'padre',  
                displayField: 'padre',
                valueField: 'padre',
                allowBlank: false,
                blankText: 'Seleccion o ingrese la Categoria Padre ',
                store: STORE('Categoria', { C: 'CATEGORIA', S: 'GetlistPadre' }, 'padre')
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
                      categoria.PnlfrmCategoria
                ]
            }
        ];
        
        this.buttons = [
            {
                text: 'Guardar',
                scope: this,
                handler: function(){this.onCategoriaCerrarClic('Guardar');}
            },{
                text: 'Cerrar',
                scope: this,
                handler: function(){this.onCategoriaCerrarClic('Cerrar');}
            }
        ];
        this.callParent(arguments);
    },
    Actualizar: function(a,d){
        
        
        
        if (a=='edit'){
            var p = Ext.apply({ C: 'CATEGORIA', S: 'GETLISTBYID' ,idcategoria:d } );
            postServer('Categoria', p, function (v) {
                    v = Ext.decode(v);
                    categoria.PnlfrmCategoria.form.setValues(v);
            });
        }else if(a=='new'){
            categoria.PnlfrmCategoria.form.reset();
            categoria.padre.store.reload();
        }
        

        
    },
    onCategoriaCerrarClic: function(c) {
        if (c=='Guardar'){
            
            if (categoria.PnlfrmCategoria.isValid()){
                if (categoria.PnlfrmCategoria.form.getFieldValues().idcategoria==0){
                    var p = Ext.apply({ C: 'CATEGORIA', S: 'INSERT' }, categoria.PnlfrmCategoria.getValues());

                    postServer('Categoria', p, function (v) {
                        admin.frmCategoriaLista.store.reload()
                        categoria.hide();     
                    });
                }else{
                    var p = Ext.apply({ C: 'CATEGORIA', S: 'EDIT' }, categoria.PnlfrmCategoria.getValues());
                    postServer('Categoria', p, function (v) {
                        admin.frmCategoriaLista.store.reload()
                        categoria.hide();     
                    });
                }

            }
            
        }else if (c=='Cerrar'){
            this.fireEvent('onCategoriaCerrar', this);
            this.hide();                
        }

    }
    
});