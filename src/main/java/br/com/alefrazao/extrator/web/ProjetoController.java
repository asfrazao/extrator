package br.com.alefrazao.extrator.web;

import br.com.alefrazao.extrator.service.ProjetoService;
import org.apache.maven.surefire.shared.io.FileExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

@RestController
public class ProjetoController {

    @Autowired
    private ProjetoService projetoService;

    @GetMapping(value = "/ler-pasta/{caminho}", produces = MediaType.TEXT_PLAIN_VALUE)
    public Mono<String> lerPasta(@PathVariable String caminho) {
        return Mono.just(projetoService.lerPasta(caminho));
    }
}