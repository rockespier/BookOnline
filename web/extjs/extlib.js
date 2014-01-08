Ext.ns('z');

//Ext.Loader.setConfig('disableCaching', false);

//Ext.Loader.setConfig('enabled', true);

//Ext.enableFx = false;

Ext.form.field.Date.prototype.format = 'd/m/Y';

//Ext.useShims = false;

var formatNumber = Ext.util.Format.numberRenderer('0,0.00'),
    
    Messages = {
        confirmDelete: 'El registro seleccionado será eliminado de forma permanente.<br/><br/>¿Desea continuar?',
        confirmDesactive:'El registro seleccionado será desactivado.<br/><br/>¿Desea continuar?',
        desactiveOk:'Registro desactivado con éxito.',
        confirmRemove: 'El registro seleccionado será removido.<br/><br/>¿Desea continuar?',
        exportPdf: 'Exportar a PDF',
        formInvalid: 'Datos erroneos o falta ingresar los campos obligatorios.',
        selectOne : 'Se debe seleccionar un registro.',
        selectOneN : 'Se debe seleccionar al menos un registro.',
        deleteOk: 'Registro(s) eliminado(s) con éxito.',
        saveOk: 'Registro guardado.',
        confirmContinue: 'Se necesita confirmar para continuar.<br/><br/>¿Desea continuar?',
        valid: 'El registro ya ha sido ingresado.'
    };

if(Ext.form.field.Time){
   Ext.apply(Ext.form.field.Time.prototype, {
      minText : "La hora debe ser igual o mayor a {0}",
      maxText : "La hora debe ser igual o menor a {0}",
      invalidText : "{0} no es una hora v&#225;lida"
   });
}

var JS = {
    
    loaded : {},
    
    include : function(url, callback, scope){
        if(JS.loaded[url]){
            if(Ext.isFunction(callback))
                callback.call(scope || window);
            return;
        }
        JS.loaded[url] = true;
        Ext.Loader.loadScriptFile(url, callback || Ext.emptyFn, Ext.emptyFn, scope);
    },
    
    isInclude : Ext.emptyFn
    
};



Ext.define('Ux.LinkButton', {
    extend : 'Ext.Component',
    xtype  : 'linkbutton',

    text    : null,
    handler : Ext.emptyFn,

    initComponent : function() {
        var me = this;

        me.html = '<a href="#">' + me.text + '</a>';

        me.callParent();
    },

    afterRender : function() {
        var me = this;

        me.callParent(arguments);

        me.mon(me.el, {
            scope    : me,
            delegate : 'a',
            click    : me.handleClick
        });
    },

    handleClick : function(e) {
        e.stopEvent();

        this.handler.call(this, this);
    },

    setText : function(text) {
        this.update('<a href="#">' + text + '</a>');
    }
});

new Ux.LinkButton({
    renderTo : document.body,
    text     : 'Test',
    handler  : function(btn) {
        console.log(btn);
    }
});




Ext.define('Ext.ux.DataTip', function(DataTip) {


    function onHostRender() {
        var e = this.isXType('panel') ? this.body : this.el;
        if (this.dataTip.renderToTarget) {
            this.dataTip.render(e);
        }
        this.dataTip.setTarget(e);
    }

    function updateTip(tip, data) {
        if (tip.rendered) {
            if (tip.host.fireEvent('beforeshowtip', tip.eventHost, tip, data) === false) {
                return false;
            }
            tip.update(data);
        } else {
            if (Ext.isString(data)) {
                tip.html = data;
            } else {
                tip.data = data;
            }
        }
    }

    function beforeViewTipShow(tip) {
        var rec = this.view.getRecord(tip.triggerElement),
            data;

        if (rec) {
            data = tip.initialConfig.data ? Ext.apply(tip.initialConfig.data, rec.data) : rec.data;
            return updateTip(tip,data);
        } else {
            return false;
        }
    }

    function beforeFormTipShow(tip) {
        var field = Ext.getCmp(tip.triggerElement.id);
        if (field && (field.tooltip || tip.tpl)) {
            return updateTip(tip, field.tooltip || field);
        } else {
            return false;
        }
    }

    return {
        extend: 'Ext.tip.ToolTip',

        mixins: {
            plugin: 'Ext.AbstractPlugin'
        },

        alias: 'plugin.datatip',

        lockableScope: 'both',

        constructor: function(config) {
            var me = this;
            me.callParent([config]);
            me.mixins.plugin.constructor.call(me, config);
        },

        init: function(host) {
            var me = this;

            me.mixins.plugin.init.call(me, host);
            host.dataTip = me;
            me.host = host;
            
            if (host.isXType('tablepanel')) {
                me.view = host.getView();
                if (host.ownerLockable) {
                    me.host = host.ownerLockable;
                }
                me.delegate = me.delegate || me.view.getDataRowSelector();
                me.on('beforeshow', beforeViewTipShow);
            } else if (host.isXType('dataview')) {
                me.view = me.host;
                me.delegate = me.delegate || host.itemSelector;
                me.on('beforeshow', beforeViewTipShow);
            } else if (host.isXType('form')) {
                me.delegate = '.' + Ext.form.Labelable.prototype.formItemCls;
                me.on('beforeshow', beforeFormTipShow);
            } else if (host.isXType('combobox')) {
                me.view = host.getPicker();
                me.delegate = me.delegate || me.view.getItemSelector();
                me.on('beforeshow', beforeViewTipShow);
            }
            if (host.rendered) {
                onHostRender.call(host);
            } else {
                host.onRender = Ext.Function.createSequence(host.onRender, onHostRender);
            }
        }
    };
});



