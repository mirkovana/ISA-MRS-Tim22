var pretrazeneKlinike;
var filtriraneKlinike;
var pretrazeniLekari;
var datum;
var vremeG;
var sifrarnikD;
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
	localStorage.tipPregleda = null;
	localStorage.cena = null;
	localStorage.datum = null;
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
	$("#sortP").show();
	var obj = JSON.parse(localStorage.getItem('user'));
	$.ajax({
		url: "api/pacijenti/ms/" +obj.id,
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

function prikazPacijenata() {
	var obj = JSON.parse(localStorage.getItem('user'));
	$.ajax({
		url: "api/pacijenti/admink/" + obj.id,
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

//pozivacemo poacijenti
function setUpUserPageLekar() {
	$("#pretragaIme").show();
	$("#pretragaPrezime").show();
	$("#pretragaBrOsiguranika").show();
	$("#pretraziPacijente").show();
	var obj = JSON.parse(localStorage.getItem('user'));
	$.ajax({
		url: "api/pacijenti/lekar/" +obj.id,
		type: "GET",
		contentType: "application/json",
		dataType: "json",
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
		complete: function(data) {
			pacijenti = JSON.parse(data.responseText);
			console.log(pacijenti)
			//loadPacijenti(pacijenti);
			loadPacijentiLekar(pacijenti);
		}
	});
}

//pozivacemo sifrarbik lekova
function setUpUserPageAKCSL() {
obrisiPretragu();
	console.log("USAO SAM KOD LEKOVA I OVO JE USER " + localStorage.getItem('user') );
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
	obrisiPretragu();
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

function prikazSala() {
	var obj = JSON.parse(localStorage.getItem('user'));
	$.ajax({
		url: "api/sale/admink/" + obj.id,
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

function izmenaSala() {
	$("#lineChart").hide();
	obrisiTabele();
	obrisiPretragu();
	obrisiFilter();
	$("#pretragaNaziv").show();
	$("#pretragaBroj").show();
	$("#pretraziSale").show();
	var obj = JSON.parse(localStorage.getItem('user'));
	$.ajax({
		url: "api/sale/admink/" + obj.id,
		type: "GET",
		contentType: "application/json",
		dataType: "json",
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
		complete: function(data) {
			sale = JSON.parse(data.responseText);
			console.log(sale)
			loadSaleIzmena(sale);
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

function prikazLekara() {
	$("#lineChart").hide();
	obrisiTabele();
	obrisiPretragu();
	obrisiFilter();
	$("#pretragaIme").show();
	$("#pretragaPrezime").show();
	$("#pretraziLekare").show();
	var obj = JSON.parse(localStorage.getItem('user'));
	$.ajax({
		url: "api/lekari/admink/" + obj.id,
		type: "GET",
		contentType: "application/json",
		dataType: "json",
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
		complete: function(data) {
			lekari = JSON.parse(data.responseText);
			console.log(lekari)
			loadLekariAdmin(lekari);
		}
	});
}

function lekariZaIzvestaj() {
	$("#lineChart").hide();
	obrisiTabele();
	obrisiPretragu();
	obrisiFilter();
	var obj = JSON.parse(localStorage.getItem('user'));
	$.ajax({
		url: "api/lekari/admink/" + obj.id,
		type: "GET",
		contentType: "application/json",
		dataType: "json",
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
		complete: function(data) {
			lekari = JSON.parse(data.responseText);
			console.log(lekari)
			loadLekariZaIzvestaj(lekari);
		}
	});
}

//klinike
function setUpUserPageAKC() {
	$("#pretraga").show();
	$("#datumBiranje").show();
	$("#dan").show();
	$("#mesec").show();
	$("#godina").show();
	$("#pretrazi").show();
	$("#sortLKP").show();
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


//lista klinika za akc
function setUpUserPageKAKC() {
	$("#sortKAKC").show();
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
			loadKlinikeAKC(klinike);
			
		}
	});
}
//pozivacemo preglede
function setUpUserPagePP() {
	var obj = JSON.parse(localStorage.getItem('user'));
	$.ajax({
		url: "api/pregledi/" + obj.id,
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
	var obj = JSON.parse(localStorage.getItem('user'));
	console.log("DA VIDIM IDDDDDDDDDDDDDDDDDD: "+obj.id);
	$.ajax({
		url: "api/operacije/" + obj.id,
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

function setUpZK() {
	var obj = JSON.parse(localStorage.getItem('user'));
	$.ajax({
		url: "api/kartoni/svi/" + obj.id,
		type: "GET",
		contentType: "application/json",
		dataType: "json",
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
		complete: function(data) {
			kartoni = JSON.parse(data.responseText);
			console.log(kartoni)
			pregledModZKPacijenta(kartoni);
		}
	});
}

//menjanje profila klinike kod AK
function setUpKlinikeAK() {
	$("#lineChart").hide();
	obrisiTabele();
	obrisiPretragu();
	obrisiFilter();
	var obj = JSON.parse(localStorage.getItem('user'));
	$.ajax({
		url: "api/klinike/admink/" + obj.id,
		type: "GET",
		contentType: "application/json",
		dataType: "json",
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
		complete: function(data) {
			klinika = JSON.parse(data.responseText);
			console.log(klinika)
			pregledModKlinikeAK(klinika);
		}
	});
}

function setUpIzvestaji() {
	$("#lineChart").hide();
	obrisiTabele();
	obrisiPretragu();
	obrisiFilter();
	var obj = JSON.parse(localStorage.getItem('user'));
	$.ajax({
		url: "api/klinike/admink/" + obj.id,
		type: "GET",
		contentType: "application/json",
		dataType: "json",
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
		complete: function(data) {
			klinika = JSON.parse(data.responseText);
			console.log(klinika)
			prikazIzvestajaMod(klinika);
		}
	});
}

function setUpKlinike() {
	var obj = JSON.parse(localStorage.getItem('idKlinike'));
	$.ajax({
		url: "api/klinike/id/" + obj,
		type: "GET",
		contentType: "application/json",
		dataType: "json",
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
		complete: function(data) {
			klinika = JSON.parse(data.responseText);
			console.log(klinika)
			pregledModKlinike(klinika);
		}
	});
}

//popunjavanje tabele sala
function loadSale(sale) {
	obrisiTabele();
	$("#tabela_sale tbody tr").remove(); 
	$("#tabela_sale thead ").remove();
	var table = $("#tabela_sale");
	table.append(makeTableHeaderSale());	
	for(let s of sale) {
		table.append(makeTableRowSale(s));
	}
}

function loadSaleIzmena(sale) {
	obrisiTabele();
	$("#tabela_sale tbody tr").remove(); 
	$("#tabela_sale thead ").remove();
	var table = $("#tabela_sale");
	table.append(makeTableHeaderSaleIzmena());	
	for(let s of sale) {
		table.append(makeTableRowSaleIzmena(s));
	}
}

//popunjavanje klinike
function loadKlinike(klinike) {
	obrisiTabele();
	obrisiFilter();
	$("#sortTPP").hide();
	$("#tabela_klinike tbody tr").remove(); 
	$("#tabela_klinike thead ").remove();
	var table = $("#tabela_klinike");
	table.append(makeTableHeaderKlinike());	
	for(let k of klinike) {
		table.append(makeTableRowKlinike(k));
	}
}

function loadKlinikeAKC(klinike) {
	obrisiTabele();
	
	$("#tabela_klinikeAKC tbody tr").remove(); 
	$("#tabela_klinikeAKC thead ").remove();
	var table = $("#tabela_klinikeAKC");
	table.append(makeTableHeaderKlinikeAKC());	
	for(let k of klinike) {
		table.append(makeTableRowKlinikeAKC(k));
	}
}

function loadPacijenti(pacijenti) {
	obrisiTabele();
	obrisiPretragu();
	$("#sortP").show();
	$("#tabela_pacijenti tbody tr").remove(); 
	$("#tabela_pacijenti thead ").remove();
	var table = $("#tabela_pacijenti");
	table.append(makeTableHeaderP());	
	for(let p of pacijenti) {
		table.append(makeTableRowP(p));
	}
}

function loadPacijentiLekar(pacijenti) {
	obrisiTabele();
	$("#sortP").show();
	$("#tabela_pacijenti_lekar tbody tr").remove(); 
	$("#tabela_pacijenti_lekar thead ").remove();
	var table = $("#tabela_pacijenti_lekar");
	table.append(makeTableHeaderPL());	
	for(let p of pacijenti) {
		table.append(makeTableRowPL(p));
	}
}

function loadZahteviOdsustva(zahtevi) {
	$("#tabela_zahtevi_o tbody tr").remove(); 
	$("#tabela_zahtevi_o thead ").remove();
	var table = $("#tabela_zahtevi_o");
	table.append(makeTableHeaderZahteviO());	
	for(let z of zahtevi) {
		table.append(makeTableRowZahteviO(z));
	}
}

function loadZahteviZaP(zahtevi) {
	console.log("usao u load zahtevi za p");
	$("#tabela_zahtevi_p tbody tr").remove(); 
	$("#tabela_zahtevi_p thead ").remove();
	var table = $("#tabela_zahtevi_p");
	table.append(makeTableHeaderZahteviP());	//ovde sam stala, napravi header i row
	for(let z of zahtevi) {
		table.append(makeTableRowZahteviP(z));
	}
}
function loadZahteviZaO(zahtevi) {
	console.log("usao u load zahtevi za p");
	$("#tabela_zahtevi_o tbody tr").remove(); 
	$("#tabela_zahtevi_o thead ").remove();
	var table = $("#tabela_zahtevi_o");
	table.append(makeTableHeaderZahteviOPA());	//ovde sam stala, napravi header i row
	for(let z of zahtevi) {
		table.append(makeTableRowZahteviOPA(z));
	}
}
function loadZahteviZaOp(zahtevi) {
	console.log("usao u load zahtevi za p");
	$("#tabela_zahtevi_op tbody tr").remove(); 
	$("#tabela_zahtevi_op thead ").remove();
	var table = $("#tabela_zahtevi_p");
	table.append(makeTableHeaderZahteviOp());
	for(let z of zahtevi) {
		table.append(makeTableRowZahteviOp(z));
	}
}

//sifrarnik lekova
function loadSifrarnikLekova(sl) {
	obrisiTabele();
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
	obrisiTabele();
	$("#tabela_sd tbody tr").remove(); 
	$("#tabela_sd thead ").remove();
	var table = $("#tabela_sd");
	table.append(makeTableHeaderSD());	
	for(let s of sd) {
		table.append(makeTableRowSD(s));
	}
}

function loadLekari(lekari) {
	obrisiTabele();
	$("#tabela_lekari tbody tr").remove(); 
	$("#tabela_lekari thead ").remove();
	var table = $("#tabela_lekari");
	table.append(makeTableHeaderLekari());	
	for(let l of lekari) {
		table.append(makeTableRowLekari(l));
	}
}

function loadLekariAdmin(lekari) {
	obrisiTabele();
	$("#tabela_lekari tbody tr").remove(); 
	$("#tabela_lekari thead ").remove();
	var table = $("#tabela_lekari");
	table.append(makeTableHeaderLekariAdmin());	
	for(let l of lekari) {
		table.append(makeTableRowLekariAdmin(l));
	}
}

function loadLekariZaIzvestaj(lekari) {
	obrisiTabele();
	$("#tabela_lekari_izvestaj tbody tr").remove(); 
	$("#tabela_lekari_izvestaj thead ").remove();
	var table = $("#tabela_lekari_izvestaj");
	table.append(makeTableHeaderLekariZaIzvestaj());	
	for(let l of lekari) {
		table.append(makeTableRowLekariZaIzvestaj(l));
	}
}

//popunjavanje tabele pregleda
function loadPregledi(pregledi) {
	obrisiTabele();
	obrisiPretragu();
	$("#sortTPP").show();
	obrisiFilter();
	$("#tabela_pregledi tbody tr").remove(); 
	$("#tabela_pregledi thead ").remove();
	var table = $("#tabela_pregledi");
	table.append(makeTableHeaderPP());	
	for(let p of pregledi) {
		table.append(makeTableRowPregledi(p));
		var sel = document.getElementById('ocenaL');
		document.getElementById('showTxt').onclick = function () {
	        // access text property of selected option
	        prikaz = sel.options[sel.selectedIndex].text;
	        console.log("OCENA L: " + prikaz + " ID LEKARA: " + p.lekar.id);
	        oceniLekara(prikaz, p.lekar.id);
	    }
		var sel1 = document.getElementById('ocenaK');
		document.getElementById('showTxt1').onclick = function () {
	        // access text property of selected option
	        prikaz = sel1.options[sel1.selectedIndex].text;
	        console.log("OCENA K: " + prikaz + " ID KLINIKE: " + p.klinika.idKlinike);
	        oceniKliniku(prikaz, p.klinika.idKlinike);
		}    
	}
}



function oceniLekara(prikaz, idL) {
	$.ajax({
		url: "api/lekari/" + idL + "/" + prikaz,
		type: "PUT",
		contentType: "application/json",
		dataType: "json",
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
		complete: function(data) {
			alert("Uspesno ste ocenili lekara!");
		}
	});
}

function oceniKliniku(prikaz, idK) {
	$.ajax({
		url: "api/klinike/" + idK + "/" + prikaz,
		type: "PUT",
		contentType: "application/json",
		dataType: "json",
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
		complete: function(data) {
			alert("Uspesno ste ocenili kliniku!");
		}
	});
}

//popunjavanje tabele operacija
function loadOperacije(operacije) {
	obrisiTabele();
	obrisiPretragu();
	obrisiFilter();
	$("#tabela_operacije tbody tr").remove(); 
	$("#tabela_operacije thead ").remove();
	var table = $("#tabela_operacije");
	table.append(makeTableHeaderPO());	
	for(let o of operacije) {
		table.append(makeTableRowOperacije(o));
		var sel = document.getElementById('ocenaL');
		document.getElementById('showTxt').onclick = function () {
	        // access text property of selected option
	        prikaz = sel.options[sel.selectedIndex].text;
	        console.log("OCENA L: " + prikaz + " ID LEKARA: " + o.lekar.id);
	        oceniLekara(prikaz, o.lekar.id);
	    }
		var sel1 = document.getElementById('ocenaK');
		document.getElementById('showTxt1').onclick = function () {
	        // access text property of selected option
	        prikaz = sel1.options[sel1.selectedIndex].text;
	        console.log("OCENA K: " + prikaz + " ID KLINIKE: " + o.klinika.idKlinike);
	        oceniKliniku(prikaz, o.klinika.idKlinike);
		}    
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

function makeTableHeaderSaleIzmena(){
	
	var row="";
	 row =
			`<thead class="thead-light" bgcolor="white">
					<tr>
						<th>Naziv</th>
						<th>Broj</th>
						<th>Izmena</th>
						<th>Brisanje</th>
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
						<th>Prikazi</th>
				     </tr>
				</thead>`;
		
		return row;
}
function makeTableHeaderKlinikeAKC(){
	
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

function makeTableHeaderLekariAdmin(){
	
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
						<th>Brisanje</th>
					</tr>
				</thead>`;
		
		return row;
}

function makeTableHeaderLekariZaIzvestaj(){
	var row="";
	 row =
			`<thead class="thead-light" bgcolor="white">
					<tr>
						<th>Ime</th>
						<th>Prezime</th>
						<th>Prosecna ocena</th>
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
						<th>Ocena klinike</th>
						<th>Ocena lekara</th>
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
						<th>Ocena klinike</th>
						<th>Ocena lekara</th>
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
						<th>Profil pacijenta</th>
					</tr>
				</thead>`;
		
		return row;
}

function makeTableHeaderPL(){
	
	var row="";
	 row =
			`<thead class="thead-light" bgcolor="white">
					<tr>
						<th>Broj osiguranika</th>
						<th>Ime</th>
						<th>Prezime</th>
						<th>Profil pacijenta</th>
					</tr>
				</thead>`;
		
		return row;
}

function makeTableHeaderZahteviO(){
	
	var row="";
	 row =
			`<thead class="thead-light" bgcolor="white">
					<tr>
						<th>Datum pocetka</th>
						<th>Datum kraja</th>
						<th>Razlog</th>
						<th>Prihvatanje</th>
						<th>Odbijanje</th>
					</tr>
				</thead>`;
		
		return row;
}

function makeTableHeaderZahteviP(){
	
	var row="";
	 row =
			`<thead class="thead-light" bgcolor="white">
					<tr>
						<th>Lekar</th>
						<th>Pacijent</th>
						<th>Vreme</th>
						<th>Tip Pregleda</th>
						<th>Cena</th>
						<th>Sala</th>
					</tr>
				</thead>`;
		
		return row;
}
function makeTableHeaderZahteviOPA(){
	
	var row="";
	 row =
			`<thead class="thead-light" bgcolor="white">
					<tr>
						<th>Lekar</th>
						<th>Pacijent</th>
						<th>Vreme</th>
						<th>Sala</th>
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

function makeTableRowSaleIzmena(sal) {
	console.log("SAL" + sal);
	var row = "";
	
	  row =
		`<tr>
			<td class="izgledTabele" id='${sal.nazivSale}'>${sal.nazivSale}</td>
			<td class="izgledTabele" id='${sal.brojSale}'>${sal.brojSale}</td>
			<td class="izgledTabele"><input type="button" id="prikazSale" value="Izmeni" onClick="prebaciNaIzmenuSale(${sal.idSale})"/></td>
			<td class="izgledTabele"><input type="button" id="brisanjeSale" value="Obrisi" onClick="brisanjeSale(${sal.idSale})"/></td>
		</tr>`;
	
	return row;
}

function brisanjeSale(idSale){
	$.ajax({
		url: "api/sale/brisanje/" + idSale,
		type: "DELETE",
		contentType:"application/json",
		dataType: "json",
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
		complete : function (data) {
			console.log("zavrsio");
			//sale = JSON.parse(data.responseText);
			//console.log(sale);
			//loadSaleIzmena(sale);
			alert("Uspesno ste izbrisali salu!");
			izmenaSala();
		}
	});
}

function prebaciNaIzmenuSale(idSale){
	console.log("uslo u prebaciNaIzmenuSale");
	window.location.replace("#izmenajednesale");
	localStorage.setItem('idSale', idSale);
	prebacivanjeNaIzmenuSale(idSale);
}

function prebacivanjeNaIzmenuSale(idSale) {
	console.log("uslo u prebacivanjenaizmenusale");
	$.ajax({
		url: "api/sale/idSale/" + idSale,
		type: "GET",
		contentType: "application/json",
		dataType: "json",
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
		complete: function(data) {
			sala = JSON.parse(data.responseText);
			console.log(sala)
			modIzmenaSale(sala);
		}
	});
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

function makeTableRowKlinikeAKC(k) {
	var row = "";
	
	row =
		`<tr>
			<td class="izgledTabele">${k.naziv}</td>
			<td class="izgledTabele" >${k.adresa}</td>
			<td class="izgledTabele" >${k.grad}</td>
			<td class="izgledTabele" >${k.opis}</td>
			<td class="izgledTabele" >${k.ocena}</td>
		</tr>`;
	
	return row;
}
function makeTableRowKlinike(k) {
	var row = "";
	
	row =
		`<tr>
			<td class="izgledTabele">${k.naziv}</td>
			<td class="izgledTabele" >${k.adresa}</td>
			<td class="izgledTabele" >${k.grad}</td>
			<td class="izgledTabele" >${k.opis}</td>
			<td class="izgledTabele" >${k.ocena}</td>
			<td class="izgledTabele" ><input type="button" id="profilKlinike" value="Profil klinike" onClick="prebaciNaKliniku(${k.idKlinike})"/></td>
		</tr>`;
	
	return row;
}

function prebaciNaKliniku(idKlinike) {
	///klinike/id/{id}
	var dan = document.getElementById("dan").value;
	var mesec = document.getElementById("mesec").value;
	var godina = document.getElementById("godina").value;
    datum = godina + "-" + mesec + "-" + dan;
    localStorage.setItem('datum', datum);
	localStorage.setItem('idKlinike', idKlinike);
	window.location.replace("./profilklinike.html");
}
function prebaciNaPacijenta(idPacijenta) {
	
    localStorage.setItem('idPacijenta', idPacijenta);
    dobaviSD();
	window.location.replace("./profilpacijenta.html");
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
			<td class="izgledTabele" id='klinika'>
			    <label for="ocenaK">${p.klinika.naziv}</label>
				<form action="/nesto.php">
		  			<select name="ocenaK" id="ocenaK">
		  				<option value="1">1</option>
		  				<option value="2">2</option>
		  				<option value="3">3</option>
		  				<option value="4">4</option>
		  				<option value="5">5</option>
		  			</select>
		  		</form>
		  		<input type="button" id="showTxt1" value="Oceni" />
		  	</td>
			<td class="izgledTabele" id='lekar'>
				<label for="ocenaL">${p.lekar.ime}</label>
			    <label for="ocenaL">${p.lekar.prezime}</label>
				<form action="/nesto.php">
		  			<select name="ocenaL" id="ocenaL">
		  				<option value="1">1</option>
		  				<option value="2">2</option>
		  				<option value="3">3</option>
		  				<option value="4">4</option>
		  				<option value="5">5</option>
		  			</select>
		  		</form>
		  		<input type="button" id="showTxt" value="Oceni" />
			</td>
		</tr>`;
	var idLekara = p.lekar.id;
	console.log(idLekara);
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
			<td class="izgledTabele" id='klinika'>
			    <label for="ocenaK">${o.klinika.naziv}</label>
				<form action="/nesto.php">
		  			<select name="ocenaK" id="ocenaK">
		  				<option value="1">1</option>
		  				<option value="2">2</option>
		  				<option value="3">3</option>
		  				<option value="4">4</option>
		  				<option value="5">5</option>
		  			</select>
		  		</form>
		  		<input type="button" id="showTxt1" value="Oceni" />
		  	</td>
			<td class="izgledTabele" id='lekar'>
			    <label for="ocenaL">${o.lekar.ime}</label>
			    <label for="ocenaL">${o.lekar.prezime}</label>
				<form action="/nesto.php">
		  			<select name="ocenaL" id="ocenaL">
		  				<option value="1">1</option>
		  				<option value="2">2</option>
		  				<option value="3">3</option>
		  				<option value="4">4</option>
		  				<option value="5">5</option>
		  			</select>
		  		</form>
		  		<input type="button" id="showTxt" value="Oceni" />
			</td>
		</tr>`;
	
	return row;
}

function makeTableRowP(p) {
	
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
			<td class="izgledTabele"><input type="button" id="profilPacijenta" value="Prikazi" onClick="prebaciNaPacijenta(${p.id})"/></td>
		</tr>`;
	
	return row;
}

function makeTableRowPL(p) {
	
	var row = "";
	
	  row =
		`<tr>
			<td class="izgledTabele" id='${p.id}'>${p.brojOsiguranika}</td>
			<td class="izgledTabele" id='${p.ime}'>${p.ime}</td>
			<td class="izgledTabele" id='${p.prezime}'>${p.prezime}</td>
			<td class="izgledTabele"><input type="button" id="profilPacijenta" value="Prikazi" onClick="prebaciNaPacijenta(${p.id})"/></td>
		</tr>`;
	
	return row;
}

function makeTableRowZahteviO(z) {
	
	var row = "";
	
	  row =
		`<tr>
			<td class="izgledTabele" id='${z.datumPocetka}'>${z.datumPocetka}</td>
			<td class="izgledTabele" id='${z.datumKraja}'>${z.datumKraja}</td>
			<td class="izgledTabele" id='${z.razlog}'>${z.razlog}</td>
			<td class="izgledTabele"><input type="button" id="prihvatanje" value="Prihvati" onClick="prihvatiZO(${z.idZahtevaOdsustva})"/></td>
			<td class="izgledTabele"><input type="button" id="odbijanje" value="Odbij" onClick="odbijanjeZO(${z.idZahtevaOdsustva})"/></td>
		</tr>`;
	
	return row;
}

function makeTableRowZahteviP(z) {
	
	var row = "";
	
	  row =
		`<tr>
			<td class="izgledTabele" id='${z.lekar.ime}'>${z.lekar.ime}</td>
			<td class="izgledTabele" id='${z.pacijent.ime}'>${z.pacijent.ime}</td>
			<td class="izgledTabele" id='${z.vreme}'>${z.vreme}</td>
			<td class="izgledTabele" id='${z.tipPregleda}'>${z.tipPregleda}</td>
			<td class="izgledTabele" id='${z.cena}'>${z.cena}</td>
			<td class="izgledTabele"><input type="button" id="odaberi" value="Odaberi salu" onClick="odaberiSaluMod(${z.idZahtevaZaPregled})"/></td>
		
		</tr>`;
	
	return row;
}
function makeTableRowZahteviOPA(z) {
	
	var row = "";
	
	  row =
		`<tr>
			<td class="izgledTabele" id='${z.lekar.ime}'>${z.lekar.ime}</td>
			<td class="izgledTabele" id='${z.pacijent.ime}'>${z.pacijent.ime}</td>
			<td class="izgledTabele" id='${z.vreme}'>${z.vreme}</td>
			<td class="izgledTabele"><input type="button" id="odaberi" value="Odaberi salu" onClick="odaberiSaluModOP(${z.idZahtevaZaOperaciju}, '${z.vreme}')"/></td>
		
		</tr>`;
	
	return row;
}
function prihvatiZO(idZahteva){
	$.ajax({
		url: "api/zahteviOdsustva/prihvati/" + idZahteva,
		type: "PUT",
		contentType:"application/json",
		dataType: "json",
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
		complete : function (data) {
			console.log("zavrsio");
			alert("Odsustvo je uspesno odobreno! Mail obavestenja je poslat zaposlenom.");
			zahteviAdmin();
		}
	});
}

function odbijZO(idZahteva){
	var razlog = document.getElementById("razlog").value;
	$.ajax({
		url: "api/zahteviOdsustva/odbij/" + idZahteva + "/" + razlog,
		type: "PUT",
		contentType:"application/json",
		dataType: "json",
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
		complete : function (data) {
			console.log("zavrsio");
			alert("Odsustvo je uspesno odbijeno! Mail obavestenja je poslat zaposlenom.");
			zahteviAdmin();
		}
	});
}

function odbijanjeZO(idZO){
	$("#modalnaForma div").remove(); 
	var modal=$("#modalnaForma");
	var m = "";
	m = `<div class="imgcontainer">
	      <span onclick="document.getElementById('modal-wrapper').style.display='none'" class="close" title="Close PopUp">&times;</span>
	      <h1 style="text-align:center">Razlog odbijanja zahteva za odsustvo</h1></div>
			<div class="container">
			<br>
			<form action="" id="formaIzmena">
			<table class="table " id="tabela_modal">
				<tbody>
					<tr>
						  <td colspan = 2><textarea name="razlog"  id = "razlog"></textarea></td>
				    </tr>
					<tr><td  class='align-middle' ><button type="button" onclick="odbijZO(${idZO})">Posalji</button></td></tr>							
				</tbody>
			</table>
			</form>
			<fieldset id="log_war"></fieldset>
		    </div>`;
			modal.append(m);
		$("#modal-wrapper").show();
	
}

function makeTableRowLekari(l) {
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

function makeTableRowLekariAdmin(l) {
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
			<td class="izgledTabele"><input type="button" id="brisanjeLekara" value="Obrisi" onClick="brisanjeLekara(${l.id})"/></td>
		</tr>`;
	
	return row;
}

function brisanjeLekara(idLekara){
	$.ajax({
		url: "api/lekari/brisanje/" + idLekara,
		type: "DELETE",
		contentType:"application/json",
		dataType: "json",
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
		complete : function (data) {
			console.log("zavrsio");
			//sale = JSON.parse(data.responseText);
			//console.log(sale);
			//loadSaleIzmena(sale);
			alert("Uspesno ste izbrisali lekara!");
			prikazLekara();
		}
	});
}

function makeTableRowLekariZaIzvestaj(l) {
	var row = "";
	
	  row =
		`<tr>
			<td class="izgledTabele">${l.ime}</td>
			<td class="izgledTabele">${l.prezime}</td>
			<td class="izgledTabele">${l.ocena}</td>
		</tr>`;
	
	return row;
}

/*function dodavanjePacijenta(){
	var data = getFormData($("#formaFiltr"));
	console.log(data);
	var org = JSON.stringify(data);
	//localStorage.setItem('user',JSON.stringify(data));
	$.ajax({
		url: "auth/signup",
		type: "POST",
		data: org,
		contentType: "application/json",
		dataType: "json",
		/*headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },//ovde je bilo 
		complete : function (data) {
			d = JSON.parse(data.responseText);
			if(d.added) {
				$("#uspesno").hide();
				$("#neuspesno").show();
				
			}else{
				localStorage.setItem('user',JSON.stringify(data));
				window.location.replace("./login.html");
			}
		} 

	});
	
}*/

function dodavanjeZahtevaOdsustva(){
	console.log("uslo u dodavanje zo");
	var obj = JSON.parse(localStorage.getItem('user'));
	var data = getFormData($("#formaFiltr"));
	console.log(data);
	console.log(obj);
	var org = JSON.stringify(data);
	console.log(org.datumPocetka)
	$.ajax({
		url: "api/zahteviOdsustva/" + obj.id,
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
				$("#uspesno").hide();
				$("#neuspesno").show();		
			}else{
				let isBoss = confirm("Uspesno kreiran zahtev!");

				window.location.replace("./homepagems.html");
				$("#neuspesno").hide();
				$("#uspesno").show();
			}
		} 
	});
}

function dodavanjeZahtevaOdsustvaL(){
	var obj = JSON.parse(localStorage.getItem('user'));
	var data = getFormData($("#formaFiltr"));
	
	var org = JSON.stringify(data);
	$.ajax({
		url: "api/zahteviOdsustva/" + obj.id,
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
				$("#uspesno").hide();
				$("#neuspesno").show();		
			}else{
				let isBoss = confirm("Uspesno kreiran zahtev!");

				window.location.replace("./homepagelekar.html");
				$("#neuspesno").hide();
				$("#uspesno").show();
			}
		} 
	});
}

function zahteviAdmin() {
	$("#lineChart").hide();
	obrisiTabele();
	obrisiPretragu();
	obrisiFilter();
	console.log("usao u zahtevi admin");
	var obj = JSON.parse(localStorage.getItem('user'));
	console.log(obj);
	$.ajax({
		url: "api/zahteviOdsustva/adminK/" + obj.id,
		type: "GET",
		contentType: "application/json",
		dataType: "json",
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
		complete: function(data) {
			console.log(data);
			console.log(data.responseText);
			zahtevi = JSON.parse(data.responseText);
			console.log(zahtevi);
			loadZahteviOdsustva(zahtevi);
		}
	});
}

function zahteviZaPAdmin() {
	$("#lineChart").hide();
	obrisiTabele();
	obrisiPretragu();
	obrisiFilter();
	console.log("usao u zahtevi za p admin");
	var obj = JSON.parse(localStorage.getItem('user'));
	console.log(obj.id);
	$.ajax({
		url: "api/zahteviZaPregled/adminK/" + obj.id,
		type: "GET",
		contentType: "application/json",
		dataType: "json",
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
		complete: function(data) {
			//console.log(data);
			//console.log(data.responseText);
			zahtevi = JSON.parse(data.responseText);
			console.log(zahtevi);
			loadZahteviZaP(zahtevi);
		}
	});
}
function zahteviZaOAdmin() {
	$("#lineChart").hide();
	obrisiTabele();
	obrisiPretragu();
	obrisiFilter();
	console.log("usao u zahtevi za p admin");
	var obj = JSON.parse(localStorage.getItem('user'));
	console.log(obj.id);
	$.ajax({
		url: "api/zahteviZaOperaciju/adminK/" + obj.id,
		type: "GET",
		contentType: "application/json",
		dataType: "json",
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
		complete: function(data) {
			//console.log(data);
			//console.log(data.responseText);
			zahtevi = JSON.parse(data.responseText);
			console.log(zahtevi);
			loadZahteviZaO(zahtevi);
		}
	});
}

/*function dodavanjeAKC(){
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
				let isBoss = confirm("Uspesno dodat administrator klinickog centra!");

				window.location.replace("./homepageadminkc.html");
				$("#neuspesno").show();
				$("#uspesno").hide();
			}
		} 

	});
	
}*/

function emptySL(){
	var x = document.getElementById("nazivLeka").value;
    var y = document.getElementById("sifraLeka").value;
    if (x == "" || y == "") {
        
        return 0;
    };
    return 1;
}

function dodavanjeSL(){
	console.log("aaaaa")
	var data = getFormData($("#formaFiltr"));
	
	var org = JSON.stringify(data);
	x = emptySL();
	if(x == 1){
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
					alert("Sifra leka vec postoji! Pokusajte ponovo.");
				}else{
					let isBoss = confirm("Uspesno dodavanje!");
	
					window.location.replace("./homepageadminkc.html");
					
				}
			} 
	
		});
	}else{
		alert("Polja ne smeju ostati prazna!");
	}
}

function emptySD(){
	var x = document.getElementById("nazivDijagnoze").value;
    var y = document.getElementById("sifraDijagnoze").value;
    if (x == "" || y == "") {
        
        return 0;
    };
    return 1;
}

function dodavanjeSD(){
	console.log("aaaaa")
	var data = getFormData($("#formaFiltr"));
	
	var org = JSON.stringify(data);
	x = emptySD();
	if(x == 1){
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
					alert("Dijagnoza sa unetom sifrom vec postoji! Pokusajte ponovo.");
					
				}else{
					let isBoss = confirm("Uspesno dodavanje!");
	
					window.location.replace("./homepageadminkc.html");
				
				}
			},
			error: function(){
				alert("Dijagnoza sa unetom sifrom vec postoji! Pokusajte ponovo.");
			}
	
		});
	}else{
		alert("Polja ne smeju ostati prazna!");
	}
}

function definisanjeSTP(){
	console.log("aaaaa")
	var data = getFormData($("#formaFiltr"));
	var obj = JSON.parse(localStorage.getItem('user'));
	console.log(data)
	console.log(obj)
	
	var org = JSON.stringify(data);
	$.ajax({
		url: "api/pregledi/idAK/" + obj.id,
		type: "POST",
		data: org,
		contentType: "application/json",
		dataType: "json",
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
		complete : function (data) {
			alert("Uspesno ste definisali novi slobodan termin pregleda!");
			window.location.replace("./homepageadministratorklinike.html");
		} 
	});
}

function emptyDodavanjeKlinike(){
    var y = document.getElementById("naziv").value;
    var a = document.getElementById("adresa").value;
    var s = document.getElementById("grad").value;
    var f = document.getElementById("opis").value;
    if (y == "" || a == "" || s == "" || f == "") {
        
        return 0;
    };
    return 1;
}

function dodavanjeKlinike(){
	console.log("aaaaa")
	var data = getFormData($("#formaFiltr"));
	
	var org = JSON.stringify(data);
	x = emptyDodavanjeKlinike();
	if(x==1){
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
					let isBoss = confirm("Uspesno dodata klinika!");
	
					window.location.replace("./homepageadminkc.html");
					$("#neuspesno").show();
					$("#uspesno").hide();
				}
			} 
	
		});
	}else{
		alert("Polja ne smeju biti prazna!");
	}
	
}

/*function dodavanjeAK(){
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
				let isBoss = confirm("Doslo je do greske!");
				$("#neuspesno").show();
				$("#uspesno").hide();
				
				
			}else{
				let isBoss = confirm("Uspesno dodat administrator klinike!");
				window.location.replace("./homepageadminkc.html");
				$("#uspesno").show();
				$("#neuspesno").hide();
			}
		}

	});
	
}*/

function dodavanjeSala(){
	console.log("aaaaa")
	var data = getFormData($("#formaFiltr"));
	var obj = JSON.parse(localStorage.getItem('user'));
	
	var org = JSON.stringify(data);
	$.ajax({
		url: "api/sale/" + obj.id,
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
				let isBoss = confirm("Uspesno dodata sala!");
				window.location.replace("./homepageadministratorklinike.html");
				$("#neuspesno").show();
				$("#uspesno").hide();
			}
		} 
	});
}

function dodavanjeTP(){
	console.log("aaaaa")
	var data = getFormData($("#formaFiltr"));
	var obj = JSON.parse(localStorage.getItem('user'));
	
	var org = JSON.stringify(data);
	$.ajax({
		url: "api/tipoviPregledaN/dodavanje/" + obj.id,
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
				let isBoss = confirm("Uspesno dodat tip pregleda!");
				window.location.replace("./homepageadministratorklinike.html");
				$("#neuspesno").show();
				$("#uspesno").hide();
			}
		} 
	});
}

function izmenaTPs() {
	$("#lineChart").hide();
	obrisiTabele();
	obrisiPretragu();
	obrisiFilter();
	$("#pretragaNazivTPN").show();
	$("#pretraziTPN").show();
	var obj = JSON.parse(localStorage.getItem('user'));
	$.ajax({
		url: "api/tipoviPregledaN/admink/" + obj.id,
		type: "GET",
		contentType: "application/json",
		dataType: "json",
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
		complete: function(data) {
			tipovi = JSON.parse(data.responseText);
			loadTPIzmena(tipovi);
		}
	});
}

function loadTPIzmena(tipovi) {
	obrisiTabele();
	$("#tabela_tp tbody tr").remove(); 
	$("#tabela_tp thead ").remove();
	var table = $("#tabela_tp");
	table.append(makeTableHeaderTPIzmena());	
	for(let tp of tipovi) {
		table.append(makeTableRowTPIzmena(tp));
	}
}

function makeTableHeaderTPIzmena(){
	
	var row="";
	 row =
			`<thead class="thead-light" bgcolor="white">
					<tr>
						<th>Naziv</th>
						<th>Izmena</th>
						<th>Brisanje</th>
					</tr>
				</thead>`;
		
		return row;
}

function makeTableRowTPIzmena(tp) {
	var row = "";
	
	  row =
		`<tr>
			<td class="izgledTabele" id='${tp.nazivTPN}'>${tp.nazivTPN}</td>
			<td class="izgledTabele"><input type="button" id="prikazTPN" value="Izmeni" onClick="prebaciNaIzmenuTP(${tp.idTPN})"/></td>
			<td class="izgledTabele"><input type="button" id="brisanjeTPN" value="Obrisi" onClick="brisanjeTPN(${tp.idTPN})"/></td>
		</tr>`;
	
	return row;
}

function prebaciNaIzmenuTP(idtpn){
	window.location.replace("#izmenajednogtpn");
	localStorage.setItem('idTPN', idtpn);
	prebacivanjeNaIzmenuTP(idtpn);
}

function prebacivanjeNaIzmenuTP(idtpn) {
	$.ajax({
		url: "api/tipoviPregledaN/idtpn/" + idtpn,
		type: "GET",
		contentType: "application/json",
		dataType: "json",
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
		complete: function(data) {
			tpn = JSON.parse(data.responseText);
			console.log(tpn)
			modIzmenaTP(tpn);
		}
	});
}

function modIzmenaTP(tpn){
	console.log("USAOOOOOO");
	$("#modalnaForma div").remove();
	var modal=$("#modalnaForma");
	var m = "";
	m = `<div class="imgcontainer">
	      <span onclick="document.getElementById('modal-wrapper').style.display='none'" class="close" title="Close PopUp">&times;</span>
	      <h1 style="text-align:center">Izmena tipa pregleda</h1></div>
			<div class="container">
			<br>
			<form action="" id="formaIzmena">
			<table class="table " id="tabela_modal">
				<tbody>
					<tr>
					<td  class='align-middle'><label for="nazivTPN">Naziv</label></td>
					<td  class='align-middle'><span style = "color:black"><input type="text" placeholder="Enter" value="${tpn.nazivTPN}" name="nazivTPN" id="nazivTPN" ></td>
					</tr>
					<tr><td  class='align-middle' ><button type="button" onclick="izmenaTP()">Sacuvaj izmene</button></td></tr>				
				</tbody>
			</table>
			</form>
			<fieldset id="log_war"></fieldset>
		    </div>`;
			modal.append(m);
		$("#modal-wrapper").show();
}

function izmenaTP(){
	console.log("usao u izmenatp");
	var dat = getFormData($("#formaIzmena"));
	var org = JSON.stringify(dat);
	var obj = JSON.parse(localStorage.getItem('idTPN'));
	console.log(obj)
	$.ajax({
		url: "api/tipoviPregledaN/izmena/" + obj,
		type: "PUT",
		data: org,
		contentType:"application/json",
		dataType: "json",
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
		complete : function (data) {
		alert("Uspesno ste izmenili tip pregleda!");	
			
		} 
	 
	});
}

function brisanjeTPN(idtpn){
	$.ajax({
		url: "api/tipoviPregledaN/brisanje/" + idtpn,
		type: "DELETE",
		contentType:"application/json",
		dataType: "json",
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
		complete : function (data) {
			console.log("zavrsio");
			//sale = JSON.parse(data.responseText);
			//console.log(sale);
			//loadSaleIzmena(sale);
			alert("Uspesno ste izbrisali tip pregleda!");
			izmenaTPs();
		}
	});
}

function dodavanjeLekara(){
	console.log("aaaaa")
	var data = getFormData($("#formaFiltr"));
	var obj = JSON.parse(localStorage.getItem('user'));
	console.log(data)
	
	var org = JSON.stringify(data);
	$.ajax({
		url: "api/lekari/" + obj.id,
		type: "POST",
		data: org,
		contentType: "application/json",
		dataType: "json",
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
	    complete : function (data) {
	    	console.log(data)
			d = JSON.parse(data.responseText);
			if(d.added) {
				$("#uspesno").show();
				$("#neuspesno").hide();
				
			}else{
				let isBoss = confirm("Uspesno dodat lekar!");
				window.location.replace("./homepageadministratorklinike.html");
				$("#neuspesno").show();
				$("#uspesno").hide();
			}
		} 
	});
}

function pregledModZKPacijenta(kartoni) {
	$("#modalnaForma div").remove(); 
	var modal=$("#modalnaForma");
	var m = "";
	m = `<div class="imgcontainer">
	      <span onclick="document.getElementById('modal-wrapper').style.display='none'" class="close" title="Close PopUp">&times;</span>
	      <h1 style="text-align:center">Zdravstveni karton pacijenta</h1></div>
			<div class="container">
			<br>
			<table class="table " id="tabela_modal">
				<tbody>
					<tr>
					<td  class='align-middle'><label for="tezina">Tezina</label></td>
					<td  class='align-middle'><span style = "color:black"><input type="text" placeholder="Enter" value="${kartoni.tezina}" name="tezina" id="tezina" disabled></td>
					</tr>
					<tr>
					<td  class='align-middle'><label for="visina">Visina</label></td>
					<td  class='align-middle'><span style = "color:black;"><input type="text" placeholder="Enter" value="${kartoni.visina}" name="visina" id="visina" disabled></td>
					</tr>
					<tr>
					<td  class='align-middle'><label for="dioptrija">Dioptrija</label></td>
					<td  class='align-middle'><span style = "color:black;"><input type="text" placeholder="Enter" value="${kartoni.dioptrija}" name="dioptrija" id="dioptrija" disabled></td>
					</tr>
					<tr>
					<td  class='align-middle'><label for="krvnaGrupa">Krvna grupa</label></td>
					<td  class='align-middle'><span style = "color:black;"><input type="text" placeholder="Enter" value="${kartoni.krvnaGrupa}" name="krvnaGrupa" id="krvnaGrupa" disabled></td>
					</tr>					
				</tbody>
			</table>
			<fieldset id="log_war"></fieldset>
		    </div>`;
			modal.append(m);
		$("#modal-wrapper").show();
	
}

function pregledModMS(){
	var obj = JSON.parse(localStorage.getItem('user'));
	$("#modalnaForma div").remove(); 
	
	console.log(obj);
	var modal=$("#modalnaForma");
	var m = "";
	m = `<div class="imgcontainer">
	      <span onclick="document.getElementById('modal-wrapper').style.display='none'" class="close" title="Close PopUp">&times;</span>
	      <h1 style="text-align:center">Profil medicinske sestre</h1></div>
			<div class="container">
			<br>
			<form action="" id="formaIzmena">
			<table class="table " id="tabela_modal">
				<tbody>
					<tr>
					<td  class='align-middle'><label for="ime">Ime</label></td>
					<td  class='align-middle'><span style = "color:black"><input type="text" placeholder="Enter" value="${obj.ime}" name="ime" id="msIme" ></td>
					</tr>
					<tr>
					<td  class='align-middle'><label for="prezime">Prezime</label></td>
					<td  class='align-middle'><span style = "color:black;"><input type="text" placeholder="Enter" value="${obj.prezime}" name="prezime" id="msPrezime" ></td>
					</tr>
					<tr>
					<td  class='align-middle'><label for="email">Email</label></td>
					<td  class='align-middle'><span style = "color:black;"><input type="text" placeholder="Enter" value="${obj.email}" name="email" id="email" readonly ></td>
					</tr>
					<tr>
					<td  class='align-middle'><label for="password">Lozinka</label></td>
					<td  class='align-middle'><span style = "color:black;"><input type="password" placeholder="Enter" value="${obj.password}" name="password" id="password" readonly></td>
					</tr>
					<tr>
					<td  class='align-middle'><label for="adresa">Adresa</label></td>
					<td  class='align-middle'><span style = "color:black;"><input type="text" placeholder="Enter" value="${obj.adresa}" name="adresa" id="adresa" ></td>
					</tr>
					<tr>
					<td  class='align-middle'><label for="grad">Grad</label></td>
					<td  class='align-middle'><span style = "color:black;"><input type="text" placeholder="Enter" value="${obj.grad}" name="grad" id="grad" ></td>
					</tr>					
					<tr>
					<td  class='align-middle'><label for="drzava">Drzava</label></td>
					<td  class='align-middle'><span style = "color:black;"><input type="text" placeholder="Enter" value="${obj.drzava}" name="drzava" id="drzava" ></td>
					</tr>					
					<tr>
					<td  class='align-middle'><label for="brojTelefona">Broj telefona</label></td>
					<td  class='align-middle'><span style = "color:black;"><input type="text" placeholder="Enter" value="${obj.brojTelefona}" name="brojTelefona" id="brojTelefona" ></td>
					</tr>	
					<tr><td  class='align-middle' ><button type="button" onclick="izmenaProfilaMS()">Sacuvaj izmene</button></td></tr>							
				</tbody>
			</table>
				</form>
			<fieldset id="log_war"></fieldset>
		    </div>`;
			modal.append(m);
		$("#modal-wrapper").show();
	
}

function pregledModAKC(){
	var obj = JSON.parse(localStorage.getItem('user'));
	$("#modalnaForma div").remove(); 
	
	console.log(obj);
	var modal=$("#modalnaForma");
	var m = "";
	m = `<div class="imgcontainer">
	      <span onclick="document.getElementById('modal-wrapper').style.display='none'" class="close" title="Close PopUp">&times;</span>
	      <h1 style="text-align:center">Profil administratora klinickog centra</h1></div>
			<div class="container">
			<br>
			<form action="" id="formaIzmena">
			<table class="table " id="tabela_modal">
				<tbody>
					<tr>
					<td  class='align-middle'><label for="ime">Ime</label></td>
					<td  class='align-middle'><span style = "color:black"><input type="text" placeholder="Enter" value="${obj.ime}" name="ime" id="msIme" ></td>
					</tr>
					<tr>
					<td  class='align-middle'><label for="prezime">Prezime</label></td>
					<td  class='align-middle'><span style = "color:black;"><input type="text" placeholder="Enter" value="${obj.prezime}" name="prezime" id="msPrezime" ></td>
					</tr>
					<tr>
					<td  class='align-middle'><label for="email">Email</label></td>
					<td  class='align-middle'><span style = "color:black;"><input type="text" placeholder="Enter" value="${obj.email}" name="email" id="email" readonly></td>
					</tr>
					<tr>
					<td  class='align-middle'><label for="password">Lozinka</label></td>
					<td  class='align-middle'><span style = "color:black;"><input type="password" placeholder="Enter" value="${obj.password}" name="password" id="password" readonly></td>
					</tr>
					<tr>
					<td  class='align-middle'><label for="adresa">Adresa</label></td>
					<td  class='align-middle'><span style = "color:black;"><input type="text" placeholder="Enter" value="${obj.adresa}" name="adresa" id="adresa" ></td>
					</tr>
					<tr>
					<td  class='align-middle'><label for="grad">Grad</label></td>
					<td  class='align-middle'><span style = "color:black;"><input type="text" placeholder="Enter" value="${obj.grad}" name="grad" id="grad" ></td>
					</tr>					
					<tr>
					<td  class='align-middle'><label for="drzava">Drzava</label></td>
					<td  class='align-middle'><span style = "color:black;"><input type="text" placeholder="Enter" value="${obj.drzava}" name="drzava" id="drzava" ></td>
					</tr>					
					<tr>
					<td  class='align-middle'><label for="brojTelefona">Broj telefona</label></td>
					<td  class='align-middle'><span style = "color:black;"><input type="text" placeholder="Enter" value="${obj.brojTelefona}" name="brojTelefona" id="brojTelefona" ></td>
					</tr>
					<tr><td  class='align-middle' ><button type="button" onclick="izmenaProfilaAKC()">Sacuvaj izmene</button></td></tr>				
				</tbody>
			</table>
			</form>
			<fieldset id="log_war"></fieldset>
		    </div>`;
			modal.append(m);
		$("#modal-wrapper").show();
	
}

function pregledModKlinikeAK(klinika){
	$("#modalnaForma div").remove();
	var modal=$("#modalnaForma");
	var m = "";
	m = `<div class="imgcontainer">
	      <span onclick="document.getElementById('modal-wrapper').style.display='none'" class="close" title="Close PopUp">&times;</span>
	      <h1 style="text-align:center">Profil klinike</h1></div>
			<div class="container">
			<br>
			<form action="" id="formaIzmena">
			<table class="table " id="tabela_modal">
				<tbody>
					<tr>
					<td  class='align-middle'><label for="naziv">Naziv</label></td>
					<td  class='align-middle'><span style = "color:black"><input type="text" placeholder="Enter" value="${klinika.naziv}" name="naziv" id="nazivKlinike" ></td>
					</tr>
					<tr>
					<td  class='align-middle'><label for="adresa">Adresa</label></td>
					<td  class='align-middle'><span style = "color:black;"><input type="text" placeholder="Enter" value="${klinika.adresa}" name="adresa" id="adresaKlinike" ></td>
					</tr>
					<tr>
					<td  class='align-middle'><label for="opis">Opis</label></td>
					<td  class='align-middle'><span style = "color:black;"><input type="text" placeholder="Enter" value="${klinika.opis}" name="opis" id="opisKlinike" ></td>
					</tr>
					<tr><td  class='align-middle' ><button type="button" onclick="izmenaProfilaKlinike(klinika)">Sacuvaj izmene</button></td></tr>				
				</tbody>
			</table>
			</form>
			<fieldset id="log_war"></fieldset>
		    </div>`;
			modal.append(m);
		$("#modal-wrapper").show();
}

function modIzmenaSale(sala){
	console.log("USAOOOOOO");
	$("#modalnaForma div").remove();
	var modal=$("#modalnaForma");
	var m = "";
	m = `<div class="imgcontainer">
	      <span onclick="document.getElementById('modal-wrapper').style.display='none'" class="close" title="Close PopUp">&times;</span>
	      <h1 style="text-align:center">Izmena sale</h1></div>
			<div class="container">
			<br>
			<form action="" id="formaIzmena">
			<table class="table " id="tabela_modal">
				<tbody>
					<tr>
					<td  class='align-middle'><label for="nazivSale">Naziv</label></td>
					<td  class='align-middle'><span style = "color:black"><input type="text" placeholder="Enter" value="${sala.nazivSale}" name="nazivSale" id="nazivSale" ></td>
					</tr>
					<tr>
					<td  class='align-middle'><label for="brojSale">Broj</label></td>
					<td  class='align-middle'><span style = "color:black;"><input type="text" placeholder="Enter" value="${sala.brojSale}" name="brojSale" id="brojSale" ></td>
					</tr>
					<tr>
					<tr><td  class='align-middle' ><button type="button" onclick="izmenaSale()">Sacuvaj izmene</button></td></tr>				
				</tbody>
			</table>
			</form>
			<fieldset id="log_war"></fieldset>
		    </div>`;
			modal.append(m);
		$("#modal-wrapper").show();
}

function pregledModKlinike(klinika){
$("#modalnaForma div").remove(); 
	

	var modal=$("#modalnaForma");
	var m = "";
	m = `<div class="imgcontainer">
	      <span onclick="document.getElementById('modal-wrapper').style.display='none'" class="close" title="Close PopUp">&times;</span>
	      <h1 style="text-align:center">Profil klinike</h1></div>
			<div class="container">
			<br>
			<table class="table " id="tabela_modal">
				<tbody>
					<tr>
					<td  class='align-middle'><label for="naziv">Naziv</label></td>
					<td  class='align-middle'><span style = "color:black"><input type="text" placeholder="Enter" value="${klinika.naziv}" name="naziv" id="naziv" readonly></td>
					</tr>
					<tr>
					<td  class='align-middle'><label for="adresa">Adresa</label></td>
					<td  class='align-middle'><span style = "color:black;"><input type="text" placeholder="Enter" value="${klinika.adresa}" name="adresa" id="adresa" readonly></td>
					</tr>
					<tr>
					<td  class='align-middle'><label for="grad">Grad</label></td>
					<td  class='align-middle'><span style = "color:black;"><input type="text" placeholder="Enter" value="${klinika.grad}" name="grad" id="grad" readonly></td>
					</tr>
					<tr>
					<td  class='align-middle'><label for="opis">Opis</label></td>
					<td  class='align-middle'><span style = "color:black;"><input type="text" placeholder="Enter" value="${klinika.opis}" name="opis" id="opis" readonly></td>
					</tr>
					<tr>
					<td  class='align-middle'><label for="ocena">Ocena</label></td>
					<td  class='align-middle'><span style = "color:black;"><input type="text" placeholder="Enter" value="${klinika.ocena}" name="ocena" id="ocena" readonly></td>
					</tr>			
				</tbody>
			</table>
			<fieldset id="log_war"></fieldset>
		    </div>`;
			modal.append(m);
		$("#modal-wrapper").show();
}

function prikazIzvestajaMod(klinika){
	$("#modalnaForma div").remove(); 
	var modal=$("#modalnaForma");
	var m = "";
	m = `<div class="imgcontainer">
	      <span onclick="document.getElementById('modal-wrapper').style.display='none'" class="close" title="Close PopUp">&times;</span>
	      <h1 style="text-align:center">Prikaz izvestaja klinike</h1></div>
			<div class="container">
			<br>
			<table class="table " id="tabela_modal">
				<tbody>
					<tr>
					<td  class='align-middle'><label for="ocena">Prosecna ocena klinike</label></td>
					<td  class='align-middle'><span style = "color:black"><input type="text" placeholder="Enter" value="${klinika.ocena}" name="ocena" id="ocena" readonly></td>
					</tr>
				</tbody>
			</table>
			<fieldset id="log_war"></fieldset>
		    </div>`;
			modal.append(m);
		$("#modal-wrapper").show();
}

function pregledModPacijent(){
	var obj = JSON.parse(localStorage.getItem('user'));
	$("#modalnaForma div").remove(); 
	
	console.log(obj);
	var modal=$("#modalnaForma");
	var m = "";
	m = `<div class="imgcontainer">
	      <span onclick="document.getElementById('modal-wrapper').style.display='none'" class="close" title="Close PopUp">&times;</span>
	      <h1 style="text-align:center">Profil pacijenta</h1></div>
			<div class="container">
			<br>
			<form action="" id="formaIzmena">
			<table class="table " id="tabela_modal">
				<tbody>
					<tr>
					<td  class='align-middle'><label for="ime">Ime</label></td>
					<td  class='align-middle'><span style = "color:black"><input type="text" placeholder="Enter" value="${obj.ime}" name="ime" id="ime"></td>
					</tr>
					<tr>
					<td  class='align-middle'><label for="prezime">Prezime</label></td>
					<td  class='align-middle'><span style = "color:black;"><input type="text" placeholder="Enter" value="${obj.prezime}" name="prezime" id="prezime"></td>
					</tr>
					<tr>
					<td  class='align-middle'><label for="email">Email</label></td>
					<td  class='align-middle'><span style = "color:black;"><input type="text" placeholder="Enter" value="${obj.email}" name="email" id="email" readonly></td>
					</tr>
					<tr>
					<td  class='align-middle'><label for="password">Lozinka</label></td>
					<td  class='align-middle'><span style = "color:black;"><input type="password" placeholder="Enter" value="${obj.password}" name="password" id="password" readonly></td>
					</tr>
					<tr>
					<td  class='align-middle'><label for="brojOsiguranika">Broj osiguranika</label></td>
					<td  class='align-middle'><span style = "color:black;"><input type="text" placeholder="Enter" value="${obj.brojOsiguranika}" name="brojOsiguranika" id="brojOsiguranika" readonly></td>
					</tr>
					<tr>
					<td  class='align-middle'><label for="adresa">Adresa</label></td>
					<td  class='align-middle'><span style = "color:black;"><input type="text" placeholder="Enter" value="${obj.adresa}" name="adresa" id="adresa"></td>
					</tr>
					<tr>
					<td  class='align-middle'><label for="grad">Grad</label></td>
					<td  class='align-middle'><span style = "color:black;"><input type="text" placeholder="Enter" value="${obj.grad}" name="grad" id="grad"></td>
					</tr>					
					<tr>
					<td  class='align-middle'><label for="drzava">Drzava</label></td>
					<td  class='align-middle'><span style = "color:black;"><input type="text" placeholder="Enter" value="${obj.drzava}" name="drzava" id="drzava"></td>
					</tr>					
					<tr>
					<td  class='align-middle'><label for="brojTelefona">Broj telefona</label></td>
					<td  class='align-middle'><span style = "color:black;"><input type="text" placeholder="Enter" value="${obj.brojTelefona}" name="brojTelefona" id="brojTelefona"></td>
					</tr>
					<tr><td  class='align-middle' ><button type="button" onclick="izmenaProfilaPacijenta()">Sacuvaj izmene</button></td></tr>				
				</tbody>
			</table>
			</form>
			<fieldset id="log_war"></fieldset>
		    </div>`;
			modal.append(m);
		$("#modal-wrapper").show();
	
}

function pregledModLekar(){
	var obj = JSON.parse(localStorage.getItem('user'));
	$("#modalnaForma div").remove(); 
	
	console.log(obj);
	var modal=$("#modalnaForma");
	var m = "";
	m = `<div class="imgcontainer">
	      <span onclick="document.getElementById('modal-wrapper').style.display='none'" class="close" title="Close PopUp">&times;</span>
	      <h1 style="text-align:center">Profil lekara</h1></div>
			<div class="container">
			<br>
			<table class="table " id="tabela_modal">
				<tbody>
					<tr>
					<td  class='align-middle'><label for="ime">Ime</label></td>
					<td  class='align-middle'><span style = "color:black"><input type="text" placeholder="Enter" value="${obj.ime}" name="ime" id="msIme" disabled></td>
					</tr>
					<tr>
					<td  class='align-middle'><label for="msPrezime">Prezime</label></td>
					<td  class='align-middle'><span style = "color:black;"><input type="text" placeholder="Enter" value="${obj.prezime}" name="msPrezime" id="msPrezime" disabled></td>
					</tr>
					<tr>
					<td  class='align-middle'><label for="email">Email</label></td>
					<td  class='align-middle'><span style = "color:black;"><input type="text" placeholder="Enter" value="${obj.email}" name="email" id="email" disabled></td>
					</tr>
					<tr>
					<td  class='align-middle'><label for="password">Lozinka</label></td>
					<td  class='align-middle'><span style = "color:black;"><input type="password" placeholder="Enter" value="${obj.password}" name="password" id="password" disabled></td>
					</tr>
					<tr>
					<td  class='align-middle'><label for="adresa">Adresa</label></td>
					<td  class='align-middle'><span style = "color:black;"><input type="text" placeholder="Enter" value="${obj.adresa}" name="adresa" id="adresa" disabled></td>
					</tr>
					<tr>
					<td  class='align-middle'><label for="grad">Grad</label></td>
					<td  class='align-middle'><span style = "color:black;"><input type="text" placeholder="Enter" value="${obj.grad}" name="grad" id="grad" disabled></td>
					</tr>					
					<tr>
					<td  class='align-middle'><label for="drzava">Drzava</label></td>
					<td  class='align-middle'><span style = "color:black;"><input type="text" placeholder="Enter" value="${obj.drzava}" name="drzava" id="drzava" disabled></td>
					</tr>					
					<tr>
					<td  class='align-middle'><label for="brojTelefona">Broj telefona</label></td>
					<td  class='align-middle'><span style = "color:black;"><input type="text" placeholder="Enter" value="${obj.brojTelefona}" name="brojTelefona" id="brojTelefona" disabled></td>
					</tr>				
				</tbody>
			</table>
			<fieldset id="log_war"></fieldset>
		    </div>`;
			modal.append(m);
		$("#modal-wrapper").show();
	
}

function profilLekara(){
	var obj = JSON.parse(localStorage.getItem('user'));
	$("#modalnaForma div").remove();
	var modal=$("#modalnaForma");
	var m = "";
	m = `<div class="imgcontainer">
	      <span onclick="document.getElementById('modal-wrapper').style.display='none'" class="close" title="Close PopUp">&times;</span>
	      <h1 style="text-align:center">Profil lekara</h1></div>
			<div class="container">
			<br>
			<form action="" id="formaIzmena">
			<table class="table " id="tabela_modal">
				<tbody>
					<tr>
					<td  class='align-middle'><label for="ime">Ime</label></td>
					<td  class='align-middle'><span style = "color:black"><input type="text" placeholder="Enter" value="${obj.ime}" name="ime" id="ime" ></td>
					</tr>
					<tr>
					<td  class='align-middle'><label for="prezime">Prezime</label></td>
					<td  class='align-middle'><span style = "color:black;"><input type="text" placeholder="Enter" value="${obj.prezime}" name="prezime" id="prezime" ></td>
					</tr>
					<tr>
					<td  class='align-middle'><label for="email">Email</label></td>
					<td  class='align-middle'><span style = "color:black;"><input type="text" placeholder="Enter" value="${obj.email}" name="email" id="email" readonly></td>
					</tr>
					<td  class='align-middle'><label for="password">Lozinka</label></td>
					<td  class='align-middle'><span style = "color:black;"><input type="password" placeholder="Enter" value="${obj.password}" name="password" id="password" readonly></td>
					</tr>
					<td  class='align-middle'><label for="adresa">Adresa</label></td>
					<td  class='align-middle'><span style = "color:black;"><input type="text" placeholder="Enter" value="${obj.adresa}" name="adresa" id="adresa" ></td>
					</tr>
					<td  class='align-middle'><label for="grad">Grad</label></td>
					<td  class='align-middle'><span style = "color:black;"><input type="text" placeholder="Enter" value="${obj.grad}" name="grad" id="grad" ></td>
					</tr>
					<td  class='align-middle'><label for="drzava">Drzava</label></td>
					<td  class='align-middle'><span style = "color:black;"><input type="text" placeholder="Enter" value="${obj.drzava}" name="drzava" id="drzava" ></td>
					</tr>
					<td  class='align-middle'><label for="brojTelefona">Broj telefona</label></td>
					<td  class='align-middle'><span style = "color:black;"><input type="text" placeholder="Enter" value="${obj.brojTelefona}" name="brojTelefona" id="brojTelefona" ></td>
					</tr>
					<tr><td  class='align-middle' ><button type="button" onclick="izmenaProfilaLekara()">Sacuvaj izmene</button></td></tr>				
				</tbody>
			</table>
			</form>
			<fieldset id="log_war"></fieldset>
		    </div>`;
			modal.append(m);
		$("#modal-wrapper").show();
}

function pregledModAK(){
	var obj = JSON.parse(localStorage.getItem('user'));
	$("#modalnaForma div").remove(); 
	
	console.log(obj);
	var modal=$("#modalnaForma");
	var m = "";
	m = `<div class="imgcontainer">
	      <span onclick="document.getElementById('modal-wrapper').style.display='none'" class="close" title="Close PopUp">&times;</span>
	      <h1 style="text-align:center">Profil administratora klinike</h1></div>
			<div class="container">
			<br>
			<table class="table " id="tabela_modal">
				<tbody>
					<tr>
					<td  class='align-middle'><label for="ime">Ime</label></td>
					<td  class='align-middle'><span style = "color:black"><input type="text" placeholder="Enter" value="${obj.ime}" name="ime" id="ime"></td>					
					</tr>
					<tr>
					<td  class='align-middle'><label for="msPrezime">Prezime</label></td>
					<td  class='align-middle'><span style = "color:black;"><input type="text" placeholder="Enter" value="${obj.prezime}" name="prezime" id="prezime"></td>
					</tr>
					<tr>
					<td  class='align-middle'><label for="email">Email</label></td>
					<td  class='align-middle'><span style = "color:black;"><input type="text" placeholder="Enter" value="${obj.email}" name="email" id="email" disabled></td>
					</tr>
					<tr>
					<td  class='align-middle'><label for="password">Lozinka</label></td>
					<td  class='align-middle'><span style = "color:black;"><input type="password" placeholder="Enter" value="${obj.password}" name="password" id="password" disabled></td>
					</tr>
					<tr>
					<td  class='align-middle'><label for="adresa">Adresa</label></td>
					<td  class='align-middle'><span style = "color:black;"><input type="text" placeholder="Enter" value="${obj.adresa}" name="adresa" id="adresa"></td>
					</tr>
					<tr>
					<td  class='align-middle'><label for="grad">Grad</label></td>
					<td  class='align-middle'><span style = "color:black;"><input type="text" placeholder="Enter" value="${obj.grad}" name="grad" id="grad"></td>
					</tr>					
					<tr>
					<td  class='align-middle'><label for="drzava">Drzava</label></td>
					<td  class='align-middle'><span style = "color:black;"><input type="text" placeholder="Enter" value="${obj.drzava}" name="drzava" id="drzava"></td>
					</tr>					
					<tr>
					<td  class='align-middle'><label for="brojTelefona">Broj telefona</label></td>
					<td  class='align-middle'><span style = "color:black;"><input type="text" placeholder="Enter" value="${obj.brojTelefona}" name="brojTelefona" id="brojTelefona"></td>
					</tr>
					<tr><td  class='align-middle' ><button type="button" onclick="izmenaProfilaAK()">Sacuvaj izmene</button></td></tr>				
				</tbody>
			</table>
			<fieldset id="log_war"></fieldset>
		    </div>`;
			modal.append(m);
		$("#modal-wrapper").show();
	
}

function profilAK(){
	var obj = JSON.parse(localStorage.getItem('user'));
	$("#modalnaForma div").remove();
	var modal=$("#modalnaForma");
	var m = "";
	m = `<div class="imgcontainer">
	      <span onclick="document.getElementById('modal-wrapper').style.display='none'" class="close" title="Close PopUp">&times;</span>
	      <h1 style="text-align:center">Profil administratora klinike</h1></div>
			<div class="container">
			<br>
			<form action="" id="formaIzmena">
			<table class="table " id="tabela_modal">
				<tbody>
					<tr>
					<td  class='align-middle'><label for="ime">Ime</label></td>
					<td  class='align-middle'><span style = "color:black"><input type="text" placeholder="Enter" value="${obj.ime}" name="ime" id="ime" ></td>
					</tr>
					<tr>
					<td  class='align-middle'><label for="prezime">Prezime</label></td>
					<td  class='align-middle'><span style = "color:black;"><input type="text" placeholder="Enter" value="${obj.prezime}" name="prezime" id="prezime" ></td>
					</tr>
					<tr>
					<td  class='align-middle'><label for="email">Email</label></td>
					<td  class='align-middle'><span style = "color:black;"><input type="text" placeholder="Enter" value="${obj.email}" name="email" id="email" readonly></td>
					</tr>
					<td  class='align-middle'><label for="password">Lozinka</label></td>
					<td  class='align-middle'><span style = "color:black;"><input type="text" placeholder="Enter" value="${obj.password}" name="password" id="password" readonly></td>
					</tr>
					<td  class='align-middle'><label for="adresa">Adresa</label></td>
					<td  class='align-middle'><span style = "color:black;"><input type="text" placeholder="Enter" value="${obj.adresa}" name="adresa" id="adresa" ></td>
					</tr>
					<td  class='align-middle'><label for="grad">Grad</label></td>
					<td  class='align-middle'><span style = "color:black;"><input type="text" placeholder="Enter" value="${obj.grad}" name="grad" id="grad" ></td>
					</tr>
					<td  class='align-middle'><label for="drzava">Drzava</label></td>
					<td  class='align-middle'><span style = "color:black;"><input type="text" placeholder="Enter" value="${obj.drzava}" name="drzava" id="drzava" ></td>
					</tr>
					<td  class='align-middle'><label for="brojTelefona">Broj telefona</label></td>
					<td  class='align-middle'><span style = "color:black;"><input type="text" placeholder="Enter" value="${obj.brojTelefona}" name="brojTelefona" id="brojTelefona" ></td>
					</tr>
					<tr><td  class='align-middle' ><button type="button" onclick="izmenaProfilaAK()">Sacuvaj izmene</button></td></tr>				
				</tbody>
			</table>
			</form>
			<fieldset id="log_war"></fieldset>
		    </div>`;
			modal.append(m);
		$("#modal-wrapper").show();
}

function izmenaProfilaAKC(){
	var tok=JSON.parse(localStorage.getItem('user')).token;
	var aut= JSON.parse(localStorage.getItem('user')).role;
	var dat = getFormData($("#formaIzmena"));
	
	var org = JSON.stringify(dat);
	var obj = JSON.parse(localStorage.getItem('user'));
	var x = emptyMSAKC();
	if(x == 1){
		$.ajax({
			url: "api/adminiKC/" + obj.id,
			type: "PUT",
			data: org,
			contentType:"application/json",
			dataType: "json",
			headers: {
		        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
		    },
			complete : function (data) {
				alert("Izmene uspesno sacuvane! Izmene ce biti vidljive nakon sto se ponovo prijavite.");
			} 
		});
	}else{
		alert("Polja ne smeju ostati prazna!");
	}
}

function izmenaProfilaAK(){
	var tok=JSON.parse(localStorage.getItem('user')).token;
	var aut= JSON.parse(localStorage.getItem('user')).role;
	var dat = getFormData($("#formaIzmena"));
	
	var org = JSON.stringify(dat);
	var obj = JSON.parse(localStorage.getItem('user'));
	$.ajax({
		url: "api/adminiKlinike/izmena/" + obj.id,
		type: "PUT",
		data: org,
		contentType:"application/json",
		dataType: "json",
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
		complete : function (data) {
			alert("Izmene uspesno sacuvane! Izmene ce biti vidljive nakon sto se ponovo prijavite.");
		}
	});
	
}

function izmenaProfilaLekara(){
	var tok=JSON.parse(localStorage.getItem('user')).token;
	var aut= JSON.parse(localStorage.getItem('user')).role;
	var dat = getFormData($("#formaIzmena"));
	
	var org = JSON.stringify(dat);
	var obj = JSON.parse(localStorage.getItem('user'));
	$.ajax({
		url: "api/lekari/izmena/" + obj.id,
		type: "PUT",
		data: org,
		contentType:"application/json",
		dataType: "json",
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
		complete : function (data) {
			alert("Izmene uspesno sacuvane! Izmene ce biti vidljive nakon sto se ponovo prijavite.");
		}
	});
	
}

function izmenaProfilaKlinike(klinika){
	var dat = getFormData($("#formaIzmena"));
	var org = JSON.stringify(dat);
	var obj = JSON.parse(localStorage.getItem('user'));
	console.log(dat)
	$.ajax({
		url: "api/klinike/izmena/" + obj.id,
		type: "PUT",
		data: org,
		contentType:"application/json",
		dataType: "json",
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
		complete : function (data) {
			alert("Izmene uspesno sacuvane! Izmene ce biti vidljive nakon sto se ponovo prijavite.");	
		} 
	 
	});
}

function izmenaSale(){
	console.log("usao u izmenaSale");
	var dat = getFormData($("#formaIzmena"));
	var org = JSON.stringify(dat);
	var obj = JSON.parse(localStorage.getItem('idSale'));
	console.log(obj)
	$.ajax({
		url: "api/sale/izmena/" + obj,
		type: "PUT",
		data: org,
		contentType:"application/json",
		dataType: "json",
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
		complete : function (data) {
			
			alert("Uspesno ste izmenili salu!");
		} 
	 
	});
}

//ispis termina za unapred definisane preglede 
function prikazSlobodnihTerminaPregleda() {
	obrisiPretragu();
	obrisiFilter();
	obrisiTabele();
	$("#lineChart").hide();
	var obj = JSON.parse(localStorage.getItem('idKlinike'));
	$.ajax({
		url: "api/pregledi/klinika/" + obj,
		type: "GET",
		contentType: "application/json",
		dataType: "json",
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
		complete: function(data) {
			pregledi = JSON.parse(data.responseText);
			loadDefinisaniPregledi(pregledi);
			
		}
	});
}

function prikazSlobodnihTerminaPregledaAdmina() {
	obrisiPretragu();
	obrisiFilter();
	var obj = JSON.parse(localStorage.getItem('user'));
	$.ajax({
		url: "api/pregledi/adminK/" + obj.id,
		type: "GET",
		contentType: "application/json",
		dataType: "json",
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
		complete: function(data) {
			pregledi = JSON.parse(data.responseText);
			loadDefinisaniPreglediAdmina(pregledi);
			
		}
	});
}

function loadDefinisaniPregledi(pregledi) {
	obrisiTabele();
	$("#tabela_definisaniPregledi tbody tr").remove(); 
	$("#tabela_definisaniPregledi thead ").remove();
	var table = $("#tabela_definisaniPregledi");
	table.append(makeTableHeaderDefinisaniPregledi());	
	for(let p of pregledi) {
		table.append(makeTableRowDefinisaniPregledi(p));
	}
}

function loadDefinisaniPreglediAdmina(pregledi) {
	obrisiTabele();
	$("#tabela_definisaniPregledi_admina tbody tr").remove(); 
	$("#tabela_definisaniPregledi_admina thead ").remove();
	var table = $("#tabela_definisaniPregledi_admina");
	table.append(makeTableHeaderDefinisaniPreglediAdmina());	
	for(let p of pregledi) {
		table.append(makeTableRowDefinisaniPreglediAdmina(p));
	}
}

function makeTableHeaderDefinisaniPregledi(){
	
	var row="";
	 row =
			`<thead class="thead-light" bgcolor="white">
					<tr>
						<th>Datum</th>
						<th>Vreme</th>
						<th>Trajanje</th>
						<th>Sala</th>
						<th>Tip</th>
						<th>Lekar</th>
						<th>Cena</th>
						<th></th>
					</tr>
				</thead>`;
		
		return row;
}

function makeTableHeaderDefinisaniPreglediAdmina(){
	
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

function makeTableRowDefinisaniPregledi(p) {
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
			<td class="izgledTabele" id='${p.lekar.ime}'>${p.lekar.ime} ${p.lekar.prezime}</td>
			<td class="izgledTabele" id='${p.cena}'>${p.cena}</td>
			<td class="izgledTabele" id='dugmeZakazi'><input type="button" id="showTxt1" value="Zakazi" onClick="zakaziPregled(${p.idPregleda})"/></td>
		</tr>`;
	return row;
}

function makeTableRowDefinisaniPreglediAdmina(p) {
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

function zakaziPregled(idPregleda) {
	var obj = JSON.parse(localStorage.getItem('user'));
	$.ajax({
		url: "api/pregledi/" + obj.id + "/" + idPregleda,
		type: "PUT",
		contentType: "application/json",
		dataType: "json",
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
		complete: function(data) {
			alert("Uspesno ste zakazali pregled!");
		}
	});
}

function emptyMSAKC() {
    var x = document.getElementById("msIme").value;
    var y = document.getElementById("msPrezime").value;
    var a = document.getElementById("adresa").value;
    var s = document.getElementById("grad").value;
    var d = document.getElementById("drzava").value;
    var f = document.getElementById("brojTelefona").value;
    if (x == "" || y == "" || a == "" || s == "" || d == "" || f == "") {
        
        return 0;
    };
    return 1;
}


function izmenaProfilaMS(){
	var tok=JSON.parse(localStorage.getItem('user')).token;
	var aut= JSON.parse(localStorage.getItem('user')).role;
	var dat = getFormData($("#formaIzmena"));
	
	var org = JSON.stringify(dat);
	var obj = JSON.parse(localStorage.getItem('user'));
	var x = emptyMSAKC();
	if(x == 1){
		$.ajax({
			url: "api/medicinskeSestre/" + obj.id,
			type: "PUT",
			data: org,
			contentType:"application/json",
			dataType: "json",
			headers: {
		        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
		    },
			complete : function (data) {
				alert("Izmene uspesno sacuvane! Izmene ce biti vidljive nakon sto se ponovo prijavite.");
			} 
		 
		});
	}else{
		alert("Polja ne smeju ostati prazna!");
	}
	
}

function pretragaKlinika(){
	var tipPregleda = document.getElementById("pretraga").value;
	var tipP = tipPregleda.toUpperCase();
	localStorage.setItem('tipPregleda',tipP);
	console.log("OBICNO: " + tipP);
	console.log("SA GETOM" + localStorage.getItem('tipPregleda'))
	var dan = document.getElementById("dan").value;
	var mesec = document.getElementById("mesec").value;
	var godina = document.getElementById("godina").value;
    datum = godina + "-" + mesec + "-" + dan;
    localStorage.setItem('datum', datum);
    console.log("DATUM " + datum);
    if(tipP != ""){
    	$.ajax({
    		url: "api/lekari/tipPregleda/" + tipP,
    		type: "GET",
    		contentType: "application/json",
    		dataType: "json",
    		headers: {
    	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
    	    },
    		complete: function(data) {
    			klinike = JSON.parse(data.responseText);
    			pretrazeneKlinike = klinike;
    			loadKlinikePretrazene(klinike);	
    		}
    	});
    }
}

function loadKlinikePretrazene(klinike) {
	$("#filterNaziv").show();
	$("#filterKlinikaNaziv").show();
	$("#filterGrad").show();
	$("#filterKlinikaGrad").show();
	$("#filterOcenaNajmanja").show();
	$("#minus").show();
	$("#filterOcenaNajveca").show();
	$("#filterKlinikaOcena").show();
	$("#filterCenaNajmanja").show();
	$("#minusCena").show();
	$("#filterCenaNajveca").show();
	$("#filterKlinikaCena").show();
	obrisiTabele();
	//obrisiPretragu();
	$("#pretraga").hide();
	$("#datumBiranje").hide();
	$("#dan").hide();
	$("#mesec").hide();
	$("#godina").hide();
	$("#pretrazi").hide()
	$("#sortLKP").hide()
	$("#tabela_klinike_pretrazene tbody tr").remove(); 
	$("#tabela_klinike_pretrazene thead ").remove();
	var table = $("#tabela_klinike_pretrazene");
	table.append(makeTableHeaderKlinikePretrazene());	
	var tipPregleda = document.getElementById("pretraga").value;
	var tipP = tipPregleda.toUpperCase();
	for(let k of klinike) {
		cenaPregleda(k.idKlinike, tipP);
		table.append(makeTableRowKlinikePretrazene(k));
	}
}

function makeTableHeaderKlinikePretrazene(){
	
	var row="";
	 row =
			`<thead class="thead-light" bgcolor="white">
					<tr>
						<th>Naziv</th>
						<th>Grad</th>
						<th>Ocena</th>
						<th>Cena</th>
						<th>Prikazi</th>
				     </tr>
				</thead>`;
		
		return row;
}

function makeTableRowKlinikePretrazene(k) {
	var row = "";
	//var tipPregleda = document.getElementById("pretraga").value;
	//var tipP = tipPregleda.toUpperCase();
	//cenaPregleda(k.idKlinike, tipP);
	var cenovnik = JSON.parse(localStorage.getItem('cena'));
	console.log(cenovnik);
	row =
		`<tr>
			<td class="izgledTabele" '>${k.naziv}</td>
			<td class="izgledTabele" '>${k.grad}</td>
			<td class="izgledTabele" '>${k.ocena}</td>
			<td class="izgledTabele" '>${cenovnik}</td>
			<td class="izgledTabele" ><input type="button" id="profilKlinike" value="Profil klinike" onClick="prebaciNaKliniku(${k.idKlinike})"/></td>
		</tr>`;
	
	return row;
}

function obrisiTabele(){
	$("#tabela_klinike_pretrazene tbody tr").remove(); 
	$("#tabela_klinike_pretrazene thead ").remove();
	$("#tabela_klinike tbody tr").remove(); 
	$("#tabela_klinike thead ").remove();
	$("#tabela_operacije tbody tr").remove(); 
	$("#tabela_operacije thead ").remove();
	$("#tabela_pregledi tbody tr").remove(); 
	$("#tabela_pregledi thead ").remove();
	$("#tabela_pacijenti tbody tr").remove(); 
	$("#tabela_pacijenti thead ").remove();
	$("#tabela_pacijenti_lekar tbody tr").remove(); 
	$("#tabela_pacijenti_lekar thead ").remove();
	$("#tabela_recepti tbody tr").remove(); 
	$("#tabela_recepti thead ").remove();
	$("#tabela_lekari tbody tr").remove(); 
	$("#tabela_lekari thead ").remove();
	$("#tabela_lekari_izvestaj tbody tr").remove(); 
	$("#tabela_lekari_izvestaj thead ").remove();
	$("#tabela_lekari_tipPregleda tbody tr").remove(); 
	$("#tabela_lekari_tipPregleda thead ").remove();
	$("#tabela_definisaniPregledi tbody tr").remove(); 
	$("#tabela_definisaniPregledi thead ").remove();
	$("#tabela_definisaniPregledi_admina tbody tr").remove(); 
	$("#tabela_definisaniPregledi_admina thead ").remove();
	$("#tabela_cenovnika tbody tr").remove(); 
	$("#tabela_cenovnika thead ").remove();	
	$("#tabela_cenovnika_admina tbody tr").remove(); 
	$("#tabela_cenovnika_admina thead ").remove();	
	$("#tabela_klinikeAKC tbody tr").remove(); 
	$("#tabela_klinikeAKC thead ").remove();
	$("#tabela_sl tbody tr").remove(); 
	$("#tabela_sl thead ").remove();
	$("#tabela_sd tbody tr").remove(); 
	$("#tabela_sd thead ").remove();
	$("#tabela_zahtevi_o tbody tr").remove(); 
	$("#tabela_zahtevi_o thead ").remove();
	$("#tabela_zahtevi_p tbody tr").remove(); 
	$("#tabela_zahtevi_p thead ").remove();
	$("#tabela_zahtevizr thead").remove();
	$("#tabela_zahtevizr tbody tr").remove();
	$("#tabela_zahtevi_op tbody tr").remove(); 
	$("#tabela_zahtevi_op thead ").remove();
	$("#tabela_prihoda tbody tr").remove(); 
	$("#tabela_prihoda thead ").remove();
	$("#tabela_tp tbody tr").remove(); 
	$("#tabela_tp thead ").remove();
	$("#tabela_sale tbody tr").remove(); 
	$("#tabela_sale thead ").remove();
	
}

function obrisiPretragu(){
	$("#pretraga").hide();
	$("#datumBiranje").hide();
	$("#dan").hide();
	$("#mesec").hide();
	$("#godina").hide();
	$("#pretrazi").hide();
	$("#pretragaIme").hide();
	$("#pretragaPrezime").hide();
	$("#ocenaL").hide();
	$("#ocena").hide();
	$("#pretraziLekare").hide();
	$("#filterNaziv").hide();
	$("#filterKlinikaNaziv").hide();
	$("#filterGrad").hide();
	$("#filterKlinikaGrad").hide();
	$("#filterOcenaNajmanja").hide();
	$("#minus").hide();
	$("#filterOcenaNajveca").hide();
	$("#filterKlinikaOcena").hide();
	$("#filterCenaNajmanja").hide();
	$("#minusCena").hide();
	$("#filterCenaNajveca").hide();
	$("#filterKlinikaCena").hide();
	$("#pretraziLekareTipPregleda").hide();
	$("#sortLKP").hide();
	$("#sortTPP").hide();
	$("#sortKAKC").hide();
	$("#sortP").hide();
	$("#pretraziLekare").hide();
	$("#pretragaNaziv").hide();
	$("#pretragaBroj").hide();
	$("#pretraziSale").hide();
	$("zauzeto").hide();
	$("#datumOd").hide();
	$("#datumDo").hide();
	$("#prikaziPrihod").hide();
	$("#pretragaNazivTPN").hide();
	$("#pretraziTPN").hide();
}

function cenaPregleda(idK, tipP){
	$.ajax({
		url: "api/cenovnik/" + idK + "/" + tipP,
		type: "GET",
		contentType: "application/json",
		dataType: "json",
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
		complete : function (data) {
			cenovnik = JSON.parse(data.responseText);
			console.log(cenovnik);
			localStorage.setItem('cena',cenovnik.cena);
			console.log(cenovnik.cena);
			
		} 
	 
	});	
}



//pozivacemo poacijenti
function overeRecepata() {
	var obj = JSON.parse(localStorage.getItem('user'));
	$.ajax({
		url: "api/recepti/ms/" + obj.id,
		type: "GET",
		contentType: "application/json",
		dataType: "json",
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
		complete: function(data) {
			recepti = JSON.parse(data.responseText);
			console.log(recepti)
			//loadPregledi(pregledi);
		//	window.location.replace("./overaRecepata.html");
			loadRecepti(recepti);
		}
	});
}


function loadRecepti(recepti) {
	obrisiPretragu();
	obrisiTabele();
	var table = $("#tabela_recepti");
	table.append(makeTableHeaderRecepti());	
	for(let r of recepti) {
		table.append(makeTableRowRecepti(r));
	}
	
}
function makeTableHeaderRecepti(){
	
	var row="";
	 row =
			`<thead class="thead-light" bgcolor="white">
					<tr>
						<th>Naziv leka</th>
						<th >Ime lekara</th>
						<th >Prezime lekara</th>
						<th >Ime pacijenta</th>
						<th >Prezime pacijenta</th>
						<th>Overi</th>
					</tr>
				</thead>`;
		
		return row;
}

function makeTableRowRecepti(r) {
	console.log(r);
	console.log(r.sifraLeka.nazivLeka);
	var row = "";
	
	  row =
		`<tr bgcolor="white">
			<td  >${r.sifraLeka.nazivLeka}</td>
			<td >${r.pregled.lekar.ime}</td>
			<td >${r.pregled.lekar.prezime}</td>
			<td >${r.pregled.pacijent.ime}</td>
			<td >${r.pregled.pacijent.prezime}</td>
			<td ><button class="button" onClick="odradiOveru(${r.idRecepta})">Overi</button></i></button></td >
		</tr>`;
	
	return row;
}

function odradiOveru(idRecepta){
	
	var obj=JSON.parse(localStorage.getItem('user'));
	
	
	
	$.ajax({
		url: "api/recepti/" + obj.id + "/" + idRecepta,
		type: "PUT",
		contentType:"application/json",
		dataType: "json",
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
		complete : function (data) {
			alert("Recept uspesno overen!");
		} 
	 
	});
}

function prikazSvihLekaraKlinike(){
	$("#pretragaIme").show();
	$("#pretragaPrezime").show();
	$("#ocenaL").show();
	$("#ocena").show();
	$("#pretraziLekare").show();
	$("#pretraziLekareTipPregleda").hide();
	obrisiTabele();
	obrisiFilter();
	var datum = localStorage.getItem('datum');
	var obj = JSON.parse(localStorage.getItem('idKlinike'));
	$.ajax({
		url: "api/lekari/klinika/" + obj + "/datum/" + datum,
		type: "GET",
		contentType: "application/json",
		dataType: "json",
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
		complete: function(data) {
			lekari = JSON.parse(data.responseText);
			console.log(lekari)
			loadLekariKlinike(lekari);
		}
	});
}



function prikazLekaraKlinike(){
	$("#pretragaIme").show();
	$("#pretragaPrezime").show();
	$("#ocenaL").show();
	$("#ocena").show();
	$("#pretraziLekareTipPregleda").show();
	$("#pretraziLekare").hide();
	obrisiTabele();
	obrisiFilter();
	var obj = JSON.parse(localStorage.getItem('idKlinike'));
	var tipPregleda = localStorage.getItem('tipPregleda');
	var datum = localStorage.getItem('datum');
	$.ajax({
		url: "api/lekari/klinika/" + obj + "/" + tipPregleda + "/datumTP/" + datum,
		type: "GET",
		contentType: "application/json",
		dataType: "json",
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
		complete: function(data) {
			lekari = JSON.parse(data.responseText);
			console.log(lekari)
			loadLekariKlinikeTipPregleda(lekari);
		}
	});
}

function loadLekariKlinike(lekari) {
	obrisiTabele();
	$("#tabela_lekari tbody tr").remove(); 
	$("#tabela_lekari thead ").remove();
	var table = $("#tabela_lekari");
	table.append(makeTableHeaderLekariKlinike());	
	for(let l of lekari) {
		table.append(makeTableRowLekariKlinike(l));
	}
}

function makeTableHeaderLekariKlinike(){
	
	var row="";
	 row =
			`<thead class="thead-light" bgcolor="white">
					<tr>
						<th>Ime</th>
						<th>Prezime</th>
						<th>Tip pregleda</th>
						<th>Ocena</th>
						<th>Vreme</th>
						<th>Zahtev za pregled</th>
					</tr>
				</thead>`;
		
		return row;
}

function makeTableRowLekariKlinike(l) {
	var row = "";
	
	  row =
		`<tr>
			<td class="izgledTabele">${l.ime}</td>
			<td class="izgledTabele">${l.prezime}</td>
			<td class="izgledTabele">${l.tipPregleda}</td>
			<td class="izgledTabele">${l.ocena}</td>
			<td class="izgledTabele">
			    <select name="vreme" id="vreme">
		  				<option value="11:00:00.000-00">11:00</option>
		  				<option value="12:00:00.000-00">12:00</option>
		  				<option value="13:00:00.000-00">13:00</option>
		  				<option value="14:00:00.000-00">14:00</option>
		  				<option value="15:00:00.000-00">15:00</option>
		  	    </select>
			</td>
			<td class="izgledTabele"><input type="button" id="zzp" value="Kreiraj zahtev" onClick="prebaciNaZahtevZaPregled(\'${l.ime}\',\'${l.prezime}\',\'${l.tipPregleda}\',\'${l.klinika.naziv}\',\'${l.id}\')"/></td>
		</tr>`;
	
	return row;
}

function loadLekariKlinikeTipPregleda(lekari) {
	obrisiTabele();
	$("#tabela_lekari_tipPregleda tbody tr").remove(); 
	$("#tabela_lekari_tipPregleda thead ").remove();
	var table = $("#tabela_lekari_tipPregleda");
	table.append(makeTableHeaderLekariKlinikeTipPregleda());	
	for(let l of lekari) {
		table.append(makeTableRowLekariKlinikeTipPregleda(l));
	}
}

function makeTableHeaderLekariKlinikeTipPregleda(){
	
	var row="";
	 row =
			`<thead class="thead-light" bgcolor="white">
					<tr>
						<th>Ime</th>
						<th>Prezime</th>
						<th>Ocena</th>
						<th>Vreme</th>
						<th>Zahtev za pregled</th>
					</tr>
				</thead>`;
		
		return row;
}

function makeTableRowLekariKlinikeTipPregleda(l) {
	var row = "";
	
	  row =
		`<tr>
			<td class="izgledTabele">${l.ime}</td>
			<td class="izgledTabele">${l.prezime}</td>
			<td class="izgledTabele">${l.ocena}</td>
			<td class="izgledTabele">
			    <select name="vreme" id="vreme">
		  				<option value="11:00:00.000-00">11:00</option>
		  				<option value="12:00:00.000-00">12:00</option>
		  				<option value="13:00:00.000-00">13:00</option>
		  				<option value="14:00:00.000-00">14:00</option>
		  				<option value="15:00:00.000-00">15:00</option>
		  	    </select>
			</td>
			<td class="izgledTabele"><input type="button" id="zzp" value="Kreiraj zahtev" onClick="prebaciNaZahtevZaPregled(\'${l.ime}\',\'${l.prezime}\',\'${l.tipPregleda}\',\'${l.klinika.naziv}\',\'${l.id}\')"/></td>
		</tr>`;
	
	return row;
}

function pretragaLekara(){
	$("#filterTipPregleda").show();
	$("#filterLekarTipPregleda").show();
	var idK = localStorage.getItem('idKlinike');
	console.log(idK);
	var imeLekara = document.getElementById("pretragaIme").value;
	var maloIme = imeLekara.toLowerCase();
	var ime = maloIme.charAt(0).toUpperCase() + maloIme.slice(1);
	var prezimeLekara = document.getElementById("pretragaPrezime").value;
	var maloPrezime = prezimeLekara.toLowerCase();
	var prezime = maloPrezime.charAt(0).toUpperCase() + maloPrezime.slice(1);
	var ocena = document.getElementById("ocena").value;
	if(ime != "" && prezime != ""){
	$.ajax({
		url: "api/lekari/klinika/" + idK + "/" + ime + "/" + prezime + "/" + ocena,
		type: "GET",
		contentType: "application/json",
		dataType: "json",
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
		complete: function(data) {
			lekari = JSON.parse(data.responseText);
			console.log(JSON.stringify(lekari));
			pretrazeniLekari = lekari;
			loadLekariKlinike(lekari);	
		}
	});	
	}else if(ime == "" && prezime != ""){//pretraga po prezimenu
		$.ajax({
			url: "api/lekari/klinika/" + idK + "/prezime/" + prezime + "/" + ocena,
			type: "GET",
			contentType: "application/json",
			dataType: "json",
			headers: {
		        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
		    },
			complete: function(data) {
				lekari = JSON.parse(data.responseText);
				console.log(JSON.stringify(lekari));
				pretrazeniLekari = lekari;
				loadLekariKlinike(lekari);	
			}
		});		
	}else if(ime != "" && prezime == ""){//pretraga po imenu
		$.ajax({
			url: "api/lekari/klinika/" + idK + "/ime/" + ime + "/" + ocena,
			type: "GET",
			contentType: "application/json",
			dataType: "json",
			headers: {
		        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
		    },
			complete: function(data) {
				lekari = JSON.parse(data.responseText);
				console.log(JSON.stringify(lekari));
				pretrazeniLekari = lekari;
				loadLekariKlinike(lekari);	
			}
		});		
	}else if(ime == "" && prezime == ""){//ime prazno, prezime prazno, ostala samo ocena
		$.ajax({
			url: "api/lekari/klinika/"+ idK +"/ocena/" + ocena,
			type: "GET",
			contentType: "application/json",
			dataType: "json",
			headers: {
		        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
		    },
			complete: function(data) {
				lekari = JSON.parse(data.responseText);
				console.log(JSON.stringify(lekari));
				pretrazeniLekari = lekari;
				loadLekariKlinike(lekari);	
			}
		});		
	}
}

function izvestajPrihod(){
	$("#lineChart").hide();
	obrisiTabele();
	obrisiPretragu();
	obrisiFilter();
	$("#datumOd").show();
	$("#datumDo").show();
	$("#prikaziPrihod").show();
}

function prikaziPrihodK(){
	var obj = JSON.parse(localStorage.getItem('user'));
	var datumOd = document.getElementById("datumOd").value;
	var datumDo = document.getElementById("datumDo").value;
	if (datumOd != null && datumDo != null){
		$.ajax({
			url: "api/klinike/adminK/" + obj.id + "/datumOd/" + datumOd + "/datumDo/" + datumDo,
			type: "GET",
			contentType: "application/json",
			dataType: "json",
			headers: {
		        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
		    },
			complete: function(data) {
				console.log("USLO OVDE");
				prihod = JSON.parse(data.responseText);
				console.log(prihod);
				loadPrihodKlinike(prihod);	
			}
		});	
	}
}

function pretragaLekaraAdmin(){
	var obj = JSON.parse(localStorage.getItem('user'));
	var imeLekara = document.getElementById("pretragaIme").value;
	var maloIme = imeLekara.toLowerCase();
	var ime = maloIme.charAt(0).toUpperCase() + maloIme.slice(1);
	var prezimeLekara = document.getElementById("pretragaPrezime").value;
	var maloPrezime = prezimeLekara.toLowerCase();
	var prezime = maloPrezime.charAt(0).toUpperCase() + maloPrezime.slice(1);
	if(ime != "" && prezime != ""){ //po imenu i prezimenu
	$.ajax({
		url: "api/lekari/adminK/" + obj.id + "/" + ime + "/" + prezime,
		type: "GET",
		contentType: "application/json",
		dataType: "json",
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
		complete: function(data) {
			console.log("USLO OVDE");
			lekari = JSON.parse(data.responseText);
			pretrazeniLekari = lekari;
			loadLekariAdmin(lekari);	
		}
	});	
	}else if(ime == "" && prezime != ""){//pretraga po prezimenu
		$.ajax({
			url: "api/lekari/adminK/" + obj.id + "/" +"prezime/" + prezime,
			type: "GET",
			contentType: "application/json",
			dataType: "json",
			headers: {
		        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
		    },
			complete: function(data) {
				lekari = JSON.parse(data.responseText);
				console.log(JSON.stringify(lekari));
				pretrazeniLekari = lekari;
				loadLekariAdmin(lekari);	
			}
		});		
	}else if(ime != "" && prezime == ""){//pretraga po imenu
		$.ajax({
			url: "api/lekari/adminK/" + obj.id + "/" +"ime/"+ ime,
			type: "GET",
			contentType: "application/json",
			dataType: "json",
			headers: {
		        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
		    },
			complete: function(data) {
				lekari = JSON.parse(data.responseText);
				console.log(JSON.stringify(lekari));
				pretrazeniLekari = lekari;
				loadLekariAdmin(lekari);	
			}
		});	
	}	
}

function pretragaPacijenata(){
	console.log("usao u pretragu pac");
	var obj = JSON.parse(localStorage.getItem('user'));
	var imePacijenta = document.getElementById("pretragaIme").value;
	var maloIme = imePacijenta.toLowerCase();
	var ime = maloIme.charAt(0).toUpperCase() + maloIme.slice(1);
	var prezimePacijenta = document.getElementById("pretragaPrezime").value;
	var maloPrezime = prezimePacijenta.toLowerCase();
	var prezime = maloPrezime.charAt(0).toUpperCase() + maloPrezime.slice(1);
	var brO = document.getElementById("pretragaBrOsiguranika").value;
	if(ime != "" && prezime != "" && brO == ""){ //po imenu i prezimenu
		console.log("usao u pretragu ip");
		$.ajax({
			url: "api/pacijenti/lekar/" + obj.id + "/" + ime + "/" + prezime,
			type: "GET",
			contentType: "application/json",
			dataType: "json",
			headers: {
		        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
		    },
			complete: function(data) {
				console.log("USLO OVDE zavrsena pretraga i p ");
				pacijenti = JSON.parse(data.responseText);
				loadPacijentiLekar(pacijenti);	
			}
		});	
	}else if(ime == "" && prezime != "" && brO == ""){//pretraga po prezimenu
		console.log("usao u pretragu p");
		$.ajax({
			url: "api/pacijenti/lekar/" + obj.id + "/prezime/" + prezime,
			type: "GET",
			contentType: "application/json",
			dataType: "json",
			headers: {
		        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
		    },
			complete: function(data) {
				pacijenti = JSON.parse(data.responseText);
				loadPacijentiLekar(pacijenti);	
			}
		});		
	}else if(ime != "" && prezime == "" && brO == ""){//pretraga po imenu
		console.log("usao u pretragu i");
		$.ajax({
			url: "api/pacijenti/lekar/" + obj.id + "/" +"ime/"+ ime,
			type: "GET",
			contentType: "application/json",
			dataType: "json",
			headers: {
		        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
		    },
			complete: function(data) {
				console.log("usao u kraj p po i");
				pacijenti = JSON.parse(data.responseText);
				console.log(pacijenti);
				loadPacijentiLekar(pacijenti);	
			}
		});	
	}else if(ime == "" && prezime == "" && brO != ""){//pretraga po br os
		$.ajax({
			url: "api/pacijenti/lekar/" + obj.id + "/" +"brO/"+ brO,
			type: "GET",
			contentType: "application/json",
			dataType: "json",
			headers: {
		        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
		    },
			complete: function(data) {
				pacijenti = JSON.parse(data.responseText);
				loadPacijentiLekar(pacijenti);	
			}
		});	
	}else if(ime != "" && prezime == "" && brO != ""){//pretraga po imenu i br os
		$.ajax({
			url: "api/pacijenti/lekar/" + obj.id + "/" +"ime/"+ ime + "/brO/" + brO,
			type: "GET",
			contentType: "application/json",
			dataType: "json",
			headers: {
		        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
		    },
			complete: function(data) {
				pacijenti = JSON.parse(data.responseText);
				loadPacijentiLekar(pacijenti);	
			}
		});	
	}else if(ime == "" && prezime != "" && brO != ""){//pretraga po prezimenu i br os
		$.ajax({
			url: "api/pacijenti/lekar/" + obj.id + "/" +"prezime/"+ prezime + "/brO/" + brO,
			type: "GET",
			contentType: "application/json",
			dataType: "json",
			headers: {
		        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
		    },
			complete: function(data) {
				pacijenti = JSON.parse(data.responseText);
				loadPacijentiLekar(pacijenti);	
			}
		});		
	}else if(ime != "" && prezime != "" && brO != ""){//pretraga po imenu i prezimenu i br os
		$.ajax({
			url: "api/pacijenti/lekar/" + obj.id + "/" +"ime/"+ ime + "/prezime/" + prezime + "/brO/" + brO,
			type: "GET",
			contentType: "application/json",
			dataType: "json",
			headers: {
		        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
		    },
			complete: function(data) {
				pacijenti = JSON.parse(data.responseText);
				loadPacijentiLekar(pacijenti);	
			}
		});	
	}	
}

function pretragaTPN(){
	var obj = JSON.parse(localStorage.getItem('user'));
	var nazivTPN = document.getElementById("pretragaNazivTPN").value;
	var naziv = nazivTPN.toUpperCase();
	console.log(naziv);
	if (naziv != ""){
		$.ajax({
			url: "api/tipoviPregledaN/adminK/" + obj.id + "/" +"naziv/"+ naziv,
			type: "GET",
			contentType: "application/json",
			dataType: "json",
			headers: {
		        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
		    },
			complete: function(data) {
				tipovi = JSON.parse(data.responseText);
				loadTPIzmena(tipovi);	
			}
		});	
	}
}

function pretragaSalaAdmin(){
	var obj = JSON.parse(localStorage.getItem('user'));
	var nazivSale = document.getElementById("pretragaNaziv").value;
	var maliNaziv = nazivSale.toLowerCase();
	var naziv = maliNaziv.charAt(0).toUpperCase() + maliNaziv.slice(1);
	var brojSale = document.getElementById("pretragaBroj").value;
	var maliBroj = brojSale.toLowerCase();
	var broj = maliBroj.charAt(0).toUpperCase() + maliBroj.slice(1);
	if(naziv != "" && broj != ""){ //po nazivu i broju
	$.ajax({
		url: "api/sale/adminK/" + obj.id + "/" + naziv + "/" + broj,
		type: "GET",
		contentType: "application/json",
		dataType: "json",
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
		complete: function(data) {
			console.log("USLO OVDE");
			sale = JSON.parse(data.responseText);
			loadSaleIzmena(sale);	
		}
	});	
	}else if(naziv == "" && broj != ""){//pretraga po broju
		$.ajax({
			url: "api/sale/adminK/" + obj.id + "/" +"broj/" + broj,
			type: "GET",
			contentType: "application/json",
			dataType: "json",
			headers: {
		        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
		    },
			complete: function(data) {
				sale = JSON.parse(data.responseText);
				loadSaleIzmena(sale);	
			}
		});		
	}else if(naziv != "" && broj == ""){//pretraga po nazivu
		$.ajax({
			url: "api/sale/adminK/" + obj.id + "/" +"naziv/"+ naziv,
			type: "GET",
			contentType: "application/json",
			dataType: "json",
			headers: {
		        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
		    },
			complete: function(data) {
				sale = JSON.parse(data.responseText);
				loadSaleIzmena(sale);	
			}
		});	
	}	
}

function filtrirajNaziKlinike(){
	var nazivKlinike = document.getElementById("filterNaziv").value;
	console.log("NAJNOVIJE " + JSON.stringify(pretrazeneKlinike));
	if(nazivKlinike != ""){
		$.ajax({
			url: "api/klinike/filterNaziv/" + nazivKlinike,
			type: "GET",
			contentType: "application/json",
			dataType: "json",
			headers: {
		        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
		    },
			complete: function(data) {
				klinike = JSON.parse(data.responseText);
				console.log(klinike)
				poredi(klinike);
			}
		});	
	}
}

function filtrirajGradKlinike(){
	var gradKlinike = document.getElementById("filterGrad").value;
	//console.log("NAJNOVIJE " + JSON.stringify(pretrazeneKlinike));
	if(gradKlinike != ""){
		$.ajax({
			url: "api/klinike/filterGrad/" + gradKlinike,
			type: "GET",
			contentType: "application/json",
			dataType: "json",
			headers: {
		        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
		    },
			complete: function(data) {
				klinike = JSON.parse(data.responseText);
				console.log(klinike)
				poredi(klinike);
			}
		});	
	}
}

function filtrirajOcenuKlinike(){
	var ocenaNajmanjaKlinike = document.getElementById("filterOcenaNajmanja").value;
	var ocenaNajvecaKlinike = document.getElementById("filterOcenaNajveca").value;
	//console.log("NAJNOVIJE " + JSON.stringify(pretrazeneKlinike));
	if(ocenaNajmanjaKlinike != "" && ocenaNajvecaKlinike != ""){
		$.ajax({
			url: "api/klinike/filterOcena/obe/" + ocenaNajmanjaKlinike + "/" + ocenaNajvecaKlinike,
			type: "GET",
			contentType: "application/json",
			dataType: "json",
			headers: {
		        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
		    },
			complete: function(data) {
				klinike = JSON.parse(data.responseText);
				console.log(klinike)
				poredi(klinike);
			}
		});	
	}else if(ocenaNajmanjaKlinike != "" && ocenaNajvecaKlinike == ""){//samo najmanja, nije uneta najveca
		$.ajax({
			url: "api/klinike/filterOcena/najmanja/" + ocenaNajmanjaKlinike,
			type: "GET",
			contentType: "application/json",
			dataType: "json",
			headers: {
		        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
		    },
			complete: function(data) {
				klinike = JSON.parse(data.responseText);
				console.log(klinike)
				poredi(klinike);
			}
		});	
	}else if(ocenaNajmanjaKlinike == "" && ocenaNajvecaKlinike != ""){//samo najveca, nije uneta najmanja
		$.ajax({
			url: "api/klinike/filterOcena/najveca/" + ocenaNajvecaKlinike,
			type: "GET",
			contentType: "application/json",
			dataType: "json",
			headers: {
		        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
		    },
			complete: function(data) {
				klinike = JSON.parse(data.responseText);
				console.log(klinike)
				poredi(klinike);
			}
		});	
	}
}

function emptyPacijent() {
    var x = document.getElementById("ime").value;
    var y = document.getElementById("prezime").value;
    var a = document.getElementById("adresa").value;
    var s = document.getElementById("grad").value;
    var d = document.getElementById("drzava").value;
    var f = document.getElementById("brojTelefona").value;
    if (x == "" || y == "" || a == "" || s == "" || d == "" || f == "") {
        
        return 0;
    };
    return 1;
}

function izmenaProfilaPacijenta(){
	var dat = getFormData($("#formaIzmena"));
	var org = JSON.stringify(dat);
	var obj = JSON.parse(localStorage.getItem('user'));
	var x = emptyPacijent();
	if(x == 1){
        $.ajax({
		url: "api/pacijenti/izmena/" + obj.id,
		type: "PUT",
		data: org,
		contentType:"application/json",
		dataType: "json",
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
		complete : function (data) {
			alert("Izmene uspesno sacuvane! Izmene ce biti vidljive nakon sto se ponovo prijavite.");
		} 
        });
    }else{
    	alert("Polja ne smeju ostati prazna!");
    }
	
}

function prikazCenovnika(){
	var idK = JSON.parse(localStorage.getItem('idKlinike')); 
	$.ajax({
		url: "api/cenovnik/" + idK,
		type: "GET",
		contentType: "application/json",
		dataType: "json",
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
		complete : function (data) {
			cenovnici = JSON.parse(data.responseText);
			console.log(cenovnici);
			loadCenovnici(cenovnici);
		} 
	});	
}

function prikazCenovnikaAdmin(){
	$("#lineChart").hide();
	obrisiTabele();
	obrisiPretragu();
	obrisiFilter();
	var obj = JSON.parse(localStorage.getItem('user')); 
	$.ajax({
		url: "api/cenovnik/adminK/" + obj.id,
		type: "GET",
		contentType: "application/json",
		dataType: "json",
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
		complete : function (data) {
			cenovnici = JSON.parse(data.responseText);
			console.log(cenovnici);
			loadCenovniciAdmin(cenovnici);
		} 
	});	
}

function loadCenovnici(cenovnici){
	obrisiTabele();
	obrisiPretragu();
	obrisiFilter();
	$("#tabela_cenovnika tbody tr").remove(); 
	$("#tabela_cenovnika thead ").remove();
	var table = $("#tabela_cenovnika");
	table.append(makeTableHeaderCenovnici());	
	for(let c of cenovnici) {
		table.append(makeTableRowCenovnici(c));
	}
}

function loadPrihodKlinike(prihod){
	console.log("uslo u loadprihodklinike");
	$("#tabela_prihoda tbody tr").remove(); 
	$("#tabela_prihoda thead ").remove();
	var table = $("#tabela_prihoda");
	table.append(makeTableHeaderPrihodKlinike());	
	for(let p of prihod) {
		table.append(makeTableRowPrihodKlinike(p));
	}
}

function makeTableHeaderPrihodKlinike(){
	console.log("uslo u header");
	var row="";
	row =
		`<thead class="thead-light" bgcolor="white">
				<tr>
					<th>Prihod</th>
				</tr>
		</thead>`;
	return row;
}

function makeTableRowPrihodKlinike(p) {
	console.log("uslo u row");
	var row = "";
	  row =
		`<tr>
			<td class="izgledTabele" id='${p}'>${p}</td>
		</tr>`;
	return row;
}

function loadCenovniciAdmin(cenovnici){
	console.log("uslo u loadcenovnici");
	console.log(cenovnici);
	$("#tabela_cenovnika_admina tbody tr").remove(); 
	$("#tabela_cenovnika_admina thead ").remove();
	var table = $("#tabela_cenovnika_admina");
	table.append(makeTableHeaderCenovnici());	
	for(let c of cenovnici) {
		table.append(makeTableRowCenovnici(c));
	}
}

function makeTableHeaderCenovnici(){
	console.log("uslo u header");
	var row="";
	row =
		`<thead class="thead-light" bgcolor="white">
				<tr>
					<th>Tip pregleda</th>
					<th>Cena</th>
				</tr>
		</thead>`;
	return row;
}

function makeTableRowCenovnici(c) {
	console.log("uslo u row");
	var row = "";
	  row =
		`<tr>
			<td class="izgledTabele" id='${c.tipPregledaCenovnik}'>${c.tipPregledaCenovnik}</td>
			<td class="izgledTabele" id='${c.cena}'>${c.cena}</td>
		</tr>`;
	return row;
}

function prebaciNaZahtevZaPregled(imeL, prezimeL, tipP, nazivK, idL){
	vremeG = document.getElementById("vreme").value;
	console.log("VRMEEEEE: " + vremeG);
	var datum = localStorage.getItem('datum');
	var spojeno = datum + " " + vremeG;
	$("#modalnaForma div").remove(); 
	var modal=$("#modalnaForma");
	var m = "";
	m = `<div class="imgcontainer">
	      <span onclick="document.getElementById('modal-wrapper').style.display='none'" class="close" title="Close PopUp">&times;</span>
	      <h1 style="text-align:center">Zahtev za pregled</h1></div>
			<div class="container">
			<br>
			<form action="" id="formaKreiranje">
			<table class="table " id="tabela_modal">
				<tbody>
					<tr>
					<td  class='align-middle'><label for="ime">Ime lekara</label></td>
					<td  class='align-middle'><span style = "color:black"><input type="text" name="ime" value="${imeL}" id="ime" readonly></td>
					</tr>
					<tr>
					<td  class='align-middle'><label for="prezime">Prezime lekara</label></td>
					<td  class='align-middle'><span style = "color:black;"><input type="text" name="prezime" value="${prezimeL}" id="prezime" readonly></td>
					</tr>
					<tr>
					<td  class='align-middle'><label for="tipPregleda">Tip pregleda</label></td>
					<td  class='align-middle'><span style = "color:black;"><input type="text" name="tipPregleda" value="${tipP}" id="tipPregleda" readonly></td>
					</tr>
					<tr>
					<td  class='align-middle'><label for="nazivKlinike">Naziv klinike</label></td>
					<td  class='align-middle'><span style = "color:black;"><input type="text" name="nazivKlinike" value="${nazivK}" id="nazivKlinike" readonly></td>
					</tr>	
					<tr>
					<td  class='align-middle'><label for="vreme">Datum i vreme pregleda</label></td>
					<td  class='align-middle'><span style = "color:black;"><input type="text" name="vreme" value="${spojeno}" id="vreme" readonly></td>
					</tr>
					<tr><td  class='align-middle' ><button type="button" onclick="sacuvajZahtevZaPregled(\'${idL}\')">Posalji zahtev</button></td></tr>									
				</tbody>
			</table>
			</form>
			<fieldset id="log_war"></fieldset>
		    </div>`;
			modal.append(m);
		$("#modal-wrapper").show();
}

function sacuvajZahtevZaPregled(idLek){
	console.log(idLek);
	var obj = JSON.parse(localStorage.getItem('user'));
	var data = getFormData($("#formaKreiranje"));
	var org = JSON.stringify(data);
	$.ajax({
		url: "api/zahteviZaPregled/" + obj.id + "/" + idLek,
		type: "POST",
		data: org,
		contentType: "application/json",
		dataType: "json",
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
		complete : function (data) {
			alert("Zahtev za pregled uspesno poslat!");
		} 

	});
}

function dobaviPacijenta(){
	var obj= localStorage.getItem('idPacijenta');
	$.ajax({
		url: "api/pacijenti/" + obj,
		type: "GET",
		contentType: "application/json",
		dataType: "json",
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
		complete: function(data) {
			pacijent = JSON.parse(data.responseText);
			console.log(JSON.stringify(pacijent))
			profilPacijenta(pacijent);
			
		}
	});
}
function profilPacijenta(obj){
	
	$("#modalnaForma div").remove(); 
	var modal=$("#modalnaForma");
	var m = "";
	m = `<div class="imgcontainer">
	      <span onclick="document.getElementById('modal-wrapper').style.display='none'" class="close" title="Close PopUp">&times;</span>
	      <h1 style="text-align:center">Profil pacijenta</h1></div>
			<div class="container">
			<br>
			
			<table class="table " id="tabela_modal">
				<tbody>
					<tr>
					<td  class='align-middle'><label for="ime">Ime</label></td>
					<td  class='align-middle'><span style = "color:black"><input type="text" placeholder="Enter" value="${obj.ime}" name="ime" id="ime" readonly></td>
					</tr>
					<tr>
					<td  class='align-middle'><label for="prezime">Prezime</label></td>
					<td  class='align-middle'><span style = "color:black;"><input type="text" placeholder="Enter" value="${obj.prezime}" name="prezime" id="prezime" readonly></td>
					</tr>
					<tr>
					<td  class='align-middle'><label for="brojOsiguranika">Broj osiguranika</label></td>
					<td  class='align-middle'><span style = "color:black;"><input type="text" placeholder="Enter" value="${obj.brojOsiguranika}" name="brojOsiguranika" id="brojOsiguranika" readonly></td>
					</tr>
					<tr>
					<td  class='align-middle'><label for="adresa">Adresa</label></td>
					<td  class='align-middle'><span style = "color:black;"><input type="text" placeholder="Enter" value="${obj.adresa}" name="adresa" id="adresa" readonly></td>
					</tr>
					<tr>
					<td  class='align-middle'><label for="grad">Grad</label></td>
					<td  class='align-middle'><span style = "color:black;"><input type="text" placeholder="Enter" value="${obj.grad}" name="grad" id="grad" readonly></td>
					</tr>					
					<tr>
					<td  class='align-middle'><label for="drzava">Drzava</label></td>
					<td  class='align-middle'><span style = "color:black;"><input type="text" placeholder="Enter" value="${obj.drzava}" name="drzava" id="drzava" readonly></td>
					</tr>					
					<tr>
					<td  class='align-middle'><label for="brojTelefona">Broj telefona</label></td>
					<td  class='align-middle'><span style = "color:black;"><input type="text" placeholder="Enter" value="${obj.brojTelefona}" name="brojTelefona" id="brojTelefona" readonly></td>
					</tr>
									
				</tbody>
			</table>
			
			<fieldset id="log_war"></fieldset>
		    </div>`;
			modal.append(m);
		$("#modal-wrapper").show();
}
function setUpZKLekar() {
	var obj = JSON.parse(localStorage.getItem('idPacijenta'));
	$.ajax({
		url: "api/kartoni/svi/" + obj,
		type: "GET",
		contentType: "application/json",
		dataType: "json",
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
		complete: function(data) {
			kartoni = JSON.parse(data.responseText);
			console.log(kartoni)
			pregledModZKKodLekara(kartoni);
		}
	});
}

function pregledModZKKodLekara(kartoni) {
	$("#modalnaForma div").remove(); 
	var modal=$("#modalnaForma");
	var m = "";
	m = `<div class="imgcontainer">
	      <span onclick="document.getElementById('modal-wrapper').style.display='none'" class="close" title="Close PopUp">&times;</span>
	      <h1 style="text-align:center">Zdravstveni karton pacijenta</h1></div>
			<div class="container">
			<br>
			<form action="" id="formaIzmena">
			<table class="table " id="tabela_modal">
				<tbody>
					<tr>
					<td  class='align-middle'><label for="tezina">Tezina</label></td>
					<td  class='align-middle'><span style = "color:black"><input type="text" placeholder="Enter" value="${kartoni.tezina}" name="tezina" id="tezina" ></td>
					</tr>
					<tr>
					<td  class='align-middle'><label for="visina">Visina</label></td>
					<td  class='align-middle'><span style = "color:black;"><input type="text" placeholder="Enter" value="${kartoni.visina}" name="visina" id="visina" ></td>
					</tr>
					<tr>
					<td  class='align-middle'><label for="dioptrija">Dioptrija</label></td>
					<td  class='align-middle'><span style = "color:black;"><input type="text" placeholder="Enter" value="${kartoni.dioptrija}" name="dioptrija" id="dioptrija" ></td>
					</tr>
					<tr>
					<td  class='align-middle'><label for="krvnaGrupa">Krvna grupa</label></td>
					<td  class='align-middle'><span style = "color:black;"><input type="text" placeholder="Enter" value="${kartoni.krvnaGrupa}" name="krvnaGrupa" id="krvnaGrupa" ></td>
					</tr>
					<tr><td  class='align-middle' ><button type="button" onclick="sacuvajIzmeneZK(\'${kartoni.idZdravstvenogKartona}\')">Sacuvaj izmene</button></td></tr>							
				</tbody>
			</table>
			</form>
			<fieldset id="log_war"></fieldset>
		    </div>`;
			modal.append(m);
		$("#modal-wrapper").show();
	
}
function sacuvajIzmeneZK(idZdravstvenogKartona) {

	var dat = getFormData($("#formaIzmena"));
	
	var org = JSON.stringify(dat);
    $.ajax({
		url: "api/kartoni/" + idZdravstvenogKartona,
		type: "PUT",
		data: org,
		contentType:"application/json",
		dataType: "json",
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
		complete : function (data) {
			alert("Izmene zdravstvenog kartona uspesno sacuvane!");
		} 
	 
	});
}


function listaPregledaPac(){
	$("#tabela_pregledi tbody tr").remove(); 
	$("#tabela_pregledi thead ").remove();
	$("#tabela_pregledi").show();
	
	var pac = JSON.parse(localStorage.getItem('idPacijenta'));
	var lek = JSON.parse(localStorage.getItem('user'));
	$.ajax({
		url: "api/pregledi/lekar/" + pac + "/" + lek.id,
		type: "GET",
		contentType: "application/json",
		dataType: "json",
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
		complete: function(data) {
			pregledi = JSON.parse(data.responseText);
			console.log(pregledi)
			loadPreglediLek(pregledi);
		}
	});
}
function loadPreglediLek(pregledi){

	var table = $("#tabela_pregledi");
	table.append(makeTableHeaderPreglediL());	
	for(let p of pregledi) {
		table.append(makeTableRowPreglediL(p));
}
}

function makeTableHeaderPreglediL(){
	
	var row="";
	 row =
			`<thead class="thead-light" bgcolor="white">
					<tr>
						<th>Datum</th>
						<th>Vreme</th>
						<th>Trajanje</th>
						<th>Sala</th>
						<th></th>
				     </tr>
				</thead>`;
		
		return row;
}

function makeTableRowPreglediL(p) {
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
			<td class="izgledTabele"><input type="button" id="zp" value="Zapocni pregled" onClick="zapocniPregled(\'${p.idPregleda}\')"/></td>
		</tr>`;
	
	return row;
}
function dobaviSD(){
	var povratna;
	$.ajax({
		url: "api/sifrarnikDijagnoza",
		type: "GET",
		contentType: "application/json",
		dataType: "json",
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
		complete : function(data) {
			sd = JSON.parse(data.responseText);
			napuniCBSD(sd);
			
		}
	});
	
}
function dobaviSL(){
	var povratna;
	$.ajax({
		url: "api/sl/novo",
		type: "GET",
		contentType: "application/json",
		dataType: "json",
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
		complete : function(data) {
			sl = JSON.parse(data.responseText);
			napuniCBSL(sl);
			
		}
	});
	
}
function napuniCBSD(sd){
	console.log(sd);
	var m = "";	
	var modal=$("#nazivDijagnoze");	
	for(let s of sd){
		console.log(s.nazivDijagnoze);
		m = `<option id="nazivDijagnoze" value=${s.nazivDijagnoze}>${s.nazivDijagnoze}</option>`;
		modal.append(m);
	}
}
function napuniCBSL(sl){
	console.log(sl);
	var m = "";	
	var modal=$("#nazivLeka");	
	for(let s of sl){
		
		m = `<option id="nazivLeka" value=${s.nazivLeka}>${s.nazivLeka}</option>`;
		modal.append(m);
	}
}
function zapocniPregled(idPregleda){
	$("#modalnaForma div").remove(); 
	var modal=$("#modalnaForma");
	var m = "";
	m = `<div class="imgcontainer">
	      <span onclick="document.getElementById('modal-wrapper').style.display='none'" class="close" title="Close PopUp">&times;</span>
	      <h1 style="text-align:center">Izvestaj o pregledu</h1></div>
			<div class="container">
			<br>
			<form action="" id="formaIzmena">
			<table class="table " id="tabela_modal">
				<tbody>
					<tr>
						  <td colspan = 2><textarea name="info"  id = "info"></textarea></td>
				    </tr>
					<tr><td>
					<select name="nazivDijagnoze" id="nazivDijagnoze">
		  				
		  			</select>
		  			</td>
		  			
		  			<td>
					<select name="nazivLeka" id="nazivLeka">
		  				
		  			</select>
		  			</td></tr>
		  			
		  			<tr><td><input type="button" id="iop" value="Zavrsi pregled" onClick="sacuvajIzvestajOPregledu(\'${idPregleda}\')"/></td>
		  			<td><input type="button" id="rec" value="Kreiraj recept" onClick="kreirajRecept(\'${idPregleda}\')"/></td></tr>
		  			
		  			<tr>
                		<td align="right">Datum novog pregleda</td>
                		<td><input type="date" name="datumVreme" id="datumVreme" ></td>
            		</tr>
 
            		<tr><td  class='align-middle' ><button type="button" onclick="posaljiZahtevZaPregled(\'${idPregleda}\')">Posalji zahtev za pregled</button></td></tr>		
            		<tr>
                		<td align="right">Datum nove operacije</td>
                		<td><input type="date" name="datumVremeO" id="datumVremeO" ></td>
            		</tr>	
            		<tr>
                		<td align="right">Dodatne informacije o operaciji</td>
                		<td><textarea name="infooop"  id = "infooop"></textarea></td>
            		</tr>	
            		<tr><td  class='align-middle' ><button type="button" onclick="posaljiZahtevZaOperaciju(\'${idPregleda}\')">Posalji zahtev za operaciju</button></td></tr>		
            		
            </tr>
						
			</tbody>
			</table>
			</form>
			<fieldset id="log_war"></fieldset>
		    </div>`;
			modal.append(m);
			dobaviSL();
			dobaviSD();
			
		$("#modal-wrapper").show(); 
		
}

function posaljiZahtevZaPregled(idPregleda){
	var data = getFormData($("#formaIzmena"));
	console.log("usao u posalji zahtev");
	console.log(data.datumVreme);
	console.log(idPregleda);
	var org = JSON.stringify(data);
	$.ajax({
		url: "api/zahteviZaPregled/datumVreme/" + data.datumVreme + "/" + idPregleda,
		type: "POST",
		data: org,
		contentType: "application/json",
		dataType: "json",
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
		complete : function (data) {
			alert("Zahtev za pregled uspesno kreiran i poslat mail administratoru klinike!");
		} 
	});	
}

function posaljiZahtevZaOperaciju(idPregleda){
	var data = getFormData($("#formaIzmena"));
	console.log("usao u posalji zahtev za operaciju");
	console.log(data.datumVremeO);
	console.log(idPregleda);
	var infooop= document.getElementById('infooop').value;
	console.log(infooop);
	var org = JSON.stringify(data);
	$.ajax({
		url: "api/zahteviZaOperaciju/datumVremeO/" + data.datumVremeO + "/" + idPregleda+"/"+infooop,
		type: "POST",
		data: org,
		contentType: "application/json",
		dataType: "json",
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
		complete : function (data) {
			alert("Zahtev za operaciju uspesno kreiran i poslat mail administratoru klinike!");
		} 
	});	
}


///izvestajOPregleduDTO/{idPregleda}

function sacuvajIzvestajOPregledu(idPregleda){
	var data = getFormData($("#formaIzmena"));
	
	var org = JSON.stringify(data);
	$.ajax({
		url: "api/izvestajOPregleduDTO/" + idPregleda,
		type: "POST",
		data: org,
		contentType: "application/json",
		dataType: "json",
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
		complete : function (data) {
			alert("Izvestaj o pregledu uspesno sacuvan!");
		} 

	});
	
	}

function kreirajRecept(idPregleda){
	
	
var data = getFormData($("#formaIzmena"));

console.log(data.nazivLeka);
	

	$.ajax({
		url: "api/recepti/dodaj/" + data.nazivLeka + "/" + idPregleda,
		type: "POST",

		contentType: "application/json",
		dataType: "json",
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
		complete : function (data) {
			alert("Uspesno kreiran recept!");
		} 

	});
	
	}

function pretragaLekaraTipPregleda(){
	var idK = localStorage.getItem('idKlinike');
	
	var tipPregleda = localStorage.getItem('tipPregleda');
	console.log(tipPregleda);
	
	console.log(idK);
	var imeLekara = document.getElementById("pretragaIme").value;
	var maloIme = imeLekara.toLowerCase();
	var ime = maloIme.charAt(0).toUpperCase() + maloIme.slice(1);
	var prezimeLekara = document.getElementById("pretragaPrezime").value;
	var maloPrezime = prezimeLekara.toLowerCase();
	var prezime = maloPrezime.charAt(0).toUpperCase() + maloPrezime.slice(1);
	var ocena = document.getElementById("ocena").value;
	if(ime != "" && prezime != ""){
	$.ajax({
		url: "api/lekari/klinika/" + idK + "/" + ime + "/" + prezime + "/" + ocena + "/" + tipPregleda,
		type: "GET",
		contentType: "application/json",
		dataType: "json",
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
		complete: function(data) {
			lekari = JSON.parse(data.responseText);
			console.log(JSON.stringify(lekari));
			loadLekariKlinikeTipPregleda(lekari);	
		}
	});	
	}else if(ime == "" && prezime != ""){//pretraga po prezimenu
		$.ajax({
			url: "api/lekari/klinika/" + idK + "/" +"prezime/" + prezime + "/" + ocena + "/" + tipPregleda,
			type: "GET",
			contentType: "application/json",
			dataType: "json",
			headers: {
		        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
		    },
			complete: function(data) {
				lekari = JSON.parse(data.responseText);
				console.log(JSON.stringify(lekari));
				loadLekariKlinikeTipPregleda(lekari);	
			}
		});		
	}else if(ime != "" && prezime == ""){//pretraga po imenu
		$.ajax({
			url: "api/lekari/klinika/" + idK + "/" +"ime/"+ ime + "/" + ocena + "/" + tipPregleda,
			type: "GET",
			contentType: "application/json",
			dataType: "json",
			headers: {
		        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
		    },
			complete: function(data) {
				lekari = JSON.parse(data.responseText);
				console.log(JSON.stringify(lekari));
				loadLekariKlinikeTipPregleda(lekari);	
			}
		});		
	}else if(ime == "" && prezime == ""){//ime prazno, prezime prazno, ostala samo ocena
		$.ajax({
			url: "api/lekari/klinika/"+ idK +"/ocena/" + ocena + "/" + tipPregleda,
			type: "GET",
			contentType: "application/json",
			dataType: "json",
			headers: {
		        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
		    },
			complete: function(data) {
				lekari = JSON.parse(data.responseText);
				console.log(JSON.stringify(lekari));
				loadLekariKlinikeTipPregleda(lekari);	
			}
		});		
	}
}

function poredi(klinike){
	var filtriraneKl = [];
	for(let kf of klinike){
		console.log(kf.idKlinike)
		for(let kp of pretrazeneKlinike){
			console.log(kp.idKlinike)
			if(kf.idKlinike == kp.idKlinike){
				filtriraneKl.push(kf);
			}
		}
	}
	pretrazeneKlinike = filtriraneKl;
	loadKlinikePretrazene(filtriraneKl);
}

function obrisiFilter(){
	$("#filterNaziv").hide();
	$("#filterKlinikaNaziv").hide();
	$("#filterGrad").hide();
	$("#filterKlinikaGrad").hide();
	$("#filterOcenaNajmanja").hide();
	$("#minus").hide();
	$("#filterOcenaNajveca").hide();
	$("#filterKlinikaOcena").hide();
	$("#filterCenaNajmanja").hide();
	$("#minusCena").hide();
	$("#filterCenaNajveca").hide();
	$("#filterKlinikaCena").hide();
	$("#filterTipPregleda").hide();
	$("#filterLekarTipPregleda").hide();
}

function filtrirajTipPregledaLekara(){
	var idK = localStorage.getItem('idKlinike');
	var tipPregledaLekara = document.getElementById("filterTipPregleda").value;
	var velikimTPL = tipPregledaLekara.toUpperCase();
	if(tipPregledaLekara != ""){
		$.ajax({
			url: "api/lekari/filter/tipPregleda/" + velikimTPL + "/" + idK,
			type: "GET",
			contentType: "application/json",
			dataType: "json",
			headers: {
		        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
		    },
			complete: function(data) {
				lekari = JSON.parse(data.responseText);
				console.log(JSON.stringify(lekari));
				porediLekareFilter(lekari);	
			}
		});	
	}
}

function porediLekareFilter(lekari){
	var filtriraniLekari = [];
	for(let lf of lekari){
		for(let lp of pretrazeniLekari){
			if(lf.id == lp.id){
				filtriraniLekari.push(lf);
			}
		}
	}
	pretrazeniLekari = filtriraniLekari;
	loadLekariKlinike(filtriraniLekari);
}

function sortTableListaKlinikaZaPacijenta() {
	  var table, rows, switching, i, x, y, shouldSwitch;
	  table = document.getElementById("tabela_klinike");
	  switching = true;
	  /*Make a loop that will continue until
	  no switching has been done:*/
	  while (switching) {
	    //start by saying: no switching is done:
	    switching = false;
	    rows = table.rows;
	    /*Loop through all table rows (except the
	    first, which contains table headers):*/
	    for (i = 1; i < (rows.length - 1); i++) {
	      //start by saying there should be no switching:
	      shouldSwitch = false;
	      /*Get the two elements you want to compare,
	      one from current row and one from the next:*/
	      x = rows[i].getElementsByTagName("TD")[0];
	      y = rows[i + 1].getElementsByTagName("TD")[0];
	      //check if the two rows should switch place:
	      if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
	        //if so, mark as a switch and break the loop:
	        shouldSwitch = true;
	        break;
	      }
	    }
	    if (shouldSwitch) {
	      /*If a switch has been marked, make the switch
	      and mark that a switch has been done:*/
	      rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
	      switching = true;
	    }
	  }
}

function sortTableIstorijaPregledaZaPacijenta() {
	  var table, rows, switching, i, x, y, shouldSwitch;
	  table = document.getElementById("tabela_pregledi");
	  switching = true;
	  /*Make a loop that will continue until
	  no switching has been done:*/
	  while (switching) {
	    //start by saying: no switching is done:
	    switching = false;
	    rows = table.rows;
	    /*Loop through all table rows (except the
	    first, which contains table headers):*/
	    for (i = 1; i < (rows.length - 1); i++) {
	      //start by saying there should be no switching:
	      shouldSwitch = false;
	      /*Get the two elements you want to compare,
	      one from current row and one from the next:*/
	      x = rows[i].getElementsByTagName("TD")[4];
	      y = rows[i + 1].getElementsByTagName("TD")[4];
	      //check if the two rows should switch place:
	      if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
	        //if so, mark as a switch and break the loop:
	        shouldSwitch = true;
	        break;
	      }
	    }
	    if (shouldSwitch) {
	      /*If a switch has been marked, make the switch
	      and mark that a switch has been done:*/
	      rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
	      switching = true;
	    }
	  }
}

