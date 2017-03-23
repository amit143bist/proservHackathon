<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<!DOCTYPE html>
<html>
<head>
<title>Home</title>
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.0/jquery.min.js"></script>
	
<script>
			$(document).ready(function(){
				
				$("#submitBtn").click(function(){
					
					alert('Button clicked');
					
				   	
				   	var data = new FormData($('input[name^="media"]'));     
				   	jQuery.each($('input[name^="media"]')[0].files, function(i, file) {
				   	    data.append("file", file);
				   	});
				   	
				   	data.append('track', new Blob([JSON.stringify({
		                "operation": "notification"           
		            })], {
		                type: "application/json"
		            }));
				   	
				   	alert('formData- ' + data);
					
					$.ajax({
						type : "POST",
						processData: false,
						dataType: 'json',
						data: data,
						cache: false,
						contentType: false,
						headers: {
			                'X-DocuSign-Authentication': '<DocuSignCredentials><Username>amitkumar.bist+test@gmail.com</Username><Password>testing1</Password><IntegratorKey>DOCU-e29abe61-0bd8-4e89-b4f9-50922b8c5b90</IntegratorKey></DocuSignCredentials>'
			            },
						url: 'account/1764240/envelopes/bulk',
				        success: function(respData) {
						
				        	alert('success- ' + respData);
				        },
						error: function(respData) {
							alert('error- ' + respData);
						}
				      });
					
				});
				
			});
		</script>
</head>
<body>
	<h1>Hello world!</h1>

	<P>The time on the server is ${serverTime}.</P>

	<form name="csvNotification" enctype="multipart/form-data">
		<div>
			<div>Pick CSV</div>
			
			<fieldset>
    			
    			<input name="media[]" type="file" multiple/>
  			</fieldset>

			<div>Select Operations</div>
			<div>
				<select>
					<option value="Notification">Notification</option>
					<option value="Void">Void</option>
					<option value="Custom Fields">Custom Fields</option>
				</select>
			</div>
			
			<div>
				<input type="button" id="submitBtn" name="submitBtn" value="Submit CSV">
			</div>
		</div>

	</form>
</body>
</html>
