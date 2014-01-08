Ext.ns('z.carrito.administrador.usuario');

Ext.define('z.carrito.administrador.usuario', {
    extend: 'Ext.window.Window',
    height: 430,
    width : 500,
    title : '',
    closeAction: 'hide',
    layout: 'border',
    modal: true,
    border: false,
    bodyBorder: false,
    closable:false,
    initComponent: function() {
        
        var PnlfrmUsuario = {
            id: 'PnlfrmUsuario',
            region: 'center',
            layout: 'form',
            //title: '',
            bodyPadding: '10 10 10 10',
            //margins: '2 5 5 0',
            border: false,
            defaultType: 'textfield',
            items: [{
                name: 'nombre',
                fieldLabel: 'Nombre',
                emptyText: 'Nombre del producto'
            },{
                xtype: 'combo',
                forceSelection: true,
                editable:       false,
                fieldLabel: 'Categoria',
                name: 'categoria',
                displayField: 'categoria',
                valueField: 'idcategoria'
                //store: 
            },{
                xtype: 'combo',
                forceSelection: true,
                editable:       false,
                fieldLabel: 'Autor',
                name: 'autor',
                displayField: 'autor',
                valueField: 'idautor'
                //store: 
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
                name: 'estado',
                fieldLabel: 'Estado',
                xtype: 'checkboxfield'
                //allowBlank: false
            }]
        };

        this.items = [
            {
                xtype: 'panel',
                region: 'center',
                layout: 'fit',
                items: [
                      PnlfrmUsuario
                ]
            }
        ];
        
        this.buttons = [
            {
                text: 'Cerrar',
                scope: this,
                handler: this.onUsuarioCerrarClic
            }
        ];
        
        this.callParent(arguments);
 
    },
    
    onUsuarioCerrarClic: function() {
        this.fireEvent('onUsuarioCerrar', this);
        this.hide();
    }
    
});