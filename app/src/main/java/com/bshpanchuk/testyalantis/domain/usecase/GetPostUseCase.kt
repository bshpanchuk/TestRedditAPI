package com.bshpanchuk.testyalantis.domain.usecase

import com.bshpanchuk.testyalantis.domain.repository.Repository

class GetPostUseCase(
    private val repository: Repository
) {

    operator fun invoke() = repository.getTopPost()

}