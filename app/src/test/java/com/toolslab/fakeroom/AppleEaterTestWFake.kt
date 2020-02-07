package com.toolslab.fakeroom

import com.toolslab.fakeroom.db.Apple
import com.toolslab.fakeroom.db.AppleDao
import com.toolslab.fakeroom.db.FakeAppleDao
import org.junit.Test

class AppleEaterTestWFake {

    val fakeAppleDao: AppleDao = FakeAppleDao()

    val underTest = AppleEater(fakeAppleDao)

    @Test
    fun `Eats one apple`() {
        val uneatenApple = Apple(id = 1, eaten = false)
        fakeAppleDao.insert(uneatenApple)

        underTest.eatApples()

        val eatenApple = uneatenApple.copy(eaten = true)
        fakeAppleDao.all().test()
            .assertValuesOnly(listOf(eatenApple))
    }

    @Test
    fun `Eats apple only once when same apple is found`() {
        val uneatenApple = Apple(id = 1, eaten = false)
        val eatenApple = uneatenApple.copy(eaten = true)
//        val samebutdiff = uneatenApple.copy(somet = "")

        underTest.eatApples()
        fakeAppleDao.insert(uneatenApple)
//        fakeAppleDao.delete(uneatenApple)
//        fakeAppleDao.delete(eatenApple)
//        fakeAppleDao.update(samebutdiff)

//        fakeAppleDao.all().test()
//            .assertValuesOnly(listOf(samebutdiff.copy(eaten = true)))
    }

//    @Test
//    fun `Eats apple only once when same apple is found in a different list`() {
//        val firstItem = Apple(1, eaten = false)
//        val secondItem = Apple(2, eaten = false)
//
//        underTest.eatApples()
//        fakeAppleDao.insert(firstItem)
//        fakeAppleDao.insert(secondItem)
////        val samebutdiff1 = firstItem.copy(somet = "")
////        fakeAppleDao.update(samebutdiff1)
////        val samebutdiff2 = secondItem.copy(somet = "")
////        fakeAppleDao.update(samebutdiff2)
//
//        fakeAppleDao.all().test()
//            .assertValuesOnly(
//                listOf(
////                    samebutdiff2.copy(eaten = true),
////                    samebutdiff1.copy(eaten = true)
//                )
//            )
//    }

    @Test
    fun `Eats no apples if all are found eaten`() {
        underTest.eatApples()

        fakeAppleDao.all().test()
            .assertValuesOnly()
    }

}
