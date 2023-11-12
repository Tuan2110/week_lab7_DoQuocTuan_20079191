package vn.edu.iuh.fit.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.dtos.ProductDTO;
import vn.edu.iuh.fit.models.Customer;
import vn.edu.iuh.fit.models.Employee;
import vn.edu.iuh.fit.models.Product;
import vn.edu.iuh.fit.repositories.CustomerRepository;
import vn.edu.iuh.fit.repositories.EmployeeRepository;

import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    public Customer login(String email, String password){
        Optional<Customer> optional = customerRepository.findByEmailAndPassword(email,password);
        if(optional.isEmpty()){
            throw new RuntimeException("Email or password is incorrect");
        }
        return optional.get();
    }
    public Page<Customer> findAll(int pageNo, int pageSize, String sortBy,
                                    String sortDirection){
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        return customerRepository.findAll(pageable);
    }

    public void addCust(Customer customer) {
        customerRepository.save(customer);
    }
    public void updateCustomer(Customer customer){
        customerRepository.save(customer);
    }

    public Customer findById(Long id) {
        Optional<Customer> optional = customerRepository.findById(id);
        if(optional.isEmpty()){
            throw new RuntimeException("Customer not found");
        }
        return optional.get();
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}
