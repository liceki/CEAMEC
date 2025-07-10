1. Cadastro de Doação com possível cadastro de Doador
```Mermaid
flowchart TD
    INICIO([Início]) --> VERIFICA{Doador já cadastrado?}
    VERIFICA -- Sim --> PREENCHEDOA[Preencher dados da doação]
    VERIFICA -- Não --> PREENCHEDOR[Preencher dados do doador]
    PREENCHEDOR --> SALVADOR[Salvar doador no sistema]
    SALVADOR --> PREENCHEDOA
    PREENCHEDOA --> SALVADOA[Salvar doação no sistema]
    SALVADOA --> FIM([Fim])
```
2. Cadastro de Doador (único)
```Mermaid
flowchart TD
    INICIO([Início]) --> PREENCHEDOR[Preencher dados do doador]
    PREENCHEDOR --> VALIDACAO{Dados válidos?}
    VALIDACAO -- Não --> ERRO[Exibir erro e solicitar correção]
    ERRO --> PREENCHEDOR
    VALIDACAO -- Sim --> SALVADOR[Salvar doador no sistema]
    SALVADOR --> FIM([Fim])
```
3. Cadastro de Doação para Doador Existente
```Mermaid
flowchart TD
    INICIO([Início]) --> BUSCA[Buscar doador]
    BUSCA --> SELECIONA{Doador encontrado?}
    SELECIONA -- Não --> ERRO[Exibir erro: doador não encontrado]
    ERRO --> FIM([Fim])
    SELECIONA -- Sim --> PREENCHEDOA[Preencher dados da doação]
    PREENCHEDOA --> VALIDACAO{Dados válidos?}
    VALIDACAO -- Não --> ERRO2[Exibir erro e solicitar correção]
    ERRO2 --> PREENCHEDOA
    VALIDACAO -- Sim --> SALVADOA[Salvar doação no sistema]
    SALVADOA --> FIM
```
4. Fluxo de Validação de Dados no Cadastro
```Mermaid
flowchart TD
    INICIO([Início do cadastro]) --> PREENCHIMENTO[Usuário preenche formulário]
    PREENCHIMENTO --> VALIDACAO{Dados obrigatórios preenchidos?}
    VALIDACAO -- Não --> ERRO[Exibe mensagem de erro]
    ERRO --> PREENCHIMENTO
    VALIDACAO -- Sim --> CONTINUA[Prossegue para salvamento]
    CONTINUA --> FIM([Fim do fluxo])
```
5. Cadastro com Tratamento de Exceção no Banco
```Mermaid
flowchart TD
    INICIO([Início do cadastro]) --> TENTA[Sistema tenta salvar dados]
    TENTA --> SUCESSO{Salvo com sucesso?}
    SUCESSO -- Sim --> MENSAGEMOK[Exibe mensagem de sucesso]
    MENSAGEMOK --> FIM([Fim])
    SUCESSO -- Não --> EXCECAO[Tratar exceção e exibir mensagem de erro]
    EXCECAO --> FIM
```
