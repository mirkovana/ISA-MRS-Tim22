var pretrazeneKlinike;
var filtriraneKlinike;

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

//pozivacemo poacijenti
function setUpUserPageLekar() {
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
			loadPacijenti(pacijenti);
			
		}
	});
}

//pozivacemo sifrarbik lekova
function setUpUserPageAKCSL() {
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
	$("#pretraga").show();
	$("#dan").show();
	$("#mesec").show();
	$("#godina").show();
	$("#pretrazi").show();
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

function setUpKlinikeAK() {
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

//popunjavanje klinike
function loadKlinike(klinike) {
	obrisiTabele();
	$("#tabela_klinike tbody tr").remove(); 
	$("#tabela_klinike thead ").remove();
	var table = $("#tabela_klinike");
	table.append(makeTableHeaderKlinike());	
	for(let k of klinike) {
		table.append(makeTableRowKlinike(k));
	}
}

function loadPacijenti(pacijenti) {
	obrisiTabele();
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

//popunjavanje tabele pregleda
function loadPregledi(pregledi) {
	obrisiTabele();
	obrisiPretragu();
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
		complete: function(data) {}
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
		complete: function(data) {}
	});
}

//popunjavanje tabele operacija
function loadOperacije(operacije) {
	obrisiTabele();
	obrisiPretragu();
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
		`
		    <tr>
			<td class="izgledTabele" >${k.naziv}</td>
			<td class="izgledTabele" >${k.adresa}</td>
			<td class="izgledTabele" >${k.grad}</td>
			<td class="izgledTabele" >${k.opis}</td>
			<td class="izgledTabele" >${k.ocena}</td>
			<td class="izgledTabele" ><input type="button" id="profilKlinike" value="Profil klinike" onClick="prebaciNaKliniku(${k.idKlinike})"/></td>
		    </tr>
		 `
		;
	
	return row;
}

function prebaciNaKliniku(idKlinike) {
	///klinike/id/{id}
	localStorage.setItem('idKlinike', idKlinike);
	window.location.replace("./profilklinike.html");
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
		</tr>`;
	
	return row;
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

function dodavanjePacijenta(){
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
	    },*/
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
	
}

function dodavanjeZahtevaOdsustva(){
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
				$("#uspesno").show();
				$("#neuspesno").hide();
				
			}else{
				window.location.replace("./homepagems.html");
				$("#neuspesno").show();
				$("#uspesno").hide();
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
				window.location.replace("./homepageadminkc.html");
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
				window.location.replace("./homepageadminkc.html");
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
				window.location.replace("./homepageadminkc.html");
				$("#neuspesno").show();
				$("#uspesno").hide();
			}
		} 

	});
	
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
			d = JSON.parse(data.responseText);
			if(d.added) {
				$("#uspesno").show();
				$("#neuspesno").hide();
				
			}else{
				window.location.replace("./homepageadministratorklinike.html");
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
				window.location.replace("./homepageadminkc.html");
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
				window.location.replace("./homepageadminkc.html");
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
					</tr>s
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
					<td  class='align-middle'><label for="brojOsiguranika">Broj osiguranika</label></td>
					<td  class='align-middle'><span style = "color:black;"><input type="text" placeholder="Enter" value="${obj.brojOsiguranika}" name="brojOsiguranika" id="brojOsiguranika" disabled></td>
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

function izmenaProfilaAKC(){
	var tok=JSON.parse(localStorage.getItem('user')).token;
	var aut= JSON.parse(localStorage.getItem('user')).role;
	var dat = getFormData($("#formaIzmena"));
	
	var org = JSON.stringify(dat);
	var obj = JSON.parse(localStorage.getItem('user'));
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
			//localStorage.setItem('user',trenutni);
			//d = JSON.parse(data.responseText);
			//console.log(org);
			//console.log("OVO JE DATA " + JSON.stringify(data).responseJSON);
//			console.log("OVO JE DATA druga varijanta" + JSON.stringify(data.responseJSON));
//			data.responseJSON.token=tok;
//			data.responseJSON.role=aut;
//			localStorage.setItem('user',JSON.stringify(data.responseJSON));
//			//localStorage.setItem('user.token',tok);
//			console.log("ovo je user novi???????" + localStorage.getItem('user') );
			
		} 
	 /*   success: function(result){
			 console.log(result);
			 localStorage.setItem('user',JSON.stringify(result));
			 localStorage.setItem(setItem('user')).token.accessToken, tok);
			 }*/
	});
	
}

function izmenaProfilaKlinike(klinika){

	var dat = getFormData($("#formaIzmena"));
	
	var org = JSON.stringify(dat);
	$.ajax({
		url: "api/klinike/" + klinika.id,
		type: "PUT",
		data: org,
		contentType:"application/json",
		dataType: "json",
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
		complete : function (data) {
			//localStorage.setItem('user',trenutni);
			//d = JSON.parse(data.responseText);
			//console.log(org);
			//console.log("OVO JE DATA " + JSON.stringify(data).responseJSON);
//			console.log("OVO JE DATA druga varijanta" + JSON.stringify(data.responseJSON));
//			data.responseJSON.token=tok;
//			data.responseJSON.role=aut;
//			localStorage.setItem('user',JSON.stringify(data.responseJSON));
//			//localStorage.setItem('user.token',tok);
//			console.log("ovo je user novi???????" + localStorage.getItem('user') );
			
		} 
	 /*   success: function(result){
			 console.log(result);
			 localStorage.setItem('user',JSON.stringify(result));
			 localStorage.setItem(setItem('user')).token.accessToken, tok);
			 }*/
	});
	
}

//ispis termina za unapred definisane preglede 
function prikazSlobodnihTerminaPregleda() {
	obrisiPretragu();
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
						<th></th>
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
			<td ><input type="button" id="showTxt1" value="Zakazi" onClick="zakaziPregled(${p.idPregleda})"/></td>
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
		complete: function(data) {}
	});
}

function izmenaProfilaMS(){
	var tok=JSON.parse(localStorage.getItem('user')).token;
	var aut= JSON.parse(localStorage.getItem('user')).role;
	var dat = getFormData($("#formaIzmena"));
	
	var org = JSON.stringify(dat);
	var obj = JSON.parse(localStorage.getItem('user'));
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
			
			
		} 
	 
	});
	
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
	$("#tabela_recepti tbody tr").remove(); 
	$("#tabela_recepti thead ").remove();
	$("#tabela_lekari tbody tr").remove(); 
	$("#tabela_lekari thead ").remove();
	$("#tabela_lekari_tipPregleda tbody tr").remove(); 
	$("#tabela_lekari_tipPregleda thead ").remove();
	$("#tabela_definisaniPregledi tbody tr").remove(); 
	$("#tabela_definisaniPregledi thead ").remove();
	
}

function obrisiPretragu(){
	$("#pretraga").hide();
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
			
			
		} 
	 
	});
}

function prikazSvihLekaraKlinike(){
	$("#pretragaIme").show();
	$("#pretragaPrezime").show();
	$("#ocenaL").show();
	$("#ocena").show();
	$("#pretraziLekare").show();
	obrisiTabele();
	var obj = JSON.parse(localStorage.getItem('idKlinike'));
	$.ajax({
		url: "api/lekari/klinika/" + obj,
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
	$("#pretraziLekare").show();
	obrisiTabele();
	var obj = JSON.parse(localStorage.getItem('idKlinike'));
	var tipPregleda = localStorage.getItem('tipPregleda');
	$.ajax({
		url: "api/lekari/klinika/" + obj + "/" + tipPregleda,
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
						<th>Ocena</th>
						<th>Prikazi</th>
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
			<td class="izgledTabele">${l.ocena}</td>
			<td class="izgledTabele">Lista</td>
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
						<th>Prikazi</th>
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
			<td class="izgledTabele">Lista</td>
		</tr>`;
	
	return row;
}

function pretragaLekara(){
	var idK = localStorage.getItem('idKlinike');
	console.log(idK);
	var imeLekara = document.getElementById("pretragaIme").value;
	var maloIme = imeLekara.toLowerCase();
	var ime = maloIme.charAt(0).toUpperCase() + maloIme.slice(1);
	var prezimeLekara = document.getElementById("pretragaPrezime").value;
	var maloPrezime = prezimeLekara.toLowerCase();
	var prezime = maloPrezime.charAt(0).toUpperCase() + maloPrezime.slice(1);
	var ocena = document.getElementById("ocena").value;
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
			loadLekariKlinike(lekari);	
		}
	});	
}

function filtrirajNaziKlinike(){
	var nazivKlinike = document.getElementById("filterNaziv").value;
	var naziv2 = nazivKlinike.toUpperCase();
	console.log("NAJNOVIJE " + JSON.stringify(pretrazeneKlinike));
	//var listaPretrazenihKlinika = JSON.stringify(pretrazeneKlinike);
	//pocetno veliko ostalo mala
	/*for(let k of pretrazeneKlinike) {
		var naziv1 = k.naziv.totoUpperCase();
		if(naziv1.localeCompare(naziv2)){
			filtriraneKlinike
		}
	}*/
	//loadKlinikePretrazene(filtriraneKlinike);
	/*$.ajax({
		url: "api/klinike/filterNaziv/" + nazivKlinike + "/" + listaPretrazenihKlinika,
		type: "GET",
		contentType: "application/json",
		dataType: "json",
		headers: {
	        'Authorization': 'Bearer '+JSON.parse(localStorage.getItem('user')).token.accessToken
	    },
		complete: function(data) {
			klinike = JSON.parse(data.responseText);
			console.log(klinike)
			loadKlinikePretrazene(klinike);
		}
	});	*/
}