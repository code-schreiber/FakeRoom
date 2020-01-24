package com.toolslab.fakeroom

import com.toolslab.fakeroom.db.AppleDao
import io.reactivex.rxkotlin.flatMapIterable

class AppleEater(private val appleDao: AppleDao) {

    fun eatApples() {
        appleDao.findUneaten()
            .toObservable()
            .flatMapIterable() // Eating all eatable apples, one by one
            .distinct()
            .map { it.copy(eaten = true) } // Eat next apple
            .subscribe { appleDao.update(it) } // Save eaten apple
    }
}
