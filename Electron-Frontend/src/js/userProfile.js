var user = {
  name: "Leo",
  city: "Villingen",
  email: "leo.dicapucino@gmail.com",
  age: 22,
  description: "I am an IT student and a actor",
  profilePicture: "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9a/Leonardo_DiCaprio_2010.jpg/1024px-Leonardo_DiCaprio_2010.jpg",
  getName: function () {
    return this.name
  },
  getCity: function () {
    return this.city
  },
  getEmail: function () {
    return this.email
  },
  getAge: function() {
    return this.age
  },
  getDescription: function() {
    return this.description
  },
  getProfilePicture: function() {
    return this.profilePicture
  }
}

document.getElementById("username").innerHTML = user.getName()
document.getElementById("city").innerHTML = user.getCity()
document.getElementById("email").innerHTML = user.getEmail()
document.getElementById("age").innerHTML = user.getAge()
document.getElementById("description").innerHTML = user.getDescription()
document.images.namedItem("profile").src = user.getProfilePicture()