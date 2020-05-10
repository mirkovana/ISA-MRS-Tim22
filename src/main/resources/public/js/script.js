function getFormData($form) {
	var unindexedArray = $form.serializeArray();
	console.log(unindexedArray)
	var indexedArray = {}
	
	$.map(unindexedArray, function(n, i){
		indexedArray[n['name']] = n['value'];
    });

    return indexedArray;
}


function login() {
	var data = getFormData($("#login"));
	var s = JSON.stringify(data);
	$.ajax({
		url: "rest/login",
		type: "POST",
		data: s,
		contentType: "application/json",
		dataType: "json",
		complete : function (data) {
			d = JSON.parse(data.responseText);
			console.log(d)
			if(!d.message) {
				$("#login_war").text("Pogresan unos!!!");
				$("#login_war").show();
			}
			else{
			   window.location.replace("/homepagepacijent.html");
				
			}
			}
		 
	});
}
//pozivacemo poacijenti
function setUpUserPage() {
	$.ajax({
		url: "api/pacijenti",
		type: "GET",
		contentType: "application/json",
		dataType: "json",
		complete: function(data) {
			pacijenti = JSON.parse(data.responseText);
			console.log(pacijenti)
			loadPacijenti(pacijenti);
			
		}
	});
}

function loadPacijenti(pacijenti) {
	var table = $("#tabela_pacijenti");
	table.append(makeTableHeaderP());	
	for(let p of pacijenti) {
		table.append(makeTableRowP(p));
	}
}

function makeTableHeaderP(){
	
	var row="";
	 row =
			`<thead class="thead-light">
					<tr>
						<th>Ime</th>
						<th>Prezime</th>
						<th>Email</th>
						<th>Adresa</th>
						<th>Grad</th>
						<th>Drzava</th>
						<th>Broj telefona</th>
						<th>Broj osiguranika</th>
					</tr>
				</thead>`;
		
		return row;
}
function makeTableRowP(p) {
	//var aktivan = vm.radi ? "Radi" : "Ne radi";
	var row = "";
	
	  row =
		`<tr>
			<td class="0" id='${p.ime}'>${p.ime}</td>
			<td class="1" id='${p.prezime}'>${p.prezime}</td>
			<td class="2">${p.email}</td>
			<td class="3">${p.adresa}</td>
			<td class="4">${p.grad}</td>
			<td class="5">${p.drzava}</td>
			<td class="6">${p.brojTelefona}</td>
			<td class="7">${p.brojOsiguranika}</td>
		</tr>`;
	
	return row;
}


function dodavanjeAKC(){
	console.log("aaaaa")
	var data = getFormData($("#formaFiltr"));
	
	var org = JSON.stringify(data);
	$.ajax({
		url: "api/adminiKC",
		type: "POST",
		data: org,
		contentType: "application/json",
		dataType: "json"
//		complete : function (data) {
//			d = JSON.parse(data.responseText);
//			if(d.added) {
//				var table = $("#table_izbor tbody");
//				table.append(makeTableRowIzbor("diskovi",JSON.parse(org)));
//				$("#greskaUnos").hide();
//				$("#greskaUnos1").hide();
//				$("#modal-wrapper").hide();
//			}else{
//				$("#log_war").text("Postoji disk sa tim nazivom");
//				$("#log_war").show();
//			}
//		} 
	});
	
}





