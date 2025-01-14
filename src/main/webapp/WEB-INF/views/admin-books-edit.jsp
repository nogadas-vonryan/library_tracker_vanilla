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
			<a href="/admin/books" class="block px-4 p-1 rounded-md bg-white text-black">
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
			<form class="inline-block w-full mt-2" method="POST" action="/logout">
				<button type="submit" class="inline-block text-left font-medium w-full p-1 pl-4 rounded-md hover:bg-white hover:text-black">
					<span class="material-symbols-outlined translate-y-[6px] mr-2">logout</span>
					<span>Logout</span>
				</button>
			</form>
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
		<form id="methodForm" method="POST" action="/admin/books/${book.id}" enctype="multipart/form-data" class="bg-white shadow-md p-6 rounded-lg space-y-4 w-2/3 mx-auto">
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
				<input type="file" value="/uploads/${book.imageUrl}" name="bookCover">
			</div>
			<div class="flex justify-between">
				<input id="methodInput" type="hidden" name="_method" />
				<input type="hidden" name="id" value="${book.id}" />
				
				<div class="flex">
					<button type="button" onclick="submitForm('DELETE')" class="bg-primary text-white px-4 py-1 rounded-md font-semibold">Delete</button>
				</div>
				<div class="flex">
					<button type="button" onclick="submitForm('PUT')" class="bg-green-900 text-white px-4 py-1 rounded-md font-semibold">Update</button>
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