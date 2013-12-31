Ext.define('GW.view.LoginWindow', {
    extend : 'Ext.window.Window',
    alias : 'widget.loginwindow',
    constrain : true,
    title : 'Please login',
    height : 200,
    width : 400,
    modal : true,
    layout : 'fit',
    items : [ {
	header : false,
	bodyPadding : 5,
	xtype : 'form',

	// The form will submit an AJAX request to this URL when submitted
	url : '/j_security_check',

	// Fields will be arranged vertically, stretched to full width
	layout : 'anchor',
	defaults : {
	    anchor : '100%'
	},

	// The fields
	items : [ {
	    xtype : 'label',
	    text : 'Invalid credentials. Please try again',
	    hidden : true,
	    cls : 'login-error'
	}, {
	    fieldLabel : 'Username',
	    name : 'j_username',
	    allowBlank : false,
	    xtype : 'textfield'
	}, {
	    fieldLabel : 'Password',
	    name : 'j_password',
	    allowBlank : false,
	    inputType : 'password',
	    xtype : 'textfield'
	}, {
	    name : 'j_validate',
	    value : 'true',
	    xtype : 'hidden'
	} ],

	// Reset and Submit buttons
	buttons : [ {
	    text : 'Login',
	    formBind : true, // only enabled once the form is valid
	    disabled : true,
	    handler : function() {
		var formPanel = this.up('form');
		var form = formPanel.getForm();
		if (form.isValid()) {
		    var label = formPanel.down('label');
		    label.hide();
		    form.submit({
			success : function(form, action) {
			    formPanel.up('window').hide();
			},
			failure : function(form, action) {
			    var errorMsg = action.response.responseText;
			    label.setText(errorMsg).show();
			}
		    });
		}
	    }
	} ]
    } ]
});