package br.com.fiap.iNature.repository;

import br.com.fiap.iNature.model.Noticia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticiaRepository extends JpaRepository<Noticia, Long> {
}
