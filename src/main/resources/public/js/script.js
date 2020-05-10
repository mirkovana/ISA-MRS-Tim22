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

//pozivacemo sale
function setUpUserPageAK() {
	$.ajax({
		url: "api/sale",
		type: "GET",
		contentType: "application/json",
		dataType: "json",
		complete: function(data) {
			sale = JSON.parse(data.responseText);
			console.log(sale)
			loadSale(sale);
			
		}
	});
}

//pozivacemo preglede
function setUpUserPagePP() {
	$.ajax({
		url: "api/pregledi",
		type: "GET",
		contentType: "application/json",
		dataType: "json",
		complete: function(data) {
			pregledi = JSON.parse(data.responseText);
			console.log(pregledi)
			loadPregledi(pregledi);
			
		}
	});
}

//pozivacemo operacije
function setUpUserPagePO() {
	$.ajax({
		url: "api/operacije",
		type: "GET",
		contentType: "application/json",
		dataType: "json",
		complete: function(data) {
			operacije = JSON.parse(data.responseText);
			console.log(operacije)
			loadOperacije(operacije);
			
		}
	});
}

//popunjavanje tabele sala
function loadSale(sale) {
	var table = $("#tabela_sale");
	table.append(makeTableHeaderSale());	
	for(let s of sale) {
		table.append(makeTableRowSale(s));
	}
}

function loadPacijenti(pacijenti) {
	var table = $("#tabela_pacijenti");
	table.append(makeTableHeaderP());	
	for(let p of pacijenti) {
		table.append(makeTableRowP(p));
	}
}

//popunjavanje tabele pregleda
function loadPregledi(pregledi) {
	var table = $("#tabela_pregledi");
	table.append(makeTableHeaderPP());	
	for(let p of pregledi) {
		table.append(makeTableRowPregledi(p));
	}
}

//popunjavanje tabele operacija
function loadOperacije(operacije) {
	var table = $("#tabela_operacije");
	table.append(makeTableHeaderPO());	
	for(let o of operacije) {
		table.append(makeTableRowOperacije(o));
	}
}

function makeTableHeaderSale(){
	
	var row="";
	 row =
			`<thead class="thead-light">
					<tr>
						<th>Naziv</th>
						<th>Broj</th>
					</tr>
				</thead>`;
		
		return row;
}

//zaglavlje tabele pregleda za pacijente
function makeTableHeaderPP(){
	
	var row="";
	 row =
			`<thead class="thead-light">
					<tr>
						<th>Datum i vreme</th>
						<th>Trajanje</th>
						<th>Sala</th>
						<th>Tip</th>
					</tr>
				</thead>`;
		
		return row;
}

//zaglavlje tabele operacija za pacijente
function makeTableHeaderPO(){
	
	var row="";
	 row =
			`<thead class="thead-light">
					<tr>
						<th>Datum i vreme</th>
						<th>Trajanje</th>
						<th>Dodatne info</th>
						<th>Sala</th>
					</tr>
				</thead>`;
		
		return row;
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

function makeTableRowSale(s) {
	var row = "";
	
	  row =
		`<tr>
			<td class="0" id='${s.nazivSale}'>${s.nazivSale}</td>
			<td class="1" id='${s.brojSale}'>${s.brojSale}</td>
		</tr>`;
	
	return row;
}

function makeTableRowPregledi(p) {
	var row = "";
	
	  row =
		`<tr>
			<td class="0" id='${p.vreme}'>${p.vreme}</td>
			<td class="1" id='${p.trajanje}'>${p.trajanje}</td>
			<td class="2" id='${p.sala}'>${p.sala.nazivSale}</td>
			<td class="3" id='${p.tipPregleda}'>${p.tipPregleda}</td>
		</tr>`;
	
	return row;
}

function makeTableRowOperacije(o) {
	var row = "";
	
	  row =
		`<tr>
			<td class="0" id='${o.vremeOperacije}'>${o.vremeOperacije}</td>
			<td class="1" id='${o.trajanje}'>${o.trajanje}</td>
			<td class="2" id='${o.dodatneInfoOOperaciji}'>${o.dodatneInfoOOperaciji}</td>
			<td class="3" id='${o.sala}'>${o.sala.nazivSale}</td>
		</tr>`;
	
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

function dodavanjeSala(){
	console.log("aaaaa")
	var data = getFormData($("#formaFiltr"));
	
	var org = JSON.stringify(data);
	$.ajax({
		url: "api/sale",
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


function dodavanjeLekara(){
	console.log("aaaaa")
	var data = getFormData($("#formaFiltr"));
	
	var org = JSON.stringify(data);
	$.ajax({
		url: "api/lekari",
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


