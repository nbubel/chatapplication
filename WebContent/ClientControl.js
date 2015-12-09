/**
 *  author: Niels Bubel
 */

var httpGetMessages = erzeugeXMLHttpRequestObj();
var httpPostMessage = erzeugeXMLHttpRequestObj();
var MessageID = 1;

function erzeugeXMLHttpRequestObj(){
	// Mozilla, Chrom...
	if(window.XMLHttpRequest) {
		try { 
			obj = new XMLHttpRequest(); 
		} catch(e) {
			obj = false;
		}
		// InternetExplorer
	} else if(window.ActiveXObject) {
		try {
			obj = new ActiveXObject("Msxml2.XMLHTTP");
		} catch(e) {
			try {
				obj = new ActiveXObject("Microsoft.XMLHTTP");
			} catch(e) {
				obj = false;
			}
		}
	}
	return obj;
}


function getMessages() {
	if (httpGetMessages.readyState == 4 || httpGetMessages.readyState == 0) { 
		console.log("GET: http://localhost:8080/CambiChat/ChatControl?id="+MessageID);
		httpGetMessages.open("GET","http://localhost:8080/CambiChat/ChatControl?id="+MessageID, true);
		httpGetMessages.onreadystatechange = bearbeiteHttpGetMessages; 
		httpGetMessages.send(null);
		}
}

// Response-Verarbeitung und Ausgabe mittels DOM
function bearbeiteHttpGetMessages() {

if (httpGetMessages.readyState == 4) {
response = httpGetMessages.responseXML.documentElement;
messages = response.getElementsByTagName("message");

if(messages.length > 0) {
for (var i=0; i<messages.length; i++) {
chatverlauf = document.getElementById("chatverlauf");
neuerParagraph = document.createElement("p");
neuerParagraph.setAttribute("class","nachricht"); //CSS Klasse
neueZeile = document.createElement("div");
neueZeile.setAttribute("class", "datumUndName");
neuNameDate = document.createTextNode( messages[i].getElementsByTagName("name")[0].firstChild.nodeValue+ " schrieb am " + messages[i].getElementsByTagName
("date")[0].firstChild.nodeValue + " um " + messages[i].getElementsByTagName("time")[0].firstChild.nodeValue + ":");
neueZeile.appendChild(neuNameDate);
neuerParagraph.appendChild(neueZeile);
neueNachricht = document.createTextNode(messages[i].getElementsByTagName
("nachricht")[0].firstChild.nodeValue);
neuerParagraph.appendChild(neueNachricht);
chatverlauf.insertBefore(neuerParagraph, chatverlauf.firstChild);
MessageID++;
console.log("Neue MessageID: "+ MessageID);

}

}

else {
	console.log("keine Nachrichten vorhanden!");
}

// Abrufen der Nachrichten alle 2 Sekunden
setTimeout("getMessages();",2000);
}
}

function postMessage(name, nachricht) {
	if (httpPostMessage.readyState == 4 || httpPostMessage.readyState == 0) {
		console.log("Post to: http://localhost:8080/CambiChat/ChatControl");
		httpPostMessage.open("POST","http://localhost:8080/CambiChat/ChatControl", true);
		httpPostMessage.setRequestHeader("Content-type", "application/xml");
		httpPostMessage.setRequestHeader("Access-Control-Allow-Origin", "*");
		var xmlMessage ="<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+
		"<messages xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xsi:schemaLocation='http://localhost:8080/CambiChat/schema.xsd'>"+
		"<message><id>0</id><date>"+ getDate() +"</date><time>"+ getTime() +"</time><name>" + name +"</name><nachricht>" + nachricht +"</nachricht>" +
		"</message></messages>";	
		httpPostMessage.send(xmlMessage);
		httpPostMessage.onreadystatechange = console.log("Post Data finished am " + getDate() + " um " + getTime()); 
		}
}

function abschicken(){
	//document.getElementById("ChatFormular").submit();
	var name = document.getElementById("Name").value;
	console.log("Name: "+name.toString());
	var nachricht = document.getElementById("Nachricht").value;
	console.log("nachricht: "+nachricht);
	postMessage(name, nachricht);
	document.getElementById("Nachricht").value = "";
}

function getDate(){
	var time = new Date();
	var year = time.getFullYear();
	var month = time.getMonth();
	var day = time.getDay();
	return year+"-"+month+"-"+day;
}

function getTime(){
	var time = new Date();
	var hours = time.getHours();
	var minutes = time.getMinutes();
	var seconds = time.getSeconds();
	return hours+":"+minutes+":"+seconds;
}


