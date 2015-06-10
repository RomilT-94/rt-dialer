<?php 
class PushyAPI
{
    static public function sendPushNotification( $data, $ids )
    {
        // Your Pushy API key
        $apiKey = 'f897d8772d80d51c4db8f69dcd436dfd87e840a9c0453aa8ef53f705d3782fc7';
		

        // URL to Pushy endpoint
        $url = 'https://pushy.me/push?api_key='.$apiKey;
        // Set post variables
        $post = array(
                        'registration_ids'  => $ids,
                        'data'              => $data,
        );

        // Set Content-Type since we're sending JSON
        $headers = array(
                        'Content-Type: application/json'
        );

        // Initialize curl handle
        $ch = curl_init();

        // Set URL to Pushy endpoint
        curl_setopt( $ch, CURLOPT_URL, $url );

        // Set request method to POST
        curl_setopt( $ch, CURLOPT_POST, true );

        // Set our custom headers
        curl_setopt( $ch, CURLOPT_HTTPHEADER, $headers );

        // Get the response back as string instead of printing it
        curl_setopt( $ch, CURLOPT_RETURNTRANSFER, true );
		
		curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);

        // Set post data as JSON
        curl_setopt( $ch, CURLOPT_POSTFIELDS, json_encode( $post ) );

        // Actually send the push
        $result = curl_exec( $ch );

        // Display errors
        if ( curl_errno( $ch ) )
        {
			$pushy_error = curl_error( $ch );
            echo 'CURL error: ' . curl_error( $ch );
			$logfetch = mysql_query("SELECT log_id FROM log_details WHERE device_id = '".$ids[0]."'"); 
		while ($rows = mysql_fetch_array($logfetch)) $lid=$rows['log_id'];
			mysql_query("UPDATE log_details SET pushy_server='$pushy_error' WHERE log_id='$lid'");
			
        }else{
		$logfetch = mysql_query("SELECT log_id FROM log_details WHERE device_id = '".$ids[0]."'"); //Offset 0
		while ($rows = mysql_fetch_array($logfetch)) 
			$lid=$rows['log_id'];
			$server_stat="pushy_server_OK";
			$logg = mysql_query("UPDATE log_details SET pushy_server='$server_stat' WHERE log_id='$lid'"); //UndefinedVariable
			if(!$logg)
			echo "Log not saved";
		
		}
        // Close curl handle
        curl_close( $ch );
		
		
        // Debug API response
        echo "<p style='text-align:center;color:orange;'>".$result."<br></p>";
	
    }
}
?>