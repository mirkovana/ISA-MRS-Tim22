var obj = JSON.parse(localStorage.getItem('user'));
var events = [];


function prikaziOdsustva(){
	$('.schedule-tab').empty();
	console.log("Usli smo u f-ju");

	$.ajax({
			type : "GET",
			url : "api/ms/odsustva/rk/" + obj.id,
			
			contentType: "application/json",
			dataType: "json",
			headers: {
		        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
		    },
			success : function(data) {
				odsustva = JSON.parse(JSON.stringify(data));
				
				for(let od of odsustva) {
					console.log(new Date(od.datumPocetka))
					var poc = new Date(od.datumPocetka);
					var kraj = new Date(od.datumKraja)
					podaci = {
 							id: 'pocetak' + od.idZahtevaOdsustva +  'p', 
 							name: "pocetak " + " " + od.razlog, 
 							
 							date: poc,
 							type: "birthday",
 							color: "#ff6666"
 					};
					podaci2 = {
 							id: 'kraj: ' + od.idZahtevaOdsustva +  'p', 
 							name: "kraj " + " " + od.razlog, 
 							
 							date: kraj,
 							type: "birthday",
 							color: "#ff6666"
 					};
 					events.push(podaci);
 					events.push(podaci2);
				    }
					
				
				$('#demoEvoCalendar').evoCalendar({
 			    	
 			        todayHighlight: true,
 			        format: 'yyyy-MM-dd',
 			        calendarEvents:[{
 						id: 'sdfsdfs', 
 						name: "Danasnji datum",
 						date: new Date(), 
 						type: "holiday"
 				}]
 			    });
			   
			    for(let ev of events){
			    	$("#demoEvoCalendar").evoCalendar('addCalendarEvent', ev);
			    }
		},
			error: function(data) {
				console.log(data);
			}
	});
}
function goBack(){
	window.location = "homepagems.html"
}



$(document).ready(function() {
	
	    
	
	prikaziOdsustva();
	
	
    
});