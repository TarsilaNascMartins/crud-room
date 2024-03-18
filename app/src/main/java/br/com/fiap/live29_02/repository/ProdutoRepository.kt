package br.com.fiap.live29_02.repository

import android.content.Context
import br.com.fiap.live29_02.dao.EstoqueDb
import br.com.fiap.live29_02.model.Produto

class ProdutoRepository(context: Context) {

  private val db = EstoqueDb.getDataBase(context).produtoDao()

  fun salvar(produto: Produto): Long{
    return db.salvar(produto)
  }

  fun listarTodos(): List<Produto>{
    return db.listarTodos()
  }

  fun atualizar(produto:Produto): Int {
    return db.atualizar(produto)
  }

  fun excluir(produto:Produto): Int {
    return db.excluir(produto)
  }

  fun buscarContatoPeloId(id: Long): Produto? {
    return db.buscarProdutoPorCodigo(id)
  }


}