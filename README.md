# 🌿 iNature

**Tecnologia a favor da vida e do meio ambiente.**

## 👥 Equipe

- Matheus Esteves (RM554769)  
- Arthur Spedine (RM554489)  
- Gabriel Falanga (RM555061)

---

## 🎯 Desafio

O mundo enfrenta cada vez mais **eventos extremos da natureza** — enchentes, queimadas, desabamentos e apagões. O **Desafio FIAP: Eventos Extremos** nos convida a usar tecnologia, inovação e boas ideias para **ajudar pessoas**, **proteger o meio ambiente** e **prevenir problemas ainda maiores**.

---

## 💡 O Projeto

O **iNature** é uma plataforma digital voltada para sustentabilidade e prevenção de desastres naturais. Com ela, os usuários poderão:

- 📍 Verificar a **probabilidade de enchentes** na sua região  
- 🚨 Reportar **enchentes**, **queimadas**, **desabamentos** e **falta de energia**  
- 📰 Ler e publicar **notícias sustentáveis** (para perfis do tipo `JORNALISTA`)  
- 📊 Acompanhar dados e alertas de risco de forma clara, acessível e útil

Tudo isso em um só lugar, com foco em **prevenção, conscientização e engajamento social**.

---

## 🧑‍🤝‍🧑 Público-Alvo

Nosso público inclui:

- Moradores de áreas urbanas suscetíveis a desastres naturais  
- Cidadãos preocupados com o meio ambiente  
- Agentes da defesa civil  
- ONGs e jornalistas ambientais  

> 🧠 Segundo o IBGE (2022), mais de **8 milhões de brasileiros** vivem em áreas de risco.  
> 🚨 Estudos da ANA mostram que alertas antecipados podem reduzir em até **45% os danos** causados por enchentes.

---

## 🛠️ Arquitetura da Solução

- **Backend:** Java/Spring Boot com autenticação JWT e controle de acesso por roles (`JORNALISTA`, `USUARIO`, etc.)  
- **Frontend:** Aplicação em React Native  
- **Banco de Dados:** Relacional, com registro de ocorrências, usuários e notícias  
- **APIs externas:** para previsão de enchentes e dados climáticos  
- **Armazenamento:** Supabase Storage para imagens e conteúdos
- **Deploy:** Máquina virtual da Azure
- 
---

## ✅ Impacto Esperado

📍 A meta é impactar diretamente **1 milhão de pessoas** nas regiões mais críticas nos **primeiros 2 anos** de uso da plataforma.

🔄 Além disso, o iNature busca **educar e engajar** a população para um futuro mais sustentável e resiliente.

---

## 🚀 Conclusão

O **iNature** é mais do que uma solução tecnológica. É um movimento para criar cidades mais preparadas, pessoas mais informadas e um planeta mais protegido.

> 🌱 Grandes ideias nascem dos grandes desafios. E com o iNature, damos um passo real rumo a um futuro mais consciente e sustentável.

## 🏁 Como Rodar o Projeto

Para rodar o projeto localmente ou em ambiente de testes, utilize os seguintes usuários cadastrados:

| Tipo       | Email                | Senha  |
|------------|----------------------|--------|
| Usuário    | user@email.com       | 12345  |
| Jornalista | jornalista@email.com  | 12345  |

### Passos básicos:

**Localmente:**

1. Clone o repositório.  
2. Configure as variáveis de ambiente para banco de dados, JWT e Supabase.  
3. Execute o backend Spring Boot

**Em deploy:**
1. Acesse a url da aplicação
2. Realize o login e já pode realizar as operações desejadas

