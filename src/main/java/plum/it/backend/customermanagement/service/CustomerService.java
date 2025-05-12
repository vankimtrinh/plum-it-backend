package plum.it.backend.customermanagement.service;

import plum.it.backend.customermanagement.domain.Customer;
import plum.it.backend.customermanagement.domain.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    CustomerService(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    public void createCustomer(String forename, String surname, String streetname, String streetnumber, String plz, String city, String country, String email, String mobil, String contactperson) {
        if ("fail".equals(forename) || "fail".equals(surname) || "fail".equals(streetname) || "fail".equals(streetnumber) || "fail".equals(plz) || "fail".equals(city) || "fail".equals(country) || "fail".equals(email) || "fail".equals(mobil) || "fail".equals(contactperson)) {
            throw new RuntimeException("This is for testing the error handler");
        }
        var customer = new Customer();
        customer.setForename(forename);
        customer.setSurname(surname);
        customer.setStreetname(streetname);
        customer.setStreetnumber(streetnumber);
        customer.setCity(city);
        customer.setPlz(plz);
        customer.setCountry(country);
        customer.setEmail(email);
        customer.setMobil(mobil);
        customer.setContactperson(contactperson);
        customerRepository.saveAndFlush(customer);
    }

    // Find Customer by ID (Wrapper for CustomerRepository's findById)
    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }

    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    // Optionally, add other methods like save or update
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Long id, Customer updatedCustomer) {
        return customerRepository.findById(id)
                .map(customer -> {
                    customer.setForename(updatedCustomer.getForename());
                    customer.setEmail(updatedCustomer.getEmail());
                    return customerRepository.save(customer);
                })
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    @Transactional
    public void deleteCustomer(Long id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Customer with ID " + id + " not found.");
        }
    }
    
    public List<Customer> list(Pageable pageable){
        return customerRepository.findAllBy(pageable).toList();
    }           
}
