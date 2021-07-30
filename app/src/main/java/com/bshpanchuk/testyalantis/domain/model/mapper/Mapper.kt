package com.bshpanchuk.testyalantis.domain.model.mapper

interface Mapper<IN, OUT> {

    fun map(oldData: IN) : OUT
}