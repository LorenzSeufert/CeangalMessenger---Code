import user from 'js/user.js'
console.log(user.getAge())

document.getElementById("username").innerHTML = user.getName()
document.getElementById("city").innerHTML = user.getCity()
document.getElementById("email").innerHTML = user.getEmail()
document.getElementById("age").innerHTML = user.getAge()
document.getElementById("description").innerHTML = user.getDescription()
document.images.namedItem("profile").src = user.getProfilePicture()
//document.getElementById("editDesc").innerHTML = user.getDescription()