<?php 

	

    $error = "Problem connecting to database. Please try again later.";

    $connect = mysql_connect("localhost","root","") or die($error);

	mysql_select_db("rt_rbos_new") or die($error);

?>