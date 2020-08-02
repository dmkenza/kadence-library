package com.kadencelibrary.extension


fun <T, U> List<T>.intersect(uList: List<U>, filterPredicate: (T, U) -> Boolean) =
    filter { m -> uList.any { filterPredicate(m, it) } }


fun <T, U> List<T>.intersect2Pair(uList: List<U>, filterPredicate: (T, U) -> Boolean) = map { m ->

    val second = uList.find { filterPredicate(m, it) }

    if (second == null) {
        return@map null
    }

    return@map Pair<T, U>(m, second)
}.filterNotNull()