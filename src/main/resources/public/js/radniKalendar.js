var obj = JSON.parse(localStorage.getItem('user'));
var events = [];
function prikaziPreglede(){
	    	$('.schedule-tab').empty();
	    	console.log("Usli smo u f-ju");
	 
	    	$.ajax({
	 			type : "GET",
	 			url : "api/lekar/pregledi/rk/" + obj.id,
	 			
	 			contentType: "application/json",
	 			dataType: "json",
	 			headers: {
	 		        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	 		    },
	 			success : function(data) {
	 				pregledi = JSON.parse(JSON.stringify(data));
	 				
	 				for(let pregl of pregledi) {
	 					var dat = pregl.vreme.split("T")
	 					console.log(pregl)
	 					console.log(new Date(pregl.vreme))
	 					if(pregl.pacijent != null){
		 					podaci = {
		 							id: 'r' + pregl.idPregleda +  'p', 
		 							name: "P: " + pregl.pacijent.ime +". " + pregl.pacijent.prezime  + " " +pregl.vreme.toString(), 
		 							date: new Date(pregl.vreme),  
		 							type: "birthday"
		 					};
		 					events.push(podaci);
	 				}
	 					}
	 			 
	    	
	 				$('#demoEvoCalendar').evoCalendar({
	 			    	
	 			        todayHighlight: true,
	 			        format: 'yyyy-MM-dd',
	 			        calendarEvents:[{
	 						id: 'sdfsdfs', 
	 						name: "jdfshkjdfh",
	 						date: new Date(), 
	 						type: "holiday"
	 				}]
	 			    });
	 				

	 			
	 			    
	 			    $('#demoEvoCalendar').evoCalendar('setTheme', "Midnight blue");
	 				
	 			   
	 			    for(let ev of events){
	 			    	$("#demoEvoCalendar").evoCalendar('addCalendarEvent', ev);
	 			    }
//	 			    $.each(events, function(key, value) {
//	 			    	pregledi = JSON.parse(JSON.stringify(value));
//		 				
//		 				
//	 						//console.log(pregl.pacijent)
//		 					if(pregledi.pacijent != null){
//		 						$("#demoEvoCalendar").evoCalendar('addCalendarEvent', value);
//			 					
//		 				}
//	 			    });
	 			},
	 			error: function(data) {
	 				console.log(data);
	 			}
	    	});
	 }

function goBack(){
	window.location = "homepagelekar.html"
}


$(document).ready(function() {
	
	    
	prikaziPreglede();
	

	
    
});