package albek.petprojects.unisafetestapp.core

fun String.toIntOrZero(): Int = toIntOrNull() ?: 0
fun String.toIntOrElse(elseValue: Int): Int = toIntOrNull() ?: elseValue