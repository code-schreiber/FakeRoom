package com.toolslab.fakeroom.db

import android.database.sqlite.SQLiteConstraintException
import org.junit.Test

// Same tests as AppleDaoInstrumentedTest
class FakeAppleDaoTest {

    val apples = listOf(
        Apple(1, true),
        Apple(2, false)
    )

    val underTest: AppleDao = FakeAppleDao()

    @Test
    fun all() {
        apples.forEach { underTest.insert(it) }

        val testSubscriber = underTest.all().test()

        testSubscriber.assertValuesOnly(apples)
    }

    @Test(expected = SQLiteConstraintException::class)
    fun insertingTwiceErrors() {
        val apple = Apple(1, true)
        underTest.insert(apple)
        underTest.insert(apple)
    }

    @Test
    fun update() {
        apples.forEach { underTest.insert(it) }
        val updatedMedia = apples[0].copy(eaten = true)

        underTest.update(updatedMedia)

        underTest.all().test()
            .assertValuesOnly(listOf(updatedMedia, apples[1]))
    }

    @Test
    fun delete() {
        apples.forEach { underTest.insert(it) }

        underTest.delete(apples[0])

        underTest.all().test()
            .assertValuesOnly(listOf(apples[1]))
    }

    @Test
    fun findUneaten() {
        apples.forEach { underTest.insert(it) }

        val testSubscriber = underTest.findUneaten().test()

        testSubscriber.assertValuesOnly(listOf(apples.last()))
    }
}
