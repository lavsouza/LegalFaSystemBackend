package legalfasystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import legalfasystem.model.Contrato;

public interface ContratoRepository extends JpaRepository<Contrato, Long> {
    List<Contrato> findByEmpresaId(Long empresaId);
}