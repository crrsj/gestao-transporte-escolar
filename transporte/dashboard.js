// Função principal para carregar todos os dados da dashboard
async function carregarDashboard() {
    try {
        // Dispara as requisições em paralelo para otimizar o carregamento
        const [resResponsaveis, resEscolas, resAlunos, resPagamentos] = await Promise.all([
            fetch(`${API_URL}/responsaveis`).catch(() => null),
            fetch(`${API_URL}/escolas`).catch(() => null),
            fetch(`${API_URL}/alunos`).catch(() => null),
            fetch(`${API_URL}/pagamentos`).catch(() => null)
        ]);

        // Processa Responsáveis
        if (resResponsaveis && resResponsaveis.ok) {
            const data = await resResponsaveis.json();
            const total = (data.content || data).length || 0;
            atualizarContador('dash-total-responsaveis', total);
        }

        // Processa Escolas
        if (resEscolas && resEscolas.ok) {
            const data = await resEscolas.json();
            const total = (data.content || data).length || 0;
            atualizarContador('dash-total-escolas', total);
        }

        // Processa Alunos
        if (resAlunos && resAlunos.ok) {
            const data = await resAlunos.json();
            const total = (data.content || data).length || 0;
            atualizarContador('dash-total-alunos', total);
        }

        // Processa Pagamentos (Métricas financeiras e status)
        if (resPagamentos && resPagamentos.ok) {
            const data = await resPagamentos.json();
            const listaPagamentos = data.content || data;
            
            let totalArrecadado = 0;
            let totalPendente = 0;

            if (listaPagamentos && listaPagamentos.length > 0) {
                listaPagamentos.forEach(pag => {
                    const valor = Number(pag.valorMensalidade || 0);
                    if (pag.status === 'PAGO') {
                        totalArrecadado += valor;
                    } else if (pag.status === 'PENDENTE' || pag.status === 'ATRASADO') {
                        totalPendente += valor;
                    }
                });
            }

            // Atualiza os valores formatados em Real (R$) na tela
            atualizarElementoTexto('dash-valor-pago', totalArrecadado.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' }));
            atualizarElementoTexto('dash-valor-pendente', totalPendente.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' }));
        }

    } catch (e) {
        console.error("Erro ao carregar dados da dashboard:", e);
    }
}

// Funções auxiliares para atualizar os elementos com segurança
function atualizarContador(elementId, valor) {
    const el = document.getElementById(elementId);
    if (el) el.textContent = valor;
}

function atualizarElementoTexto(elementId, texto) {
    const el = document.getElementById(elementId);
    if (el) el.textContent = texto;
}

carregarDashboard();