<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <link rel="stylesheet" href="stylesheet/main.css">
</head>

<body class="h-svh background">
    <div class="flex flex-col justify-center items-center pt-10">
        <img src="assets/pup-logo.png" class="w-[120px] h-[120px] mb-4" alt="PUP Logo">
        <div class="font-bold text-2xl text-primary">LibLib: Book Tracker</div>
    </div>

    <div class="p-4 pt-16 lg:w-1/3 m-auto">
        <div class="text-stone-700 text-2xl font-semibold mb-4">Login</div>
        
        <c:if test="${param.error == 'InvalidCredentials'}">
        	<div class="text-red-900 bg-red-200 p-2 rounded-lg mb-2">Incorrect reference number or password</div>
        </c:if>
        
        <c:if test="${param.error == 'InvalidRole'}">
        	<div class="text-red-900 bg-red-200 p-2 rounded-lg mb-2">No Role found</div>
        </c:if>
        
        <c:if test="${param.success == 'PasswordRequestSent'}">
        	<div class="text-green-900 bg-green-200 p-2 rounded-lg mb-2">Change Password Request Sent</div>
        </c:if>
        
        <form method="POST" action="login">
        <div class="flex flex-col items-center">
            <div class="w-full space-y-4">
                <div>
                    <div class="text-stone-700">Reference Number</div>
                    <input class="p-1 rounded-md border w-full" name="referenceNumber" type="text" placeholder="Enter your username...">
                </div>
                <div class="w-full mb-4">
                    <div class="text-stone-700">Password</div>
                    <input class="p-1 rounded-md border w-full" name="password" type="password" placeholder="Enter your password...">
                    <a href="/change-password-request.jsp" class="text-stone-400 text-sm pl-2">Forgot your password?</a>
                </div>
            </div>

            <div class="w-full flex justify-end">
                <button type="submit" class="bg-primary font-semibold text-white p-2 rounded-lg w-24 mt-4">Login</button>
            </div>
        </div>
        </form>

        <div class="pt-10 text-center">
            <div class="text-stone-700">Don't have an account? <a href="/register" class="font-bold">Register now!</a></div>
        </div>
    </div>
</body>

</html>