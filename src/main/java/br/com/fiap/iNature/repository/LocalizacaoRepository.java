package br.com.fiap.iNature.repository;

import br.com.fiap.iNature.model.Localizacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalizacaoRepository extends JpaRepository<Localizacao, Long> {
}
