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
</head>

<body class="h-screen flex flex-col lg:flex-row justify-between">
	<c:if test="${isExpired}">
		<dialog>
			PLEASE RETURN THE BOOK IMMEDIATELY!
		</dialog>
	</c:if>
	
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
        <div class="text-white font-bold text-2xl pb-2">Records</div>
        <search>
            <form action="">
                <input type="text" placeholder="Search for a record..." class="border p-1 w-full rounded-xl">
            </form>
        </search>
    </div>

    <!-- Content -->
    <div class="grow p-4 bg-gray-100 overflow-auto">
        <div class="hidden lg:block lg:flex p-4">
            
            <form class="w-1/5" method="GET" action="/admin/records">
			<input class="border rounded-md p-1" type="text" name="search"
				placeholder=" Search for a record..."> 
			</form>	
			<span class="material-symbols-outlined text-neutral-400 -translate-x-[16px] translate-y-[6px]">search</span>

			<a href="/admin/records/add" class="bg-primary text-white px-4 py-1 rounded-md">New Record</a>

			<select onchange="updateSorting('sortOrder', this.value)" class="ml-2 pl-2 rounded-md">
				<option value="" disabled selected>Sort Order</option>
				<option value="asc">Ascending</option>
				<option value="desc">Descending</option>
			</select>
			<select onchange="updateSorting('status', this.value)" class="ml-2 pl-2 rounded-md">
				<option value="" disabled selected>Status</option>
				<option value="borrowing">Borrowing</option>
				<option value="returned">Returned</option>
				<option value="expired">Expired</option>
			</select>
			<input onchange="updateDateSorting(this.value)" class="ml-2 bg-gray-200 rounded-md" type="month" />
			<a href="/admin/records" class="ml-2 bg-gray-300 text-gray-800 px-4 py-1 rounded-md">Clear Filters</a>
		</div>

        <table class="w-full text-center border-separate border-spacing-y-2">
            <thead>
                <tr class="bg-white text-primary shadow-md">
                	<th class="p-3">Student Number</th>
                    <th class="p-3">Name</th>
                    <th class="p-3">Book Borrowed</th>
                    <th class="p-3">Status</th>
                    <th class="p-3">Date Borrowed</th>
                    <th class="p-3">Return Date</th>
                    <th class="p-3">Actions</th>
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
                	<td class="p-3 text-sm"> ${record.user.referenceNumber} </td>
                    <td class="p-3"> 
                    	<div class="text-sm truncate max-w-[8rem]"> ${record.user.lastName}, ${record.user.firstName} </div>
                     </td>
                    <td class="p-3">
                    	<div class="text-sm truncate max-w-[13rem]"> ${record.book.title} </div>
                    </td>
                    <td class="p-3 space-y-2">	
						<c:if test="${record.returned}">
                        <span class="text-green-900 font-medium">Returned</span>
                        </c:if>
                        
                        <c:if test="${!record.returned}">
                        <span class="text-red-900 font-medium">Borrowing</span>
                        </c:if>
                    </td>
                    <td class="p-3"> ${record.borrowDate}</td>
                    <td class="p-3 ${RecordService.isExpired(record) ? 'text-red-700' : 'text-black'}"> ${record.returnDate}</td>
                    <td class="p-3 space-x-2 font-semibold">
                    	<div class="inline">
                    		<form class="inline" method="POST" action="/admin/records">
                    			<input type="hidden" name="_method" value="PUT" />
								<input type="hidden" name="recordId" value="${record.id}" />
								<input type="hidden" name="isReturned" value="${!record.returned}" />
                        		
                        		<c:if test="${record.returned}">
                        		<button type="submit" class="bg-red-900 text-white px-4 py-1 rounded-md">Undo</button>
                        		</c:if>
                        		
                        		<c:if test="${!record.returned}">
                        		<button type="submit" class="bg-green-900 text-white px-4 py-1 rounded-md">Returned</button>
                        		</c:if>
                        		
                        	</form>
                    	</div>
                        <form class="inline" method="POST" action="/admin/records">
                            <input type="hidden" name="_method" value="DELETE" />
							<input type="hidden" name="id" value="${record.id}" />
                        	<button type="submit" class="bg-primary text-white px-4 py-1 rounded-md">Delete</button>
                        </form>
                    </td>    
                </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <div
		class="py-3 flex justify-around bg-primary text-white rounded-t-xl lg:hidden">
		<a href="/admin/books" class="grow text-center"> <span
			class="material-symbols-outlined !text-4xl">library_books</span>
		</a> <a href="/admin/records" class="grow text-center"> <span
			class="material-symbols-outlined !text-4xl">history</span>
		</a> <a href="/logout" class="grow text-center"> <span
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