<?php
// Require Pushy API Class
require('connect.php');
require('pushy.php');

// Payload data you want to send to devices
if(isset($_REQUEST['userno']) && $_REQUEST['userno']!='' && isset($_REQUEST['dialerno']) && $_REQUEST['dialerno']!='')
{
		$mb=$_REQUEST['userno'];
	$callresult = mysql_query("SELECT * FROM dialer_details_pushy WHERE phno = '".$_REQUEST['userno']."'");
	if(!$callresult) {
   			 die("Database query failed: " . mysql_error());
					}
		else{
		while ($rows = mysql_fetch_array($callresult)) 
		{
    		$devid = $rows['devid'];
		}
		$logresult = mysql_query("INSERT INTO log_details SET device_id = '$devid' , medium='pushy' , sent_time=now() , mobile='$mb'"); //
		if(!$logresult)
		echo 'Failed';
		$logfetch = mysql_query("SELECT log_id FROM log_details WHERE device_id = '$devid'");
		while ($rows = mysql_fetch_array($logfetch)){
		$lid=$rows['log_id']; }
		$data = array( 'message' => $lid."-".$_REQUEST['dialerno'] );		
// The recipient device registration IDs
$ids = array($devid);
// Send it via Pushy API
PushyAPI::sendPushNotification( $data, $ids );
}}
else
echo 'Please Enter Number';
?>