/**
 * Chat-Client, der die Nachrichten per XMLHTTP-Request mit der GET bzw. POST
 * Methode vom bzw. an den Server abfragt bzw. schickt.
 * 
 * author: Niels Bubel
 */

var httpGetMessages = erzeugeXMLHttpRequestObj();
var httpPostMessage = erzeugeXMLHttpRequestObj();
var MessageID = 1;
var saveChatFlag = false;
var savepoint = 0;

function init(){
	servlet = "http://tomcat/CambiChat/GetUser"
	console.log("Start request");
	sendRequest(servlet, setName)
}

function setName(servletRequest){
	console.log("Callback: setName()");
	username = servletRequest.responseText;
	console.log("get Name:" + username);
	namefield = document.getElementById("Name");
	namefield.setAttribute("value", username);
}

function sendRequest(url, callback) {
	console.log("Send Request!");
	if (httpGetMessages.readyState == 4 || httpGetMessages.readyState == 0) {
	httpGetMessages.open("GET",url,true);
	httpGetMessages.setRequestHeader('Content-type','application/x-www-form-urlencoded');
	httpGetMessages.onreadystatechange = function () {
		if (httpGetMessages.readyState != 4) return;
		if (httpGetMessages.status != 200 && httpGetMessages.status != 304) {
			alert("HTTP error ' + req.status");
			return;
		}
		callback(httpGetMessages);
	}
	
	if (httpGetMessages.readyState == 4) return;
	console.log("Schicke Request ab!")
	httpGetMessages.send();
	}
}

function erzeugeXMLHttpRequestObj() {
	// Diverse andere Browser wie Chrome, Mozilla...
	if (window.XMLHttpRequest) {
		try {
			obj = new XMLHttpRequest();
		} catch (e) {
			obj = false;
		}
		// MS Internet Explorer
	} else if (window.ActiveXObject) {
		try {
			obj = new ActiveXObject("Msxml2.XMLHTTP");
		} catch (e) {
			try {
				obj = new ActiveXObject("Microsoft.XMLHTTP");
			} catch (e) {
				obj = false;
			}
		}
	}
	return obj;
}

function getMessages() {
	var empfaenger = document.getElementById("group-id").value;
	if (empfaenger != "") {
		getMessagesForGroup(empfaenger, saveChatFlag);
	} else {
		alert("Bitte geben Sie einen Gruppennamen fuer den Empfaenger an!");
	}
}

function getMessagesForGroup(empfaenger, save) {

	if (httpGetMessages.readyState == 4 || httpGetMessages.readyState == 0) {
		if (save){
			console.log("SAVE TO FILE: http://tomcat/CambiChat/ChatControl?id=" +
					savepoint + "&group="
					+ empfaenger + "&save=" + save);
			httpGetMessages.open("GET",
					"http://tomcat/CambiChat/ChatControl?id=" + MessageID
							+ "&group=" + empfaenger  + "&save=" + save, true);	
			alert("Der Chatverlauf wurde gespeichert!");
		}
		
		console.log("GET: http://tomcat/CambiChat/ChatControl?id="
				+ MessageID + "&group=" + empfaenger + "&save=" + false);
		httpGetMessages.open("GET",
				"http://tomcat/CambiChat/ChatControl?id=" + MessageID
						+ "&group=" + empfaenger  + "&save=" + save, true);
		
		httpGetMessages.onreadystatechange = bearbeiteHttpGetMessages;
		httpGetMessages.send(null);
		saveChatFlag = false;
		savepoint = MessageID;
	}
}

// Response-Verarbeitung und Ausgabe mittels DOM
function bearbeiteHttpGetMessages() {

	if (httpGetMessages.readyState == 4) {
		response = httpGetMessages.responseXML.documentElement;
		messages = response.getElementsByTagName("message");

		if (messages.length > 0) {
			for (var i = 0; i < messages.length; i++) {
				chatverlauf = document.getElementById("chatverlauf");
				neuerParagraph = document.createElement("p");
				neuerParagraph.setAttribute("class", "nachricht"); // CSS
				// Klasse
				neueZeile = document.createElement("div");
				neueZeile.setAttribute("class", "datumUndName");
				neuNameDate = document
						.createTextNode(messages[i]
								.getElementsByTagName("name")[0].firstChild.nodeValue
								+ " schrieb am "
								+ messages[i].getElementsByTagName("date")[0].firstChild.nodeValue
								+ " um "
								+ messages[i].getElementsByTagName("time")[0].firstChild.nodeValue
								+ " an "
								+ messages[i].getElementsByTagName("group")[0].firstChild.nodeValue
								+ ":");
				neueZeile.appendChild(neuNameDate);
				neuerParagraph.appendChild(neueZeile);
				neueNachricht = document
						.createTextNode(messages[i]
								.getElementsByTagName("nachricht")[0].firstChild.nodeValue);
				neuerParagraph.appendChild(neueNachricht);
				chatverlauf
						.insertBefore(neuerParagraph, chatverlauf.firstChild);
				if (MessageID == 1) {
					insertResetandSaveButtons();
				}

				MessageID++;
				console.log("Neue MessageID: " + MessageID);

			}
		}

		else {
			console.log("keine Nachrichten vorhanden!");
		}

		var empfaenger = document.getElementById("group-id").value;
		// Abrufen der Nachrichten alle 2 Sekunden
		setTimeout(function() {
			getMessages();
		}, 2000);
	}
}

