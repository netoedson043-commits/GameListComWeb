const STATUS_LABEL = {
  VOU_JOGAR: "Vou jogar",
  JOGANDO: "Jogando",
  CONCLUIDO: "Concluído",
  SEM_INFORMACOES: "Sem informações"
};

function mostrarMensagem(texto, tipo) {
  const el = document.getElementById("mensagem");
  el.innerHTML = `<div class="mensagem ${tipo}">${texto}</div>`;
  setTimeout(() => el.innerHTML = "", 3500);
}

// ---- navegação entre abas ----
document.querySelectorAll("nav button").forEach(btn => {
  btn.addEventListener("click", () => {
    document.querySelectorAll("nav button").forEach(b => b.classList.remove("active"));
    document.querySelectorAll(".painel").forEach(p => p.classList.remove("active"));
    btn.classList.add("active");
    document.getElementById("painel-" + btn.dataset.painel).classList.add("active");

    if (btn.dataset.painel === "favoritos") carregarFavoritos();
    if (btn.dataset.painel === "devs") carregarDevs();
  });
});

// ---- BUSCAR JOGOS ----
document.getElementById("formBusca").addEventListener("submit", async (e) => {
  e.preventDefault();
  const nome = document.getElementById("inputBusca").value.trim();
  if (!nome) return;

  const lista = document.getElementById("listaBusca");
  lista.innerHTML = "<li class='vazio'>Buscando...</li>";

  try {
    const resp = await fetch(`/api/jogos/buscar?nome=${encodeURIComponent(nome)}`);
    const jogos = await resp.json();

    if (!jogos.length) {
      lista.innerHTML = "<li class='vazio'>Nenhum jogo encontrado</li>";
      return;
    }

    lista.innerHTML = "";
    jogos.forEach(jogo => {
      const li = document.createElement("li");
      li.className = "item";
      li.innerHTML = `
        <img src="${jogo.imagem || ''}" onerror="this.style.visibility='hidden'">
        <div class="info">
          <div class="nome">${jogo.nome}</div>
          <div class="detalhe">${jogo.ano || "ano desconhecido"}</div>
        </div>
        <div class="acoes">
          <select>
            <option value="VOU_JOGAR">Vou jogar</option>
            <option value="JOGANDO">Jogando</option>
            <option value="CONCLUIDO">Concluído</option>
          </select>
          <button class="primario">Adicionar</button>
        </div>
      `;
      li.querySelector("button").addEventListener("click", async () => {
        const status = li.querySelector("select").value;
        try {
          const r = await fetch(`/api/jogos/favoritos/${jogo.id}?status=${status}`, { method: "POST" });
          if (!r.ok) {
            const msg = await r.text();
            mostrarMensagem(msg || "Erro ao adicionar", "erro");
            return;
          }
          mostrarMensagem(`"${jogo.nome}" adicionado aos favoritos`, "ok");
        } catch {
          mostrarMensagem("Erro de conexão com o servidor", "erro");
        }
      });
      lista.appendChild(li);
    });
  } catch {
    lista.innerHTML = "<li class='vazio'>Erro ao buscar jogos</li>";
  }
});

// ---- FAVORITOS ----
async function carregarFavoritos() {
  const lista = document.getElementById("listaFavoritos");
  lista.innerHTML = "<li class='vazio'>Carregando...</li>";

  const resp = await fetch("/api/favoritos");
  const favoritos = await resp.json();

  if (!favoritos.length) {
    lista.innerHTML = "<li class='vazio'>Nenhum jogo favoritado ainda</li>";
    return;
  }

  lista.innerHTML = "";
  favoritos.forEach(fav => {
    const li = document.createElement("li");
    li.className = "item";
    li.innerHTML = `
      <img src="${fav.imagem || ''}" onerror="this.style.visibility='hidden'">
      <div class="info">
        <div class="nome">${fav.nome}</div>
        <div class="detalhe">${fav.desenvolvedor ? fav.desenvolvedor.nome : ""} • ${fav.ano || ""}</div>
      </div>
      <div class="acoes">
        <select>
          ${Object.entries(STATUS_LABEL).map(([v, l]) =>
            `<option value="${v}" ${fav.statusJogo === v ? "selected" : ""}>${l}</option>`).join("")}
        </select>
        <button class="perigo">Remover</button>
      </div>
    `;
    li.querySelector("select").addEventListener("change", async (e) => {
      await fetch(`/api/favoritos/${fav.id}/status?status=${e.target.value}`, { method: "PUT" });
      mostrarMensagem("Status atualizado", "ok");
    });
    li.querySelector("button").addEventListener("click", async () => {
      await fetch(`/api/favoritos/${fav.id}`, { method: "DELETE" });
      mostrarMensagem(`"${fav.nome}" removido`, "ok");
      carregarFavoritos();
    });
    lista.appendChild(li);
  });
}

// ---- DESENVOLVEDORAS ----
async function carregarDevs() {
  const lista = document.getElementById("listaDevs");
  document.getElementById("listaJogosDev").innerHTML = "";
  lista.innerHTML = "<li class='vazio'>Carregando...</li>";

  const resp = await fetch("/api/desenvolvedoras");
  const devs = await resp.json();

  if (!devs.length) {
    lista.innerHTML = "<li class='vazio'>Nenhuma desenvolvedora cadastrada</li>";
    return;
  }

  lista.innerHTML = "";
  devs.forEach(dev => {
    const li = document.createElement("li");
    li.className = "item";
    li.innerHTML = `
      <div class="info">
        <div class="nome">${dev.nome}</div>
      </div>
      <div class="acoes">
        <button>Ver jogos</button>
        <button class="perigo">Remover</button>
      </div>
    `;
    const [btnVer, btnRemover] = li.querySelectorAll("button");
    btnVer.addEventListener("click", () => carregarJogosDaDev(dev.id, dev.nome));
    btnRemover.addEventListener("click", async () => {
      const r = await fetch(`/api/desenvolvedoras/${dev.id}`, { method: "DELETE" });
      if (!r.ok) {
        mostrarMensagem(await r.text(), "erro");
        return;
      }
      mostrarMensagem(`"${dev.nome}" removida`, "ok");
      carregarDevs();
    });
    lista.appendChild(li);
  });
}

async function carregarJogosDaDev(id, nomeDev) {
  const lista = document.getElementById("listaJogosDev");
  lista.innerHTML = "<li class='vazio'>Carregando...</li>";

  const resp = await fetch(`/api/desenvolvedoras/${id}/jogos`);
  const jogos = await resp.json();

  if (!jogos.length) {
    lista.innerHTML = `<li class='vazio'>${nomeDev} não tem jogos favoritados</li>`;
    return;
  }

  lista.innerHTML = "";
  jogos.forEach(j => {
    const li = document.createElement("li");
    li.className = "item";
    li.innerHTML = `<div class="info"><div class="nome">${j.nome}</div></div>`;
    lista.appendChild(li);
  });
}