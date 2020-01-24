package com.toolslab.fakeroom

import com.toolslab.fakeroom.db.Apple
import com.toolslab.fakeroom.db.AppleDao
import io.mockk.*
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.PublishSubject
import org.junit.Test

class AppleEaterTest {

    val mockAppleDao: AppleDao = mockk()

    val appleSubject = PublishSubject.create<List<Apple>>()

    val underTest = AppleEater(mockAppleDao)

    @Test
    fun `Eats one apple`() {
        val uneatenApple = Apple(id = 1, eaten = false)
        val eatenApple = uneatenApple.copy(eaten = true)
        every { mockAppleDao.findUneaten() } returns Flowable.just(listOf(uneatenApple))
        every { mockAppleDao.update(eatenApple) } just Runs

        underTest.eatApples()

        verify { mockAppleDao.update(eatenApple) }
    }

    @Test
    fun `Eats apple only once when same apple is found`() {
        val uneatenApple = Apple(id = 1, eaten = false)
        val eatenApple = uneatenApple.copy(eaten = true)
        every { mockAppleDao.findUneaten() } returns appleSubject.toFlowable(BackpressureStrategy.ERROR)
        every { mockAppleDao.update(eatenApple) } just Runs

        underTest.eatApples()
        appleSubject.onNext(listOf(uneatenApple))
        appleSubject.onNext(listOf(uneatenApple))

        verify { mockAppleDao.update(eatenApple) }
    }

    @Test
    fun `Eats apple only once when same apple is found in a different list`() {
        val firstItem = Apple(1, eaten = false)
        val secondItem = Apple(2, eaten = false)
        val eatenFirstApple = firstItem.copy(eaten = true)
        val eatenSecondApple = secondItem.copy(eaten = true)
        every { mockAppleDao.findUneaten() } returns appleSubject.toFlowable(BackpressureStrategy.ERROR)
        every { mockAppleDao.update(eatenFirstApple) } just Runs
        every { mockAppleDao.update(eatenSecondApple) } just Runs

        underTest.eatApples()
        appleSubject.onNext(listOf(firstItem, secondItem))
        appleSubject.onNext(listOf(secondItem))

        verify(exactly = 1) { mockAppleDao.update(eatenFirstApple) }
        verify(exactly = 1) { mockAppleDao.update(eatenSecondApple) }
    }

    @Test
    fun `Eats no apples if all are found eaten`() {
        every { mockAppleDao.findUneaten() } returns Flowable.just(emptyList())

        underTest.eatApples()

        verify(exactly = 0) { mockAppleDao.update(any()) }
    }

}
