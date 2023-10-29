if (isset($_POST['login'])) {
    // 데이터베이스 연결
  	  $link = mysqli_connect("localhost", "root", "1001", "users");

 	   // 데이터베이스 연결 오류 처리
    if (!$link) {
  	      die("Connection failed: " . mysqli_connect_error());
  	  }

   	    $id = $_POST['id'];
    	$password = $_POST['password'];


  	  // 입력받은 로그인 정보를 사용하여 데이터베이스에서 접속
 $sql = "SELECT * FROM login WHERE username = '" . $id . "' AND password = '" . $password . "'";
   	 $recordset = mysqli_query($link, $sql);


 	   if (mysqli_num_rows($recordset) > 0) {
 	       $row = mysqli_fetch_array($recordset);
   	     $message = "Login SUCCESS! " . $row["username"] . " Welcome!";
  	  } else {
   	     $message = "계정 정보를 잘못 입력했습니다.";
   	 }

  	  // 데이터베이스 연결 종료
  	  mysqli_close($link);
    }