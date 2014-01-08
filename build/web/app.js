Ext.ns('z.carrito');

Ext.application({  
    name: 'Carrito',  
    launch: function() {
        this.crearEscritorio();
        Ext.EventManager.on(window, 'beforeunload', this.onUnload, this);
    },
    crearEscritorio : function(){
        Ext.syncRequire('carrito.escritorio');
        z.carrito.escritorio.show();
    },
    onUnload : function(e){
        if(this.fireEvent('beforeunload', this) === false){
            e.stopEvent();
        }
    }
});  


    
