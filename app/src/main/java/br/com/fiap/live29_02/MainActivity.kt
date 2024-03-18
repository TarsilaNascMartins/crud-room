package br.com.fiap.live29_02

import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.live29_02.model.Produto
import br.com.fiap.live29_02.repository.ProdutoRepository
import br.com.fiap.live29_02.ui.theme.Live2902Theme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      Live2902Theme {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
          CadastroScreen()
        }
      }
    }
  }
}
@Composable
fun CadastroScreen() {

  val context = LocalContext.current
  val repository = ProdutoRepository(context)

  // Variáveis e estados para controlar os campos de entrada de texto
  var nomeState by remember { mutableStateOf("") }
  var quantidadeState by remember { mutableStateOf("") }
  var dataState by remember { mutableStateOf("") }
  var disponivelState by remember { mutableStateOf(false) }

  // Variável para armazenar o ID do produto em edição
  var produtoIdEditado by remember { mutableStateOf(-1) }

  // Estado para controlar o texto do cabeçalho
  var textoCabecalho by remember { mutableStateOf("Cadastro: Itens Reciclagem") }

  // Estado para armazenar a lista de produtos
  var listaProdutosState = remember { mutableStateOf(listOf<Produto>()) }

  // Atualiza a lista de produtos ao criar ou deletar um produto
  listaProdutosState.value = repository.listarTodos()

  Column(
    modifier = Modifier.padding(16.dp)
  ) {


   Button(
      onClick = {
       // navController.navigate("menu")
      },
      colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF80A999))//,
     // shape = RoundedCornerShape(16.dp)
    ) { Text(
      text = "VOLTAR",
      fontWeight = FontWeight.Bold,
      color = Color.White,
      fontSize = 14.sp
     
    )}

    Image(
      painter = painterResource(
        id = R.drawable.logo2sustentabilidade
      ),
      contentDescription = "Imagem com um cupom na mão e com folhas ao redor",
      modifier = Modifier
        .size(100.dp, 100.dp), contentScale = ContentScale.Crop

    )

    Spacer(modifier = Modifier.height(10.dp))

    Text(
      text = textoCabecalho,
      fontSize = 24.sp,
      fontWeight = FontWeight.Bold,
      modifier = Modifier.padding(bottom = 8.dp),
      color = colorResource(id = R.color.cor_primaria_app),
      textAlign = TextAlign.Center
    )

      OutlinedTextField(
        value = nomeState,
        onValueChange = {
          nomeState = it
        },
        label = { Text(text = "Nome do item para reciclagem") },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
          capitalization = KeyboardCapitalization.Words,
          keyboardType = KeyboardType.Email
        ),
        colors = OutlinedTextFieldDefaults.colors(
          unfocusedBorderColor = colorResource(id = R.color.cor_primaria_app),
          focusedBorderColor = colorResource(id = R.color.cor_primaria_app)
        )
      )
      OutlinedTextField(
        value = quantidadeState,
        onValueChange = { quantidade ->
          quantidadeState = quantidade
        },
        label = { Text(text = "Quantidade de itens") },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
          capitalization = KeyboardCapitalization.None,
          keyboardType = KeyboardType.Number
        ),
        colors = OutlinedTextFieldDefaults.colors(
          unfocusedBorderColor = colorResource(id = R.color.cor_primaria_app),
          focusedBorderColor = colorResource(id = R.color.cor_primaria_app)
        )
      )
      OutlinedTextField(
        value = dataState,
        onValueChange = {
          dataState = it
        },
        label = { Text(text = "Data da coleta") },
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
          unfocusedBorderColor = colorResource(id = R.color.cor_primaria_app),
          focusedBorderColor = colorResource(id = R.color.cor_primaria_app)
        )
      )
      Row(
        verticalAlignment = Alignment.CenterVertically
      ) {
        Checkbox(
          checked = disponivelState,
          onCheckedChange = {
            disponivelState = it
          },
          colors = CheckboxDefaults.colors(
            checkedColor = colorResource(id = R.color.cor_primaria_app),
            uncheckedColor = Color(0xFFE18791)
          )
        )
        Text(text = "Entregue")
      }

      Button(
        onClick = {
          val produto = Produto(
            nome = nomeState,
            disponivel = disponivelState,
            quantidade = quantidadeState.toIntOrNull() ?: 0,
            dataValidade = dataState
          )

          if (produtoIdEditado != -1) {
            // Se houver um ID de produto em edição, atualize o produto correspondente
            produto.codigoProduto = produtoIdEditado.toLong()
            repository.atualizar(produto)
            produtoIdEditado = -1 // Limpa o ID do produto em edição
          } else {
            // Caso contrário, salve um novo produto
            repository.salvar(produto)
          }

          // Atualiza a lista de produtos
          listaProdutosState.value = repository.listarTodos()

          // Restaura o texto do cabeçalho para "Cadastro de produtos"
          textoCabecalho = "Cadastro: Itens Reciclagem"

          // Limpa os campos de entrada de texto
          nomeState = ""
          quantidadeState = ""
          dataState = ""
          disponivelState = false
        },
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.cor_primaria_app))
      ) {
        Text(text = "Salvar")
      }

      LazyColumn {
        items(listaProdutosState.value) { produto ->
          Card(
            modifier = Modifier
              .fillMaxWidth()
              .padding(top = 8.dp),
            colors = CardDefaults
              .cardColors(containerColor = Color(0xFFF4DFDE)),
            elevation = CardDefaults.cardElevation(15.dp)
          ) {
            Column(modifier = Modifier.padding(8.dp)) {
              Text(
                text ="Item de Reciclagem ",
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.cor_primaria_app),
                fontSize = 15.sp)

              Row {
                Text(text ="Nome item: ", fontWeight = FontWeight.Bold)
                Text(text = produto.nome)
              }
              Row{
                Text(text = "Data da coleta: ", fontWeight = FontWeight.Bold)
                Text(text = produto.dataValidade)
              }
              Row {
                Text(text = "Quantidade itens: ", fontWeight = FontWeight.Bold)
                Text(text = produto.quantidade.toString())
              }
              Row {
                Text(text = "Status: ", fontWeight = FontWeight.Bold)
                Text(text = if (produto.disponivel) "Entregue" else "Não Entregue")
              }


              Row {
                Button(onClick = {
                  // Define o ID do produto em edição
                  produtoIdEditado = produto.codigoProduto.toInt()

                  // Atualiza o texto do cabeçalho
                  textoCabecalho = "Edite o Produto para Reciclagem"

                  // Preenche os campos de entrada de texto com os dados do produto
                  nomeState = produto.nome
                  quantidadeState = produto.quantidade.toString()
                  dataState = produto.dataValidade
                  disponivelState = produto.disponivel
                },
                  colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.cor_primaria_app))) {
                  Text(text = "Editar")
                }
                Text(text = "  ")
                Button(onClick = {
                  val produtoRepository = ProdutoRepository(context)
                  produtoRepository.excluir(produto)
                  listaProdutosState.value = repository.listarTodos()
                },
                  colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.cor_primaria_app))) {
                  Text(text = "Excluir")
                }
              }
            }
          }
        }
      }
    }
  }


//@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CadastroScreenPreview() {
  Live2902Theme {
    CadastroScreen()
  }
}

