```mermaid
classDiagram
    direction LR

    class Pessoa {
        <<Abstract>>
        -id: int
        -nomeCompleto: String
        -email: String
        -endereco: Endereco
    }

    class PessoaFisica {
        +cpf: String
        +dataNascimento: Date
    }

    class PessoaJuridica {
        +id_pj: int
        +cnpj: String
        +razaoSocial: String
        -endereco: Endereco
    }

    class Endereco {
        -id: int
        -logradouro: String
        -numero: String
        -complemento: String
        -bairro: String
        -cidade: String
        -estado: String
        -cep: String
    }

    class IDoador {
        <<Interface>>
        +realizarDoacao(doacao): void
        +getHistoricoDoacoes(): List~Doacao~
    }

    class Usuario {
        <<Entity>>
        -login: String
        -senhaHash: String
        +autenticar(senha): boolean
    }

    class Crianca {
        <<Entity>>
        -fotoPerfilUrl: String
        -historicoMedico: String
        +adicionarDocumento(doc): void
    }

    class Doacao {
        <<Entity>>
        -id: int
        -dataHora: Datetime
        -valor: double
        -comprovanteUrl: String
    }
    
    class Documento {
        <<Entity>>
        -id: int
        -tipoDocumento: String
        -documentoUrl: String
    }

    Pessoa "1" *-- "1" Endereco
    PessoaJuridica "1" *-- "1" Endereco

    Pessoa <|-- PessoaFisica
    PessoaFisica <|-- Usuario
    PessoaFisica <|-- Crianca
    
    PessoaFisica ..|> IDoador
    PessoaJuridica ..|> IDoador

    IDoador "1" -- "0..*" Doacao : "realiza >"
    Crianca "1" --> "0..*" Documento : "possui >"
```
