<?php header('Content-type: text/xml');header('Pragma: public');header('Cache-control: private');header('Expires: -1');
"<?xml version=\"1.0\" encoding=\"utf-8\"?>";require('connect.php');
		$logsrc=$_REQUEST['logs'];
		if(isset($_REQUEST['logs']) && isset($_REQUEST['to']))
		{
			if($logsrc=="log.js")
			{
	$info = mysql_query("SELECT * FROM log_details ORDER By log_id DESC LIMIT 0,".(int)$_REQUEST['to']);
	if($info)
	{
	
	echo "<Logs>";
	   	while ($logs=mysql_fetch_array($info)) {
	echo "
		<Call>
		<log_id>".$logs['log_id']."</log_id>
		<Device_id>".$logs['device_id']."</Device_id>
		<Mobile>".$logs['mobile']."</Mobile>
		<Pushy_Server_Reply>".$logs['pushy_server']."</Pushy_Server_Reply>
		<Medium>".$logs['medium']."</Medium>
		<Time_Sent>".$logs['sent_time']."</Time_Sent>
		<Time_Received>".$logs['received_time']."</Time_Received>
		</Call>";	
		}
	echo "</Logs>";
	
				if(isset($_REQUEST['ci']) && $_REQUEST['ci']=="ok" && isset($_REQUEST['pass']) && isset($_REQUEST['too']))
				{
				$infoc = mysql_query("SELECT * FROM rf_customer ORDER By customer_id DESC LIMIT 0,".(int)$_REQUEST['too']);
				if($infoc)
				{
				$ci=$_REQUEST['ci'];$pass=$_REQUEST['pass'];
					while ($csi=mysql_fetch_array($infoc)) {
						if($pass=="go.php")
						{
							echo "
		<customer>
		<id>".$csi['customer_id']."</id>
		<f_name>".$csi['firstname']."</f_name>
		<l_name>".$csi['lastname']."</l_name>
		<email>".$csi['email']."</email>
		<mobile>".$csi['telephone']."</mobile>
		</customer>";
						}
					}
				}
			else
			{die("<logs>Query Failed cus: ".mysql_error()."</logs>");}}
	}
	else
		die("<logs>Query Failed: ".mysql_error()."</logs>");
		}}
		else
			echo "<logs>Don't waste your time,,, Don't Disturb me Unnecessarily</logs>";
	?>