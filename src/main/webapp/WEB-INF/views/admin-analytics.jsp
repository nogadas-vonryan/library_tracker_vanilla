<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../../stylesheet/main.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined" />
    <title>Borrowing Manager</title>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/4.4.1/chart.umd.js"></script>
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
                <input type="text" placeholder="Search for a book..." class="border p-1 w-full rounded-xl">
            </form>
        </search>
    </div>

    <!-- Content -->
    <div class="grow p-4">
        <canvas id="ctx"></canvas>
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
        const ctx = document.getElementById('ctx');
        let dataset = [
	        <c:forEach var="record" items="${records}" varStatus="status">
	            {
	                month: "${record.month}",
	                count: "${record.count}",
	            }
	        </c:forEach>
    	];
        
        const config = {
            type: 'line',
            data: {
                labels: dataset.map(row => row.month),
                datasets: [
                    {
                        label: 'Books Borrowed',
                        data: dataset.map(row => row.count)
                    }
                ]
            },
        };

        const myChart = new Chart(ctx, config);
	
        console.log(dataset);
    </script>
</body>

</html>