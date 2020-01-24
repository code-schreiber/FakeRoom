package com.toolslab.fakeroom.db

import android.database.sqlite.SQLiteConstraintException
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.BehaviorSubject

class FakeAppleDao : AppleDao {

    private val apples = mutableListOf<Apple>()
    private val applesSubject = BehaviorSubject.create<List<Apple>>()
    private val applesFlowable: Flowable<List<Apple>> =
        applesSubject.toFlowable(BackpressureStrategy.ERROR)

    override fun all() = applesFlowable

    override fun insert(apple: Apple) {
        if (apples.any { it.id == apple.id }) throw SQLiteConstraintException("UNIQUE constraint failed: apple.id (code 1555 SQLITE_CONSTRAINT_PRIMARYKEY)")
        apples.add(apple)
        notifyObservers(apples)
    }

    override fun update(apple: Apple) {
        apples.removeIf { it.id == apple.id }
        apples.add(0, apple)
        notifyObservers(apples)
    }

    override fun delete(apple: Apple) {
        apples.remove(apple)
        notifyObservers(apples)
    }

    override fun findUneaten(): Flowable<List<Apple>> {
        return applesFlowable.map { apples ->
            apples.filter { !it.eaten }
        }
    }

    private fun notifyObservers(apples: List<Apple>) = applesSubject.onNext(apples)

}