Ext.z = function(){
    var msgCt;

    function createBox(t, s){
       // return ['<div class="msg">',
       //         '<div class="x-box-tl"><div class="x-box-tr"><div class="x-box-tc"></div></div></div>',
       //         '<div class="x-box-ml"><div class="x-box-mr"><div class="x-box-mc"><h3>', t, '</h3>', s, '</div></div></div>',
       //         '<div class="x-box-bl"><div class="x-box-br"><div class="x-box-bc"></div></div></div>',
       //         '</div>'].join('');
       return '<div class="msg"><h3>' + t + '</h3><p>' + s + '</p></div>';
    }
    return {
        msg : function(title, format){
            title='Carrito: ' + title ;
            if(!msgCt){
                msgCt = Ext.DomHelper.insertFirst(document.body, {id:'msg-div'}, true);
            }
            var s = Ext.String.format.apply(String, Array.prototype.slice.call(arguments, 1));
            var m = Ext.DomHelper.append(msgCt, createBox(title, s), true);
            m.hide();
            m.slideIn('t').ghost("t", { delay: 2000, remove: true});
        },

        init : function(){
            if(!msgCt){
                msgCt = Ext.DomHelper.insertFirst(document.body, {id:'msg-div'}, true);
            }
        }
    };
}();




Ext.onReady(function() {

    if(Ext.LoadMask){
      Ext.LoadMask.prototype.msg = "Cargando...";
    }
    
    if(Ext.Date) {
        Ext.Date.monthNames = [
          "Enero",
          "Febrero",
          "Marzo",
          "Abril",
          "Mayo",
          "Junio",
          "Julio",
          "Agosto",
          "Septiembre",
          "Octubre",
          "Noviembre",
          "Diciembre"
        ];

        Ext.Date.getShortMonthName = function(month) {
          return Ext.Date.monthNames[month].substring(0, 3);
        };

        Ext.Date.monthNumbers = {
          Ene : 0,
          Feb : 1,
          Mar : 2,
          Abr : 3,
          May : 4,
          Jun : 5,
          Jul : 6,
          Ago : 7,
          Sep : 8,
          Oct : 9,
          Nov : 10,
          Dic : 11
        };

        Ext.Date.getMonthNumber = function(name) {
          return Ext.Date.monthNumbers[name.substring(0, 1).toUpperCase() + name.substring(1, 3).toLowerCase()];
        };

        Ext.Date.dayNames = [
          "Domingo",
          "Lunes",
          "Martes",
          "Mi&#233;rcoles",
          "Jueves",
          "Viernes",
          "S&#225;bado"
        ];

        Ext.Date.getShortDayName = function(day) {
          if (day==3) return "Mié";
          if (day==6) return "Sáb";
          return Ext.Date.dayNames[day].substring(0, 3);
        };

        Ext.Date.parseCodes.S.s = "(?:st|nd|rd|th)";
    }
    

});



//**********************************************************

