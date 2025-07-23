
## 1. **Usuário Preenche os Filtros na Interface**

O usuário, na tela (ex: `PainelBuscaDoacao`), pode selecionar/preencher vários campos de filtro, como:
- Quantidade mínima e máxima
- Período de datas (inicial e final)
- Tipo de doação (ex: dinheiro, alimento, etc)
- Doadores específicos (selecionados a partir de uma sugestão/autocomplete)

---

## 2. **Usuário Clica em “Buscar”**

Ao clicar no botão de busca, a tela coleta os valores dos campos e monta um objeto `DoacaoFilterDTO` contendo todos esses critérios.

```java
DoacaoFilterDTO filtro = new DoacaoFilterDTO(
    qtdMin, // Double
    qtdMax, // Double
    dataMin, // LocalDateTime
    dataMax, // LocalDateTime
    tiposSelecionados, // List<TipoDoacao>
    doadorIds // List<Long>
);
```

---

## 3. **Chamada ao Service**

O painel chama o método do serviço de doações, passando o filtro:

```java
List<DoacaoDTO> resultados = doacaoService.buscarDoacoes(filtro);
```

---

## 4. **Service Monta o Filtro (Specification)**

No `DoacaoService`, o método `buscarDoacoes(filtro)` é chamado.  
Dentro desse método:

- É criada uma **Specification** (usando a API do Spring Data JPA) para transformar os critérios de filtro do DTO em restrições de consulta.
- Cada campo do filtro é checado:
  - Se não está nulo, adiciona-se uma restrição na Specification correspondente (ex: quantidade >= min, quantidade <= max, data >= inicial, tipo in lista, etc).

Exemplo simplificado:
```java
Specification<Doacao> spec = Specification.where(null);

if (filtro.getQtdMin() != null) {
    spec = spec.and((root, query, cb) -> cb.ge(root.get("quantidade"), filtro.getQtdMin()));
}
if (filtro.getQtdMax() != null) {
    spec = spec.and((root, query, cb) -> cb.le(root.get("quantidade"), filtro.getQtdMax()));
}
if (filtro.getDataInicial() != null) {
    spec = spec.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("data"), filtro.getDataInicial()));
}
if (filtro.getDataFinal() != null) {
    spec = spec.and((root, query, cb) -> cb.lessThanOrEqualTo(root.get("data"), filtro.getDataFinal()));
}
if (filtro.getTipos() != null && !filtro.getTipos().isEmpty()) {
    spec = spec.and((root, query, cb) -> root.get("tipo").in(filtro.getTipos()));
}
if (filtro.getDoadorIds() != null && !filtro.getDoadorIds().isEmpty()) {
    spec = spec.and((root, query, cb) -> root.get("doador").get("id").in(filtro.getDoadorIds()));
}
```

---

## 5. **Consulta ao Repositório**

O service chama o repository, passando a Specification criada:

```java
List<Doacao> doacoes = doacaoRepository.findAll(spec);
```

O repository executa uma query SQL correspondente aos critérios definidos na Specification e retorna as entidades `Doacao` que batem com o filtro.

---

## 6. **Conversão das Entidades em DTOs**

De volta ao service, as entidades retornadas são convertidas para DTOs para serem exibidas na tela.  
Isso é feito usando um Mapper (ex: MapStruct ou manual):

```java
return doacoes.stream()
    .map(doacaoMapper::toDTO)
    .collect(Collectors.toList());
```

---

## 7. **Exibição dos Resultados**

O painel recebe a lista de `DoacaoDTO` e preenche a tabela de resultados, mostrando as informações filtradas para o usuário.

---

## **Resumo Visual**

1. Usuário preenche filtros e clica “Buscar”
2. Tela monta um DTO de filtro e chama o service
3. Service transforma critérios em Specification (filtro JPA)
4. Repository executa a consulta com esses filtros
5. Service converte entidades em DTOs
6. Tela exibe a lista filtrada

