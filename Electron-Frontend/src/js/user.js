var user = {
    name: "Leo",
    city: "Villingen",
    email: "leo.dicapucino@gmail.com",
    age: 22,
    description: "I am an IT student and a actor",
    profilePicture: "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9a/Leonardo_DiCaprio_2010.jpg/1024px-Leonardo_DiCaprio_2010.jpg",
}

var userObj = function () {
  /*let getName = function () {
    return user.name
  }
  let getCity = function () {
    return user.city
  }
  let getEmail = function () {
    return user.email
  }
  let getAge = function () {
    return user.age
  }
  let getDescription = function () {
    return user.description
  }
  let getProfilePicture = function () {
    return user.profilePicture
  }*/

  return {
    getName: function () {
      return user.name
    },
    getCity: function () {
      return user.city
    },
    getEmail: function () {
      return user.email
    },
    getAge: function () {
      return user.age
    },
    getDescription: function () {
      return user.description
    },
    getProfilePicture: function () {
      return user.profilePicture
    }
  }
}