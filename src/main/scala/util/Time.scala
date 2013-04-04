package org.example

import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeUnit.{NANOSECONDS => Nanos, MILLISECONDS => Millis}
import scala.math.ceil

package object util {

  private def measure[A](thunk: => A): (A, Long) = {
    val before = System.nanoTime
    val result = thunk
    val delta = (System.nanoTime - before)

    (result, delta)
  }

  private def stats(times: Seq[Long]) = {
    val sortedTimes = times.sorted
    val mean = ("mean", (times.sum / times.size))
    val fifty = ("50%", sortedTimes(ceil(times.size * 0.5).toInt))
    val seventyFive = ("75%", sortedTimes(ceil(times.size * 0.75).toInt))
    val ninety = ("90%", sortedTimes(ceil(times.size * 0.90).toInt))
    val ninetyNine = ("99%", sortedTimes(ceil(times.size * 0.99).toInt))

    mean :: fifty :: seventyFive :: ninety :: ninetyNine :: Nil
  }

  def time[A](unit: TimeUnit = Millis)(thunk: => A): A = {
    val (result, time) = measure(thunk)
    println(s"snippet took: ${unit.convert(time, Nanos)} ${unit.toString.toLowerCase}")

    result
  }

  def benchmark[A](unit: TimeUnit = Millis, times: Int = 100)(thunk: => A) = {

    // warm up.
    (1 to 100) foreach (i => thunk)

    // measure.
    val results = (1 to times) map (i => measure(thunk)) map (_._2)
    val info = stats(results)

    // inform.
    println("RESULTS:")
    println("--------")
    println()
    info foreach { case (label, value) =>
      printf("%-4s: %-6s %s %n", label, unit.convert(value, Nanos), unit.toString.toLowerCase)
    }
  }
}