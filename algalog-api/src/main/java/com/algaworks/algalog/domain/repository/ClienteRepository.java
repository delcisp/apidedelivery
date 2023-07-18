package com.algaworks.algalog.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algaworks.algalog.domain.model.Cliente;


/*eh uma anotação que define que a interface é um componente do spring
 * porém com uma semantica bem definida. */
/*componente spring é um tipo onde as instancias desse tipo sao gerenciadas
 * pelo proprio conteiner do spring e com isso da p injetar uma
 * instancia de repositorio em objetos de outras classes do projeto
 * usando injeção de dependencias*/
public interface ClienteRepository extends JpaRepository<Cliente, Long>{
/*essa eh uma interface generica que é um repositorio JPA*/
	
	List<Cliente> findByNome (String nome);
	//digitar o nome correto
	List<Cliente> findByNomeContaining (String nome);
	/* esse segundo método faz com que possa pesquisar por letra, meio nome etc
	 * nao precisando ser o nome exato*/
	Optional<Cliente> findByEmail(String email);
}
