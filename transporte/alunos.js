async function abrirModalAluno() {
        document.getElementById('modal-aluno').classList.remove('hidden');
        await carregarOpcoesEscolas();
        await carregarOpcoesResponsaveis();
    }

    function fecharModalAluno() {
        document.getElementById('modal-aluno').classList.add('hidden');
        document.getElementById('form-aluno').reset();
    }

    // 3. Popula o select de escolas
async function carregarOpcoesEscolas() {
    try {
        const res = await fetch(`${API_URL}/escolas`);
        const data = await res.json();
        const select = document.getElementById('aluno-escola');
        select.innerHTML = '<option value="">Selecione uma escola</option>';
        
        const lista = data.content || data; // Compatível se retornar Page ou List direta
        lista.forEach(escola => {
            select.innerHTML += `<option value="${escola.id}">${escola.nomeEscola || escola.nome}</option>`;
        });
    } catch (e) {
        console.error("Erro ao carregar lista de escolas:", e);
    }
}

// 4. Popula o select de responsáveis
async function carregarOpcoesResponsaveis() {
    try {
        const res = await fetch(`${API_URL}/responsaveis`);
        const data = await res.json();
        const select = document.getElementById('aluno-responsavel');
        select.innerHTML = '<option value="">Selecione um responsável</option>';
        
        const lista = data.content || data;
        lista.forEach(resp => {
            select.innerHTML += `<option value="${resp.id}">${resp.nomeResponsavel || 'Sem Nome'} (CPF: ${resp.cpf || '-'})</option>`;
        });
    } catch (e) {
        console.error("Erro ao carregar lista de responsáveis:", e);
    }
}

async function salvarAluno(event) {
    event.preventDefault();

    const responsavelId = document.getElementById('aluno-responsavel').value;
    const escolaId = document.getElementById('aluno-escola').value;

    const alunoDTO = {
        nomeAluno: document.getElementById('aluno-nome').value,
        dataNasc: document.getElementById('aluno-dataNasc').value,
        sexo: document.getElementById('aluno-sexo').value,
        turma: document.getElementById('aluno-turma').value,
        turno: document.getElementById('aluno-turno').value,
        tipoEnsino: document.getElementById('aluno-tipoEnsino').value,
        ano: document.getElementById('aluno-ano').value
    };

    try {
        const response = await fetch(`${API_URL}/alunos/responsaveis/${responsavelId}/escolas/${escolaId}`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(alunoDTO)
        });

        if (response.ok) {
            alert('Aluno cadastrado com sucesso!');
            fecharModalAluno();
            // Se já tiver a função de listar alunos na tela, chame aqui:
             carregarAlunos();
        } else {
            const err = await response.json();
            alert('Erro ao cadastrar aluno: ' + (err.message || 'Verifique os dados digitados.'));
        }
    } catch (e) {
        console.error("Erro ao cadastrar aluno:", e);
        alert("Erro de conexão com o servidor.");
    }
}

async function carregarAlunos() {
    try {
        const response = await fetch(`${API_URL}/alunos`);
        
        if (!response.ok) {
            throw new Error('Erro ao buscar alunos na API.');
        }

        const data = await response.json();
        const tbody = document.getElementById('tabela-alunos-body');
        if (!tbody) return; // Segurança caso a tabela não esteja visível na página atual
        
        tbody.innerHTML = ''; // Limpa a tabela antes de preencher
        
        // Compatível se o backend retornar um objeto Page (.content) ou uma List direta
        const listaAlunos = data.content || data;

        if (!listaAlunos || listaAlunos.length === 0) {
            tbody.innerHTML = `
                <tr>
                    <td colspan="6" class="p-4 text-center text-zinc-500">Nenhum aluno cadastrado.</td>
                </tr>
            `;
            return;
        }

        // Itera sobre os alunos e monta as linhas da tabela
        listaAlunos.forEach(aluno => {
            tbody.innerHTML += `
                <tr class="hover:bg-zinc-300 transition">
                    <td class="p-4 font-medium text-zinc-900">${aluno.nomeAluno || '-'}</td>                 
                    <td class="p-4">${aluno.turma || '-'}</td>
                    <td class="p-4">${aluno.turno || '-'}</td>
                    <td class="p-4">${aluno.tipoEnsino || '-'}</td>
                    <td class="p-4">${aluno.ano || '-'}</td>
                    <td class="p-4 text-right">
                        <button onclick="abrirModalEditarAluno(${aluno.id})" class="text-amber-600 hover:text-amber-800 transition mr-3" title="Editar">
                            <i class="fa-solid fa-pencil"></i>
                        </button>
                        <button onclick="deletarAluno(${aluno.id})" class="text-red-600 hover:text-red-800 transition" title="Excluir">
                            <i class="fa-solid fa-trash"></i>
                        </button>
                    </td>
                </tr>
            `;
        });
    } catch (e) {
        console.error("Erro ao carregar alunos:", e);
        const tbody = document.getElementById('tabela-alunos-body');
        if (tbody) {
            tbody.innerHTML = `
                <tr>
                    <td colspan="6" class="p-4 text-center text-red-500">Erro ao carregar os dados do servidor.</td>
                </tr>
            `;
        }
    }
}


