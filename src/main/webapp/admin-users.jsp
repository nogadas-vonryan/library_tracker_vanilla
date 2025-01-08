<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../../stylesheet/main.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined" />
    <title>User Manager</title>
</head>
<body class="h-screen flex flex-col justify-between">
    <div class="p-4 bg-primary">
        <div class="text-white font-bold text-2xl pb-2">User Manager</div>
        <search>
            <form action="">
                <input type="text" placeholder="Search for a book..." class="border p-1 w-full rounded-xl">
            </form>
        </search>
    </div>

    <div class="grow overflow-auto p-2">
        <div class="flex justify-between border p-2 mb-2 rounded-md">
            Von Ryan Nogadas <span>2022-00197-TG-0</span>
        </div>
    </div>

    <div class="py-3 flex justify-around bg-primary text-white">
        <a href="./book-manager.html" class="grow text-center">
            <span class="material-symbols-outlined !text-4xl">library_books</span>
        </a>
        <a href="./borrowing-manager.html" class="grow text-center">
            <span class="material-symbols-outlined !text-4xl">local_library</span>
        </a>
        <a href="./user-manager.html" class="grow text-center">
            <span class="material-symbols-outlined !text-4xl">manage_accounts</span>
        </a>
        <a href="./logout.html" class="grow text-center">
            <span class="material-symbols-outlined !text-4xl">logout</span>
        </a>
    </div>
</body>
</html>