import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future}
import scala.concurrent.duration._

object FutureMcDonalds extends App {
  val start = System.currentTimeMillis()
  def info(msg: String) = printf("%.2f: %s\n", (System.currentTimeMillis() - start) / 1000.0, msg)

  case class Dish(name: String) {
    def +(other: Dish) = Dish(s"$name with ${other.name}")
  }
  def cook(what: String): Dish = {
    Thread.sleep(1000L)
    info(s"$what is cooked!")
    Dish(what)
  }
  def serve(dish: Dish): Unit = {
    info(s"Here is your ${dish.name}, bon appetit!")
  }

  val futureBurger = Future {cook("burger")}
  val futureFries = Future {cook("potatoes")}

  val servingCompleteMenu: Future[Unit] = for {
    burger <- futureBurger
    fries <- futureFries
  } yield {
    serve(burger+fries)
  }

  // Takes a future and waits until is there
  Await.result(servingCompleteMenu, 10.seconds)
}
