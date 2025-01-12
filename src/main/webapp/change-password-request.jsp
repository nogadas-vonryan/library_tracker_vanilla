<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Change Password Request</title>
    <link rel="stylesheet" href="/stylesheet/main.css">
</head>

<body class="h-svh background">
    <div class="p-4 pt-16 lg:w-1/3 m-auto">
        <div class="text-stone-700 text-2xl font-semibold mb-4">Change Password Request</div>
        <form method="POST" action="/admin/password-requests">
        <div class="flex flex-col items-center">
            <div class="w-full space-y-4">
                <div>
                    <div class="text-stone-700">Reference Number</div>
                    <input class="p-1 rounded-md border w-full" name="referenceNumber" type="text" placeholder="Enter your username...">
                </div>
            </div>

            <div class="w-full flex justify-end">
                <input type="hidden" name="_method" value="POST" />
                <button type="submit" class="bg-primary font-semibold text-white p-2 rounded-lg w-24 mt-4">Request</button>
            </div>
        </div>
        </form>
    </div>
</body>

</html>