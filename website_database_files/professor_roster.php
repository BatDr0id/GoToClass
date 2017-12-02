<?php
    $connection = new mysqli("address", "user", "password", "table"); #fill these in acording to personal connection
    $classID = $_POST["classID"];
    $sectionID = $_POST["sectionID"];
    $date = $_POST["date"];
    $respone = array(); //Array that will be used to store the data
    $response["success"] = false;   //boolean will tell if entire script was

    //Statement that selects all students in a particular class that are currently
    //have not enetered a code for the class in the day
    $statement = mysqli_prepare($connection, "SELECT classSection.studentID, studentInfo.firstname, studentInfo.lastname FROM classSection, studentInfo, studentLogin where classSection.classID= ? and classSection.sectionNumber = ? and studentInfo.studentID = studentLogin.studentID and classSection.studentID = studentLogin.studentID and not EXISTS (select * from attendance where attendance.studentID = classSection.studentID and attendance.classID = ? and classSection.sectionNumber = ? and attendance.dateAttended = ?)");
    mysqli_stmt_bind_param($statement, "sssss", $classID, $sectionID, $classID, $sectionID, $date);         #Bind the parameters basically put the variable in the respective locations of "?"
    mysqli_stmt_execute($statement);    #execute

    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement,$studentID, $fname, $lname);
    $int = 0;

    if (!$statement){} //If no rows just do nothing and continue

    else{   //Else add the rows to the 2d array
        $attendance = false;//Student attened equals false
        while(mysqli_stmt_fetch($statement)){
            $response["success"] = true;
            $response["$int"] = array($studentID, $fname, $lname, $attendance);
            $int = $int + 1;

        };
    };

    //Statement that selects all students in a particular class that are currently
    //have enetered a code for the class in the day
    $statement2 = mysqli_prepare($connection, "SELECT attendance.studentID, firstname, lastname from attendance, studentInfo WHERE attendance.classID = ? and studentInfo.studentID = attendance.studentID and attendance.sectionNumber = ? and attendance.dateAttended = ? ");
    mysqli_stmt_bind_param($statement2, "sss", $classID, $sectionID, $date);#Bind the parameters basically put the variable in the respective locations of "?"
    mysqli_stmt_execute($statement2);   #execute

    mysqli_stmt_store_result($statement2);
    mysqli_stmt_bind_result($statement2, $studentID, $fname, $lname);
    if (!$statement2){} //If no rows just do nothing and continue
    else{  //Else add the rows to the 2d array
            $attendance = true; //Students have attended
            while(mysqli_stmt_fetch($statement2)){
                $response["success"] = true;
                $response["$int"] = array($studentID, $fname, $lname, $attendance);
                $int = $int + 1;
            };
    }

    echo json_encode($response); #Encode and send back $response
?>



