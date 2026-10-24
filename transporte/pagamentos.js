// 1. Abre o modal e busca os responsáveis para o select, definindo o ano atual por padrão
async function abrirModalPagamento() {
    document.getElementById('modal-pagamento').classList.remove('hidden');
    document.getElementById('pagamento-ano').value = new Date().getFullYear();
    await carregarOpcoesResponsaveisParaPagamento();
}

// 2. Fecha o modal de pagamento e limpa o formulário
function fecharModalPagamento() {
    document.getElementById('modal-pagamento').classList.add('hidden');
    document.getElementById('form-pagamento').reset();
}

// 3. Popula o select com os responsáveis cadastrados
async function carregarOpcoesResponsaveisParaPagamento() {
    try {
        const res = await fetch(`${API_URL}/responsaveis`);
        const data = await res.json();
        const select = document.getElementById('pagamento-responsavel');
        select.innerHTML = '<option value="">Selecione um responsável</option>';
        
        const listaResponsaveis = data.content || data;
        
        if (listaResponsaveis && listaResponsaveis.length > 0) {
            listaResponsaveis.forEach(resp => {
                select.innerHTML += `<option value="${resp.id}">${resp.nomeResponsavel || 'Sem Nome'} (CPF: ${resp.cpf || '-'})</option>`;
            });
        } else {
            select.innerHTML = '<option value="">Nenhum responsável cadastrado.</option>';
        }
    } catch (e) {
        console.error("Erro ao carregar responsáveis:", e);
        document.getElementById('pagamento-responsavel').innerHTML = '<option value="">Erro ao carregar responsáveis.</option>';
    }
}

// 4. Envia o pagamento via POST para a rota aninhada do responsável
async function salvarPagamento(event) {
    event.preventDefault();

    const responsavelId = document.getElementById('pagamento-responsavel').value;

    const pagamentoDTO = {
        pagador: document.getElementById('pagamento-pagador').value,
        ano: parseInt(document.getElementById('pagamento-ano').value),
        mes: document.getElementById('pagamento-mes').value,
        valorMensalidade: parseFloat(document.getElementById('pagamento-valor').value),
        status: document.getElementById('pagamento-status').value
    };

    try {
        // Rota estruturada baseada na relação @ManyToOne com Responsavel
        const response = await fetch(`${API_URL}/pagamentos/responsaveis/${responsavelId}`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(pagamentoDTO)
        });

        if (response.ok) {
            alert('Pagamento registrado com sucesso!');
            fecharModalPagamento();
            // Se já tiver a listagem de pagamentos, chame aqui:
             carregarPagamentos();
        } else {
            const err = await response.json().catch(() => ({}));
            alert('Erro ao registrar pagamento: ' + (err.message || 'Verifique os dados digitados.'));
        }
    } catch (e) {
        console.error("Erro ao registrar pagamento:", e);
        alert("Erro de conexão com o servidor.");
    }
}

async function carregarPagamentos() {
    try {
        const response = await fetch(`${API_URL}/pagamentos`);
        
        if (!response.ok) {
            throw new Error('Erro ao buscar pagamentos na API.');
        }

        const data = await response.json();
        const tbody = document.getElementById('tabela-pagamentos-body');
        if (!tbody) return; // Segurança caso a tabela não esteja visível na página atual
        
        tbody.innerHTML = ''; // Limpa a tabela antes de preencher
        
        // Compatível caso o backend retorne um objeto Page (.content) ou uma List direta
        const listaPagamentos = data.content || data;

        if (!listaPagamentos || listaPagamentos.length === 0) {
            tbody.innerHTML = `
                <tr>
                    <td colspan="6" class="p-4 text-center text-zinc-500">Nenhum pagamento registado.</td>
                </tr>
            `;
            return;
        }

        // Itera sobre os pagamentos e monta as linhas da tabela
        listaPagamentos.forEach(pagamento => {
            // Formata o valor para o padrão monetário brasileiro (R$)
            const valorFormatado = Number(pagamento.valorMensalidade || 0).toLocaleString('pt-BR', {
                style: 'currency',
                currency: 'BRL'
            });

            // Formata a data de AAAA-MM-DD para DD/MM/AAAA
            let dataFormatada = '-';
            if (pagamento.dataPagamento) {
                const partes = pagamento.dataPagamento.split('-');
                if (partes.length === 3) {
                    dataFormatada = `${partes[2]}/${partes[1]}/${partes[0]}`;
                }
            }

            // Define cores dinâmicas para o status do pagamento
            let statusBadge = '';
            if (pagamento.status === 'PAGO') {
                statusBadge = '<span class="px-2 py-1 bg-green-100 text-green-800 rounded-full text-xs font-semibold">Pago</span>';
            } else if (pagamento.status === 'ATRASADO') {
                statusBadge = '<span class="px-2 py-1 bg-red-100 text-red-800 rounded-full text-xs font-semibold">Atrasado</span>';
            } else {
                statusBadge = '<span class="px-2 py-1 bg-amber-100 text-amber-800 rounded-full text-xs font-semibold">Pendente</span>';
            }

            tbody.innerHTML += `
                <tr class="hover:bg-zinc-300 transition">
                    <td class="p-4 font-medium text-zinc-900">${pagamento.pagador || '-'}</td>
                    <td class="p-4">${dataFormatada}</td>
                    <td class="p-4 font-semibold text-zinc-800">${valorFormatado}</td>                  
                    <td class="p-4">${statusBadge}</td>
                    <td class="p-4">${pagamento.mes || '-'} / ${pagamento.ano || '-'}</td>
                    <td class="p-4 text-right">
                        <button onclick="abrirModalEditarPagamento(${pagamento.id})" class="text-amber-600 hover:text-amber-800 transition mr-3" title="Editar">
                            <i class="fa-solid fa-pencil"></i>
                        </button>
                        <button onclick="deletarPagamento(${pagamento.id})" class="text-red-600 hover:text-red-800 transition" title="Excluir">
                            <i class="fa-solid fa-trash"></i>
                        </button>
                    </td>
                </tr>
            `;
        });
    } catch (e) {
        console.error("Erro ao carregar pagamentos:", e);
        const tbody = document.getElementById('tabela-pagamentos-body');
        if (tbody) {
            tbody.innerHTML = `
                <tr>
                    <td colspan="6" class="p-4 text-center text-red-500">Erro ao carregar os dados do servidor.</td>
                </tr>
            `;
        }
    }
}

