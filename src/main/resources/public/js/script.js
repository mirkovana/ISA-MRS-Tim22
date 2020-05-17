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
		url: "/auth/login",
		method: "POST",
		dataType: "json",
		contentType:"application/json",
		data: JSON.stringify(user),
		success: function(result){
			 localStorage.setItem('user',JSON.stringify(result));
    location.replace(getAdminUrlSlug(result));
		}
	});
}

function logout(e) {
	  e.preventDefault();
	  localStorage.user = null;
	  location.replace("/login.html");
	  return false;
}

function getAdminUrlSlug(user) {
	  switch(user.role) {
	    case 'ROLE_ADMINKC':
	      return '/homepageadminkc.html';
	    case 'ROLE_ADMINK':
	      return '/homepageadministratorklinike.html';
	    case 'ROLE_LEKAR':
	      return '/homepagelekar.html';
	    case 'ROLE_MS':
	      return '/homepagems.html';
	    case 'ROLE_PACIJENT':
		  return '/homepagepacijent.html';
	    default:
	      return '';
	  }
}

//pozivacemo poacijenti
function setUpUserPage() {
	$.ajax({
		url: "api/pacijenti",
		type: "GET",
		contentType: "application/json",
		dataType: "json",
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
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
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
		complete: function(data) {
			sl = JSON.parse(data.responseText);
			console.log(sl)
			loadSifrarnikLekova(sl);
			
		}
	});
}

//pozivacemo sifrarnik dijagnoza
function setUpUserPageAKCSD() {
	$.ajax({
		url: "api/sifrarnikDijagnoza",
		type: "GET",
		contentType: "application/json",
		dataType: "json",
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
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
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
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
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
		complete: function(data) {
			lekari = JSON.parse(data.responseText);
			console.log(lekari)
			loadLekari(lekari);
			
		}
	});
}

