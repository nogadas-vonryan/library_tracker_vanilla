<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" href="/library_tracker/stylesheet/main.css">
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
				<img src="/library_tracker/assets/open-book.png" alt="book Logo"
					class="w-[60px] h-[60px] m-auto">
				<div class="text-center pt-4 text-sm">Admin Portal</div>
			</div>
			<hr>
			<div class="px-4 p-1 rounded-md hover:bg-white hover:text-black">
				<span class="material-symbols-outlined translate-y-[6px]">library_books</span>
				<a href="/library_tracker/admin/books" class="font-medium ml-2">Books</a>
			</div>
			<div class="px-4 p-1 rounded-md hover:bg-white hover:text-black">
				<span class="material-symbols-outlined translate-y-[6px]">history</span>
				<a href="/library_tracker/admin/records" class="font-medium ml-2">Records</a>
			</div>
		</div>
		<div>
			<hr>
			<div class="px-4 p-1 mt-2 rounded-md hover:bg-white hover:text-black">
				<span class="material-symbols-outlined translate-y-[6px]">logout</span>
				<form class="inline" method="POST" action="/library_tracker/logout">
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
		<form id="methodForm" method="POST" action="/library_tracker/admin/books/${book.id}" enctype="multipart/form-data" class="bg-white shadow-md p-6 rounded-lg space-y-4 w-2/3 mx-auto">
			<div class="text-xl font-semibold text-gray-700"> Update existing book</div>
			<hr>
			<div class="flex flex-col pt-2">
				<label>Title</label>
				<input class="border rounded-md p-1" value="${book.title}" name="title" placeholder="Add the book name" required>
			</div>
			<div class="flex flex-col">
				<label>Author</label>
				<input class="border rounded-md p-1" value="${book.author}" name="author" placeholder="Add the book author" required>
			</div>
			<div class="flex flex-col">
				<label>Category</label>
				<input class="border rounded-md p-1" value="${book.category}" name="category" placeholder="Add a book name" required>
			</div>
			<div class="flex flex-col">
				<label>Book Cover</label>
				<input type="file" value="/library_tracker/uploads/${book.imageUrl}" name="bookCover">
			</div>
			<div class="flex justify-between">
				<input id="methodInput" type="hidden" name="_method" />
				<input type="hidden" name="id" value="${book.id}" />
				
				<div class="flex">
					<button type="button" onclick="submitForm('DELETE')" class="bg-primary text-white px-4 py-1 rounded-md font-semibold">Delete</button>
				</div>
				<div class="flex">
					<button type="button" onclick="submitForm('UPDATE')" class="bg-green-900 text-white px-4 py-1 rounded-md font-semibold">Update</button>
				</div>
			</div>
		</form>
	</div>
	<script>
		function submitForm(method) {
			event.preventDefault();
	        document.getElementById("methodInput").value = method;
	        document.getElementById("methodForm").submit();
	    }
	</script>
</body>
</html>