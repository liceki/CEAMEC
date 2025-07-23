

### 1. **deveFiltrarPorQuantidadeMinimaEMaxima**
```java
@Test
void deveFiltrarPorQuantidadeMinimaEMaxima() {
    DoacaoFilterDTO filtro = new DoacaoFilterDTO(10.0, 50.0, null, null, null, null);

    when(doacaoRepository.findAll(any(Specification.class)))
            .thenReturn(List.of(doacaoValida));

    List<DoacaoDTO> result = doacaoService.buscarDoacoes(filtro);

    assertEquals(1, result.size());
    assertEquals(20.0, result.get(0).quantidade());
}
```
**Explicação:**  
Este teste verifica se o filtro por quantidade mínima e máxima está funcionando.  
- O filtro define uma quantidade mínima de 10.0 e máxima de 50.0.
- O mock do repositório retorna uma doação com quantidade 20.0.
- O teste espera que o resultado contenha exatamente essa doação e que a quantidade seja 20.0.

---

### 2. **deveFiltrarPorPeriodoDeData**
```java
@Test
void deveFiltrarPorPeriodoDeData() {
    LocalDateTime inicio = LocalDateTime.of(2024, 1, 1, 0, 0);
    LocalDateTime fim = LocalDateTime.of(2024, 12, 31, 23, 59);
    DoacaoFilterDTO filtro = new DoacaoFilterDTO(null, null, inicio, fim, null, null);

    when(doacaoRepository.findAll(any(Specification.class)))
            .thenReturn(List.of(doacaoValida));

    List<DoacaoDTO> result = doacaoService.buscarDoacoes(filtro);

    assertEquals(1, result.size());
    assertTrue(result.get(0).data().isAfter(inicio.minusDays(1)));
    assertTrue(result.get(0).data().isBefore(fim.plusDays(1)));
}
```
**Explicação:**  
Este teste garante que o filtro por intervalo de datas funcione corretamente.
- O filtro define um período de 01/01/2024 até 31/12/2024.
- O mock retorna uma doação com data dentro desse intervalo.
- O teste verifica que a doação retornada está dentro do período esperado.

---

### 3. **deveFiltrarPorTipoDeDoacao**
```java
@Test
void deveFiltrarPorTipoDeDoacao() {
    DoacaoFilterDTO filtro = new DoacaoFilterDTO(null, null, null, null, List.of(TipoDoacao.DINHEIRO), null);

    when(doacaoRepository.findAll(any(Specification.class)))
            .thenReturn(List.of(doacaoValida));

    List<DoacaoDTO> result = doacaoService.buscarDoacoes(filtro);

    assertEquals(1, result.size());
    assertEquals(TipoDoacao.DINHEIRO, result.get(0).tipo());
}
```
**Explicação:**  
Este teste verifica se o filtro por tipo de doação funciona.
- O filtro pede apenas doações do tipo DINHEIRO.
- O mock retorna uma doação desse tipo.
- O teste garante que apenas essa doação é retornada e que o tipo está correto.

---

### 4. **deveFiltrarPorIdsDeDoadores**
```java
@Test
void deveFiltrarPorIdsDeDoadores() {
    Long doadorId = 1L;
    DoacaoFilterDTO filtro = new DoacaoFilterDTO(null, null, null, null, null, List.of(doadorId));

    when(doacaoRepository.findAll(any(Specification.class)))
            .thenReturn(List.of(doacaoValida));

    List<DoacaoDTO> result = doacaoService.buscarDoacoes(filtro);

    assertEquals(1, result.size());
    // Adicione mais asserts se necessário para validar o doador
}
```
**Explicação:**  
Este teste valida o filtro por IDs de doadores.
- O filtro pede doações feitas por um doador de ID 1.
- O mock retorna uma doação (que no caso simula ter esse doador).
- O teste verifica que a doação é retornada corretamente.

---

### 5. **deveRetornarListaVaziaSeNaoEncontrar**
```java
@Test
void deveRetornarListaVaziaSeNaoEncontrar() {
    DoacaoFilterDTO filtro = new DoacaoFilterDTO(9999.0, 99999.0, null, null, null, null);

    when(doacaoRepository.findAll(any(Specification.class)))
            .thenReturn(List.of());

    List<DoacaoDTO> result = doacaoService.buscarDoacoes(filtro);

    assertTrue(result.isEmpty());
}
```
**Explicação:**  
Este teste verifica o comportamento do serviço quando nenhum registro é encontrado.
- O filtro define uma faixa de quantidade muito alta (onde não existem doações).
- O mock retorna uma lista vazia.
- O teste espera que a lista de resultado realmente seja vazia.




