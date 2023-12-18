package bosch.AgendaETS.model.turma;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TurmaRepository extends JpaRepository<Turma,Integer> {
    Page<Turma> findAllByAtivoTrue(Pageable pageable);


    Turma findByNome(String nome);

}
