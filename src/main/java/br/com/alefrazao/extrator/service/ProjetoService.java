package br.com.alefrazao.extrator.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

@Service
public class ProjetoService {

    @Value("${PATH_SAIDA}")
    private String pathSaida;

    public String lerPasta(String caminho) {
        // Verifica se o caminho existe no HD local
        Path pastaLida = Paths.get(caminho);
        if (!Files.exists(pastaLida)) {
            return "O caminho do projeto não existe no HD local.";
        }

        try {
            String conteudoPasta = lerConteudoPasta(pastaLida);
            Path pastaSaida = Paths.get(pathSaida);

            if (!Files.exists(pastaSaida)) {
                criarPastaSaida(pastaSaida);

                // Mensagem informando que a pasta foi criada no padrão
                String mensagem = "Pasta Criada no Padrão: " + pathSaida;

                return gravarTXT(pastaSaida, pastaLida, conteudoPasta, mensagem);
            } else {
                // A pasta de saída já existe, continua o processo normalmente
                return gravarTXT(pastaSaida, pastaLida, conteudoPasta, null);
            }
        } catch (IOException e) {
            return "Erro ao realizar operações: " + e.getMessage();
        }
    }

    private String lerConteudoPasta(Path pastaLida) throws IOException {
        return Files.walk(pastaLida)
                .map(Path::toString)
                .collect(Collectors.joining("\n"));
    }

    private void criarPastaSaida(Path pastaSaida) throws IOException {
        Files.createDirectories(pastaSaida);
    }

    private String gravarTXT(Path pastaSaida, Path pastaLida, String conteudoPasta, String mensagem) throws IOException {
        Path arquivoSaida = pastaSaida.resolve(pastaLida.getFileName().toString() + ".txt");

        if (Files.exists(arquivoSaida)) {
            mensagem = (mensagem != null) ? mensagem + "\n" : "";
            return mensagem + "Arquivo já existe e será sobrescrito: " + arquivoSaida.toString();
        } else {
            Files.write(arquivoSaida, conteudoPasta.getBytes());
            mensagem = (mensagem != null) ? mensagem + "\n" : "";
            return mensagem + "Arquivo gravado com sucesso em: " + arquivoSaida.toString();
        }
    }
}