function sortPacijenata() {
	  var table, rows, switching, i, x, y, shouldSwitch;
	  table = document.getElementById("tabela_pacijenti");
	  switching = true;
	  /*Make a loop that will continue until
	  no switching has been done:*/
	  while (switching) {
	    //start by saying: no switching is done:
	    switching = false;
	    rows = table.rows;
	    /*Loop through all table rows (except the
	    first, which contains table headers):*/
	    for (i = 1; i < (rows.length - 1); i++) {
	      //start by saying there should be no switching:
	      shouldSwitch = false;
	      /*Get the two elements you want to compare,
	      one from current row and one from the next:*/
	      x = rows[i].getElementsByTagName("TD")[1];
	      y = rows[i + 1].getElementsByTagName("TD")[1];
	      //check if the two rows should switch place:
	      if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
	        //if so, mark as a switch and break the loop:
	        shouldSwitch = true;
	        break;
	      }
	    }
	    if (shouldSwitch) {
	      /*If a switch has been marked, make the switch
	      and mark that a switch has been done:*/
	      rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
	      switching = true;
	    }
	  }
}

function sortTableKlinikaAKC() {
	  var table, rows, switching, i, x, y, shouldSwitch;
	  table = document.getElementById("tabela_klinikeAKC");
	  switching = true;
	  /*Make a loop that will continue until
	  no switching has been done:*/
	  while (switching) {
	    //start by saying: no switching is done:
	    switching = false;
	    rows = table.rows;
	    /*Loop through all table rows (except the
	    first, which contains table headers):*/
	    for (i = 1; i < (rows.length - 1); i++) {
	      //start by saying there should be no switching:
	      shouldSwitch = false;
	      /*Get the two elements you want to compare,
	      one from current row and one from the next:*/
	      x = rows[i].getElementsByTagName("TD")[0];
	      y = rows[i + 1].getElementsByTagName("TD")[0];
	      //check if the two rows should switch place:
	      if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
	        //if so, mark as a switch and break the loop:
	        shouldSwitch = true;
	        break;
	      }
	    }
	    if (shouldSwitch) {
	      /*If a switch has been marked, make the switch
	      and mark that a switch has been done:*/
	      rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
	      switching = true;
	    }
	  }
}

