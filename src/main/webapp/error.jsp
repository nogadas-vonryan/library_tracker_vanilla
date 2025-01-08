<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/library_tracker_vanilla/stylesheet/main.css">
    <title>Error</title>
</head>
<body class="flex justify-center p-16">
    <div class="text-center">
    	<div>
    		<img class="m-auto w-[300px] h-[300px]" src="/library_tracker_vanilla/assets/error_icon.jpeg" />
			<div class="text-6xl font-bold text-center pb-4">Error</div>
    	</div>
	
		<div>
			<div class="text-lg pb-2">There seems to be an error on our end.</div>
			<div class="text-lg">Please contact the administrator as soon as possible.</div>
		</div>
	    <div class="">Error <%= response.getStatus() %></div>
	    
	    <% if(request.getAttribute("javax.servlet.error.status_code") != null) { %>
		    <p><b>Error Code:</b> <%= request.getAttribute("javax.servlet.error.status_code") %></p>
		    <p><b>Message:</b> <%= request.getAttribute("javax.servlet.error.message") %></p>
		    <p><b>URI:</b> <%= request.getAttribute("javax.servlet.error.request_uri") %></p>
		<% } %>
    </div>
    
</body>
</html>
