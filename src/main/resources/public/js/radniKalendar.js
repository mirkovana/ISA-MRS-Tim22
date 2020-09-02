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
	 				    }else{
	 				    	podaci = {
		 							id: 'r' + pregl.idPregleda +  'p', 
		 							name: "P: " + " " +pregl.vreme.toString(), 
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
	 						id: 'Danasnji datum', 
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


function prikaziOdsustva(){
	$('.schedule-tab').empty();
	console.log("Usli smo u f-ju");

	$.ajax({
			type : "GET",
			url : "api/lekar/odsustva/rk/" + obj.id,
			
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
 							id: 'poc' + od.idZahtevaOdsustva +  'p', 
 							name: "pocetak " + " " + od.razlog, 
 							
 							date: poc,
 							type: "birthday",
 							color: "#ff6666"
 					};
					podaci2 = {
 							id: 'kraj' + od.idZahtevaOdsustva +  'p', 
 							name: "kraj " + " " + od.razlog, 
 							
 							date: kraj,
 							type: "birthday",
 							color: "#ff6666"
 					};
 					events.push(podaci);
 					events.push(podaci2);
				    }
					
		
			    
				
			   
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
	window.location = "homepagelekar.html"
}

function prikazOperacija(){
	
	
	$('.schedule-tab').empty();
	console.log("Usli smo u f-ju");

	$.ajax({
			type : "GET",
			url : "api/lekar/operacije/rk/" + obj.id,
			
			contentType: "application/json",
			dataType: "json",
			headers: {
		        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
		    },
			success : function(data) {
				operacije = JSON.parse(JSON.stringify(data));
				
				for(let oper of operacije) {
					
					
					
 					podaci = {
 							id: 'oper' + oper.idOperacije +  'p', 
 							name: "O: " + oper.pacijent.ime +". " + oper.pacijent.prezime  + " " +oper.vremeOperacije.toString(), 
 							date: new Date(oper.vremeOperacije),  
 							type: "birthday"
 					};
 					events.push(podaci);
				    
					}
			 
	
			   
			    for(let ev of events){
			    	$("#demoEvoCalendar").evoCalendar('addCalendarEvent', ev);
			    }
		},
			error: function(data) {
				console.log(data);
			}
	});
}

$(document).ready(function() {
	
	    
	prikaziPreglede();
	prikaziOdsustva();
	prikazOperacija();
	
    
});