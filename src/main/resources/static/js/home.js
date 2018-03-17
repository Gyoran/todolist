let request = new XMLHttpRequest();
let request2  = new XMLHttpRequest();
let delrequest = new XMLHttpRequest();

window.onload = function() {
	request.open("GET", "/points", true);
	request.send();
	clean(document);
}

request.onreadystatechange = function() {
	if(request.readyState === 4) {
		let reqTitle = document.getElementById("requestTitle");
		for (let i = 0; i < reqTitle.childNodes.length; i++) {
			if (reqTitle.childNodes[i] != document.getElementById("requestTitleHeadLi")) {
				reqTitle.removeChild(reqTitle.childNodes[i]);
				i--;
			}
		}
		if (request.responseText == "{}")
			reqTitle.innerHTML = "No ToDo Points";
		else {
			let points = JSON.parse(request.responseText);
			for (let i = 0; true; i++) {
				if (points.points[i] == null)
					break;
				pointli = createPointLi(points.points[i], i);
				reqTitle.appendChild(pointli);
			}
			document.getElementById("requestTitleHeadLi").style.display = "initial";
		}
		clean(document);
	}
}

function stateSort() {
	request.open("GET", "/points?fsort=yes", true);
	request.send();
}

function dateSort() {
	request.open("GET", "/points?dsort=yes", true);
	request.send();
}

function apDescriptionClicked(id) {
	let btn = document.getElementById(id);
	if (btn.getAttribute("data-clicked") == "false") {
		btn.setAttribute("data-clicked", "true");
		btn.style.height = "auto";
	}
	else {
		btn.setAttribute("data-clicked", "false");
		btn.style.height = "20px";
	}
}

function checkAP(element, uncheck) {
	let id = element.parentNode.parentNode.id;
	id = id.substr(id.indexOf(">") + 1);
	let params = "";
	if (uncheck === "false")
		params = "finished=1";
	else if (uncheck === "true")
		params = "finished=0";
	else
		return;
	request2.open("POST", "/edit/" + id);
	request2.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	request2.send(params);
}

function deleteAP(element) {
	let id = element.parentNode.id;
	id = id.substr(id.indexOf(">") + 1);
	delrequest.open("DELETE", "/" + id);
	delrequest.send();
}

function createPointLi(point, index) {
	let result = document.createElement("li");
	let name = document.createElement("span");
	name.classList.add("apName");
	let description = document.createElement("span");
	description.classList.add("apDescription");
	description.onclick = function(){apDescriptionClicked("apDescription" + index)};
	description.setAttribute("data-clicked", "false");
	description.id = "apDescription" + index;
	let finished = document.createElement("span");
	finished.classList.add("apFinished");
	let deadline = document.createElement("span");
	deadline.classList.add("apDeadline");
	let deletebtn = document.createElement("button");
	deletebtn.onclick = function(){deleteAP(this)};
	deletebtn.innerHTML = "Delete";
	
	name.innerHTML = point.name;
	description.innerHTML = point.description;
	finished.innerHTML = point.finished == 0 ? "No" : "Yes";
	let finishedbtn = document.createElement("button");
	if (point.finished == 0)
		finishedbtn.onclick = function(){checkAP(this, "false")};
	else
		finishedbtn.onclick = function(){checkAP(this, "true")};
	finishedbtn.innerHTML = point.finished == 0 ? "Check" : "Uncheck";
	finished.appendChild(finishedbtn);
	deadline.innerHTML = point.deadline.day + "-" + point.deadline.month + "-" + point.deadline.year;
	
	result.appendChild(name);
	result.appendChild(description);
	result.appendChild(finished);
	result.appendChild(deadline);
	result.appendChild(deletebtn);
	result.id = "<point>" + point.id;
	return result;
}

function newActionPoint() {
	document.getElementById("newApButton").style.display = "none";
	document.getElementById("newApForm").style.display = "initial";
}

request2.onreadystatechange = function() {
	if (request2.readyState === 4) {
		if (request2.responseText != "") {
			window.location = "http://localhost:8080";
		}
	}
}

delrequest.onreadystatechange = function() {
	if (delrequest.readyState === 4) {
		if (delrequest.responseText === "true") {
			window.location = "http://localhost:8080";
		}
	}
}

function clean(node)
{
  for(var n = 0; n < node.childNodes.length; n ++)
  {
    var child = node.childNodes[n];
    if
    (
      child.nodeType === 8 
      || 
      (child.nodeType === 3 && !/\S/.test(child.nodeValue))
    )
    {
      node.removeChild(child);
      n --;
    }
    else if(child.nodeType === 1)
    {
      clean(child);
    }
  }
}