package com.sergiofierro.mailapp.persistence

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import org.junit.After
import org.junit.Before
import java.io.IOException

abstract class BaseDaoTest {

  lateinit var db: AppDatabase

  @Before
  fun createDb() {
    db = Room.inMemoryDatabaseBuilder(getApplicationContext(), AppDatabase::class.java)
      .allowMainThreadQueries()
      .build()
  }

  @After
  @Throws(IOException::class)
  fun closeDB() {
    db.close()
  }
}
