package br.com.fiap.live29_02.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Update
import br.com.fiap.live29_02.model.Produto

@Dao
interface ProdutoDao {

  @Insert
  fun salvar(produto: Produto): Long

  @Insert(onConflict = REPLACE )
  fun substituir(produto: Produto): Long

  @Update
  fun atualizar(produto: Produto): Int

  @Delete
  fun excluir(produto: Produto): Int

  @Query("SELECT * FROM tbl_produtos WHERE codigo_produto = :codigo")
  fun buscarProdutoPorCodigo(codigo: Long): Produto

  @Query("SELECT * FROM tbl_produtos ORDER BY nome ASC")
  fun listarTodos(): List<Produto>


}