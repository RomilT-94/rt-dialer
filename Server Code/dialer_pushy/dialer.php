
<html>
<head>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script>
$(document).ready(function(){
$('#submit').click(function(){
document.getElementById('success').innerHTML='';
 document.getElementById('sec').innerHTML='<img src="alo.gif">';
$.post("railtiffin_pushy.php", $("#form").serialize(),  function(response) {
document.getElementById('sec').innerHTML='';
$('#success').html(response);document.getElementById('dialerno').value='';
});
return false;
});
});
</script>
</head>
<body style="width: 600px;margin-left: 400px;align:center;">

<form id="form" class="form" method="post" action="" enctype="application/x-www-form-urlencoded" accept-charset="UTF-8">
    <h1 style="text-align:center;">RailTiffin Dialer</h1>
    <div class="content">
        <div class="intro"></div>
        <div id="section0" >


<?php
	require('connect.php');
	if(isset($_POST["dailerno"])){

		$phno = $_POST["userno"];
		$callresult = mysql_query("SELECT * FROM dialer_details_pushy WHERE phno = '$phno'");

		if(!$callresult) {
   			 die("Database query failed: " . mysql_error());
		}

		while ($callrow = mysql_fetch_array($callresult)) {
    		
    		$devid = $callrow["devid"];
		}
	}

	$result = mysql_query("SELECT * FROM dialer_details_pushy");
	if(!$result) {
   			 die("Database query failed: " . mysql_error());
	}

	?>
	<div id="sec" style="color:green;margin-left:130px;"></div>
	<div id="success" style="color:green;"></div>
	<div class="field"><label for="userno"  >Select Your Number</label>
	<select id="userno" name="userno" style="width: 300px;" required>

	<?php

	while ($row = mysql_fetch_array($result)) {

			if(isset($_POST["userno"])){

				if($_POST["userno"]==$row["phno"])
				{
					echo '<option value='.$row["phno"].' selected>'.$row["phno"].$row["devid"].'</option>';$set=1;
					
			$sid=$row["devid"];
					}
			}

    			if($_POST["userno"]!=$row["phno"]){
    				echo '<option value='.$row["phno"].'>'.$row["phno"].'</option>';
					
					}}


?>
			</select>
			</div>
            
            <div class="field" ><label for="dialerno"  >Enter Number to Dial</label><input type="tel" id="dialerno" name="dialerno" placeholder="Enter Number to Dail" style="width: 300px;" required autofocus></div>
            <div class="field"><div class="edit-options"><div class="edit"></div><div class="delete"></div></div><input type="submit" id="submit" name="submit" style="width: 300px;" value="Call"></div>
        </div>
    </div>
</form>

</body>

<link href="http://fonts.googleapis.com/css?family=Open+Sans" id="google-font-selector" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="css/main.css" type="text/css">


</html>