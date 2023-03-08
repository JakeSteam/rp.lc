package config.util

import pixel.Rule
import kotlin.reflect.full.valueParameters

fun Rule.getInputParams() = this::class.members.first().valueParameters

fun Rule.getReturnType() = this::class.members.first().returnType