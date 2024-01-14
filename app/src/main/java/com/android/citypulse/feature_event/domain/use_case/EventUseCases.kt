package com.android.citypulse.feature_event.domain.use_case

data class EventUseCases(
    val getEventsUseCase: GetEventsUseCase,
    val getEventUseCase: GetEventUseCase,
    val addEventUseCase: AddEventUseCase,
    val deleteEventUseCase: DeleteEventUseCase,
    val deleteAllEventsUseCase: DeleteAllEventsUseCase,
    val deleteEventFromFavouritesUseCase: DeleteEventFromFavouritesUseCase,
    val toggleFavouriteStatus: ToggleFavouriteStatus,
    val updateEventUseCase: UpdateEventUseCase
)