<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/stylesheet/main.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined" />
    <title>Borrowing Manager</title>
</head>

<body class="h-screen flex flex-col lg:flex-row justify-between">

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
			<a href="/admin/records" class="block px-4 p-1 rounded-md bg-white text-black">
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
                <input type="text" placeholder="Search for a book..." class="border p-1 w-full rounded-xl">
            </form>
        </search>
    </div>

    <!-- Content -->
    <div class="grow">
        <div class="grow bg-gray-100 h-full p-12">
		<c:if test="${param.error == 'StudentOrBookNotFound'}">
        	<div class="text-red-900 bg-red-200 p-2 rounded-lg mb-2">The entered Student or Book was not found</div>
        </c:if>
            <form method="POST" action="/admin/records/add"
                class="bg-white shadow-md p-6 rounded-lg space-y-4 w-2/3 mx-auto">
                <div class="text-xl font-semibold text-gray-700"> Add a new record</div>
                <hr>
                <c:if test="${error == 'ExpiredReturnDate'}">
				<div class="text-red-900 bg-red-200 p-2 rounded-lg">Return Date is already past the current date</div>
			    </c:if>
                <div class="flex flex-col pt-2">
                    <label>Student Number</label>
                    <input list="users" class="border rounded-md p-1" name="studentNumber" placeholder="Add the student number" required>
                </div>
                <div class="flex flex-col">
                    <label>Book Title</label>
                    <input list="books" class="autocomplete-searchbar border rounded-md p-1" name="bookTitle" placeholder="Add the book title" required>
                	<div class="autocomplete-suggestion rounded-md p-1">
                		<div class="hidden border border-t-0 border-b-0 p-1 cursor-pointer hover:bg-primary hover:text-white"></div>
                	</div>
                </div>
                <div class="flex flex-col">
                    <label>Return Date</label>
                    <input class="border rounded-md p-1" name="returnDate" type="date" placeholder="Add the return date" required>
                </div>
                <div class="flex justify-end">
                    <button type="submit" class="bg-primary text-white px-4 py-1 rounded-md font-semibold">Add</button>
                </div>
            </form>
        </div>
    </div>
    
    <datalist id="books">
		<c:forEach var="book" items="${books}">
			<option value="${book.title}">
		</c:forEach>
	</datalist>
	
	<datalist id="users">
		<c:forEach var="user" items="${users}">
			<option value="${user.referenceNumber}">
		</c:forEach>
	</datalist>


    <div class="py-3 flex justify-around bg-primary text-white rounded-t-xl lg:hidden">
        <a href="/admin/books" class="grow text-center"> <span
                class="material-symbols-outlined !text-4xl">library_books</span>
        </a> <a href="/admin/records" class="grow text-center"> <span
                class="material-symbols-outlined !text-4xl">history</span>
        </a> <a href="/logout" class="grow text-center"> <span
                class="material-symbols-outlined !text-4xl">logout</span>
        </a>
    </div>
</body>

</html>