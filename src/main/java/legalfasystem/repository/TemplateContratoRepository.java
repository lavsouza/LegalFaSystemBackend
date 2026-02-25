package legalfasystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import legalfasystem.model.TemplateContrato;

public interface TemplateContratoRepository extends JpaRepository<TemplateContrato, Long> {
}