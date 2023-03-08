package util

import pixel.Rule
import kotlin.reflect.full.valueParameters

fun Rule.getInputParams() = this::class.members.find { it.name == "invoke" }!!.valueParameters

fun Rule.getReturnType() = this::class.members.find { it.name == "invoke"}!!.returnType