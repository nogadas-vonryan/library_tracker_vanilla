<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
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

	<!-- Content -->
	<div class="grow overflow-auto bg-neutral-100">
		
		<!-- Desktop Search -->
		<div class="hidden lg:flex pl-4 pt-8 pb-2 space-x-4">
			<div class="w-80 relative">
				<form method="GET" action="/admin/books" class="inline">
					<input class="autocomplete-searchbar border rounded-md p-1 w-full" name="search" placeholder=" Search for a book..."> 
					<span class="material-symbols-outlined text-neutral-400 absolute right-0 -translate-x-[4px] translate-y-[3px] z-10">search</span>
				</form>
			</div>
			<form method="GET" action="/admin/books" >
				<button class="bg-gray-300 text-gray-800 px-4 py-1 rounded-md">Clear Search</button>
			</form>
			<a href="/admin/books/add" class="bg-primary text-white px-4 py-1 rounded-md">Add Book</a>
		</div>

		<div class="grow grid grid-cols-3 lg:grid-cols-5 gap-4 p-4 text-stone-700 overflow-auto">
	        <c:forEach var="book" items="${books}">
	        <div class="bg-white flex flex-col justify-center items-center h-40 lg:h-64 lg:w-52 shadow-md">
				
				
				<c:if test="${not empty book.imageUrl}">
				<a href="/admin/books/${book.id}" class="w-full h-full">
					<img src="/uploads/${book.imageUrl}" class="w-full h-full">
				</a>
				</c:if>
				
				<c:if test="${empty book.imageUrl}">
				<a href="/admin/books/${book.id}" class="flex justify-center items-center h-full w-full p-2" >
					<span class="block align-middle font-semibold overflow-hidden overflow-ellipsis"> ${book.title} </span>
				</a>
				</c:if>
				 
			</div>
			</c:forEach>
		</div>
	</div>

	<div
		class="py-3 flex justify-around bg-primary text-white rounded-t-xl lg:hidden">
		<a href="./book-list.html" class="grow text-center"> <span
			class="material-symbols-outlined !text-4xl">library_books</span>
		</a> <a href="./user-history.html" class="grow text-center"> <span
			class="material-symbols-outlined !text-4xl">history</span>
		</a> <a href="./logout.html" class="grow text-center"> <span
			class="material-symbols-outlined !text-4xl">logout</span>
		</a>
	</div>
</body>
</html>
