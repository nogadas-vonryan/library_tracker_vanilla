<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List" %>
<%@ page import="services.Auth" %>
<%@ page import="models.Book" %>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="../stylesheet/main.css">
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
				<img src="/library_tracker_vanilla/assets/open-book.png" alt="book Logo"
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
			<div class="px-4 p-1 rounded-md hover:bg-white hover:text-black">
				<span class="material-symbols-outlined translate-y-[6px]">monitoring</span>
				<a href="/library_tracker/admin/analytics" class="font-medium ml-2">Analytics</a>
			</div>
		</div>
		<div>
			<hr>
			<div class="px-4 p-1 mt-2 rounded-md hover:bg-white hover:text-black">
				<span class="material-symbols-outlined translate-y-[6px]">logout</span>
				<form class="inline" method="POST" action="/logout">
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

	<!-- Content -->
	<div class="grow overflow-auto bg-neutral-100">
		
		<!-- Desktop Search -->
		<div class="hidden lg:flex pl-4 pt-8 pb-2 space-x-4">
			<div class="w-80 relative">
				<form method="GET" action="/library_tracker/admin/books" class="inline">
					<input class="autocomplete-searchbar border rounded-md p-1 w-full" name="search" placeholder=" Search for a book..."> 
					<span class="material-symbols-outlined text-neutral-400 absolute right-0 -translate-x-[4px] translate-y-[3px] z-10">search</span>
				</form>
			</div>
			<form method="GET" action="/library_tracker/admin/books" >
				<button class="bg-gray-300 text-gray-800 px-4 py-1 rounded-md">Clear Search</button>
			</form>
			<a href="/library_tracker/admin/books/add" class="bg-primary text-white px-4 py-1 rounded-md">Add Book</a>
		</div>

		<div class="grow grid grid-cols-3 lg:grid-cols-5 gap-4 p-4 text-stone-700 overflow-auto">
	        <c:forEach var="book" items="${books}">
	        <div class="bg-white flex flex-col justify-center items-center h-40 lg:h-64 lg:w-52 shadow-md">
				
				
				<c:if test="${not empty book.imageUrl}">
				<a href="/library_tracker/admin/books/${book.id}" class="h-full">
					<img src="/library_tracker_vanilla/uploads/${book.imageUrl}" class="h-full">
				</a>
				</c:if>
				
				<c:if test="${empty book.imageUrl}">
				<a href="/library_tracker/admin/books/${book.id}" class="flex justify-center items-center h-full w-full" >
					<span class="block align-middle font-semibold"> ${book.title} </span>
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
