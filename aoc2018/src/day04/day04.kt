package day04

import useInput

sealed class LogEntry(val time: String) {
    class BeginShift(time: String, val guard: Int) : LogEntry(time)
    class FallAsleep(time: String) : LogEntry(time)
    class WakeUp(time: String) : LogEntry(time)

    val minute get() = time.slice(14..15).toInt()
    override fun toString() = "[$time] " + when(this) {
        is BeginShift -> "begin shift $guard"
        is FallAsleep -> "fall asleep"
        is WakeUp -> "wake up"
    }
}

val guardPattern = Regex("Guard #(\\d+) begins shift")

fun main(args: Array<String>) {
    val log = useInput { lines ->
        lines.map {
            val date = it.slice(1..16)
            when (val le = it.substring(19)) {
                "falls asleep" -> LogEntry.FallAsleep(date)
                "wakes up" -> LogEntry.WakeUp(date)
                else -> LogEntry.BeginShift(date, guardPattern.matchEntire(le)!!.groupValues[1].toInt())
            }
        }.toList().sortedBy { it.time }
    }

    log.forEach(::println)

    val sleepMap = log.associateGuards()
        .groupBy { it.first }
        .mapValues { (_, entries) -> entries.minutesAsleep().toList() }

//    val sleeper = sleepMap.maxBy { it.value.size }!!.key
//    println(sleeper)
//    val sleepMinute = sleepMap[sleeper]!!.groupingBy { it }.eachCount().maxBy { it.value }!!.key
//    println(sleepMinute)
//    println(sleepMinute * sleeper)
    val maxSleepMap = sleepMap.mapValues { (_, asleep) ->
        asleep.groupingBy { it }.eachCount().maxBy { it.value }!!
    }.maxBy { it.value.value }
    println(maxSleepMap)
    println(maxSleepMap!!.key * maxSleepMap.value.key)
}

fun List<LogEntry>.associateGuards() = sequence {
    var guard = -1
    for (e in this@associateGuards) {
        when (e) {
            is LogEntry.BeginShift -> guard = e.guard
            is LogEntry.FallAsleep -> yield(guard to e)
            is LogEntry.WakeUp -> yield(guard to e)
        }
    }
}

fun List<Pair<Int, LogEntry>>.minutesAsleep() = sequence {
    var asleep: Int? = null
    for ((_, le) in this@minutesAsleep) {
        when (le) {
            is LogEntry.FallAsleep -> asleep = le.minute
            is LogEntry.WakeUp -> if (asleep != null) {
                yieldAll(asleep until le.minute)
                asleep = null
            }
        }
    }
}