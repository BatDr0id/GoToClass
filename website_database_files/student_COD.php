<?php
$connection = new mysqli("localhost", "id3609176_dr1197dr", "raptor042115", "id3609176_attendanceapp");
    $respone = array(); //Array that will be used to store the data
    $response["success"] = false;   //boolean will tell if entire script was successful or not
    $studentID = $_POST["studentID"];
    $code = $_POST["code"];
    $classID = $_POST["classID"];
    $sectionID = $_POST["sectionID"];
    $date = $_POST["date"];

    $retrieveCode = null; // will be used to match student submitted code to actual code



    $statement = mysqli_prepare($connection, "SELECT professorCOD.code FROM professorCOD, studentLogin, classSection, professorLogin WHERE studentLogin.studentID = ? and classSection.classID = ? and classSection.sectionNumber = ? and classSection.studentID = studentLogin.studentID and classSection.professorID = professorLogin.professorID and professorLogin.id = professorCOD.id" );
    mysqli_stmt_bind_param($statement, "sss", $studentID, $classID, $sectionID);
    mysqli_stmt_execute($statement);

    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $code2);

    while(mysqli_stmt_fetch($statement)){
            $retrieveCode = $code2; //make retrievecode = to code from the database
        };
      if ($code == $retrieveCode) { //if they match fill student info to mark attended
        $response["success"] = true;
        $statement2 = mysqli_prepare($connection, "insert into attendance values (null,?, ?, ?, ?)");
        mysqli_stmt_bind_param($statement2, "ssss", $classID,$sectionID,  $date,  $studentID);
        mysqli_stmt_execute($statement2);

      };
    echo json_encode($response); #Encode and send back $response
?>
