Ext.ns('z.carrito.administrador.autor');

Ext.define('z.carrito.administrador.autor', {
    extend: 'Ext.window.Window',
    height: 330,
    width : 400,
    title : '',
    closeAction: 'hide',
    layout: 'border',
    modal: true,
    border: false,
    bodyBorder: false,
    closable:true,
    initComponent: function() {
        autor=this;
        autor.PnlfrmAutor = new Ext.form.Panel({
            id: 'PnlfrmAutor',
            region: 'center',
            layout: 'form',
            //title: '',
            bodyPadding: '10 10 10 10',
            //margins: '2 5 5 0',
            border: false,
            defaultType: 'textfield',
            items: [{
                xtype: 'hiddenfield',
                name: 'idautor',
                value:'0'
            },{
                name: 'nombre',
                fieldLabel: 'Nombre',
                emptyText: 'Nombre completo',
                allowBlank: false
            },{
                name: 'correo',
                vtype:'email',
                fieldLabel: 'Correo',
                emptyText: 'ejemplo@dominio.com',
                allowBlank: false
            },{
                name: 'foto',
                fieldLabel: 'Foto',
                xtype: 'filefield',
                buttonText: 'Buscar foto...'
            },{
                xtype: 'displayfield',
                fieldLabel: 'Biografia'
            },{
                name: 'biografia',
                xtype: 'textareafield',
                allowBlank: false
            },{
                width: 65,
                xtype: 'combo',
                mode:'local',
                value:'1',
                //triggerAction:  'all',
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
                      autor.PnlfrmAutor
                ]
            }
        ];
        
        this.buttons = [
            {
                text: 'Guardar',
                scope: this,
                handler: function(){this.onAutorCerrarClic('Guardar');}
            },{
                text: 'Cerrar',
                scope: this,
                handler: function(){this.onAutorCerrarClic('Cerrar');}
            }
        ];
        
        this.callParent(arguments);
 
    },
    Actualizar: function(a,d){
        
        
        
        if (a=='edit'){
            var p = Ext.apply({ C: 'AUTOR', S: 'GETLISTBYID' ,idautor:d } );
            postServer('Autor', p, function (v) {
                    v = Ext.decode(v);
                    autor.PnlfrmAutor.form.setValues(v);
            });
        }else if(a=='new'){
            autor.PnlfrmAutor.form.reset();
        }
        

        
    },
    onAutorCerrarClic: function(c) {
        if (c=='Guardar'){
            
            if (autor.PnlfrmAutor.isValid()){
                if (autor.PnlfrmAutor.form.getFieldValues().idautor==0){
                    var p = Ext.apply({ C: 'AUTOR', S: 'INSERT' }, autor.PnlfrmAutor.getValues());

                    postServer('Autor', p, function (v) {
                        admin.frmAutorLista.store.reload()
                        autor.hide();     
                    });
                }else{
                    var p = Ext.apply({ C: 'AUTOR', S: 'EDIT' }, autor.PnlfrmAutor.getValues());
                    postServer('Autor', p, function (v) {
                        admin.frmAutorLista.store.reload()
                        autor.hide();     
                    });
                }

            }
            
        }else if (c=='Cerrar'){
            this.fireEvent('onAutorCerrar', this);
            this.hide();                
        }

    }
    
});