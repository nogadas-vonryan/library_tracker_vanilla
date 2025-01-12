<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Change Password</title>
    <link rel="stylesheet" href="/stylesheet/main.css">
</head>

<body class="h-svh background">
    <div class="p-4 pt-16 lg:w-1/3 m-auto">
        <div class="text-stone-700 text-2xl font-medium mb-4">Changing Password of <span class="block font-semibold">${request.user.referenceNumber}</span></div>
        <c:if test="${param.error == 'PasswordMismatch'}">
        <div class="text-red-900 bg-red-200 p-2 rounded-lg mb-2">The password do not match</div>
        </c:if>
        
        <form method="POST" action="/admin/password-requests/process">
        <div class="flex flex-col items-center">
            <div class="w-full space-y-4">
                <div>
                    <div class="text-stone-700">New Password</div>
                    <input class="p-1 rounded-md border w-full" name="newPassword" type="password" placeholder="Enter new password">
                </div>
                <div>
                    <div class="text-stone-700">Confirm Password</div>
                    <input class="p-1 rounded-md border w-full" name="confirmPassword" type="password" placeholder="Confirm password">
                </div>
            </div>

            <div class="w-full flex justify-end">
                <input type="hidden" name="referenceNumber" value="${request.user.referenceNumber}" />
                <button type="submit" class="bg-primary font-semibold text-white p-2 rounded-lg w-24 mt-4">Change</button>
            </div>
        </div>
        </form>
    </div>
</body>

</html>