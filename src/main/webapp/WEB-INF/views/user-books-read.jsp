<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
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
				<div class="text-center pt-4 text-sm">User Portal</div>
			</div>
			<hr>
			<a href="/user/books" class="block px-4 p-1 rounded-md bg-white text-black">
				<span class="material-symbols-outlined translate-y-[6px]">library_books</span>
				<span class="inline p-2 font-medium">Books</span>
			</a>	
			<a href="/user/records" class="block px-4 p-1 rounded-md hover:bg-white hover:text-black">
				<span class="material-symbols-outlined translate-y-[6px]">monitoring</span>
				<span class="inline p-2 font-medium">Records</span>
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
	
	<div class="grow bg-gray-100 h-full p-12 overflow-auto">
		<div class="bg-white shadow-md p-6 rounded-lg space-y-4 w-2/3 mx-auto">
			<div class="text-xl font-semibold text-gray-700">Book Details</div>
			<hr>
			<div class="flex justify-center">
				<c:if test="${not empty book.imageUrl}">
				<a href="#" class="lg:h-64 lg:w-52">
					<img src="/uploads/${book.imageUrl}" class="h-full w-full">
				</a>
				</c:if>
				
				<c:if test="${empty book.imageUrl}">
				<a href="#" class="book-cover text-white flex justify-center items-center lg:h-64 lg:w-52 p-2">
					<span class="block align-middle font-semibold overflow-hidden overflow-ellipsis"> ${book.title} </span>
				</a>
				</c:if>
			</div>
			<div class="flex flex-col pt-2">
				<label class="font-semibold">Title</label>
				<input class="bg-slate-100 border rounded-md p-1" value="${book.title}" name="title" placeholder="No book name" disabled readonly>
			</div>
			<div class="flex flex-col">
				<label class="font-semibold">Author</label>
				<input class="bg-slate-100 border rounded-md p-1" value="${book.author}" name="author" placeholder="No author" disabled readonly>
			</div>
			<div class="flex flex-col">
				<label class="font-semibold">Category</label>
				<input class="bg-slate-100 border rounded-md p-1" value="${book.category}" name="category" placeholder="No category" disabled required>
			</div>
		</div>
	</div>
	
	<script>
		const colorList = [
			"bg-red-800",
	        "bg-yellow-800",
	        "bg-green-800",
	        "bg-blue-800",
	        "bg-indigo-800",
	        "bg-purple-800",
	        "bg-pink-800",
	        "bg-amber-800",
	    ];
	
	    function assignRandomColor(element) {
	        const randomColor = colorList[Math.floor(Math.random() * colorList.length)];
	        element.classList.add(randomColor);
	    }
	
	    document.addEventListener("DOMContentLoaded", () => {
	        const element = document.querySelector(".book-cover");
	        if (element) {
	            assignRandomColor(element);
	        }
	    });
	</script>
</body>
</html>