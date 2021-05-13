// variance positions (rockthejvm)
// code for https://www.youtube.com/watch?v=aUmj7jnXet4

class Animal
class Dog extends Animal
class Cat extends Animal
class Crocodile extends Animal

// COVARIANT
class MyList[+T]
// INVARIANT
// class MyList[T]

// if Dogs are Animals then should MyList[Dog] is also a MyList[Animal]
// the variance questions

// 1.yes => generic type is COVARIANT +T

val animal: Animal = new Dog
val animals: MyList[Animal] = new MyList[Dog]

// 2.no => generic type is INVARIANT

// 3. hell no! it's actually backwards CONTRAVARIANT
class Vet[-T]
val lassieVet: Vet[Dog] = new Vet[Animal]

// variance problem
/*abstract class MyList2[+T] {
  def head: T
  def tail: MyList[T]
  def add(element: T): MyList[T]
}*/

/*  // types of val fields are in COVARIANT position
class Vet2[-T](val favoriteAnimal: T)

val garfield =  new Cat
val theVet: Vet2[Animal] = new Vet2[Animal](garfield)
val lassiesVet2: Vet2[Dog] = theVet
val lassies = lassiesVet2.favoriteAnimal // type conflict*/

// types of var fields are also in COVARIANT positions
// class Vert3[-T](var favoriteAnimal: T)

// types of var fields are also in CONTRAVARIANT positions
/*  class MutableOption[+T](var contents: T)
val maybeAnimal: MutableOption[Animal] = new MutableOption[Dog](new Dog)
maybeAnimal.contents = new Cat*/

// var fields do not work with generics

// type of method arguments are in CONTRAVARIANT position
/*  class MyList2[+T] {
  //def head: T
  //def tail: MyList[T]
  def add(element: T): MyList[T] = ???
}

val animals2: MyList2[Animal] = new MyList2[Cat]
val moreAnimals = animals2.add(new Dog) // type conflict*/

class Vet3[-T] {
  def heal(animal: T): Boolean = true
}

val lassiesVet3: Vet3[Dog] = new Vet3[Animal]
lassiesVet3.heal(new Dog)
// lassiesVet3.heal(new Cat) // legit error

// method return types are in COVARIANT position
/* abstract class Vet4[-T] {
  def rescueAnimal(): T
}

val vet4: Vet4[Animal] = new Vet4[Animal] {
  override def rescueAnimal(): Animal = new Cat
}
val lassiesVet4: Vet4[Dog] = vet4
val rescuedDog: Dog = lassiesVet4.rescueAnimal() //Dog*/

class MyListC[+T] {
  // S is supertype of T / lower bound in T
  def add[S >: T](element: S): MyList[S] = new MyList[S]
}

val lst = new MyListC[Dog]
lst.add(new Dog)
lst.add(new Cat)

class VetC[-T] {
  def rescueAnimal[S <: T](): S = ???
}

val lassiesVetC: VetC[Dog] = new VetC[Animal]
val rescuedDogC: Dog = lassiesVetC.rescueAnimal()