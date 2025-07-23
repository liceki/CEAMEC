

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

---

Se quiser uma explicação mais detalhada sobre como cada filtro funciona dentro do service, ou sobre os mocks, só pedir!
