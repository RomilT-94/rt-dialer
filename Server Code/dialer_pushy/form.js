var xmlHttp = createXmlHttpRequestObject();

function createXmlHttpRequestObject() {
	var xmlHttp;
	
	if(window.ActiveXObject){
	try{
	xmlHttp= new ActiveXObject("Microsoft.XMLHTTP");
	}catch(e){
	xmlHttp= false;
	}
	}
	
	else{
	try{
	xmlHttp= new XMLHttpRequest();
	}catch(e){
	xmlHttp= false;
	}
	}
	
	if(!xmlHttp)
	alert(" Something wrong with your Browser!!");
	else
	return xmlHttp;
	}
	
function process() {
	if(xmlHttp.readyState== 0 || xmlHttp.readyState==4)
	{
	dialerno=encodeURIComponent(document.getElementById("dialerno").value);
	userno = encodeURIComponent(document.getElementById("userno").value);
	document.getElementById("msg").innerHTML ='<span style="color:blue"> processing </span>';
	xmlHttp.open("GET","railtiffin_pushy.php?userno=" + userno + "&dialerno="+dialerno, false);
	xmlHttp.onreadystatechange = handleServerResponse;
	xmlHttp.send(null);
	}
	
	}
	
function handleServerResponse(){
	if(xmlHttp.readyState==4){
	if(xmlHttp.status==200){
	xmlResponse= xmlHttp.responseXML;
	xmlDocumentElement = xmlResponse.documentElement;
	message= 	xmlDocumentElement.firstChild.data;
	document.getElementById("msg").innerHTML ='<span style="color:blue">' +message + '</span>';
	}
	else
	alert('something went wrong!!');
	}
	}