//prikaz zahteva za registraciju
function setUpUserPageAKCZZR() {
obrisiPretragu();
	$.ajax({
		url: "api/zahtevizr",
		type: "GET",
		contentType: "application/json",
		dataType: "json",
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
		complete: function(data) {
			z = JSON.parse(data.responseText);
			console.log(z)
			//loadSifrarnikLekova(sl);
			loadZahteviZaRegistraciju(z);
			
		}
	});
}

//sifrarnik lekova
function loadZahteviZaRegistraciju(z) {
	obrisiTabele();
	$("#tabela_sl tbody tr").remove(); 
	$("#tabela_sl thead ").remove();
	var table = $("#tabela_zahtevizr");
	table.append(makeTableHeaderZR());	
	for(let s of z) {
		table.append(makeTableRowZR(s));
	}
}

//header za tabelu zahteva za registraciju
function makeTableHeaderZR(){
	
	var row="";
	 row =
			`<thead class="thead-light" bgcolor="white">
					<tr>
						<th>Ime</th>
						<th>Prezime</th>
						<th>Email</th>
						<th>Broj telefona</th>
						<th></th>
						<th></th>
				     </tr>
				</thead>`;
		
		return row;
}

function makeTableRowZR(s) {
	var row = "";
	
	  row =
		`<tr>
			<td class="izgledTabele" >${s.ime}</td>
			<td class="izgledTabele" >${s.prezime}</td>
			<td class="izgledTabele" >${s.email}</td>
			<td class="izgledTabele" >${s.brojTelefona}</td>
			<td class="izgledTabele" ><input type="button" id="pr" value="Prihvati zahtev" onClick="prihvatiZahtev(${s.idZahtevaZaRegistraciju})"/></td>
		   <td class="izgledTabele" ><input type="button" id="od" value="Odbij zahtev" onClick="odbijZahtevMod(${s.idZahtevaZaRegistraciju})"/></td>
		</tr>`;
	
	return row;
}



