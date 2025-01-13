<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register</title>
    <link rel="stylesheet" href="stylesheet/main.css">
</head>

<body class="h-svh background">
    <div class="flex flex-col justify-center items-center pt-4">
        <img src="assets/pup-logo.png" class="w-[120px] h-[120px] mb-4" alt="PUP Logo">
        <div class="font-bold text-2xl text-primary">LibLib: Book Tracker</div>
    </div>

    <div class="p-4 pt-8 lg:w-1/3 m-auto">
        <div class="text-stone-700 text-2xl font-semibold mb-4">Register</div>
        
        <c:if test="${param.error == 'InvalidReferenceNumber'}">
        	<div class="text-red-900 bg-red-200 p-2 rounded-lg mb-2">Reference Number must be of 2XXX-XXXXX-XX-0 format</div>
        </c:if>
        
        <c:if test="${param.error == 'InvalidPassword'}">
        	<div class="text-red-900 bg-red-200 p-2 rounded-lg mb-2">Password must be 8 characters long, no special characters, and has an upper and lowercase character</div>
        </c:if>
        
        <c:if test="${param.error == 'PasswordMismatch'}">
        	<div class="text-red-900 bg-red-200 p-2 rounded-lg mb-2">Password does not match</div>
        </c:if>
        
        <form method="POST" action="/register">
        <div class="flex flex-col items-center">
            <div class="w-full space-y-4">
                <div class="w-full mb-4">
                    <div class="text-stone-700">Student Number</div>
                    <input class="p-1 rounded-md border w-full" type="text" name="referenceNumber" placeholder="Enter your student number..." required>
                </div>
                <div class="w-full mb-4">
                    <div class="text-stone-700">First Name</div>
                    <input class="p-1 rounded-md border w-full" type="text" name="firstName" placeholder="Enter your first name..." required>
                </div>
                <div class="w-full mb-4">
                    <div class="text-stone-700">Last Name</div>
                    <input class="p-1 rounded-md border w-full" type="text" name="lastName" placeholder="Enter your last name..." required>
                </div>
                <div class="w-full mb-4">
                    <div class="text-stone-700">Password</div>
                    <input class="p-1 rounded-md border w-full" type="password" name="password" placeholder="Enter your password..." required>
                </div>
                <div class="w-full mb-4">
                    <div class="text-stone-700">Confirm Password</div>
                    <input class="p-1 rounded-md border w-full" type="password" name="confirmPassword" placeholder="Enter your password..." required>
                </div>
            </div>

            <div class="w-full flex justify-end">
                <button type="submit" class="bg-primary font-semibold text-white p-2 rounded-2xl w-24 mt-4">Register</button>
            </div>
        </div>
        </form>

        <div class="pt-4 text-center">
            <div class="text-stone-700">Already have an account? <a href="/login" class="font-bold">Login now!</a></div>
        </div>
    </div>
</body>

</html>