function postServer(url, params, onCallBack, scope, config) {
    if(Ext.isFunction(config))
        config = {onErrorCB: config};
    config = config || {};
    config.method = 'POST';
    callServer(url, params, onCallBack, scope, config);
}

function callServer(url, params, onCallBack, scope, config) {
    if(Ext.isFunction(config))
        config = {onErrorCB: config};
    config = config || {};
    if(config.mask !== false && scope && scope.el && scope.el.mask)
        scope.el.mask('&#160;', 'x-mask-loading');

    Ext.Ajax.request(Ext.apply({
        url: url,
        params: params,
        method: 'POST', //'POST',//'GET',
        onCallBack: onCallBack,
        scope: scope,
        onErrorCB: config.onErrorCB,
        success: function (res, opt) {
            if (config.mask !== false && scope && scope.el && scope.el.unmask)
                scope.el.unmask();

            var json;
            try {
                json = res.getResponseHeader('X-JSON');
                json = json ? eval('(' + json + ')') : null;
            } catch (e) { json = null; }

            if (json !== null && json.error) {
                if (typeof opt.onErrorCB == 'function') {
                    opt.onErrorCB.call(opt.scope || window, json.message, json);
                } else {
                    Ext.z.msg("", json.message);
                }
            } else {
                if (typeof opt.onCallBack == 'function') {
                    opt.onCallBack.call(opt.scope || window, res.responseText, json);
                }
            }
        },
        failure: function (res, opt) {
            if (res.statusText == '') {
                var json;
                try {
                    json = res.getResponseHeader('X-JSON');
                    json = json ? eval('(' + json + ')') : null;
                } catch (e) { json = null; }

                if (json !== null && json.error) {
                    Ext.z.msg("", json.message);
                }
            }
            else {
                Ext.z.msg("", json.message);
            }
        }
    }, config));
}

function __STORE_INIT_PARAMS(config, fields){
    config = config || {};
    
    Ext.applyIf(config, {autoLoad: true});
    
    if(typeof fields == 'string'){
        fields = fields.replace(/[ ]/g, '').split(',');
    }
    if (!(fields instanceof Array)){
        fields = ['id', 'text'];
    }
    
    // Listeners
    var listeners = config.listeners || {};
    
    if(config.beforeLoad){
        listeners.beforeload = function(store, oper){
            oper.params = oper.params || {};
            return config.beforeLoad.call(config.scope || window, oper.params, oper);
        };
    }
    var model = config.model;
    if(!model){
        
        if(!config.idProperty){
            var idProperty = (config.id === undefined ? fields[0] : config.id);
            delete config.id;
            if(idProperty){
                config.idProperty = idProperty;
            }
        }
        
        model = Ext.define('Ext.data.Store.ImplicitModel-' + (Ext.id()), {
            extend: 'Ext.data.Model',
            fields: fields,
            idProperty: config.idProperty
        });
    }
    
    Ext.apply(config, {
        model: model,
        listeners: listeners
    });
    
    // reader
    if(!config.reader){
        config.reader = {type: 'array' /*idProperty: config.idProperty*/};
    } else if(Ext.isString(config.reader) && config.reader == 'json'){
        config.reader = {type: config.reader, root: 'items'};
    }
    
    return config;
}

function SIMPLESTORE(value, fields, config){
    value = value || [];
    if (!fields){
        if(value.length === 0 || (Ext.isArray(value[0]) && value[0].length > 1))
            fields = ['id', 'nombre'];
        else
            fields = ['nombre'];
    }
    config = __STORE_INIT_PARAMS(config, fields);
    var reader = config.reader;
    delete config.reader;
    return new Ext.data.ArrayStore(Ext.apply({
            data    : value,
            proxy   : new Ext.data.proxy.Memory({
                    model   : config.model,
                    reader  : reader
            })
    }, config));
}

function STORE(url, baseParams, fields, config, configobsoleto){
    if(config === true){
        config = Ext.apply({autoLoad: false}, configobsoleto);
    }
    config = __STORE_INIT_PARAMS(config, fields);
    var reader = config.reader;
    delete config.reader;
    return new Ext.data.Store(Ext.apply({
            proxy   : new Ext.data.proxy.Ajax({
                    url         : url,
                    //reader      : config.reader || new Ext.data.reader.Array({ /*idProperty: config.idProperty*/ }),
                    reader      : reader,
                    extraParams : baseParams
            })
    }, config));
}