function odbijZahtevMod(idZahtevaReg){
	$("#modalnaForma div").remove(); 
	var modal=$("#modalnaForma");
	var m = "";
	m = `<div class="imgcontainer">
	      <span onclick="document.getElementById('modal-wrapper').style.display='none'" class="close" title="Close PopUp">&times;</span>
	      <h1 style="text-align:center">Razlog odbijanja zahteva za regsitraciju</h1></div>
			<div class="container">
			<br>
			<form action="" id="formaIzmena">
			<table class="table " id="tabela_modal">
				<tbody>
					<tr>
						  <td colspan = 2><textarea name="razlog"  id = "razlog"></textarea></td>
				    </tr>
					<tr><td  class='align-middle' ><button type="button" onclick="odbijZahtev(${idZahtevaReg})">Potvrdi</button></td></tr>							
				</tbody>
			</table>
			</form>
			<fieldset id="log_war"></fieldset>
		    </div>`;
			modal.append(m);
		$("#modal-wrapper").show();
	
}


function odbijZahtev(idZahtevaReg){
	var razlog = document.getElementById("razlog").value;
	console.log(razlog);
	$.ajax({
		url: "api/odbijenZahtev/" +idZahtevaReg + "/" + razlog,
		type: "POST",
		consumes : "application/json" , 
		contentType: "application/json",
		dataType: "json",
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
	    complete : function(data) {
	    	alert("Zahtev za registraciju odbijen! Mail je poslat korisniku.");
	    }
	});
	let isBoss = confirm("Mail poslat!");
}

