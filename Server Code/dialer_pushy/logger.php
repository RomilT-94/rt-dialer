<?php 

require('connect.php');
if(isset($_REQUEST['rcvd_time']) && $_REQUEST['rcvd_time']!='' && isset($_REQUEST['id']) && $_REQUEST['id']!='' )
{
$rcvd_time=$_REQUEST['rcvd_time'];
$log_id=$_REQUEST['id'];
echo $rcvd_time."@";
if(mysql_query("UPDATE log_details SET received_time =$rcvd_time WHERE log_id = '$log_id'"))
echo "received";
else
echo "fail";
}
else
echo "chuchu pana na karo";
?>