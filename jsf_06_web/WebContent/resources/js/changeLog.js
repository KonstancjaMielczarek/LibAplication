const valueLogin = document.querySelector(".logIn span");
const valueLogOf = document.querySelector(".logOf");

console.log(valueLogin);
console.log(valueLogOf);

if(valueLogin.innerHTML == "ZALOGUJ"){
	console.log("Możesz się zalogować");
	valueLogOf.style.display = "none";
	
}else if(valueLogin.innerHTML == "WYLOGUJ"){
	console.log("Możesz się wylogować")
	valueLogin.style.display = "none";
}else{
	console.log("Nie pykło");
}


