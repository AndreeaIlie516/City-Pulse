package com.android.citypulse.favoriteevents

//
//@Composable
//fun FavoriteEventsScreen(
//    eventViewModel: EventViewModel,
//    modifier: Modifier = Modifier,
//    navController: NavController
//) {
//    Log.i("FavoriteEventsScreen", "favEventsList on top: ${eventViewModel.favoriteList}")
//    Log.i("FavoriteEventsScreen", "privateEventsList on top: ${eventViewModel.events.value}")
//
//    Column(
//        modifier = modifier
//    ) {
//        SetScreenTitle(modifier = modifier)
//        eventViewModel.events.value?.let {
//            EventsList(
//                favoriteList = eventViewModel.favoriteList,
//                privateEventsList = it,
//                eventViewModel = eventViewModel,
//                modifier = modifier,
//                navController = navController
//            )
//        }
//    }
//}
//
//@Composable
//fun SetScreenTitle(modifier: Modifier = Modifier) {
//    Row(
//        modifier = modifier
//            .padding(start = 25.dp, top = 65.dp, bottom = 20.dp)
//            .fillMaxWidth(),
//        verticalAlignment = Alignment.CenterVertically,
//    ) {
//
//        Column(
//            modifier = modifier
//        )
//        {
//            Text(
//                text = stringResource(id = R.string.favorite_events_screen),
//                textAlign = TextAlign.Left,
//                color = colorResource(R.color.black),
//                style = MaterialTheme.typography.headlineMedium,
//                fontFamily = FontFamily(Font(R.font.sf_pro_display_bold))
//            )
//        }
//    }
//}
//
//@Composable
//private fun EventsList(
//    favoriteList: SnapshotStateMap<PredefinedEvent, Boolean>,
//    privateEventsList: List<PrivateEvent>,
//    eventViewModel: EventViewModel,
//    modifier: Modifier = Modifier,
//    navController: NavController
//) {
//    Log.i("FavoriteEventsScreen", "privateEventsList: $privateEventsList")
//    Box(modifier = Modifier.fillMaxSize()) {
//        LazyColumn(
//            modifier = modifier
//        ) {
//            items(PredefinedEvent.values()) { event ->
//                if (favoriteList[event] == true) {
//                    EventCellFavorite(
//                        modifier = modifier,
//                        event = event,
//                        onClickEvent = {},
//                    )
//                }
//            }
//            items(privateEventsList) { event ->
//                DeleteItem(event = event,
//                    onClickEvent = {},
//                    onClickEditEvent = {
//                        eventViewModel.privateEventToEdit = event
//                        navController.navigate("update")
//                    },
//                    onDelete = {
//                        eventViewModel.deleteEvent(event)
//                    }
//                )
//            }
//            item {
//                Spacer(modifier = Modifier.height(150.dp))
//            }
//        }
//
//
//        AddButton(
//            modifier = Modifier
//                .align(BottomEnd)
//                .padding(bottom = 45.dp, end = 10.dp),
//            navController = navController
//        )
//    }
//}
//
//@Composable
//private fun AddButton(
//    modifier: Modifier = Modifier,
//    navController: NavController
//) {
//    val touchedDown = remember { mutableStateOf(false) }
//
//    var backgroundColor = colorResource(id = R.color.purple)
//
//    if (touchedDown.value) {
//        backgroundColor = colorResource(id = R.color.purple_700)
//    }
//
//    Box(
//        modifier = modifier,
//        contentAlignment = BottomEnd
//    ) {
//        Card(
//            modifier = modifier
//                .pointerInput(Unit) {
//                    detectTapGestures(
//                        onPress = {
//                            // log
//                            touchedDown.value = true
//
//                            val released = try {
//                                tryAwaitRelease()
//                            } catch (c: CancellationException) {
//                                false
//                            }
//
//                            if (released) {
//                                //log
//
//                                touchedDown.value = false
//                                navController.navigate("add")
//                            }
//                        }
//                    )
//                }
//                .size(50.dp)
//                .align(BottomEnd),
//            shape = AbsoluteRoundedCornerShape(
//                topLeft = 15.dp,
//                topRight = 15.dp,
//                bottomLeft = 15.dp,
//                bottomRight = 15.dp
//            ),
//            colors = CardDefaults.cardColors(
//                containerColor = backgroundColor
//            )
//        ) {
//            Box(
//                modifier = Modifier
//                    .padding(start = 13.dp),
//                contentAlignment = Alignment.Center,
//            ) {
//                Text(
//                    text = stringResource(id = R.string.add_button_label),
//                    color = colorResource(id = R.color.white),
//                    style = TextStyle(
//                        fontSize = 35.sp,
//                        fontFamily = FontFamily(Font(R.font.sf_pro_display_bold))
//                    ),
//                )
//            }
//        }
//    }
// }