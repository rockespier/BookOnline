Ext.ns('z');

Ext.define('z.Pago', {
    extend: 'Ext.window.Window',
    height: 405,
    width : 670,
    title : 'A pagar',
    closeAction: 'hide',
    layout: 'border',
    modal: true,
    border: false,
    bodyBorder: false,
    autoScroll: false,
    pago:null,
    initComponent: function() {
        
        pago=this;
        
        pago.PnlUsuarioLogeo = Ext.widget({
                id:'PnlUsuarioLogeo',
                xtype: 'form',
                layout: 'form',
                autoScroll: false,
                region: 'north',
                collapsible: false,
                frame: false,
                split:true,
                title: '',
                bodyPadding: '100 100 100 100',
                fieldDefaults: {
                    msgTarget: 'qtip',
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
                },{
                    name: 'clave',
                    fieldLabel: 'Contraseña',
                    flex: 3,
                    //margins: '0 0 0 6',
                    blankText: 'Digite su clave ...',
                    emptyText: '',
                    inputType: 'password',
                    allowBlank: false
                },{
                    xtype:'button',
                    text: 'Iniciar sesion',
                    handler: this.onIniciarClic
                },{
                    xtype: 'displayfield',
                    value:'<br>'
                },{
                    xtype: 'linkbutton',
                    text:'¿No puedes acceder a tu cuenta?',
                    handler: this.onRecuperarClic
                },{
                    xtype: 'displayfield',
                    value:'<br>'
                },{
                    xtype: 'displayfield',
                    value:'¿ No dispones de una cuenta ? '
                },{
                    xtype: 'linkbutton',
                    text:'<b>Regístrate ahora</b>',
                    handler: function(){pago.onRegistrarClic('view')}
                }]
            });
        
        

        pago.PnlUsuarioCrear = Ext.widget({
                id:'PnlUsuarioCrear',
                xtype: 'form',
                layout: 'form',
                autoScroll: false,
                region: 'north',
                collapsible: false,
                frame: false,
                split:true,
                title: '',
                bodyPadding: '100 100 100 100',
                fieldDefaults: {
                    msgTarget: 'side',
                    labelWidth: 150
                },
                defaultType: 'textfield',
                items: [{
                    name: 'nombre',
                    fieldLabel: 'Usuario',
                    //flex: 2,
                    blankText: '<div style="width:200px;">Digite su usuario ... </div>',
                    emptyText: '',
                    allowBlank: false
                },{
                    name: 'clave',
                    fieldLabel: 'Contraseña',
                    //flex: 3,
                    //margins: '0 0 0 6',
                    blankText: '<div style="width:200px;">Digite su clave ... </div>',
                    emptyText: '',
                    inputType: 'password',
                    allowBlank: false
                },{
                    name: 'clave',
                    fieldLabel: 'Repita su Contraseña',
                    //flex: 3,
                    //margins: '0 0 0 6',
                    blankText: '<div style="width:200px;">Digite su clave ... </div>',
                    emptyText: '',
                    inputType: 'password',
                    allowBlank: false
                },{
                    fieldLabel: 'Correo',
                    name: 'correo',
                    vtype: 'email',
                    //flex: 1,
                    blankText: 'correo',
                    allowBlank: false
                },{
                    xtype:'button',
                    text: 'Registrar',
                    handler: function(){pago.onRegistrarClic('save')}
                    
                },{
                    xtype: 'displayfield',
                    value:'<br>'
                },{
                    xtype:'linkbutton',
                    text: 'Regresar',
                    handler: function(){pago.onLogeoClic('logeo')}
                }]
            
            });


        pago.PnlUsuarioRecuperar = Ext.widget({
                id:'PnlUsuarioRecuperar',
                xtype: 'form',
                layout: 'form',
                autoScroll: false,
                region: 'north',
                collapsible: false,
                frame: false,
                split:true,
                title: '',
                bodyPadding: '100 100 100 100',
                fieldDefaults: {
                    msgTarget: 'side',
                    labelWidth: 150
                },
                defaultType: 'textfield',
                items: [{
                    name: 'correo',
                    fieldLabel: 'Correo',
                    flex: 2,
                    vtype:'email',
                    blankText: 'Digite su correo ... ',
                    emptyText: 'ejemplo@dominio.com',
                    allowBlank: false
                },{
                    xtype: 'displayfield',
                    value:'<br>'
                },{
                    xtype:'button',
                    text: 'Recuperar',
                    handler: function(){pago.onLogeoClic('recuperar')}
                },{
                    xtype: 'displayfield',
                    value:'<br>'
                },{
                    xtype:'linkbutton',
                    text: 'Regresar',
                    handler: function(){pago.onLogeoClic('logeo')}
                }]
            });
        
        
        pago.PnlUsuarioPago = Ext.widget({
                id:'PnlUsuarioPago',xtype: 'form',layout: 'form',autoScroll: false,region: 'center',collapsible: false,frame: false,split:true,
                title: '',
                //store: me.store,
                bodyPadding: '5 5 0',
                fieldDefaults: {
                    msgTarget: 'side',
                    labelWidth: 75,
                    msgTarget: 'qtip'
                },defaultType: 'textfield',
                items: [{
                         xtype: 'fieldset',title: 'Datos de contacto',defaultType: 'textfield',layout: 'anchor',
                         defaults: {
                             anchor: '100%'
                         },
                         items: [{
                             xtype: 'fieldcontainer',fieldLabel: 'Nombre',layout: 'hbox',defaultType: 'textfield',
                             defaults: {
                                 hideLabel: 'true'
                             },
                             items: [{
                                 name: 'nombre',flex: 2,blankText: 'ingrese su nombre',emptyText: 'Nombres',allowBlank: false
                             }, {
                                 name: 'apellidos',flex: 3,margins: '0 0 0 6',emptyText: 'Apellidos',allowBlank: false
                             }]
                         }, {
                             xtype: 'container',layout: 'hbox',defaultType: 'textfield',margin: '0 0 5 0',
                             items: [{
                                 fieldLabel: 'Correo',name: 'correo',vtype: 'email',flex: 1,allowBlank: false
                             }, {
                                 fieldLabel: 'Telefono',labelWidth: 100,name: 'telefono',width: 200,margins: '0 0 0 6'
                             }]
                         }]
                     }, {
                         xtype: 'fieldset',title: 'Ubicacion',defaultType: 'textfield',layout: 'anchor',
                         defaults: {
                             anchor: '100%'
                         },
                         items: [{
                             fieldLabel: 'Direccion',name: 'direccion',allowBlank: false
                         }, {
                             xtype: 'container',
                             layout: 'hbox',
                             margin: '0 0 5 0',
                             items: [{
                                 //labelWidth: 50,
                                 xtype: 'textfield',
                                 fieldLabel: 'Distrito',
                                 name: 'distrito',
                                 flex: 1,
                                 allowBlank: false
                             },{
                                 labelWidth: 100,
                                 xtype: 'datefield',
                                 fieldLabel: 'Fec. Entrega',
                                 name: 'fechaentrega',
                                 width:220,
                                 margins: '0 0 0 6',
                                 allowBlank: false
                             }]
                         }]
                     }, {
                         xtype: 'fieldset',
                         title: 'Formas de pago',
                         layout: 'anchor',
                         defaults: {
                             anchor: '100%'
                         },
                         items: [{
                             xtype: 'radiogroup',
                             anchor: 'none',
                             layout: {
                                 autoFlex: false
                             },
                             defaults: {
                                 name: 'tarjetatipo',
                                 margin: '0 15 10 15'
                             },
                             items: [{
                                 inputValue: 'v',
                                 boxLabel: 'VISA',
                                 checked: true
                             }, {
                                 inputValue: 'm',
                                 boxLabel: 'MasterCard'
                             }, {
                                 inputValue: 'a',
                                 boxLabel: 'American Express'
                             }, {
                                 inputValue: 'd',
                                 boxLabel: 'Discover'
                             }]
                         },{
                             xtype: 'container',
                             layout: 'hbox',
                             margin: '0 0 5 0',
                             items: [{
                                 xtype: 'textfield',
                                 name: 'tarjetanumero',
                                 fieldLabel: '# Tarjeta',
                                 flex: 1,
                                 allowBlank: false,
                                 minLength: 15,
                                 maxLength: 16,
                                 enforceMaxLength: true,
                                 maskRe: /\d/
                             }, {
                                 xtype: 'fieldcontainer',
                                 fieldLabel: 'Expiracion',
                                 labelWidth: 75,
                                 layout: 'hbox',
                                 items: [{
                                     xtype: 'combobox',
                                     name: 'tarjetaexpiracionmes',
                                     displayField: 'nombre',
                                     valueField: 'numero',
                                     queryMode: 'local',
                                     emptyText: 'Mes',
                                     hideLabel: true,
                                     margins: '0 6 0 6',
                                     store: new Ext.data.Store({
                                         fields: ['nombre', 'numero'],
                                         data: (function() {
                                             var data = [];
                                                 Ext.Array.forEach(Ext.Date.monthNames, function(name, i) {
                                                 data[i] = {nombre: name, numero: i + 1};
                                             });
                                             return data;
                                         })()
                                     }),
                                     width: 100,
                                     allowBlank: false,
                                     forceSelection: true
                                 }, {
                                     xtype: 'numberfield',
                                     name: 'tarjetaexpiracionano',
                                     hideLabel: true,
                                     width: 70,
                                     value: new Date().getFullYear(),
                                     minValue: new Date().getFullYear(),
                                     allowBlank: false
                                 }]
                             }]
                         }]
                     },{
                        xtype: 'fieldcontainer',
                        layout: 'hbox',
                        //layout: 'anchor',
                        //width:'100%',
                        items: [{
                            xtype:'linkbutton',
                            width:'90%',
                            text: 'Iniciar sesion con otra cuenta',
                            handler: function(){pago.onLogeoClic('logeo')}
                        },{
                            xtype:'button',
                            text: 'Pagar',
                            handler: this.onPagarClic
                        }]}
                ]
            });
        
        var PnlUsuario = {
             id: 'PnlUsuarioPrincipal',
             region: 'center',
             layout: 'card',
             autoScroll: false,
             //margins: '2 5 5 0',
             activeItem: 0,
             border: false,
             items: [pago.PnlUsuarioLogeo,pago.PnlUsuarioPago,pago.PnlUsuarioCrear,pago.PnlUsuarioRecuperar]
        };
        
        
        this.items = [
            PnlUsuario
        ];
        /*
        this.buttons = [
            {
                text: 'Me lo llevo',
                scope: this,
                handler: this.onPagarClic
            },
            {
                text: 'Cerrar',
                scope: this,
                handler: function() {
                    this.hide();
                }
            }
        ];
        */
        this.callParent(arguments);

        /*this.addEvents(
            'Generarpago'
        );*/
            
    },
    onSessionIniciada: function(){
        if (me.frmPago.title=='Iniciar sesion'){
            pago.hide();
            if (!zt||!zt.r||zt.r==0){ }
            else if (!zt||!zt.r||zt.r==1){
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
            }
        }
        else{
            var p = Ext.apply({ C: 'USUARIO', S: 'GETLISTBYID' ,idusuario:zt.u } );
            postServer('Usuario', p, function (v) {
                    v = Ext.decode(v);
                    pago.PnlUsuarioPago.form.setValues(v);
            });
            Ext.getCmp('PnlUsuarioPrincipal').layout.setActiveItem('PnlUsuarioPago');
        }
    },
    onPagarClic: function() {
        if (pago.PnlUsuarioPago.isValid()){
            var p = Ext.apply({ C: 'USUARIO', S: 'PAGAR' }, pago.PnlUsuarioPago.getValues()),
                carrito = [];
            
            me.frmCarritoLista.store.each(function (r) {
                            var d = r.data;
                            carrito.push([d.idproducto, d.cantidad, d.precio].join(']]'));
                        });
                        
            p.carrito = carrito.join('<->');
            
            postServer('Usuario', p, function (v) {
                pago.fireEvent('Generarpago', 'me');
                pago.hide();
            });  
        }
    },
    onIniciarClic: function() {
        if (pago.PnlUsuarioLogeo.isValid()){
            var p = Ext.apply({ C: 'USUARIO', S: 'LOGEO' }, pago.PnlUsuarioLogeo.getValues());
            postServer('Usuario', p, function (v) {
                var t = Ext.JSON.decode(v);
                if (!t.idusuario){
                    Ext.z.msg("", "Usuario o contraseña inválida.");
                }else{
                    me.INIT();
                    pago.onSessionIniciada();
                }
            });  
        }
    },
    onRegistrarClic: function(a) {
        if (a=="view"){
            Ext.getCmp('PnlUsuarioPrincipal').layout.setActiveItem('PnlUsuarioCrear');
        }else if (a=="save"){
            if (pago.PnlUsuarioCrear.isValid())
            {
                var p = Ext.apply({ C: 'USUARIO', S: 'INSERT' }, pago.PnlUsuarioCrear.getValues());
                postServer('Usuario', p, function (v) {
                    me.INIT();
                    pago.onSessionIniciada();
                });                  
            }
        }
    },
    onRecuperarClic: function() {
        Ext.getCmp('PnlUsuarioPrincipal').layout.setActiveItem('PnlUsuarioRecuperar');
    },
    onLogeoClic: function(a) {
        if (a=="recuperar"){
            if (pago.PnlUsuarioRecuperar.isValid()){
                var p = Ext.apply({ C: 'USUARIO', S: 'RECUPERAR' }, pago.PnlUsuarioRecuperar.getValues());
                postServer('Usuario', p, function (v) {
                    pago.onLogeoClic('logeo');
                });  
            }            
        }else if (a=="logeo"){
            Ext.getCmp('PnlUsuarioPrincipal').layout.setActiveItem('PnlUsuarioLogeo');
        }
    }
});