function prihvatiZahtev(idZahtevaReg){
	
	$.ajax({
		url: "api/prihvaceno/" +idZahtevaReg,
		type: "POST",
		
		contentType: "application/json",
		dataType: "json",
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken},
	    complete : function(data) {
	    	alert("Zahtev za registraciju prihvacen! Mail je poslat korisniku.");
	    }
	
//	    success: function(){
//	    	let isBoss = confirm("Uspesno!");
//	  },
//	    error: function(e) {
//	    	let isBoss = confirm("Nije moguce prihvatiti zahtev jer postoji korisnik sa tom Email adresom!");
//	      }
		 
	});
}

function aktivirajNalog(){
	console.log(document.URL)
	var x = document.URL.split("/")
	console.log(x[5]);
	$.ajax({
		url: "auth/omogucenaRegistracija/" + x[5],
		method: "POST",
		contentType: "application/json",
		dataType: "json",
		/*headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },*/
		complete : function () {
			alert("Nalog uspesno aktiviran!");
			window.location.replace("./login.html");
		} 

	});
	
}
	
function odaberiSaluMod(z){
	$("#modalnaForma div").remove(); 
	var modal=$("#modalnaForma");
	var m = "";
	m = `<div class="imgcontainer">
	      <span onclick="document.getElementById('modal-wrapper').style.display='none'" class="close" title="Close PopUp">&times;</span>
	      <h1 style="text-align:center">Odaberite salu</h1></div>
			<div class="container">
			<br>
			<form action="" id="formaIzmena">
			<table class="table " id="tabela_modal">
				<tbody>
					<tr><td>
					<select name="slobodneSale" id="slobodneSale">
		  				
		  			</select>
		  			</td></tr>
					<tr><td  class='align-middle' ><button type="button" onclick="potvrdiSalu(${z})">Potvrdi</button></td></tr>							
				</tbody>
			</table>
			</form>
			<fieldset id="log_war"></fieldset>
		    </div>`;
			modal.append(m);
			dobaviSlobodneSale(z);
		$("#modal-wrapper").show();
}
function odaberiSaluModOP(z, vreme){
	$("#modalnaForma div").remove(); 
	var modal=$("#modalnaForma");
	var m = "";
	console.log(vreme)
	m = `<div class="imgcontainer">
	      <span onclick="document.getElementById('modal-wrapper').style.display='none'" class="close" title="Close PopUp">&times;</span>
	      <h1 style="text-align:center">Odaberite salu</h1></div>
			<div class="container">
			<br>
			<form action="" id="formaIzmena">
			<table class="table " id="tabela_modal">
				<tbody>
					<tr>
					
					<td><label>Slobodne sale:</label><select name="slobodneSale" id="slobodneSale">
		  				
		  			</select>
		  			
		  			<td><label>Odabrani datum:</label><input type="date" name="datumOperacije" id="datumOperacije" value = ${vreme} ></td>
		  			</td></tr>
					<tr><td  class='align-middle' ><button type="button" onclick="potvrdiSaluOP(${z}, '${vreme}')">Potvrdi</button></td>
					<td><label>Prikazi slobodne sale za odabrani datum </label><button type="button" onclick="napuniSaleOpet(${z}, '${vreme}' )">Prikazi</button></td>
					</tr>							
				</tbody>
			</table>
			</form>
			<fieldset id="log_war"></fieldset>
		    </div>`;
			modal.append(m);
			dobaviSlobodneSaleOP(z);
		$("#modal-wrapper").show();
}

