# Usa a imagem oficial do Nginx leve baseada em Alpine
FROM nginx:alpine

# Copia os arquivos HTML, CSS e JS da pasta atual para o diretório padrão do Nginx
COPY . /usr/share/nginx/html

# Expõe a porta 80 do container web
EXPOSE 80

# Inicia o Nginx em primeiro plano
CMD ["nginx", "-g", "daemon off;"]