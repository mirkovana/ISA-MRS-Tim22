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
