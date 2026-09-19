package br.com.locadora.ator;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface AtorMapper {

    // Cria uma nova entidade a partir do DTO de atualização
    Ator toEntityFromAtualizacao(DadosAtualizacaoAtor dto);

    // Atualiza uma entidade existente com dados do DTO (ignora campos nulos)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(DadosAtualizacaoAtor dto, @MappingTarget Ator ator);
}
