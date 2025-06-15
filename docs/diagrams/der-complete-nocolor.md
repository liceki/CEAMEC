erDiagram

    %% --- Entidades de Base ---

    PESSOA {
        int id_pessoa PK "🔑 ID único para cada indivíduo"
        string nome_completo "Nome completo do indivíduo"
        string cpf "CPF do indivíduo (pode ser nulo inicialmente)"
        string email "Email principal (pode ser nulo)"
        date data_nascimento "Data de nascimento"
    }

    PESSOA_JURIDICA {
        int id_pj PK "🔑 ID único para cada pessoa jurídica"
        string cnpj "CNPJ da empresa (único)"
        string razao_social "Razão Social da empresa"
        string email_contato "Email de contato da empresa"
    }

    %% --- Entidades de Papel/Contexto ---

    USUARIO {
        int id_usuario PK "🔑 ID único do registro de usuário"
        int id_pessoa FK "🔗 Link para a pessoa que tem o acesso"
        string login "Login de acesso ao sistema"
        string senha_hash "Senha criptografada"
    }

    DOADOR {
        int id_doador PK "🔑 ID único do registro de doador"
        int id_pessoa FK "🔗 (Opcional) Link se o doador for pessoa física"
        int id_pj FK "🔗 (Opcional) Link se o doador for empresa"
    }

    CRIANCA {
        int id_crianca PK "🔑 ID do registro da criança no orfanato"
        int id_pessoa FK "🔗 Link para os dados pessoais da criança"
        string status "Ex: Acolhida, Em processo de adoção"
        string foto_perfil_url "🔗 URL da foto de perfil"
        text historico_medico "Informações médicas relevantes"
    }

    %% --- Entidades Transacionais ---

    DOACAO {
        int id_doacao PK "🔑 ID da doação"
        int id_doador FK "🔗 Doador que realizou"
        datetime data_hora "Data e hora da doação"
        string tipo_doacao "Ex: Dinheiro, Alimentos, Roupas"
        decimal valor "Valor monetário (se aplicável)"
        string comprovante_url "🔗 URL do comprovante (PDF, JPG)"
    }

    DOCUMENTO {
        int id_documento PK "🔑 ID único do documento"
        int id_crianca FK "🔗 Criança à qual o documento pertence"
        string tipo_documento "Ex: Certidão, RG, Carteira de Vacina"
        string documento_url "🔗 URL do arquivo escaneado (PDF, JPG)"
    }

    %% --- Relacionamentos ---

    PESSOA ||--o| USUARIO : "pode ser um"
    PESSOA ||--o| DOADOR : "pode ser um"
    PESSOA ||--o| CRIANCA : "pode ser uma"

    PESSOA_JURIDICA ||--o| DOADOR : "pode ser uma"

    DOADOR ||--o{ DOACAO : "realiza"
    CRIANCA ||--o{ DOCUMENTO : "possui"