//punjenje nakon promene datuma
function napuniSaleOpet(zahtev, vreme){
	//var povratna;
	console.log(vreme)
	$.ajax({
		url: "api/slobodneSale/"+zahtev+ "/" + document.getElementById('datumOperacije').value,
		type: "GET",
		contentType: "application/json",
		dataType: "json",
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
		complete : function(data) {
			ss = JSON.parse(data.responseText);
			napuniSlobodneSale(ss);
			
		}
	});
	
}



//ono inicijalno punjenje
function dobaviSlobodneSale(zahtev){
	//var povratna;
	$.ajax({
		url: "api/slobodneSale/"+zahtev,
		type: "GET",
		contentType: "application/json",
		dataType: "json",
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
		complete : function(data) {
			ss = JSON.parse(data.responseText);
			napuniSlobodneSale(ss);
			
		}
	});
	
}
function napuniSlobodneSale(ss){
	console.log(ss);
	var m = "";	
	$('#slobodneSale').empty();
	var modal=$("#slobodneSale");	
	for(let s of ss){
		
		m = `<option id="slobodneSale" value=${s}>${s}</option>`;
		modal.append(m);
	}
}

function potvrdiSalu(idZahteva){
	
	console.log(idZahteva)
	var data = getFormData($("#formaIzmena"));
	
	console.log(data.slobodneSale);
	
	$.ajax({
		url: "api/potvrdiSalu/" + idZahteva + "/" + data.slobodneSale,
		type: "POST",
		contentType: "application/json",
		dataType: "json",
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
	    complete : function(data) {
	    	alert("Pregled uspesno zakazan! Mail obavestenja poslat lekaru i pacijentu.");
	    }

	});
	
}

