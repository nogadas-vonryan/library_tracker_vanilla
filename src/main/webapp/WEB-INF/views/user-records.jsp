<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="services.RecordService" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/stylesheet/main.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined" />
    <title>Borrowing Manager</title>
    <style>
		dialog::backdrop {
			
			background-color: black;
			opacity: 0.75;
		}
</style>
</head>

<body class="h-screen flex flex-col lg:flex-row justify-between">

    <c:if test="${RecordService.isNearDueOrExpired(records)}">
	<dialog class="p-6 pt-4 rounded-lg" id="dialog">
		<div>
			<div class="flex justify-end"><button class="mb-2 btn btn-sm" onclick="dialog.close()">Close</button></div>
			<div role="alert" class="alert flex flex-col">
				<div class="font-semibold text-lg">Return the books before their return date.</div>
				<div class="text-sm"> Please return them by their respective due dates to avoid any penalties. Thank you!</div>
			</div>
			<div class="p-4">
				<c:forEach var="record" items="${records}">
					<c:if test="${RecordService.daysLeftBeforeExpiry(record) <= 7 && RecordService.daysLeftBeforeExpiry(record) >= 0}"> 
					<div>
						<span class="font-medium"> ${record.book.title} </span>
						<span> - ${RecordService.daysLeftBeforeExpiry(record)} days left</span>
					</div>
					</c:if>
					
					<c:if test="${RecordService.daysLeftBeforeExpiry(record) < 0}"> 
					<div class="text-red-700">
						<span class="font-medium"> ${record.book.title} </span>
						<span> - ${-RecordService.daysLeftBeforeExpiry(record)} days expired</span>
					</div>
					</c:if>
				</c:forEach>
			</div>
		</div>
	</dialog>
	</c:if>
	
	<script>
		dialog.showModal();
		
		dialog.addEventListener('click', (event) => {
	      if (event.target === dialog) {
	        dialog.close();
	      }
	    });
	</script>

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
			<a href="/user/books" class="block px-4 p-1 rounded-md hover:bg-white hover:text-black">
				<span class="material-symbols-outlined translate-y-[6px]">library_books</span>
				<span class="inline p-2 font-medium">Books</span>
			</a>	
			<a href="/user/records" class="block px-4 p-1 rounded-md bg-white text-black">
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
                <input type="text" placeholder="Search for a book..." class="border p-1 w-full rounded-xl">
            </form>
        </search>
    </div>

    <!-- Content -->
    <div class="grow p-4 bg-gray-100 overflow-auto">
        <div class="hidden lg:block lg:flex justify-center p-4">
			<select onchange="updateSorting('sortOrder', this.value)" class="ml-2 pl-2 rounded-md">
				<option value="" disabled selected>Sort Order</option>
				<option value="asc">Ascending</option>
				<option value="desc">Descending</option>
			</select>
			<select onchange="updateSorting('status', this.value)" class="ml-2 pl-2 rounded-md">
				<option value="" disabled selected>Status</option>
				<option value="borrowing">Borrowing</option>
				<option value="returned">Returned</option>
			</select>
			<input onchange="updateDateSorting(this.value)" class="ml-2 bg-gray-200 rounded-md" type="month" />
			<a href="/admin/records" class="ml-2 bg-gray-300 text-gray-800 px-4 py-1 rounded-md">Clear Filters</a>
		</div>

        <table class="w-full text-center border-separate border-spacing-y-2">
            <thead>
                <tr class="bg-white text-primary">
                    <th class="p-3">Book Borrowed</th>
                    <th class="p-3">Status</th>
                    <th class="p-3">Date Borrowed</th>
                    <th class="p-3">Return Date</th>
                </tr>
            </thead>
            <tbody>
            	<c:if test="${empty records}">
		    	<tr class="bg-white shadow-md">
		            <td colspan="7" class="p-3 text-center font-medium">No record found</td>
		        </tr>
		    	</c:if>
		    	
                <c:forEach var="record"  items="${records}">
                    <tr class="${RecordService.isExpired(record) ? 'bg-red-200' : 'bg-white'} shadow-md">
                    <td class="p-3 text-center truncate max-w-[30rem]">
                    	<div class="truncate">${record.book.title}</div>
                    </td>
                    <td class="p-3">
						<c:if test="${record.returned}">
                        <span class="text-green-900 font-medium">Returned</span>
                        </c:if>
                        
                        <c:if test="${!record.returned}">
                        <span class="text-red-900 font-medium">Borrowing</span>
                        </c:if>
                    </td>
                    <td class="p-3"> ${record.borrowDate}</td>
                    <td class="p-3"> ${record.returnDate}</td>
                </tr>
                </c:forEach> 
            </tbody>
        </table>
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

    <script>
    
	  function updateSorting(param, value) {
	    const url = new URL(window.location.href);
	    url.searchParams.set(param, value);
	    window.location.href = url.toString();
	  }
	  
	  function updateDateSorting(value) {
		value = new Date(value);
		const month = value.getMonth() + 1;
		const year = value.getFullYear();
		  
	    const url = new URL(window.location.href);
	    url.searchParams.set('month',month);
	    url.searchParams.set('year',year);
		window.location.href = url.toString();
	  }    
    </script>
</body>

</html>