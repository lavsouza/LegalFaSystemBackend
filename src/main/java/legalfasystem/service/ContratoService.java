package legalfasystem.service;

import legalfasystem.model.Contrato;
import legalfasystem.repository.ContratoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ContratoService {

    @Autowired
    private ContratoRepository contratoRepository;

    public List<Contrato> listarContratos(long idEmpresa) {
        return contratoRepository.findAllByEmpresaId(idEmpresa);
    }

    @Transactional
    public Contrato salvar(Contrato contrato) {
        return contratoRepository.save(contrato);
    }

    @Transactional
    public void deletar(Long id) {
        contratoRepository.deleteById(id);
    }
}
