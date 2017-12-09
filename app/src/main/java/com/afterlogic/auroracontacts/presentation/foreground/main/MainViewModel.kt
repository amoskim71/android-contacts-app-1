package com.afterlogic.auroracontacts.presentation.foreground.main

import android.databinding.Bindable
import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import com.afterlogic.auroracontacts.R
import com.afterlogic.auroracontacts.application.wrappers.Resources
import com.afterlogic.auroracontacts.core.rx.Subscriber
import com.afterlogic.auroracontacts.data.calendar.AuroraCalendar
import com.afterlogic.auroracontacts.presentation.common.base.ObservableRxViewModel
import com.afterlogic.auroracontacts.presentation.common.databinding.bindable
import timber.log.Timber
import java.util.*
import javax.inject.Inject

/**
 * Created by sunny on 06.12.2017.
 * mail: mail@sunnydaydev.me
 */
class MainViewModel @Inject constructor(
        private val interactor: MainInteractor,
        private val res: Resources,
        subscriber: Subscriber
): ObservableRxViewModel(subscriber)  {

    companion object {

        private val TYPE_CALENDAR = 1

    }

    val cards: ObservableList<CardViewModel<Any>> = ObservableArrayList()

    @get:Bindable var loadingData by bindable(true)

    @get:Bindable var syncing by bindable(false)

    private val calendarsMap = WeakHashMap<CalendarItemViewModel, AuroraCalendar>()

    init {

        interactor.getCalendars()
                .defaultSchedulers()
                .subscribeIt(onNext = this::handle)

        interactor.listenSyncingState()
                .defaultSchedulers()
                .subscribeIt { syncing = it }

    }


    fun onSyncClicked() {

        if (syncing) return

        interactor.requestStartSyncImmediately()
                .defaultSchedulers()
                .subscribeIt()

    }

    private fun handle(calendars: List<AuroraCalendar>) {

        if (loadingData) loadingData = false

        calendars
                .map { mapCalendar(it) to it  }
                .also {

                    calendarsMap.putAll(it)

                    cards.clear()

                    val card = CardViewModel(
                            TYPE_CALENDAR,
                            res.strings[R.string.prompt_main_calendars_title],
                            it.map { (vm , _) -> vm }
                    )

                    cards.add(card)

                }

    }

    private fun mapCalendar(calendar: AuroraCalendar) : CalendarItemViewModel =
            CalendarItemViewModel(
                    calendar.name, calendar.color,
                    calendar.settings.syncEnabled, this::onCheckedChanged
            )

    private fun onCheckedChanged(vm: CalendarItemViewModel, checked: Boolean) {

        val calendar = calendarsMap[vm] ?: return

        Timber.d("onChanged: ${calendar.name} : $checked")

        interactor.setSyncEnabled(calendar, checked)
                .defaultSchedulers()
                .subscribeIt()

    }

}