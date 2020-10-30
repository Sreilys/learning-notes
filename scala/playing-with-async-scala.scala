import scala.concurrent.ExecutionContext.Implicits.global
import scala.async.Async.{async, await}
import scala.concurrent.{Await, Future}
import scala.concurrent.duration.DurationInt
import scala.util.Random

object AsyncMcDonalds extends App {
  val start = System.currentTimeMillis()
  def info(msg: String) = printf("%.2f: %s\n", (System.currentTimeMillis() - start) / 1000.0, msg)

  case class Dish(name: String) {
    def +(other: Dish) = Dish(s"$name with ${other.name}")
  }
  def prepare(what: String): Dish = {
    Thread.sleep(1000L)
    info(s"$what is cooked!")
    Dish(what)
  }
  def serve(dish: Dish): Unit = {
    info(s"Here is your ${dish.name}, bon appetit!")
  }

  def wantsCola() = Future {Thread.sleep(500L); Random.nextBoolean()}

  val servingCompleteMenu = async {
    val asyncBurger = async {prepare("burger")}
    val asyncFries = async {prepare("potatoes")}
    if (await(wantsCola())) {
      val cola = async {prepare("cola")}
      serve(await(asyncBurger) + await(asyncFries) + await(cola))
    } else {
      serve(await(asyncBurger) + await(asyncFries))
    }
  }

  // Takes a future and waits until is there
  Await.result(servingCompleteMenu, 10.seconds)
}
