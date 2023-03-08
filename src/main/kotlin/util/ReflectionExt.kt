package util

import generation.BaseRule
import kotlin.reflect.full.valueParameters

fun BaseRule.getInputParams() = this::class.members.find { it.name == "invoke" }!!.valueParameters

fun BaseRule.getReturnType() = this::class.members.find { it.name == "invoke"}!!.returnType