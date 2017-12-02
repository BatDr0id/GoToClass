<?php
  $connection = new mysqli("address", "user", "password", "table"); #fill these in acording to personal connection

    $professorID = $_POST["professorID"];
    $code = $_POST["code"];
    $response = false;
    //update the professor code of the day statement
    $statement = mysqli_prepare($connection, "update professorCOD set code = ? where professorID = ?");
    mysqli_stmt_bind_param($statement, "ss", $code, $professorID);  #Bind the parameters basically put the variable in the respective locations of "?"
    mysqli_stmt_execute($statement); #execute
    $response = true;

    echo json_encode($response) #Encode and send back $response
?>
