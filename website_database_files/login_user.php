<?php
    $connection = new mysqli("address", "user", "password", "table"); #fill these in acording to personal connection
    $studentID = $_POST["studentID"];
    $password = $_POST["password"];
    $respone = array(); //Array that will be used to store the data
    $response["success"] = false;   //boolean will tell if entire script was successful or not

    #Statement that checks if user submitted data matches the login information  in the database
    $statement = mysqli_prepare($connection, "SELECT * FROM studentLogin, studentInfo WHERE studentLogin.studentID = ? and studentLogin.password = ? and studentLogin.id = studentInfo.id");
    mysqli_stmt_bind_param($statement, "ss", $studentID, $password); #Bind the parameters basically put the variable in the respective locations of "?"
    mysqli_stmt_execute($statement);#execute

    mysqli_stmt_store_result($statement);   #get results
    mysqli_stmt_bind_result($statement, $id, $studentID, $password, $id2, $studentID2, $firstName, $lastName, $email, $image); #bind results to variable

        while(mysqli_stmt_fetch($statement)){   #Add login and personal infomation to respone[]
            $response["success"] = true;
            $response["studentID"]= $studentID;
            $response["firstName"] = $firstName;
            $response["lastName"] = $lastName;
            $response["email"] = $email;
            $response["image"] = $image;

        };

        #If first query was successful continue to process the next
        if ($response["success"] == true){
            #Get the classes the student is currently enrolled in
            $statement2 = mysqli_prepare($connection, "Select classInfo.name, classSection.classID, classSection.sectionNumber, professorInfo.firstName, professorInfo.lastName from classInfo, classSection, professorLogin, professorInfo where classInfo.classID = classSection.classID and classSection.professorID = professorLogin.professorID and professorLogin.professorID=professorInfo.professorID and classSection.studentID = ?");
            mysqli_stmt_bind_param($statement2, "s", $studentID); #Bind the parameters basically put the variable in the respective locations of "?"
            mysqli_stmt_execute($statement2); #execute

            mysqli_stmt_store_result($statement2);  #get results
            mysqli_stmt_bind_result($statement2, $className, $classID, $classSection, $professorF, $professorL);
            $int = 0;                                           #counter for creation of 2D array
            while(mysqli_stmt_fetch($statement2)){
                $response["$int"] = array($className, $classID, $classSection,
                                                    #$int is assigned as the $response[] name to make it easier to process
                $professorF, $professorL);          #rows in java application
                $int = $int + 1;
            }

        }
    echo json_encode($response);             #Encode and send back $response
?>
