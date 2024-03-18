package br.com.fiap.live29_02.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_produtos")
data class Produto(
  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "codigo_produto")
  var codigoProduto: Long = 0,
  val nome: String = "",
  val quantidade: Int = 0,
  @ColumnInfo(name = "data_validade")
  val dataValidade: String = "",
  val disponivel: Boolean = false
)
