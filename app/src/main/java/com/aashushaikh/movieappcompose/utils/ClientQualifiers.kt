package com.aashushaikh.movieappcompose.utils

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class AuthenticatedClient

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class TokenRefreshClient

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class PublicClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthenticatedService

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class PublicService

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RefreshService
