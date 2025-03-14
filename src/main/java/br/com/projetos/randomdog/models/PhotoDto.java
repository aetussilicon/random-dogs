package br.com.projetos.randomdog.models;

public record PhotoDto(
        Long id,
        String image,
        String mimeType
) {
}
