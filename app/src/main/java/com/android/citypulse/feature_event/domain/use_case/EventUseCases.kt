package com.android.citypulse.feature_event.domain.use_case

data class EventUseCases(
    val getEventsUseCase: GetEventsUseCase,
    val getEventUseCase: GetEventUseCase,
    val addEventUseCase: AddEventUseCase,
    val deleteEventUseCase: DeleteEventUseCase,
    val deleteEventFromFavouritesUseCase: DeleteEventFromFavouritesUseCase,
    val toggleFavouriteStatus: ToggleFavouriteStatus
)