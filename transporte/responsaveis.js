const API_URL = 'http://localhost:8080';

    function abrirModalResponsavel() {
        document.getElementById('modal-responsavel').classList.remove('hidden');
    }

    function fecharModalResponsavel() {
        document.getElementById('modal-responsavel').classList.add('hidden');
        document.getElementById('form-responsavel').reset();
    }

    async function salvarResponsavel(event) {
        event.preventDefault();

        const responsavelDTO = {
            nomeResponsavel: document.getElementById('resp-nome').value,
            cpf: document.getElementById('resp-cpf').value,
            telefone: document.getElementById('resp-telefone').value,
            email: document.getElementById('resp-email').value
        };

        try {
            const response = await fetch(`${API_URL}/responsaveis`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(responsavelDTO)
            });

            if (response.ok) {
                alert('Responsável cadastrado com sucesso!');
                fecharModalResponsavel();
                // Se tiver a função de recarregar a tabela de responsáveis, chame aqui:
                 carregarResponsaveis(); 
            } else {
                const err = await response.json();
                alert('Erro ao cadastrar responsável: ' + (err.message || 'Verifique os dados digitados (CPF duplicado?).'));
            }
        } catch (e) {
            console.error("Erro ao cadastrar responsável:", e);
            alert("Erro de conexão com o servidor.");
        }
    }

    async function carregarResponsaveis() {
    try {
        const response = await fetch(`${API_URL}/responsaveis`);
        
        if (!response.ok) {
            throw new Error('Erro ao buscar responsáveis na API.');
        }

        const page = await response.json();
        const tbody = document.getElementById('tabela-responsaveis-body');
        tbody.innerHTML = ''; // Limpa a tabela antes de preencher
        
        // Verifica se há registros
        if (!page.content || page.content.length === 0) {
            tbody.innerHTML = `
                <tr>
                    <td colspan="5" class="p-4 text-center text-zinc-500">Nenhum responsável cadastrado.</td>
                </tr>
            `;
            return;
        }

        // Itera sobre os dados retornados e monta as linhas da tabela
        page.content.forEach(resp => {
            tbody.innerHTML += `
                <tr class="hover:bg-zinc-300 transition">
                    <td class="p-4 font-medium text-zinc-900">${resp.nomeResponsavel || '-'}</td>
                    <td class="p-4">${resp.cpf || '-'}</td>
                    <td class="p-4">${resp.telefone || '-'}</td>
                    <td class="p-4">${resp.email || '-'}</td>
                    <td class="p-4 text-right">
                       <button onclick="abrirModalEditarResponsavel(${resp.id})" class="text-amber-600 hover:text-amber-800 transition mr-3" title="Editar">
                          <i class="fa-solid fa-pencil"></i>
                        </button>
                         <button onclick="deletarResponsavel(${resp.id})" class="text-red-600 hover:text-red-800 transition" title="Excluir">
                            <i class="fa-solid fa-trash"></i>
                        </button>                        
                    </td>
                </tr>
            `;
        });
    } catch (e) {
        console.error("Erro ao carregar responsáveis:", e);
        const tbody = document.getElementById('tabela-responsaveis-body');
        tbody.innerHTML = `
            <tr>
                <td colspan="5" class="p-4 text-center text-red-500">Erro ao carregar os dados do servidor.</td>
            </tr>
        `;
    }
}


/*
if (response.ok) {
    alert('Responsável cadastrado com sucesso!');
    fecharModalResponsavel();
    carregarResponsaveis(); // Atualiza a lista na hora!
}else {
    // Certifique-se de usar 'err' ou o nome da variável que declarou no catch, e não 'response'
    const err = await response.json().catch(() => ({}));
    alert('Erro ao processar: ' + (err.message || 'Verifique os dados.'));
}
*/
// 1. Abre o modal, busca os dados do responsável por ID e preenche o formulário
async function abrirModalEditarResponsavel(id) {
    try {
        const response = await fetch(`${API_URL}/responsaveis/${id}`);
        if (!response.ok) throw new Error('Não foi possível carregar os dados do responsável.');

        const resp = await response.json();

        // Preenche os campos do modal com os dados retornados
        document.getElementById('edit-resp-id').value = resp.id;
        document.getElementById('edit-resp-nome').value = resp.nomeResponsavel || '';
        document.getElementById('edit-resp-cpf').value = resp.cpf || '';
        document.getElementById('edit-resp-telefone').value = resp.telefone || '';
        document.getElementById('edit-resp-email').value = resp.email || '';

        // Exibe o modal
        document.getElementById('modal-editar-responsavel').classList.remove('hidden');
    } catch (e) {
        console.error("Erro ao buscar responsável:", e);
        alert("Erro ao carregar informações para edição.");
    }
}

// 2. Fecha o modal de edição
function fecharModalEditarResponsavel() {
    document.getElementById('modal-editar-responsavel').classList.add('hidden');
    document.getElementById('form-editar-responsavel').reset();
}

// 3. Envia os dados atualizados via PATCH para a API
async function atualizarResponsavel(event) {
    event.preventDefault();

    const id = document.getElementById('edit-resp-id').value;
    const responsavelDtoSemCpf = {
        nomeResponsavel: document.getElementById('edit-resp-nome').value,
        telefone: document.getElementById('edit-resp-telefone').value,
        email: document.getElementById('edit-resp-email').value
    };

    try {
        const response = await fetch(`${API_URL}/responsaveis/${id}`, {
            method: 'PATCH',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(responsavelDtoSemCpf)
        });

        if (response.ok) {
            alert('Responsável atualizado com sucesso!');
            fecharModalEditarResponsavel();
            carregarResponsaveis(); // Atualiza a tabela na tela
        } else {
            const err = await response.json();
            alert('Erro ao atualizar responsável: ' + (err.message || 'Verifique os dados.'));
        }
    } catch (e) {
        console.error("Erro ao atualizar responsável:", e);
        alert("Erro de conexão com o servidor.");
    }
}

async function deletarResponsavel(id) {
    // Confirmação antes de excluir para evitar cliques acidentais
    if (!confirm("Tem certeza que deseja excluir este responsável?")) {
        return;
    }

    try {
        const response = await fetch(`${API_URL}/responsaveis/${id}`, {
            method: 'DELETE'
        });

        // O Spring Boot geralmente retorna 204 (No Content) para exclusões bem-sucedidas
        if (response.ok || response.status === 204) {
            alert('Responsável excluído com sucesso!');
            carregarResponsaveis(); // Recarrega a tabela atualizada
        } else {
            const err = await response.json().catch(() => ({}));
            alert('Erro ao excluir responsável: ' + (err.message || 'Verifique se há dependências vinculadas (como alunos ativos).'));
        }
    } catch (e) {
        console.error("Erro ao excluir responsável:", e);
        alert("Erro de conexão com o servidor.");
    }
}