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
					
					
				   	var data = new FormData($('input[name^="media"]'));     
				   	jQuery.each($('input[name^="media"]')[0].files, function(i, file) {
				   	    data.append("file", file);
				   	});
				   	
				   	data.append('track', new Blob([JSON.stringify({
		                "operation": "notification"           
		            })], {
		                type: "application/json"
		            }));
				   	
				   
					
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
						url: 'account/340570/envelopes/bulk',
				        success: function(result) {
						
				        	
				        	$('#batchInitialStatus').html(result.status);
			            	$('#batchInitialId').html(result.batchId);
				        },
						error: function(result) {
							
						}
				      });
					
				});
				
$("#submitBtn1").click(function(){
					
					
					var batchGuid = $("#batchGuid").val();
					
					
					
					$.ajax({
		                headers: {
		                    'X-DocuSign-Authentication': '{"Username": "barpe061@gmail.com", "Password":"Aquino1115", "IntegratorKey":"16f81d9e-e9ee-408d-bc60-d6e1aecd9756"}'
		                },
		                url: 'account/340570/envelopes/batch/' + batchGuid,
		                type: 'GET',
		                dataType: 'json',
		                success: function (result) {
		             	   $('#batchStatus').html(result.status);

		                }
		            });
					
				});
				
				
$("#batchStatusBtn").click(function(){
	
	
	var batchGuid = $("#batchStatusGuid").val();
	var batchStatus = $("#batchStatusOption").val();
	
	
	
	var urlVal = 'account/340570/envelopes/batch/'+ batchGuid + "/records";
	if(batchStatus === 'both'){
		urlVal = 'account/340570/envelopes/batch/'+ batchGuid + "/records";
	}else{
		urlVal = 'account/340570/envelopes/batch/'+ batchGuid + "/records" + "?status=" + batchStatus;
	}
	
	
	
	$.ajax({
        headers: {
            'X-DocuSign-Authentication': '{"Username": "barpe061@gmail.com", "Password":"Aquino1115", "IntegratorKey":"16f81d9e-e9ee-408d-bc60-d6e1aecd9756"}'
        },
        url: urlVal,
        type: 'GET',
        dataType: 'json',
        success: function (logResult) {
        	
        	
        	var resultData = '';
        	
      	   for(var key in logResult){
      		   
      		   
      		  resultData = resultData + logResult[key].envelopeConcurrentLogPK.envelopeId + logResult[key].envelopeTransactionComments;
      		  
      		 console.log('resultData---- ' + resultData);
      	   }
      	   
      	  $('#batchFetchStatus').html(resultData);

        }
    });
	
});
				
			});
			
			
		</script>
</head>
<body>
	<h1>Bulk Operations</h1>

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
			
			<div>
				<div>
					<div>Batch Status- </div>
					<div id="batchInitialStatus"></div>
				</div>
				 <br/>
				 <br/>
				
				<div>
					<div>Batch Id- </div>
					<div id="batchInitialId"></div>
				</div>
			</div>
			
			<br/>
			<br/>
				 
			
		</div>

	</form>
	
			<div class="section">
  						
			  <form>
			        	<input type="button" value="Search" id="submitBtn1" name="submitBtn1"/>
			            <input type="text" name="batchGuid" id="batchGuid" placeholder="Batch ID..." size="40"/>   
			            
			   </form>     
			    <div id="batchStatus"></div> <div id="batchId"></div>
			  </div>
			  
			  
			  <br/>
			  <br/>
			  
			   <form>
			  <div class="section">
			  
			  
			        	<input type="button" value="Search" id="batchStatusBtn" name="batchStatusBtn"/>
			            <input type="text" name="batchStatusGuid" id="batchStatusGuid" placeholder="Batch ID..." size="40"/>   
			            
			            <div>Select Operations</div>
						<div>
							<select id="batchStatusOption" name="batchStatusOption">
								<option value="Fail">Fail</option>
								<option value="Success">Success</option>
								<option value="Both">Both</option>
							</select>
						</div>
						
						<div id="batchFetchStatus"></div>
			        
			    
			  </div>
			  
			  </form>
</body>
</html>
