package br.com.locadora.filme;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface FilmeMapper {

    // Cria uma nova entidade a partir do DTO de atualização
    Filme toEntityFromAtualizacao(DadosAtualizacaoFilme dto);

    // Atualiza uma entidade existente com dados do DTO (ignora campos nulos)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(DadosAtualizacaoFilme dto, @MappingTarget Filme filme);
}