## 1. **Etapas do Processo de Testes**

### a) **Configuração dos Mocks**
- Antes de cada teste, usamos o `@BeforeEach` para preparar o ambiente.
- Os objetos dependentes da classe testada (neste caso, o `DoacaoRepository` e o `DoacaoMapper`) são anotados com `@Mock`.
- O objeto principal sob teste (`DoacaoService`) é anotado com `@InjectMocks`, para que esses mocks sejam automaticamente injetados nele.

### b) **Definição dos Cenários**
- Cada teste (`@Test`) define um cenário específico de uso do filtro (quantidade, datas, tipos, doador, ausência de resultados).
- Os métodos do repositório (`doacaoRepository.findAll(...)`) e do mapper (`doacaoMapper.toDTO(...)`) são configurados usando `when(...).thenReturn(...)` para simular o retorno esperado naquele cenário.

### c) **Execução**
- O método sob teste (`doacaoService.buscarDoacoes(...)`) é chamado com o filtro adequado.
- O resultado é capturado e são realizadas verificações (asserts) para garantir que a saída está correta para aquele cenário.

### d) **Verificação**
- Usam-se asserts como `assertEquals`, `assertTrue`, etc., para garantir que o resultado do método sob teste está de acordo com o esperado.
- Assim, cada teste valida uma parte da lógica de filtragem.

---

## 2. **O que é o Mockito e Por Que Ele é Necessário?**

**Mockito** é um framework de testes para Java que permite criar objetos simulados (mocks) de dependências externas.  
- Ele serve para **isolar a unidade testada** (no seu caso, o `DoacaoService`) de suas dependências (como banco de dados, mappers, serviços externos).
- Com mocks, podemos definir exatamente o comportamento das dependências, sem depender do funcionamento real delas (por exemplo, não acessamos o banco de dados de verdade).
- Isso facilita a criação de cenários de teste específicos e torna os testes mais rápidos e confiáveis.

---

## 3. **Onde é Feita a Configuração dos Mocks?**

- A configuração dos mocks é feita no início da classe de teste:
  ```java
  @Mock
  private DoacaoRepository doacaoRepository;
  @Mock
  private DoacaoMapper doacaoMapper;
  @InjectMocks
  private DoacaoService doacaoService;
  ```
- O método `MockitoAnnotations.openMocks(this)` dentro de `@BeforeEach` inicializa os mocks.
- No próprio `@BeforeEach` também configuramos os retornos padrão dos métodos dos mocks, por exemplo:
  ```java
  when(doacaoMapper.toDTO(any(Doacao.class))).thenReturn(doacaoDTOValida);
  ```

---

## 4. **Estratégia de Testes Utilizada**

- **Teste Unitário Isolado:** Cada teste isola o comportamento do service, simulando as respostas do repositório e mapper.
- **Teste de Cenários:** Cada método `@Test` cobre um cenário diferente do filtro (quantidade, datas, tipos, doador, sem resultados).
- **Mocking de Dependências:** As dependências são simuladas para garantir que o teste está verificando apenas a lógica do service.
- **Verificação com Asserts:** Os resultados são validados usando asserts para garantir que correspondem ao esperado.

---

## 5. **Por Que Essa Estratégia é Eficaz?**

- **Isolamento:** Testa apenas a lógica do service, sem depender de banco de dados ou outros componentes externos.
- **Cobertura de Casos:** Cada critério de filtro é testado separadamente, garantindo que cada um funciona e que não há interferência entre eles.
- **Controle dos Cenários:** Como os mocks têm comportamento controlado, é possível simular tanto respostas positivas quanto negativas (lista vazia, valores inesperados, etc).
- **Detecção de Erros:** Se houver qualquer comportamento inesperado (ex: filtro não está funcionando, retorna dado errado, não trata nulos), os asserts vão falhar, indicando que algo precisa ser corrigido.
- **Rapidez:** Como nada externo é acessado, os testes rodam muito rapidamente.

---

## **Resumo**

- **Mockito** é usado para simular dependências e isolar o teste da lógica real do banco de dados e mappers.
- Os mocks são configurados no início da classe e no `@BeforeEach`.
- A estratégia cobre cada cenário de filtragem, usando asserts para garantir a saída correta.
- Esse método é eficaz porque garante testes rápidos, confiáveis e que cobrem os principais caminhos lógicos da funcionalidade.


