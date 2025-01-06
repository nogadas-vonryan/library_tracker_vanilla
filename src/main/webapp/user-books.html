<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="../../stylesheet/main.css">
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined" />
<title>Book List</title>
</head>
<body class="h-screen flex flex-col lg:flex-row justify-between box-sizing">

	<!--  Desktop Sidebar -->
	<div
		class="lg:flex flex-col hidden lg:block justify-between p-3 bg-primary text-white h-svh w-[15rem]">
		<div class="grow space-y-3">
			<div class="mt-2">
				<img src="../../assets/open-book.png" alt="book Logo"
					class="w-[60px] h-[60px] m-auto">
				<div class="text-center pt-4 text-sm">User Portal</div>
			</div>
			<hr>
			<div class="px-4 p-1 rounded-md hover:bg-white hover:text-black">
				<span class="material-symbols-outlined translate-y-[6px]">library_books</span>
				<a href="/user/books" class="font-medium ml-2">Books</a>
			</div>
			<div class="px-4 p-1 rounded-md hover:bg-white hover:text-black">
				<span class="material-symbols-outlined translate-y-[6px]">history</span>
				<a href="/user/records" class="font-medium ml-2">Records</a>
			</div>
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

	<!-- Content -->
	<div class="grow overflow-auto bg-neutral-100">
		
		<!-- Desktop Search -->
		<!--
		<div class="hidden lg:block lg:flex flex-col items-center p-4">
			<div class="flex justify-center w-full">
				<input class="autocomplete-searchbar border rounded-md p-1" placeholder=" Search for a book..."> 
				<span class="material-symbols-outlined text-neutral-400 -translate-x-[30px] translate-y-[6px]">search</span>
			</div>
			<div class="flex justify-center w-full">
				<div class="autocomplete-suggestion rounded-md p-1 absolute z-10">
                	<div class="hidden bg-white border border-t-0 border-b-0 p-1 cursor-pointer hover:bg-primary hover:text-white"></div>
            	</div>
			</div>
		</div> 
		-->
		<div class="hidden lg:flex justify-center pt-4">
			<div class="w-80 relative">
				<form method="GET" action="/user/books" class="inline">
					<input class="autocomplete-searchbar border rounded-md p-1 w-full" name="search" placeholder=" Search for a book..."> 
					<span class="material-symbols-outlined text-neutral-400 absolute right-0 -translate-x-[4px] translate-y-[3px] z-10">search</span>
				</form>
			</div>
		</div>

		<div
			class="grow grid grid-cols-3 lg:grid-cols-5 gap-4 p-4 text-stone-700 overflow-auto">
	        <div th:each="book : ${books}" class="bg-white flex flex-col justify-center items-center h-40 lg:h-64 lg:w-52 shadow-md">
				<a th:href="'/user/books/' + ${book.id}" th:if="${book.imageUrl}" class="h-full">
					<img th:src="@{'/uploads/' + ${book.imageUrl}}" class="h-full">
				</a>
				<a th:href="'/user/books/' + ${book.id}" th:unless="${book.imageUrl}" class="flex justify-center items-center h-full w-full" >
                    <span class="block align-middle font-semibold" th:text="${book.title}"></span>
				</a>
			</div>
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
	<script src="../../js/books.js"></script>
	<script src="../../js/autocomplete.js"></script>
</body>
</html>
