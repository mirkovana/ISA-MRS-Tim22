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

//pozivacemo sifrarbik lekova
function setUpUserPageAKCSL() {
	$.ajax({
		url: "api/sifrarnikLekova",
		type: "GET",
		contentType: "application/json",
		dataType: "json",
		complete: function(data) {
			sl = JSON.parse(data.responseText);
			console.log(sl)
			loadSifrarnikLekova(sl);
			
		}
	});
}

//pozivacemo sifrarnik dijagnoza
function setUpUserPageAKCSD() {
	document.getElementById("myForm").clear();
	
	$.ajax({
		url: "api/sifrarnikDijagnoza",
		type: "GET",
		contentType: "application/json",
		dataType: "json",
		complete: function(data) {
			sd = JSON.parse(data.responseText);
			console.log(sd)
			loadSifrarnikDijagnoza(sd);
			
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
function setUpUserPageAKL() {
	$.ajax({
		url: "api/lekari",
		type: "GET",
		contentType: "application/json",
		dataType: "json",
		complete: function(data) {
			lekari = JSON.parse(data.responseText);
			console.log(lekari)
			loadLekari(lekari);
			
		}
	});
}

//klinike
function setUpUserPageAKC() {
	$.ajax({
		url: "api/klinike",
		type: "GET",
		contentType: "application/json",
		dataType: "json",
		complete: function(data) {
			klinike = JSON.parse(data.responseText);
			console.log(klinike)
			loadKlinike(klinike);
			
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

//popunjavanje klinike
function loadKlinike(klinike) {
	var table = $("#tabela_klinike");
	table.append(makeTableHeaderKlinike());	
	for(let k of klinike) {
		table.append(makeTableRowKlinike(k));
	}
}

function loadPacijenti(pacijenti) {
	var table = $("#tabela_pacijenti");
	table.append(makeTableHeaderP());	
	for(let p of pacijenti) {
		table.append(makeTableRowP(p));
	}
}
//sifrarnik lekova
function loadSifrarnikLekova(sl) {
	var table = $("#tabela_sl");
	table.append(makeTableHeaderSL());	
	for(let s of sl) {
		table.append(makeTableRowSL(s));
	}
}

//sifrarbik dijagnoza
function loadSifrarnikDijagnoza(sd) {
	var table = $("#tabela_sd");
	table.append(makeTableHeaderSD());	
	for(let s of sd) {
		table.append(makeTableRowSD(s));
	}
}

function loadLekari(lekari) {
	var table = $("#tabela_lekari");
	table.append(makeTableHeaderLekari());	
	for(let l of lekari) {
		table.append(makeTableRowLekari(l));
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
function makeTableHeaderKlinike(){
	
	var row="";
	 row =
			`<thead class="thead-light">
					<tr>
						<th>Naziv</th>
						<th>Adresa</th>
						<th>Grad</th>
						<th>Opis</th>
						<th>Ocena</th>
				     </tr>
				</thead>`;
		
		return row;
}

//header za SL
function makeTableHeaderSL(){
	
	var row="";
	 row =
			`<thead class="thead-light">
					<tr>
						<th>Naziv leka</th>
						<th>Sifra leka</th>
						
				     </tr>
				</thead>`;
		
		return row;
}

//header za SD
function makeTableHeaderSD(){
	
	var row="";
	 row =
			`<thead class="thead-light">
					<tr>
						<th>Naziv dijagnoze</th>
						<th>Sifra dijagnoze</th>
						
				     </tr>
				</thead>`;
		
		return row;
}

function makeTableHeaderLekari(){
	
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
						<th>Ocena</th>
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
						<th>Datum</th>
						<th>Vreme</th>
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
						<th>Datum</th>
						<th>Vreme</th>
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
//row za SL
function makeTableRowSL(s) {
	var row = "";
	
	  row =
		`<tr>
			<td class="0" >${s.nazivLeka}</td>
			<td class="1" >${s.sifraLeka}</td>
		</tr>`;
	
	return row;
}
//row za SD
function makeTableRowSD(s) {
	var row = "";
	
	  row =
		`<tr>
			<td class="0">${s.nazivDijagnoze}</td>
			<td class="1" >${s.sifraDijagnoze}</td>
		</tr>`;
	
	return row;
}

function makeTableRowKlinike(k) {
	var row = "";
	
	  row =
		`<tr>
			<td class="0" >${k.naziv}</td>
			<td class="1" >${k.adresa}</td>
			<td class="2" >${k.grad}</td>
			<td class="3" >${k.opis}</td>
			<td class="4" >${k.ocena}</td>
		</tr>`;
	
	return row;
}

function makeTableRowPregledi(p) {
	var row = "";
	var deli = p.vreme.split("T");
	var datum = deli[0];
	var deli1 = deli[1].split(".");
	var vreme = deli1[0];
	
	  row =
		`<tr>
			<td class="0" id='${p.vreme}'>${datum}</td>
			<td class="1" id='${p.vreme}'>${vreme}</td>
			<td class="2" id='${p.trajanje}'>${p.trajanje}</td>
			<td class="3" id='${p.sala}'>${p.sala.nazivSale}</td>
			<td class="4" id='${p.tipPregleda}'>${p.tipPregleda}</td>
		</tr>`;
	
	return row;
}

function makeTableRowOperacije(o) {
	var row = "";
	var deli = o.vremeOperacije.split("T");
	var datum = deli[0];
	var deli1 = deli[1].split(".");
	var vreme = deli1[0];
	
	  row =
		`<tr>
			<td class="0" id='${o.vremeOperacije}'>${datum}</td>
			<td class="1" id='${o.vremeOperacije}'>${vreme}</td>
			<td class="2" id='${o.trajanje}'>${o.trajanje}</td>
			<td class="3" id='${o.dodatneInfoOOperaciji}'>${o.dodatneInfoOOperaciji}</td>
			<td class="4" id='${o.sala}'>${o.sala.nazivSale}</td>
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

function makeTableRowLekari(l) {
	//var aktivan = vm.radi ? "Radi" : "Ne radi";
	var row = "";
	
	  row =
		`<tr>
			<td class="0">${l.ime}</td>
			<td class="1">${l.prezime}</td>
			<td class="2">${l.email}</td>
			<td class="3">${l.adresa}</td>
			<td class="4">${l.grad}</td>
			<td class="5">${l.drzava}</td>
			<td class="6">${l.brojTelefona}</td>
			<td class="7">${l.ocena}</td>
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
		dataType: "json",
		complete : function (data) {
			d = JSON.parse(data.responseText);
			if(d.added) {
				$("#uspesno").show();
				$("#neuspesno").hide();
				
			}else{
				$("#neuspesno").show();
				$("#uspesno").hide();
			}
		} 

	});
	
}



function dodavanjeKlinike(){
	console.log("aaaaa")
	var data = getFormData($("#formaFiltr"));
	
	var org = JSON.stringify(data);
	$.ajax({
		url: "api/klinike",
		type: "POST",
		data: org,
		contentType: "application/json",
		dataType: "json",
		complete : function (data) {
			d = JSON.parse(data.responseText);
			if(d.added) {
				$("#uspesno").show();
				$("#neuspesno").hide();
				
			}else{
				$("#neuspesno").show();
				$("#uspesno").hide();
			}
		} 

	});
	
}
function dodavanjeAK(){
	console.log("aaaaa")
	var data = getFormData($("#formaFiltr"));
	
	var org = JSON.stringify(data);
	$.ajax({
		url: "api/adminiKlinike",
		type: "POST",
		data: org,
		contentType: "application/json",
		dataType: "json",
		complete : function (data) {
			d = JSON.parse(data.responseText);
			if(d.added) {
				$("#neuspesno").show();
				$("#uspesno").hide();
				
				
			}else{
				$("#uspesno").show();
				$("#neuspesno").hide();
			}
		}

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

	});
	
}