//klinike
function setUpUserPageAKC() {
	console.log(localStorage.getItem('user'));
	console.log(JSON.parse(localStorage.getItem('user')));
	console.log(JSON.parse(localStorage.getItem('user')).token);
	console.log(JSON.parse(localStorage.getItem('user')).token.accessToken);
	console.log('Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken);
	$.ajax({
		url: "api/klinike",
		type: "GET",
		contentType: "application/json",
		dataType: "json",
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
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
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
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
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
		complete: function(data) {
			operacije = JSON.parse(data.responseText);
			console.log(operacije)
			loadOperacije(operacije);
			
		}
	});
}

//popunjavanje tabele sala
function loadSale(sale) {
	$("#tabela_sale tbody tr").remove(); 
	$("#tabela_sale thead ").remove();
	var table = $("#tabela_sale");
	table.append(makeTableHeaderSale());	
	for(let s of sale) {
		table.append(makeTableRowSale(s));
	}
}

//popunjavanje klinike
function loadKlinike(klinike) {
	$("#tabela_klinike tbody tr").remove(); 
	$("#tabela_klinike thead ").remove();
	var table = $("#tabela_klinike");
	table.append(makeTableHeaderKlinike());	
	for(let k of klinike) {
		table.append(makeTableRowKlinike(k));
	}
}

function loadPacijenti(pacijenti) {
	$("#tabela_pacijenti tbody tr").remove(); 
	$("#tabela_pacijenti thead ").remove();
	var table = $("#tabela_pacijenti");
	table.append(makeTableHeaderP());	
	for(let p of pacijenti) {
		table.append(makeTableRowP(p));
	}
}
//sifrarnik lekova
function loadSifrarnikLekova(sl) {
	$("#tabela_sl tbody tr").remove(); 
	$("#tabela_sl thead ").remove();
	var table = $("#tabela_sl");
	table.append(makeTableHeaderSL());	
	for(let s of sl) {
		table.append(makeTableRowSL(s));
	}
}

//sifrarbik dijagnoza
function loadSifrarnikDijagnoza(sd) {
	$("#tabela_sd tbody tr").remove(); 
	$("#tabela_sd thead ").remove();
	var table = $("#tabela_sd");
	table.append(makeTableHeaderSD());	
	for(let s of sd) {
		table.append(makeTableRowSD(s));
	}
}

function loadLekari(lekari) {
	$("#tabela_lekari tbody tr").remove(); 
	$("#tabela_lekari thead ").remove();
	var table = $("#tabela_lekari");
	table.append(makeTableHeaderLekari());	
	for(let l of lekari) {
		table.append(makeTableRowLekari(l));
	}
}

//popunjavanje tabele pregleda
function loadPregledi(pregledi) {
	$("#tabela_pregledi tbody tr").remove(); 
	$("#tabela_pregledi thead ").remove();
	var table = $("#tabela_pregledi");
	table.append(makeTableHeaderPP());	
	for(let p of pregledi) {
		table.append(makeTableRowPregledi(p));
	}
}

//popunjavanje tabele operacija
function loadOperacije(operacije) {
	$("#tabela_operacije tbody tr").remove(); 
	$("#tabela_operacije thead ").remove();
	var table = $("#tabela_operacije");
	table.append(makeTableHeaderPO());	
	for(let o of operacije) {
		table.append(makeTableRowOperacije(o));
	}
}

function makeTableHeaderSale(){
	
	var row="";
	 row =
			`<thead class="thead-light" bgcolor="white">
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
			`<thead class="thead-light" bgcolor="white">
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
			`<thead class="thead-light" bgcolor="white">
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
			`<thead class="thead-light" bgcolor="white">
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
			`<thead class="thead-light" bgcolor="white">
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
			`<thead class="thead-light" bgcolor="white">
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
			`<thead class="thead-light" bgcolor="white">
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
			`<thead class="thead-light" bgcolor="white">
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
			<td class="izgledTabele" id='${s.nazivSale}'>${s.nazivSale}</td>
			<td class="izgledTabele" id='${s.brojSale}'>${s.brojSale}</td>
		</tr>`;
	
	return row;
}
//row za SL
function makeTableRowSL(s) {
	var row = "";
	
	  row =
		`<tr>
			<td class="izgledTabele" >${s.nazivLeka}</td>
			<td class="izgledTabele" >${s.sifraLeka}</td>
		</tr>`;
	
	return row;
}
//row za SD
function makeTableRowSD(s) {
	var row = "";
	
	  row =
		`<tr>
			<td class="izgledTabele">${s.nazivDijagnoze}</td>
			<td class="izgledTabele" >${s.sifraDijagnoze}</td>
		</tr>`;
	
	return row;
}

function makeTableRowKlinike(k) {
	var row = "";
	
	  row =
		`<tr>
			<td class="izgledTabele" >${k.naziv}</td>
			<td class="izgledTabele" >${k.adresa}</td>
			<td class="izgledTabele" >${k.grad}</td>
			<td class="izgledTabele" >${k.opis}</td>
			<td class="izgledTabele" >${k.ocena}</td>
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
			<td class="izgledTabele" id='${p.vreme}'>${datum}</td>
			<td class="izgledTabele" id='${p.vreme}'>${vreme}</td>
			<td class="izgledTabele" id='${p.trajanje}'>${p.trajanje}</td>
			<td class="izgledTabele" id='${p.sala}'>${p.sala.nazivSale}</td>
			<td class="izgledTabele" id='${p.tipPregleda}'>${p.tipPregleda}</td>
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
			<td class="izgledTabele" id='${o.vremeOperacije}'>${datum}</td>
			<td class="izgledTabele" id='${o.vremeOperacije}'>${vreme}</td>
			<td class="izgledTabele" id='${o.trajanje}'>${o.trajanje}</td>
			<td class="izgledTabele" id='${o.dodatneInfoOOperaciji}'>${o.dodatneInfoOOperaciji}</td>
			<td class="izgledTabele" id='${o.sala}'>${o.sala.nazivSale}</td>
		</tr>`;
	
	return row;
}

function makeTableRowP(p) {
	//var aktivan = vm.radi ? "Radi" : "Ne radi";
	var row = "";
	
	  row =
		`<tr>
			<td class="izgledTabele" id='${p.ime}'>${p.ime}</td>
			<td class="izgledTabele" id='${p.prezime}'>${p.prezime}</td>
			<td class="izgledTabele">${p.email}</td>
			<td class="izgledTabele">${p.adresa}</td>
			<td class="izgledTabele">${p.grad}</td>
			<td class="izgledTabele">${p.drzava}</td>
			<td class="izgledTabele">${p.brojTelefona}</td>
			<td class="izgledTabele">${p.brojOsiguranika}</td>
		</tr>`;
	
	return row;
}

function makeTableRowLekari(l) {
	//var aktivan = vm.radi ? "Radi" : "Ne radi";
	var row = "";
	
	  row =
		`<tr>
			<td class="izgledTabele">${l.ime}</td>
			<td class="izgledTabele">${l.prezime}</td>
			<td class="izgledTabele">${l.email}</td>
			<td class="izgledTabele">${l.adresa}</td>
			<td class="izgledTabele">${l.grad}</td>
			<td class="izgledTabele">${l.drzava}</td>
			<td class="izgledTabele">${l.brojTelefona}</td>
			<td class="izgledTabele">${l.ocena}</td>
		</tr>`;
	
	return row;
}

function dodavanjePacijenta(){
	var data = getFormData($("#formaFiltr"));
	
	var org = JSON.stringify(data);
	$.ajax({
		url: "auth/signup",
		type: "POST",
		data: org,
		contentType: "application/json",
		dataType: "json",
		/*headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },*/
		complete : function (data) {
			d = JSON.parse(data.responseText);
			if(d.added) {
				$("#uspesno").hide();
				$("#neuspesno").show();
				
			}else{
				window.location.replace("./homepagepacijent.html");
			}
		} 

	});
	
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
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
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

function dodavanjeSL(){
	console.log("aaaaa")
	var data = getFormData($("#formaFiltr"));
	
	var org = JSON.stringify(data);
	$.ajax({
		url: "api/sifrarnikLekova",
		type: "POST",
		data: org,
		contentType: "application/json",
		dataType: "json",
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
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
function dodavanjeSD(){
	console.log("aaaaa")
	var data = getFormData($("#formaFiltr"));
	
	var org = JSON.stringify(data);
	$.ajax({
		url: "api/sifrarnikDijagnoza",
		type: "POST",
		data: org,
		contentType: "application/json",
		dataType: "json",
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
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
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
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
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
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
		dataType: "json",
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    }
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
		dataType: "json",
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    }
	});
	
}


