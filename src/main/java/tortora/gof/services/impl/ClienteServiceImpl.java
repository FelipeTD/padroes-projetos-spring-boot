package tortora.gof.services.impl;

import org.springframework.stereotype.Service;
import tortora.gof.model.Cliente;
import tortora.gof.model.ClienteRepository;
import tortora.gof.model.Endereco;
import tortora.gof.model.EnderecoRepository;
import tortora.gof.services.ClienteService;
import tortora.gof.services.ViaCepService;

import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository repository;
    private final EnderecoRepository enderecoRepository;
    private final ViaCepService service;

    public ClienteServiceImpl(ClienteRepository repository, EnderecoRepository enderecoRepository, ViaCepService service) {
        this.repository = repository;
        this.enderecoRepository = enderecoRepository;
        this.service = service;
    }

    @Override
    public Iterable<Cliente> buscarTodos() {
        return repository.findAll();
    }

    @Override
    public Cliente buscaPorId(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void inserir(Cliente cliente) {
        salvarClienteComCep(cliente);
    }

    @Override
    public void atualizar(Long id, Cliente cliente) {
        Optional<Cliente> clienteAtual = repository.findById(id);
        if (clienteAtual.isPresent()) {
            salvarClienteComCep(cliente);
        }
    }

    @Override
    public void deletar(Long id) {
        repository.deleteById(id);
    }

    private void salvarClienteComCep(Cliente cliente) {
        String cep = cliente.getEndereco().getCep();
        Endereco endereco = enderecoRepository.findById(cep).orElseGet(() -> {
            Endereco novoEndereco = service.consultarCep(cep);
            enderecoRepository.save(novoEndereco);
            return novoEndereco;
        });
        cliente.setEndereco(endereco);
        repository.save(cliente);
    }

}
