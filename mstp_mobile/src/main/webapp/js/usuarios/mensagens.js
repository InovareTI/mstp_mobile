function oneSignal_manual_init(usuario,empresa){
	 notificationOpenedCallback = function(jsonData) {
		    //alert('notificationOpenedCallback: ' + JSON.stringify(jsonData));
		  };
	window.plugins.OneSignal.startInit("ae9ad50e-520d-436a-b0b0-23aaddedee7b").handleNotificationOpened(notificationOpenedCallback).endInit();
	  //window.plugins.OneSignal.setLogLevel({logLevel: 4, visualLevel: 4});
	  //window.plugins.OneSignal.deleteTag("User");
	  window.plugins.OneSignal.getTags(function(tags) {
		  console.log('Tags Received: ' + JSON.stringify(tags));
		});
	  window.plugins.OneSignal.sendTags({User: usuario, empresa: empresa});
	 
	  //alert("sistema de mensagens ativado!")
}