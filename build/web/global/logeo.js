Ext.ns('z');

Ext.define('z.Logeo', {
    extend: 'Ext.window.Window',
    height: 200,
    width : 400,
    title : 'Identifiquese',
    closeAction: 'hide',
    layout: 'border',
    modal: true,
    border: false,
    bodyBorder: false,
    
    initComponent: function() {
        
        var frmLogeo = {
                xtype: 'form',
                layout: 'form',
                autoScroll: true,
                region: 'north',
                collapsible: false,
                frame: false,
                split:true,
                title: '',
                bodyPadding: '5 5 0',
                fieldDefaults: {
                    msgTarget: 'side',
                    labelWidth: 75
                },
                defaultType: 'textfield',
                items: [{
                    name: 'nombre',
                    fieldLabel: 'Usuario',
                    flex: 2,
                    blankText: 'Digite su usuario ... ',
                    emptyText: '',
                    allowBlank: false
                }, {
                    name: 'apellido',
                    fieldLabel: 'Contraseña',
                    flex: 3,
                    margins: '0 0 0 6',
                    blankText: '<div style="width:200px;">Digite su clave ... </div>',
                    emptyText: '',
                    inputType: 'password',
                    allowBlank: false
                }]
            };
        
        
        this.items = [
            {
                xtype: 'panel',
                region: 'center',
                layout: 'fit',
                items: [
                      frmLogeo
                ]
            }
        ];
        
        this.buttons = [
            {
                text: 'Iniciar',
                id: 'btIniciarSession',
                scope: this,
                handler: this.onLoggerClic
            },
            {
                text: 'Cancelar',
                scope: this,
                handler: function() {
                    this.hide();
                }
            }
        ];
        
        this.callParent(arguments);

        /*this.addEvents(
            'onLogea'
        );*/
    },
    
    onLoggerClic: function() {
        this.fireEvent('onLogea', '');
        this.hide();
    }
});