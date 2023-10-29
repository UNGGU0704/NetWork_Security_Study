if (isset($_POST['login'])) {
    // 데이터베이스 연결
  	  $link = mysqli_connect("localhost", "root", "1001", "users");

 	   // 데이터베이스 연결 오류 처리
    if (!$link) {
  	      die("Connection failed: " . mysqli_connect_error());
  	  }

    $id = mysqli_real_escape_string($link, $_POST['id']);
    $password = mysqli_real_escape_string($link, $_POST['password']);
    // 입력값 검증
    if (!preg_match('/^[a-zA-Z0-9]+$/', $id)) {
        die("SQL INJECTION 방지!");
    }
    $sql = "SELECT * FROM login WHERE username = ? AND password = ?";
    $stmt = mysqli_prepare($link, $sql);
    
    mysqli_stmt_bind_param($stmt, 'ss', $id, $password);
    mysqli_stmt_execute($stmt);
    $recordset = mysqli_stmt_get_result($stmt);
    if (mysqli_num_rows($recordset) > 0) {
        $row = mysqli_fetch_array($recordset);
        $message = "Login SUCCESS! " . $row["username"] . " Welcome!";
    } else {
        $message = "계정 정보를 잘못 입력했습니다.";
    }
    mysqli_close($link);

}
    