async function carregarOpcoesResponsaveisParaEdicaoPagamento(responsavelSelecionadoId) {
    try {
        const res = await fetch(`${API_URL}/responsaveis`);
        const data = await res.json();
        const select = document.getElementById('edit-pagamento-responsavel');
        select.innerHTML = '<option value="">Selecione um responsável</option>';
        
        const listaResponsaveis = data.content || data;
        if (listaResponsaveis) {
            listaResponsaveis.forEach(resp => {
                const selected = resp.id == responsavelSelecionadoId ? 'selected' : '';
                select.innerHTML += `<option value="${resp.id}" ${selected}>${resp.nomeResponsavel || 'Sem Nome'} (CPF: ${resp.cpf || '-'})</option>`;
            });
        }
    } catch (e) {
        console.error("Erro ao carregar responsáveis para edição:", e);
    }
}

async function abrirModalEditarPagamento(id) {
    try {
        const response = await fetch(`${API_URL}/pagamentos/${id}`);
        if (!response.ok) throw new Error('Não foi possível carregar os dados do pagamento.');

        const pagamento = await response.json();

        document.getElementById('edit-pagamento-id').value = pagamento.id;
        document.getElementById('edit-pagamento-pagador').value = pagamento.pagador || '';
        document.getElementById('edit-pagamento-valor').value = pagamento.valorMensalidade || '';
        document.getElementById('edit-pagamento-ano').value = pagamento.ano || '';
        document.getElementById('edit-pagamento-mes').value = pagamento.mes || '';
        document.getElementById('edit-pagamento-data').value = pagamento.dataPagamento || '';
        document.getElementById('edit-pagamento-status').value = pagamento.status || 'PENDENTE';

        const responsavelId = pagamento.responsavel ? pagamento.responsavel.id : null;
        await carregarOpcoesResponsaveisParaEdicaoPagamento(responsavelId);

        document.getElementById('modal-editar-pagamento').classList.remove('hidden');
    } catch (e) {
        console.error("Erro ao buscar pagamento:", e);
        alert("Erro ao carregar informações para edição.");
    }
}

function fecharModalEditarPagamento() {
    document.getElementById('modal-editar-pagamento').classList.add('hidden');
    document.getElementById('form-editar-pagamento').reset();
}

async function atualizarPagamento(event) {
    event.preventDefault();

    const id = document.getElementById('edit-pagamento-id').value;

    // Estrutura exata correspondente ao PagamentoDTO atualizado
    const pagamentoDTO = {
        pagador: document.getElementById('edit-pagamento-pagador').value,
        ano: parseInt(document.getElementById('edit-pagamento-ano').value),
        mes: document.getElementById('edit-pagamento-mes').value,
        dataPagamento: document.getElementById('edit-pagamento-data').value || null, // Mantém o valor original ou nulo
        valorMensalidade: parseFloat(document.getElementById('edit-pagamento-valor').value),
        status: document.getElementById('edit-pagamento-status').value
    };

    try {
        const response = await fetch(`${API_URL}/pagamentos/${id}`, {
            method: 'PATCH', // ou PUT dependendo do seu controlador
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(pagamentoDTO)
        });

        if (response.ok) {
            alert('Pagamento atualizado com sucesso!');
            fecharModalEditarPagamento();
            carregarPagamentos();
        } else {
            const err = await response.json().catch(() => ({}));
            alert('Erro ao atualizar pagamento: ' + (err.message || 'Verifique os dados.'));
        }
    } catch (e) {
        console.error("Erro ao atualizar pagamento:", e);
        alert("Erro de conexão com o servidor.");
    }
}

async function deletarPagamento(id) {
    // Confirmação para evitar cliques acidentais
    if (!confirm("Tem certeza de que deseja excluir este registo de pagamento?")) {
        return;
    }

    try {
        const response = await fetch(`${API_URL}/pagamentos/${id}`, {
            method: 'DELETE'
        });

        // O Spring Boot costuma retornar 204 (No Content) em exclusões bem-sucedidas
        if (response.ok || response.status === 204) {
            alert('Pagamento excluído com sucesso!');
            carregarPagamentos(); // Atualiza a tabela na tela
        } else {
            const err = await response.json().catch(() => ({}));
            alert('Erro ao excluir pagamento: ' + (err.message || 'Verifique os dados no servidor.'));
        }
    } catch (e) {
        console.error("Erro ao excluir pagamento:", e);
        alert("Erro de conexão com o servidor.");
    }
}