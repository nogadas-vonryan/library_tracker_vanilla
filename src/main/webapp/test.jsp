<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Responsive Sidebar</title>
  <style>
    * {
      margin: 0;
      padding: 0;
      box-sizing: border-box;
    }

    .container {
      display: flex;
    }

    .sidebar {
      background-color: #333;
      color: white;
      padding: 20px;
      /* Sidebar takes only the space it needs */
      flex-grow: 0;
    }

    .main-content {
      flex-grow: 1; /* This will take up the remaining space */
      padding: 20px;
      background-color: #f4f4f4;
    }
  </style>
</head>
<body>
<div>hello world</div>
<div class="container">
  <div class="sidebar">
    <h2>Sidebar</h2>
    <p>Sidebar content goes here</p>
  </div>
  <div class="main-content">
    <h1>Main Content</h1>
    <p>Main content goes here</p>
  </div>
</div>

</body>
</html>
