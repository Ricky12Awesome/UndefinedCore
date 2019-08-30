package com.github.ricky12awesome.undefinedcore.utils

fun xpNeededToLevel(pair: Pair<Int, Int>): Int = xpNeededToLevel(pair.first, pair.second)
fun xpNeededToLevel(lowerLevel: Int, higherLevel: Int): Int {
  var expToSubtract = 0
  for (i in lowerLevel until higherLevel) {
    expToSubtract += xpNeededForNextLevel(i)
  }
  return expToSubtract
}

fun xpNeededForNextLevel(lvl: Int): Int = when {
  lvl >= 30 -> 112 + (lvl - 30) * 9
  lvl >= 15 -> 37 + (lvl - 15) * 5
  else -> 7 + lvl * 2
}

fun levelToXp(lvl: Int): Int {
  var xp = 0

  if (lvl >= 1) repeat(lvl) {
    xp += xpNeededForNextLevel(it)
  } else repeat(lvl.positive) {
    xp -= xpNeededForNextLevel(it)
  }

  return xp
}

fun xpToLevelAndProgress(xp: Int): Pair<Int, Float> {
  var level = 0
  var progress = xp / xpNeededForNextLevel(level).toFloat()

  while (progress >= 1.0f) {
    progress = (progress - 1.0f) * xpNeededForNextLevel(level)
    level += 1
    progress /= xpNeededForNextLevel(level)
  }

  return level to progress
}

fun xpToLevel(xp: Int): Int = xpToLevelAndProgress(xp).first

fun xpProgress(xp: Int): Float = xpToLevelAndProgress(xp).second


