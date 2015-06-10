<?php

require('connect.php');

$phno=$_REQUEST['phn'];
$devid=$_REQUEST['devid'];


//echo $_SESSION['uid'];

if( mysql_query("INSERT INTO dialer_details_pushy VALUES ('$devid','$phno')") == true) {	
	echo 'working';         
}

else
if( mysql_query("INSERT INTO dialer_details_pushy VALUES ('$devid','$phno')") == false) {

	if(mysql_query("UPDATE dialer_details_pushy SET phno = '$phno' WHERE devid = '$devid'")==1){

		echo 'working';  
	}
}

else {
 	echo 'invalid';
}


?>