package br.com.fiap.live29_02.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.fiap.live29_02.model.Produto

@Database(entities = [Produto::class], version = 1)
abstract class EstoqueDb : RoomDatabase() {

  abstract fun produtoDao(): ProdutoDao

  companion object {
    lateinit var instanciaBanco: EstoqueDb

    fun getDataBase(context: Context): EstoqueDb {

      if (!::instanciaBanco.isInitialized) {
        instanciaBanco = Room
          .databaseBuilder(
            context = context,
            klass = EstoqueDb::class.java,
            name = "db_estoque"
          )
          .allowMainThreadQueries()
          .fallbackToDestructiveMigration()
          .build()
      }
      return instanciaBanco
    }

  }


}