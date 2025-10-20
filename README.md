# 🛠️ Painel de Utilidades

<div align="center">

![Java](https://img.shields.io/badge/Java-17+-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Gson](https://img.shields.io/badge/Gson-Google-4285F4?style=for-the-badge&logo=google&logoColor=white)
![APIs](https://img.shields.io/badge/APIs-Múltiplas-brightgreen?style=for-the-badge)
![Status](https://img.shields.io/badge/Status-Concluído-4CAF50?style=for-the-badge)

Um hub multifuncional desenvolvido em **Java** que integra três APIs distintas para fornecer serviços de **conversão de moedas**, **consulta meteorológica** e **busca de filmes** — tudo através de uma interface de console elegante e intuitiva.

*Projeto desenvolvido com foco em boas práticas de programação Java e arquitetura modular.*

</div>

---

## 📖 Sobre o Projeto

O **Painel de Utilidades** é um aplicativo de console multifuncional que serve como um hub central para acessar diferentes serviços web em tempo real. O projeto demonstra a integração eficiente de múltiplas APIs externas com uma arquitetura limpa e organizada.

### 🎯 Conceitos e Boas Práticas Aplicadas

- ✅ **Programação Orientada a Objetos (OOP)**
- ✅ **Organização por Funcionalidade** (Package-by-Feature)
- ✅ **Injeção de Dependência Manual** para desacoplamento
- ✅ **Princípio da Responsabilidade Única (SRP)**
- ✅ **Records do Java** para modelagem de dados (DTOs)
- ✅ **Separação de Concerns** entre módulos
- ✅ **Reutilização de Código** com cliente HTTP compartilhado

---

## ✨ Funcionalidades Principais

### 💱 [1] Conversor de Moeda
- Utiliza a **ExchangeRate-API** para cotações em tempo real
- Suporta conversões entre BRL, USD, EUR e ARS
- Exibe taxa de câmbio atualizada
- Registra todas as conversões no histórico

### 🌤️ [2] Consultor de Clima
- Integração com **WeatherAPI** para dados meteorológicos
- Consulta clima atual de qualquer cidade
- Previsão futura para datas específicas
- Informações detalhadas: temperatura, condições e umidade

### 🎬 [3] Buscador de Filmes
- Powered by **OMDb API** (The Open Movie Database)
- Ficha técnica completa com:
  - Sinopse detalhada
  - Elenco e diretor
  - Avaliações (IMDb e Rotten Tomatoes)
  - Gênero e duração
- Interface estilizada para exibição dos resultados

### 📜 [4] Histórico Centralizado
- Registro automático de todas as consultas bem-sucedidas
- Persistência em arquivo local (`historico.txt`)
- Timestamp completo de cada operação
- Visualização organizada por tipo de serviço

---

## 📺 Demonstração

### Menu Principal
```
╔════════════════════════════════════════╗
║     🛠️ PAINEL DE UTILIDADES 🛠️        ║
╚════════════════════════════════════════╝

[1] 💱 Conversor de Moeda
[2] 🌤️ Consultar Clima
[3] 🎬 Buscar Filme
[4] 📜 Ver Histórico de Pesquisas
[0] ❌ Sair

👉 Escolha uma opção: 
```

### Exemplo: Busca de Filme
```
Digite o nome do filme para busca (ou '0' para voltar): Interstellar

═══════════════════════════════════════════════
            🎬 FICHA TÉCNICA 🎬
═══════════════════════════════════════════════
Filme: Interstellar (2014)
Gênero: Adventure, Drama, Sci-Fi
Duração: 169 min
───────────────────────────────────────────────
Diretor: Christopher Nolan
Atores: Matthew McConaughey, Anne Hathaway, Jessica Chastain
───────────────────────────────────────────────
Sinopse: A team of explorers travel through a wormhole in space 
in an attempt to ensure humanity's survival.
───────────────────────────────────────────────
Avaliações:
  ⭐ IMDb: 8.7/10
  🍅 Rotten Tomatoes: 73%
═══════════════════════════════════════════════
```

### Exemplo: Histórico
```
═══════════════════════════════════════════════
        📜 Histórico Geral de Operações
═══════════════════════════════════════════════

[19/10/2025 14:30:15] [MOEDA] Conversão: 100.00 BRL → 18.20 USD (Taxa: 0.1820)
[19/10/2025 14:31:02] [CLIMA] Busca Atual: 'São Paulo' → Encontrado: São Paulo, 22.0°C, Parcialmente nublado
[19/10/2025 14:31:45] [FILME] Busca: 'Interstellar' → Encontrado: Interstellar (2014)

═══════════════════════════════════════════════
```

---

## 🛠️ Tecnologias Utilizadas

| Tecnologia | Descrição |
|------------|-----------|
| **Java 17+** | Linguagem principal do projeto |
| **Gson** | Biblioteca Google para parsing de JSON |
| **HttpClient** | Cliente HTTP nativo do Java |
| **ExchangeRate-API** | Dados de câmbio em tempo real |
| **WeatherAPI** | Informações meteorológicas |
| **OMDb API** | Base de dados de filmes |

---

## 📂 Estrutura do Projeto
```
src/
└── painel/
    └── utilidades/
        ├── principal/
        │   └── Main.java                        # Ponto de entrada e menu principal
        │
        ├── http/
        │   └── ClienteHttp.java                 # Cliente HTTP reutilizável
        │
        ├── historico/
        │   └── GerenciadorDeHistorico.java      # Gerenciamento de logs
        │
        └── modulos/
            ├── filme/                            # Módulo de Filmes
            │   ├── BuscadorDeFilmes.java         # Lógica de busca
            │   ├── FilmeInfo.java                # Record de dados
            │   └── Rating.java                   # Record de avaliações
            │
            ├── clima/                            # Módulo de Clima
            │   ├── ConsultorDeClima.java         # Lógica de consulta
            │   ├── ClimaInfo.java                # Record clima atual
            │   └── ClimaFuturo.java              # Record previsão
            │
            └── moeda/                            # Módulo de Moedas
                ├── ConversorDeMoeda.java         # Lógica de conversão
                └── ConversaoMoeda.java           # Record de conversão
```

---

## 🚀 Como Executar

### Pré-requisitos
- ☕ **Java JDK 17** ou superior instalado
- 📦 **Biblioteca Gson** adicionada ao classpath
- 🔑 **Três chaves de API** (gratuitas)

### 1️⃣ Obtenha suas Chaves de API

Registre-se nos seguintes serviços para obter as chaves gratuitas:

| Serviço | URL | Uso |
|---------|-----|-----|
| **ExchangeRate-API** | https://www.exchangerate-api.com/ | Conversão de moedas |
| **WeatherAPI** | https://www.weatherapi.com/ | Dados meteorológicos |
| **OMDb API** | http://www.omdbapi.com/apikey.aspx | Informações de filmes |

### 2️⃣ Configure as Chaves no Código

Insira suas chaves nos arquivos correspondentes:

**💱 Moedas:**
```java
// Arquivo: painel/utilidades/modulos/moeda/ConversorDeMoeda.java
private static final String API_KEY = "SUA_CHAVE_AQUI";
```

**🌤️ Clima:**
```java
// Arquivo: painel/utilidades/modulos/clima/ConsultorDeClima.java
private static final String API_KEY = "SUA_CHAVE_AQUI";
```

**🎬 Filmes:**
```java
// Arquivo: painel/utilidades/modulos/filme/BuscadorDeFilmes.java
private static final String API_KEY = "SUA_CHAVE_AQUI";
```

### 3️⃣ Clone e Execute
```bash
# Clone o repositório
git clone https://github.com/seu-usuario/painel-de-utilidades.git

# Entre na pasta do projeto
cd painel-de-utilidades

# Abra na sua IDE favorita (IntelliJ, Eclipse, VS Code)
# Garanta que o Gson.jar está no classpath
# Execute a classe Main.java
```

---

## 🎯 Próximas Melhorias

- [ ] Adicionar mais opções de moedas
- [ ] Implementar cache de requisições
- [ ] Criar interface gráfica (GUI) com JavaFX
- [ ] Adicionar busca de séries de TV
- [ ] Exportar histórico em diferentes formatos (CSV, JSON)
- [ ] Implementar sistema de favoritos
- [ ] Adicionar gráficos de tendências
- [ ] Criar testes unitários

---

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

---

## 👨‍💻 Autor

<div align="center">

**Desenvolvido com ❤️ por Willian Brendo**

[![GitHub](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/willianbrendo-dev)
[![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](www.linkedin.com/in/willian-brendo-alves-batista)

*💼 Projeto desenvolvido com propósito educacional e demonstração de boas práticas*

</div>

---

## 🌟 Agradecimentos

- **ExchangeRate-API** pela API de câmbio gratuita
- **WeatherAPI** pelos dados meteorológicos
- **OMDb** pelo acesso à base de dados de filmes
- **Google Gson** pela excelente biblioteca de JSON

---

<div align="center">

⭐ **Se este projeto foi útil para você, considere deixar uma estrela!** ⭐

*Feito com ☕ e 💻 | Painel de Utilidades © 2025*

</div>
