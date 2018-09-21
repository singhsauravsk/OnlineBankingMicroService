/**
 * Javascript for server-side processes.
 */

/** To submit login form */
var cardObject;

$("#login-form").submit(function(event) {
	event.preventDefault();
	
	var userObject = {
			username : $("#username").val(),
			password : $("#password").val()
	};
	
	var userObjectJson = JSON.stringify(userObject);
	
	var request = $.ajax({
		url : "/login",
		method : "POST",
		data : userObjectJson,
		contentType : "application/json",
		success:function(data) {
			
			if(data == "success") {
					window.location="/home"
			}
			else {
				
				alert("Login failed.\nUsername or password is worng.");
			}},
		error:function(jqXHR,textStatus){console.log(textStatus);}
	})
})
			
$("#create-account-form").submit(function(event) {
	event.preventDefault();
    
	var accountObject = {
			holderName : $("#holderName").val(),
			accountType : $("#accountType").val()
	};
	
	if(accountObject.accountType.toUpperCase() == "CURRENT" || accountObject.accountType.toUpperCase() == "SAVING") {
		var accountObjectJson = JSON.stringify(accountObject);
		
		var request = $.ajax({
			url : "/account/add",
			method : "POST",
			data : accountObjectJson,
			contentType : "application/json",
			
			success:function(data) {
				
				if (data == "success") {
					window.location.reload();
				}
				else {
					alert("Account creation failed");
				}},
			error:function(jqXHR,textStatus){console.log(textStatus);}
		})
	}
	else {
		alert("Account type is not correct");
	}
})


$('.add-card-form').on('submit', function(event){
	$('#addCardModal').modal('show');
	event.preventDefault();
	
	cardObject = {
		foreignKey : $(this).find("input")[0].value
	};
});

$("#add-card-form-modal").submit(function(event) {
	event.preventDefault();
    
	cardObject.cardType = $("#cardType").val();
	cardObject.maximumLimit = $("#maximumLimit").val();
	
	if(cardObject.cardType.toUpperCase() == "DEBIT" || cardObject.cardType.toUpperCase() == "CREDIT") {
		var cardObjectJson = JSON.stringify(cardObject);
		
		var request = $.ajax({
			url : "/card/add",
			method : "POST",
			data : cardObjectJson,
			contentType : "application/json",
			
			success:function(data) {
				
				if (data == "success") {
					window.location.reload();
				}
				else {
					alert("Card creation failed");
				}},
			error:function(jqXHR,textStatus){console.log(textStatus);}
		})
	}
	else {
		alert("Card type is not correct");
	}
	cardObject = null;
})

$('.view-card-form').on('submit', function(event) {
	event.preventDefault();
	
	var cardDetail = {
		foreignKey : $(this).find("input")[0].value
	};
	var cardObjectJson = JSON.stringify(cardDetail);
	
	var request = $.ajax({
		url : "/card/view",
		method : "POST",
		data : cardObjectJson,
		contentType : "application/json",
		
		success:function(data) {
			
			if(data == "success") {
				window.location = "/listCard";
			}
			else {
				alert("Card Loading Failed! Please Try Again Later.");
			}},
		error:function(jqXHR,textStatus){console.log(textStatus);}
	})
});