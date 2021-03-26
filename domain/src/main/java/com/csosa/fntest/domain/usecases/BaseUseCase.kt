package com.csosa.fntest.domain.usecases


interface BaseUseCase<in T, out R> {
    suspend operator fun invoke(params: T): R
}
