// Imperative style: note we use var and this is bad, scala world does not like var that much (mutability)
val limit = 10
var result = ""
if(limit < 10) result = "You did't get to the limit yet"
else if (limit > 10) result = "You got too far over the limit"
else result = "Ok, stay there, don't move!"

// More functional way to do the same thing: Better uhh :)
val result = 	if(limit < 10) "You did't get to the limit yet"
				else if(limit > 10) "You got too far over the limit"
				else "Ok, stay there, don't move!"

// while, do while..
val limit = 10
var result = ""
while(limit > 0) {
	result = result + limit
	if (limit > 1) result = result + ","
	limit = limit - 1
}
// better this right?
val result = (1 to 10).mkString(",")

// for comprehension
val myList = List(1,2,3,4) // list of integers
val result = for (item <- myList) yield (item * 3)
