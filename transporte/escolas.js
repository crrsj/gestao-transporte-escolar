// 1. Abre o modal de escola
function abrirModalEscola() {
    document.getElementById('modal-escola').classList.remove('hidden');
}

// 2. Fecha o modal de escola e limpa o formulário
function fecharModalEscola() {
    document.getElementById('modal-escola').classList.add('hidden');
    document.getElementById('form-escola').reset();
}

// 3. Envia os dados da escola via POST para a API
async function salvarEscola(event) {
    event.preventDefault();

    // Mapeando exatamente com os atributos do seu EscolaDTO
    const escolaDTO = {
        nomeEscola: document.getElementById('escola-nome').value,
        telefoneEscola: document.getElementById('escola-telefone').value
    };

    try {
        const response = await fetch(`${API_URL}/escolas`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(escolaDTO)
        });

        if (response.ok) {
            alert('Escola cadastrada com sucesso!');
            fecharModalEscola();
            // Se já tiver a função de listagem de escolas, chame aqui:
             carregarEscolas();
        } else {
            const err = await response.json();
            alert('Erro ao cadastrar escola: ' + (err.message || 'Verifique os dados digitados.'));
        }
    } catch (e) {
        console.error("Erro ao cadastrar escola:", e);
        alert("Erro de conexão com o servidor.");
    }
}

async function carregarEscolas() {
    try {
        const response = await fetch(`${API_URL}/escolas`);
        
        if (!response.ok) {
            throw new Error('Erro ao buscar escolas na API.');
        }

        const data = await response.json();
        const tbody = document.getElementById('tabela-escolas-body');
        if (!tbody) return; // Segurança caso a tabela não esteja na página atual
        
        tbody.innerHTML = ''; // Limpa a tabela antes de preencher
        
        // Verifica se os dados vêm em formato de página (.content) ou lista direta
        const listaEscolas = data.content || data;

        if (!listaEscolas || listaEscolas.length === 0) {
            tbody.innerHTML = `
                <tr>
                    <td colspan="3" class="p-4 text-center text-zinc-500">Nenhuma escola cadastrada.</td>
                </tr>
            `;
            return;
        }

        // Itera sobre as escolas e monta as linhas da tabela
        listaEscolas.forEach(escola => {
            tbody.innerHTML += `
                <tr class="hover:bg-zinc-300 transition">
                    <td class="p-4 font-medium text-zinc-900">${escola.nomeEscola || '-'}</td>
                    <td class="p-4">${escola.telefoneEscola || '-'}</td>
                    <td class="p-4 text-right">
                        <button onclick="abrirModalEditarEscola(${escola.id})" class="text-amber-600 hover:text-amber-800 transition mr-3" title="Editar">
                            <i class="fa-solid fa-pencil"></i>
                        </button>
                        <button onclick="deletarEscola(${escola.id})" class="text-red-600 hover:text-red-800 transition" title="Excluir">
                            <i class="fa-solid fa-trash"></i>
                        </button>
                    </td>
                </tr>
            `;
        });
    } catch (e) {
        console.error("Erro ao carregar escolas:", e);
        const tbody = document.getElementById('tabela-escolas-body');
        if (tbody) {
            tbody.innerHTML = `
                <tr>
                    <td colspan="3" class="p-4 text-center text-red-500">Erro ao carregar os dados do servidor.</td>
                </tr>
            `;
        }
    }
}

// 1. Abre o modal, busca os dados da escola por ID e preenche o formulário
async function abrirModalEditarEscola(id) {
    try {
        const response = await fetch(`${API_URL}/escolas/${id}`);
        if (!response.ok) throw new Error('Não foi possível carregar os dados da escola.');

        const escola = await response.json();

        // Preenche os campos considerando o EscolaDTO (nomeEscola e telefoneEscola)
        document.getElementById('edit-escola-id').value = escola.id;
        document.getElementById('edit-escola-nome').value = escola.nomeEscola || '';
        document.getElementById('edit-escola-telefone').value = escola.telefoneEscola || '';

        // Exibe o modal
        document.getElementById('modal-editar-escola').classList.remove('hidden');
    } catch (e) {
        console.error("Erro ao buscar escola:", e);
        alert("Erro ao carregar informações para edição.");
    }
}

// 2. Fecha o modal de edição de escola
function fecharModalEditarEscola() {
    document.getElementById('modal-editar-escola').classList.add('hidden');
    document.getElementById('form-editar-escola').reset();
}

// 3. Envia os dados atualizados para a API (ajuste o método se o seu backend usa PUT ou PATCH)
async function atualizarEscola(event) {
    event.preventDefault();

    const id = document.getElementById('edit-escola-id').value;
    const escolaDTO = {
        nomeEscola: document.getElementById('edit-escola-nome').value,
        telefoneEscola: document.getElementById('edit-escola-telefone').value
    };

    try {
        const response = await fetch(`${API_URL}/escolas/${id}`, {
            method: 'PATCH', // ou 'PATCH', dependendo de como configurou no seu EscolaControle
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(escolaDTO)
        });

        if (response.ok) {
            alert('Escola atualizada com sucesso!');
            fecharModalEditarEscola();
            carregarEscolas(); // Atualiza a tabela na tela
        } else {
            const err = await response.json().catch(() => ({}));
            alert('Erro ao atualizar escola: ' + (err.message || 'Verifique os dados.'));
        }
    } catch (e) {
        console.error("Erro ao atualizar escola:", e);
        alert("Erro de conexão com o servidor.");
    }
}

async function deletarEscola(id) {
    // Confirmação para evitar cliques acidentais
    if (!confirm("Tem certeza de que deseja excluir esta escola?")) {
        return;
    }

    try {
        const response = await fetch(`${API_URL}/escolas/${id}`, {
            method: 'DELETE'
        });

        // O Spring Boot costuma retornar 204 (No Content) em exclusões bem-sucedidas
        if (response.ok || response.status === 204) {
            alert('Escola excluída com sucesso!');
            carregarEscolas(); // Atualiza a tabela na tela
        } else {
            const err = await response.json().catch(() => ({}));
            alert('Erro ao excluir escola: ' + (err.message || 'Verifique se existem alunos vinculados a esta escola.'));
        }
    } catch (e) {
        console.error("Erro ao excluir escola:", e);
        alert("Erro de conexão com o servidor.");
    }
}