//OPERACIJE
function dobaviSlobodneSaleOP(zahtev){
	//var povratna;
	$.ajax({
		url: "api/slobodneSaleOP/"+zahtev,
		type: "GET",
		contentType: "application/json",
		dataType: "json",
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
		complete : function(data) {
			ss = JSON.parse(data.responseText);
			napuniSlobodneSaleOP(ss);
			
		}
	});
	
}
function napuniSlobodneSaleOP(ss){
	console.log(ss);
	var m = "";	
	var modal=$("#slobodneSale");	
	for(let s of ss){
		
		m = `<option id="slobodneSale" value=${s}>${s}</option>`;
		modal.append(m);
	}
}

function potvrdiSaluOP(idZahteva, vreme){
	
	console.log(idZahteva)
	var data = getFormData($("#formaIzmena"));
	
	console.log(data.slobodneSale);
	
	$.ajax({
		url: "api/potvrdiSaluOP/" + idZahteva + "/" + data.slobodneSale + "/"+document.getElementById('datumOperacije').value,
		type: "POST",
		contentType: "application/json",
		dataType: "json",
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
	    complete : function(data) {
	    	alert("Operacija uspesno zakazana! Mail obavestenja poslat lekaru i pacijentu.");
	    }

	});
	
}

function setUpUserPageZakP() {
	var obj = JSON.parse(localStorage.getItem('user'));
	$.ajax({
		url: "api/pregledi/zakazani/" + obj.id,
		type: "GET",
		contentType: "application/json",
		dataType: "json",
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
		complete: function(data) {
			pregledi = JSON.parse(data.responseText);
			console.log(pregledi)
			loadZakazaniPregledi(pregledi);
			
		}
	});
}