// 1. Popula o select de escolas na edição, marcando a escola atual do aluno
async function carregarOpcoesEscolasParaEdicao(escolaSelecionadaId) {
    try {
        const res = await fetch(`${API_URL}/escolas`);
        const data = await res.json();
        const select = document.getElementById('edit-aluno-escola');
        select.innerHTML = '<option value="">Selecione uma escola</option>';
        
        const listaEscolas = data.content || data;
        if (listaEscolas && listaEscolas.length > 0) {
            listaEscolas.forEach(escola => {
                const selected = escola.id == escolaSelecionadaId ? 'selected' : '';
                select.innerHTML += `<option value="${escola.id}" ${selected}>${escola.nomeEscola || 'Sem Nome'}</option>`;
            });
        }
    } catch (e) {
        console.error("Erro ao carregar escolas para edição:", e);
    }
}


async function abrirModalEditarAluno(id) {
    try {
        const response = await fetch(`${API_URL}/alunos/${id}`);
        if (!response.ok) throw new Error('Não foi possível carregar os dados do aluno.');

        const aluno = await response.json();

        document.getElementById('edit-aluno-id').value = aluno.id;
        document.getElementById('edit-aluno-nome').value = aluno.nomeAluno || '';
        document.getElementById('edit-aluno-datanasc').value = aluno.dataNasc || '';
        document.getElementById('edit-aluno-sexo').value = aluno.sexo || '';
        document.getElementById('edit-aluno-turma').value = aluno.turma || '';
        document.getElementById('edit-aluno-turno').value = aluno.turno || '';
        document.getElementById('edit-aluno-tipo-ensino').value = aluno.tipoEnsino || '';
        document.getElementById('edit-aluno-ano').value = aluno.ano || '';

        const escolaId = aluno.escola ? aluno.escola.id : null;
        await carregarOpcoesEscolasParaEdicao(escolaId);

        document.getElementById('modal-editar-aluno').classList.remove('hidden');
    } catch (e) {
        console.error("Erro ao buscar aluno:", e);
        alert("Erro ao carregar informações para edição.");
    }
}

function fecharModalEditarAluno() {
    document.getElementById('modal-editar-aluno').classList.add('hidden');
    document.getElementById('form-editar-aluno').reset();
}

async function atualizarAluno(event) {
    event.preventDefault();

    const id = document.getElementById('edit-aluno-id').value;
    const escolaId = document.getElementById('edit-aluno-escola').value;

    const alunoDTO = {
        nomeAluno: document.getElementById('edit-aluno-nome').value,
        dataNasc: document.getElementById('edit-aluno-datanasc').value,
        sexo: document.getElementById('edit-aluno-sexo').value,
        turma: document.getElementById('edit-aluno-turma').value,
        turno: document.getElementById('edit-aluno-turno').value,
        tipoEnsino: document.getElementById('edit-aluno-tipo-ensino').value,
        ano: document.getElementById('edit-aluno-ano').value,
        escola: escolaId ? { id: parseInt(escolaId) } : null
    };

    try {
        const response = await fetch(`${API_URL}/alunos/${id}`, {
            method: 'PATCH',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(alunoDTO)
        });

        if (response.ok) {
            alert('Aluno atualizado com sucesso!');
            fecharModalEditarAluno();
            carregarAlunos();
        } else {
            const err = await response.json().catch(() => ({}));
            alert('Erro ao atualizar aluno: ' + (err.message || 'Verifique os campos obrigatórios.'));
        }
    } catch (e) {
        console.error("Erro ao atualizar aluno:", e);
        alert("Erro de conexão com o servidor.");
    }
}

async function deletarAluno(id) {
    // Confirmação para evitar cliques acidentais
    if (!confirm("Tem certeza de que deseja excluir este registo de aluno?")) {
        return;
    }

    try {
        const response = await fetch(`${API_URL}/alunos/${id}`, {
            method: 'DELETE'
        });

        // O Spring Boot costuma retornar 204 (No Content) em exclusões bem-sucedidas
        if (response.ok || response.status === 204) {
            alert('Aluno excluído com sucesso!');
            carregarAlunos(); // Atualiza a tabela na tela
        } else {
            const err = await response.json().catch(() => ({}));
            alert('Erro ao excluir aluno: ' + (err.message || 'Verifique se existem dependências associadas.'));
        }
    } catch (e) {
        console.error("Erro ao excluir aluno:", e);
        alert("Erro de conexão com o servidor.");
    }
}