function postMessage(name, nachricht, empfaenger, setting) {
	if (httpPostMessage.readyState == 4 || httpPostMessage.readyState == 0) {
		if(setting != null){
		alert("Post settings to: http://tomcat/CambiChat/ChatControl");
		httpPostMessage.open("POST",
				"http://tomcat/CambiChat/ChatControl?setting=true", true);
		httpPostMessage.setRequestHeader("Content-type", "application/xml");
		httpPostMessage.setRequestHeader("Access-Control-Allow-Origin", "*");
			var xmlMessage = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+ "<messages xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xsi:schemaLocation='http://tomcat/CambiChat/schema.xsd'>"
				+ "<message><setting>" + setting +"</setting><group>" + empfaenger
				+ "</group>" + "</message></messages>";
			alert("Send settings to server:" + xmlMessage);
		}
		else {
			console.log("Post message to: http://tomcat/CambiChat/ChatControl");
			httpPostMessage.open("POST",
					"http://tomcat/CambiChat/ChatControl?setting=false", true);
			httpPostMessage.setRequestHeader("Content-type", "application/xml");
			httpPostMessage.setRequestHeader("Access-Control-Allow-Origin", "*");
			var xmlMessage = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+ "<messages xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xsi:schemaLocation='http://tomcat/CambiChat/schema.xsd'>"
				+ "<message><id>0</id><date>" + getDate() + "</date><time>"
				+ getTime() + "</time><name>" + name + "</name><nachricht>"
				+ nachricht + "</nachricht>" + " <group>" + empfaenger
				+ "</group>" + "</message></messages>";
			
		}
		httpPostMessage.send(xmlMessage);
		httpPostMessage.onreadystatechange = console
				.log("Post Data finished am " + getDate() + " um " + getTime());
	}
}

function abschicken() {
	// document.getElementById("ChatFormular").submit();
	var name = document.getElementById("Name").value;
	console.log("Name: " + name.toString());
	var nachricht = document.getElementById("Nachricht").value;
	console.log("nachricht: " + nachricht);
	var empfaenger = document.getElementById("group-id").value;
	console.log("Empfaenger: " + empfaenger);
	postMessage(name, nachricht, empfaenger, null);
	document.getElementById("Nachricht").value = "";
}

function saveChat() {
	console.log("saveChat(): setze SaveFlag");
	saveChatFlag = true;
	chatverlauf = document.getElementById("chatverlauf");
	while (chatverlauf.firstChild) {
	    chatverlauf.removeChild(chatverlauf.firstChild);
	}
}

function doSettings() {
	console.log("doSettings()");
	selectedOption = document.getElementById("SettingsSelect").value;
	selectedGroup = document.getElementById("SettingsGroup").value;
	if (selectedOption == "load"){
		alert("Load!");
		postMessage(null, null, selectedGroup, "load");
	}
	else {
		alert("delete!");
		postMessage(null, null, selectedGroup, "delete");
	}
}

function insertResetandSaveButtons() {
	buttons = document.getElementById("buttonsHidden");
	buttons.setAttribute("id", "buttons");
}

function getDate() {
	var date = new Date();
	var year = date.getFullYear();
	var month = ("0" + (date.getMonth() + 1)).slice(-2);
	var day = date.getDate();
	console.log("Get Date " + year + "-" + month + "-" + day);
	return year + "-" + month + "-" + day;
}

function getTime() {
	var time = new Date();
	var hours = time.getHours();
	var minutes = time.getMinutes();
	var seconds = time.getSeconds();
	return hours + ":" + minutes + ":" + seconds;
}
