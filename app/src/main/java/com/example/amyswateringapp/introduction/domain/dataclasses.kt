package com.example.amyswateringapp.introduction.domain


enum class Position(){
    One,
    Two,
    Three,
    Four,
    Five,
}
data class InOrOutScreenStates(
    var moveIn: Boolean,
    var moveOut: Boolean,
    var moveToStartPosition:Boolean
)

val start = InOrOutScreenStates(moveIn = false, moveOut = false, moveToStartPosition = true)
val middle = InOrOutScreenStates(moveIn = true, moveOut = false, moveToStartPosition = false)
val end = InOrOutScreenStates(moveIn = false, moveOut = true, moveToStartPosition = false)
val none = InOrOutScreenStates(moveIn = false, moveOut = false, moveToStartPosition = false)
