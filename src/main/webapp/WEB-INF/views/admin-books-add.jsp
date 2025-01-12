<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" href="/stylesheet/main.css">
	<link rel="stylesheet"
		href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined" />
	<title>Book List</title>
</head>
<body class="h-screen flex flex-col lg:flex-row justify-between">
	<!--  Desktop Sidebar -->
	<div
		class="lg:flex flex-col hidden lg:block justify-between p-3 bg-primary text-white h-svh w-[15rem]">
		<div class="grow space-y-3">
			<div class="mt-2">
				<img src="/assets/open-book.png" alt="book Logo"
					class="w-[60px] h-[60px] m-auto">
				<div class="text-center pt-4 text-sm">Admin Portal</div>
			</div>
			<hr>
			<a href="/admin/books" class="block px-4 p-1 rounded-md hover:bg-white hover:text-black">
				<span class="material-symbols-outlined translate-y-[6px]">library_books</span>
				<span class="inline p-2 font-medium">Books</span>
			</a>
			<a href="/admin/records" class="block px-4 p-1 rounded-md hover:bg-white hover:text-black">
				<span class="material-symbols-outlined translate-y-[6px]">history</span>
				<span class="inline p-2 font-medium">Records</span>
			</a>
			<a href="/admin/analytics" class="block px-4 p-1 rounded-md hover:bg-white hover:text-black">
				<span class="material-symbols-outlined translate-y-[6px]">monitoring</span>
				<span class="inline p-2 font-medium">Analytics</span>
			</a>
			<a href="/admin/password-requests" class="block px-4 p-1 rounded-md hover:bg-white hover:text-black">
				<span class="material-symbols-outlined translate-y-[6px]">key</span>
				<span class="inline p-2 font-medium">Password Request</span>
			</a>
		</div>
		<div>
			<hr>
			<div class="px-4 p-1 mt-2 rounded-md hover:bg-white hover:text-black">
				<span class="material-symbols-outlined translate-y-[6px]">logout</span>
				<form class="inline" method="POST" action="/logout">
					<input type="hidden" name="_csrf" th:value="${_csrf.token}" />
					<button type="submit" class="inline font-medium ml-2">Logout</button>
				</form>
			</div>
		</div>
	</div>
	

	<!-- Mobile Navbar -->
	<div class="p-4 bg-primary rounded-b-xl lg:hidden">
		<div class="text-white font-bold text-2xl pb-2">Book List</div>
		<search>
		<form action="">
			<input type="text" placeholder="Search for a book..."
				class="border p-1 w-full rounded-xl">
		</form>
		</search>
	</div>
	
	<div class="grow bg-gray-100 h-full p-12">
		<form method="POST" action="/admin/books/add" enctype="multipart/form-data" class="bg-white shadow-md p-6 rounded-lg space-y-4 w-2/3 mx-auto">
			<div class="text-xl font-semibold text-gray-700"> Add a new book</div>
			<hr>
			<div class="flex flex-col pt-2">
				<label>Title</label>
				<input class="border rounded-md p-1" name="title" placeholder="Add the book name" required>
			</div>
			<div class="flex flex-col">
				<label>Author</label>
				<input class="border rounded-md p-1" name="author" placeholder="Add the book author" required>
			</div>
			<div class="flex flex-col">
				<label>Category</label>
				<input class="border rounded-md p-1" name="category" placeholder="Add a book name" required>
			</div>
			<div class="flex flex-col">
				<label>Book Cover</label>
				<input type="file" name="bookCover">
			</div>
			<div class="flex justify-end">
				<button type="submit" class="bg-primary text-white px-4 py-1 rounded-md font-semibold">Add</button>
			</div>
		</form>
	</div>
</body>
</html>