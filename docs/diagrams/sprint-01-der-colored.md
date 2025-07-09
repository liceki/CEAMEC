```mermaid
erDiagram

    ENDERECO {
        int id_endereco PK "🔑 ID único do endereço"
        string logradouro "Rua, Avenida, etc."
        string numero "Número"
        string complemento "Apto, Bloco, etc."
        string bairro "Bairro"
        string cidade "Cidade"
        string estado "UF"
        string cep "CEP"
    }

    PESSOA {
        int id_pessoa PK "🔑 ID único para cada indivíduo"
        string nome_completo "Nome completo"
        string cpf "CPF do indivíduo"
        string email "Email principal"
        date data_nascimento "Data de nascimento"
    }

    USUARIO {
        int id_usuario PK "🔑 ID único do usuário"
        int id_pessoa FK "🔗 Link para a pessoa"
        string login "Login de acesso"
        string senha_hash "Senha criptografada"
    }

    PESSOA_JURIDICA {
        int id_pj PK "🔑 ID único para cada pessoa jurídica"
        string cnpj "CNPJ da empresa (único)"
        string razao_social "Razão Social"
        string email_contato "Email de contato"
    }

    DOADOR {
        int id_doador PK "🔑 ID único do doador"
        int id_pessoa FK "🔗 (Opcional) Pessoa física"
        int id_pj FK "🔗 (Opcional) Pessoa jurídica"
    }


   

    PESSOA }|--|| ENDERECO : "possui"
    PESSOA_JURIDICA }|--|| ENDERECO : "possui"
    PESSOA ||--o{ USUARIO : "pode ser um"
    PESSOA ||--o{ DOADOR : "pode ser um"
    PESSOA_JURIDICA ||--o{ DOADOR : "pode ser uma"

    classDef base fill:#B3E5FC,stroke:#01579B,stroke-width:2px,color:black
    classDef roles fill:#C8E6C9,stroke:#1B5E20,stroke-width:2px,color:black
    classDef transactions fill:#FFF9C4,stroke:#FF6F00,stroke-width:2px,color:black
    class PESSOA,PESSOA_JURIDICA,ENDERECO base
    class USUARIO,DOADOR,CRIANCA roles
    class DOACAO,DOCUMENTO transactions
```