function loadZakazaniPregledi(pregledi) {
	obrisiTabele();
	obrisiPretragu();
	obrisiFilter();
	$("#tabela_pregledi tbody tr").remove(); 
	$("#tabela_pregledi thead ").remove();
	var table = $("#tabela_pregledi");
	table.append(makeTableHeaderPPZakazani());
	for(let p of pregledi) {
		table.append(makeTableRowPPZakazani(p));
	}
}

function makeTableHeaderPPZakazani(){
	
	var row="";
	 row =
			`<thead class="thead-light" bgcolor="white">
					<tr>
						<th>Datum</th>
						<th>Vreme</th>
						<th>Trajanje</th>
						<th>Sala</th>
						<th>Tip</th>
						<th>Otkazi pregled</th>
					</tr>
				</thead>`;
		
		return row;
}

function makeTableRowPPZakazani(p){
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
			<td class="izgledTabele"><input type="button" id="profilPacijenta" value="Otkazi" onClick="otkaziPregled(${p.idPregleda})"/></td>
		</tr>`;
	
	return row;
}

function otkaziPregled(idPregleda) {
	$.ajax({
		url: "api/pregledi/obrisi/" + idPregleda,
		type: "PUT",
		contentType: "application/json",
		dataType: "json",
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
		complete: function(data) {
			alert("Uspesno otkazan pregled!");
		}
	});
}

function vratiNazad(){
	window.history.back();
}

function izvestajiOPregledima(){
	$("#tabela_pregledi tbody tr").remove(); 
	$("#tabela_pregledi thead ").remove();
	$("#tabela_pregledi").show();
	
	var pac = JSON.parse(localStorage.getItem('idPacijenta'));
	var lek = JSON.parse(localStorage.getItem('user'));
	$.ajax({
		url: "api/pregledi/lekar/izvestaj/" + pac + "/" + lek.id,
		type: "GET",
		contentType: "application/json",
		dataType: "json",
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
		complete: function(data) {
			pregledi = JSON.parse(data.responseText);
			console.log(pregledi)
			loadPreglediLekIzvestaj(pregledi);
		}
	});	
}

function loadPreglediLekIzvestaj(pregledi){

	var table = $("#tabela_pregledi");
	table.append(makeTableHeaderPreglediLIzvestaj());	
	for(let p of pregledi) {
		table.append(makeTableRowPreglediLIzvestaj(p));
}
}

function makeTableHeaderPreglediLIzvestaj(){
	
	var row="";
	 row =
			`<thead class="thead-light" bgcolor="white">
					<tr>
						<th>Datum</th>
						<th>Dijagnoza</th>
						<th>Lek</th>
						<th>Info</th>
						<th>Sacuvaj izmene</th>
				     </tr>
				</thead>`;
		
		return row;
}

function makeTableRowPreglediLIzvestaj(p) {
	var row = "";
	var deli = p.pregled.vreme.split("T");
	var datum = deli[0];
	  row =
		`<tr>
			<td class="izgledTabele" id='${p.pregled.vreme}'>${datum}</td>
			<td class="izgledTabele" id='${p.sifrarnikDijagnoza.nazivDijagnoze}'>${p.sifrarnikDijagnoza.nazivDijagnoze}</td>
			<td class="izgledTabele" id='${p.sifrarnikLekova.nazivLeka}'>${p.sifrarnikLekova.nazivLeka}</td>
			<td class="izgledTabele" id='${p.info}'><input type="text" id="info" value="${p.info}"/></td>
			<td class="izgledTabele"><input type="button" id="zp" value="Sacuvaj" onClick="sacuvajIzmeneInfo(${p.idIOP})"/></td>
		</tr>`;
	
	return row;
}

function sacuvajIzmeneInfo(idIzvestaja){
	var info = document.getElementById('info').value;
	$.ajax({
		url: "api/izvestaj/izmena/" + info + "/" + idIzvestaja,
		type: "PUT",
		contentType: "application/json",
		dataType: "json",
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
		complete: function(data) {
			alert("Uspesno ste izvrsili izmenu!");
		}
	});
}

function izvestajGrafici(){
	obrisiPretragu();
	obrisiFilter();
	obrisiTabele();
	$("#lineChart").show();
	console.log("usao u izvestajgrafici")
	var obj = JSON.parse(localStorage.getItem('user'));
	$.ajax({
		url: "api/pregledi/izvestaj/" + obj.id,
		type: "GET",
		contentType: "application/json",
		dataType: "json",
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
		complete: function(data) {
			lista = JSON.parse(data.responseText);
			console.log(lista)
			podaci(lista);
		}
	});
}

function podaci(lista){
	console.log("usao u podaci")
	console.log(lista)
	const CHART = document.getElementById("lineChart");
	let lineChart = new Chart(CHART, {
    		type: 'line',
    		data: {
            labels: ["1.9.", "2.9.", "3.9.", "4.9.", "5.9.", "6.9.", "7.9.", "8.9.", "9.9.", "10.9.", "11.9.", "12.9.", "13.9.", "14.9.", "15.9.", "16.9.", "17.9.", "18.9.", "19.9.", "20.9.", "21.9.", "22.9.", "23.9.", "24.9.", "25.9.", "26.9.", "27.9.", "28.9.", "29.9.", "30.9."],
            datasets: [
                {
                    label: "Broj pregleda u danu",
                    fill: true,
                    lineTension: 0.1,
                    backgroundColor: "#ffffff",
                    borderColor: "rgba(75, 192, 192, 1)",
                    borderCapStyle: 'butt',
                    borderDash: [],
                    borderDashOffset: 0.0,
                    borderJoinStyle: 'miter',
                    pointBorderColor: "rgba(75,192,192,1)",
                    pointBackgroundColor: "#fff",
                    pointBorderWidth: 1,
                    pointHoverRadius: 5,
                    pointHitRadius: 10,
                    data: lista,
                }
    		]}
	});
}

function izvestajGraficiNedelja(){
	obrisiPretragu();
	obrisiFilter();
	obrisiTabele();
	$("#lineChart").show();
	console.log("usao u izvestajgrafici")
	var obj = JSON.parse(localStorage.getItem('user'));
	$.ajax({
		url: "api/pregledi/izvestajNedelja/" + obj.id,
		type: "GET",
		contentType: "application/json",
		dataType: "json",
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
		complete: function(data) {
			lista = JSON.parse(data.responseText);
			console.log(lista)
			podaciNedelja(lista);
		}
	});
}

function podaciNedelja(lista){
	console.log("usao u podaci")
	console.log(lista)
	const CHART = document.getElementById("lineChart");
	let lineChart = new Chart(CHART, {
    		type: 'line',
    		data: {
            labels: ["31.8. - 6.9.", "7.9. - 13.9.", "14.9. - 20.9.", "21.9. - 27.9", "28.9. - 4.10"],
            datasets: [
                {
                    label: "Broj pregleda u nedelji",
                    fill: true,
                    lineTension: 0.1,
                    backgroundColor: "#ffffff",
                    borderColor: "rgba(75, 192, 192, 1)",
                    borderCapStyle: 'butt',
                    borderDash: [],
                    borderDashOffset: 0.0,
                    borderJoinStyle: 'miter',
                    pointBorderColor: "rgba(75,192,192,1)",
                    pointBackgroundColor: "#fff",
                    pointBorderWidth: 1,
                    pointHoverRadius: 5,
                    pointHitRadius: 10,
                    data: lista,
                }
    		]}
	});
}

function izvestajGraficiMesec(){
	obrisiPretragu();
	obrisiFilter();
	obrisiTabele();
	$("#lineChart").show();
	console.log("usao u izvestajgrafici")
	var obj = JSON.parse(localStorage.getItem('user'));
	$.ajax({
		url: "api/pregledi/izvestajMesec/" + obj.id,
		type: "GET",
		contentType: "application/json",
		dataType: "json",
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
		complete: function(data) {
			lista = JSON.parse(data.responseText);
			console.log(lista)
			podaciMesec(lista);
		}
	});
}

function podaciMesec(lista){
	console.log("usao u podaci")
	console.log(lista)
	const CHART = document.getElementById("lineChart");
	let lineChart = new Chart(CHART, {
    		type: 'line',
    		data: {
            labels: ["Jan", "Feb", "Mart", "Apr", "Maj", "Jun", "Jul", "Avg", "Sept", "Okt", "Nov", "Dec"],
            datasets: [
                {
                    label: "Broj pregleda u mesecu",
                    fill: true,
                    lineTension: 0.1,
                    backgroundColor: "#ffffff",
                    borderColor: "rgba(75, 192, 192, 1)",
                    borderCapStyle: 'butt',
                    borderDash: [],
                    borderDashOffset: 0.0,
                    borderJoinStyle: 'miter',
                    pointBorderColor: "rgba(75,192,192,1)",
                    pointBackgroundColor: "#fff",
                    pointBorderWidth: 1,
                    pointHoverRadius: 5,
                    pointHitRadius: 10,
                    data: lista,
                }
    		]}
	});
}

