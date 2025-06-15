classDiagram
    direction LR

    class Pessoa:::base {
        <<Abstract>>
        -id: int
        -nomeCompleto: String
        -email: String
        -endereco: Endereco
    }

    class PessoaFisica:::base {
        +cpf: String
        +dataNascimento: Date
    }

    class PessoaJuridica:::base {
        +id_pj: int
        +cnpj: String
        +razaoSocial: String
        -endereco: Endereco
    }

    class Endereco:::base {
        -id: int
        -logradouro: String
        -numero: String
        -complemento: String
        -bairro: String
        -cidade: String
        -estado: String
        -cep: String
    }

    class IDoador:::interface{
        <<Interface>>
        +realizarDoacao(doacao): void
        +getHistoricoDoacoes(): List~Doacao~
    }

    class Usuario:::entities {
        <<Entity>>
        -login: String
        -senhaHash: String
        +autenticar(senha): boolean
    }

    Pessoa "1" *-- "1" Endereco
    PessoaJuridica "1" *-- "1" Endereco

    Pessoa <|-- PessoaFisica
    PessoaFisica <|-- Usuario
    
    PessoaFisica ..|> IDoador
    PessoaJuridica ..|> IDoador
    
    classDef base fill:#BBDEFB,stroke:#0D47A1,stroke-width:2px,color:black
    classDef interface fill:#E1BEE7,stroke:#4A148C,stroke-width:2px,color:black
    classDef entities fill:#C8E6C9,stroke:#1B5E20,stroke-width:2px,color:black

