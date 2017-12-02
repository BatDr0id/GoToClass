<?php
    $connection = new mysqli("address", "user", "password", "table"); #fill these in acording to personal connection
    $professorID = $_POST["professorID"];
    $password = $_POST["password"];
    $respone = array(); //Array that will be used to store the data
    $response["success"] = false;   //boolean will tell if entire script was successful or not

    #Statement that checks if user submitted data matches the login information  in the database
    $statement = mysqli_prepare($connection, "SELECT professorLogin.professorID, professorInfo.firstname, professorInfo.lastname, professorInfo.email, professorInfo.image, professorCOD.code FROM professorLogin, professorInfo, professorCOD WHERE professorLogin.professorID = ? and professorLogin.password = ? and professorLogin.id = professorInfo.id and professorCOD.professorID = professorLogin.professorID");
    mysqli_stmt_bind_param($statement, "ss", $professorID, $password);  #Bind the parameters basically put the variable in the respective locations of "?"
    mysqli_stmt_execute($statement);    #execute

    mysqli_stmt_store_result($statement);   #get results
    mysqli_stmt_bind_result($statement, $professorID, $firstName, $lastName, $email, $image, $code); #bind results to variable

        while(mysqli_stmt_fetch($statement)){   #Add login and personal infomation to respone[]
            $response["success"] = true;
            $response["professorID"]= $professorID;
            $response["firstName"] = $firstName;
            $response["lastName"] = $lastName;
            $response["email"] = $email;
            $response["image"] = $image;
            $response["code"] = $code;

        }

            #If first query was successful continue to process the next
        if ($response["success"] == true){
            #Get the classes the professor currently instructs
            $statement2 = mysqli_prepare($connection, "Select Distinct classInfo.name, classSection.classID, classSection.sectionNumber from classSection, professorLogin, classInfo where classSection.classID = classInfo.classID and classSection.professorID = professorLogin.professorID and professorLogin.professorID= ?");
            mysqli_stmt_bind_param($statement2, "s", $professorID); #Bind the parameters basically put the variable in the respective locations of "?"
            mysqli_stmt_execute($statement2);   #execute

            mysqli_stmt_store_result($statement2); #get results
            mysqli_stmt_bind_result($statement2, $className, $classID, $classSection); #bind results to variables
            $int = 0;                               #counter for creation of 2D array
            while(mysqli_stmt_fetch($statement2)){
                $response["$int"] = array($className, $classID, $classSection); #$int is assigned as the $response[] name to make it easier to process
                $int = $int + 1;                                             #rows in java application
            }

        }
    echo json_encode($response);    #Encode and send back $response
?>
