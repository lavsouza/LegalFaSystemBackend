package legalfasystem.repository;

import legalfasystem.model.Contrato;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContratoRepository extends JpaRepository<Contrato, Long> {
    public List<Contrato> findAllByEmpresaId(long id);
}
