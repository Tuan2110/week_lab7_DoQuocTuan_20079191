package vn.edu.iuh.fit;

import net.datafaker.Faker;
import net.datafaker.providers.base.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import vn.edu.iuh.fit.enums.EmployeeStatus;
import vn.edu.iuh.fit.enums.ProductStatus;
import vn.edu.iuh.fit.models.*;
import vn.edu.iuh.fit.repositories.*;
import vn.edu.iuh.fit.services.OrderService;
import vn.edu.iuh.fit.services.ProductService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@SpringBootApplication
public class Lab7Application {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    private ProductService productService;
    public static void main(String[] args) {
        SpringApplication.run(Lab7Application.class, args);
    }
    @Autowired
    private OrderService orderService;


    CommandLineRunner commandLineRunner(EmployeeRepository employeeRepository,
                                        ProductImageRepository productImageRepository,
                                        ProductPriceRepository productPriceRepository,
                                        OrderRepository orderRepository,
                                        OrderDetailRepository orderDetailRepository){
        return args -> {

//            Employee employee = employeeRepository.findByEmailAndPassword("billi.ondricka@hotmail.com", "123456").get();
//            System.out.println(employee);
//            Faker faker = new Faker();
//            Random rnd = new Random();
//            Device device = faker.device();
//            for (long i = 0; i <= 100; i++) {
//                Product product = new Product(
//                        device.modelName(),
//                        faker.lorem().paragraph(30),
//                        "piece",
//                        device.manufacturer(),
//                        ProductStatus.ACTIVE
//                );
//                productRepository.save(product);
//                Customer customer = new Customer(
//                        faker.name().fullName(),
//                        faker.internet().emailAddress(),
//                        faker.phoneNumber().cellPhone(),
//                        faker.address().fullAddress(),
//                        "123456"
//                );
//                customerRepository.save(customer);

//                Employee employee = new Employee(
//                  faker.name().fullName(),
//                  LocalDate.of(rnd.nextInt(1998,2005),rnd.nextInt(1,12),rnd.nextInt(1,28)),
//                  faker.internet().emailAddress(),
//                  faker.phoneNumber().cellPhone(),
//                  faker.address().fullAddress(),
//                  "123456",
//                  EmployeeStatus.ACTIVE
//                );
//                employeeRepository.save(employee);
//                ProductImage productImage = new ProductImage(
//                        faker.internet().image(),
//                        faker.lorem().paragraph(1),
//                        productRepository.findById(rnd.nextLong(1,200)).get()
//                );
//                productImageRepository.save(productImage);
//                ProductPrice productPrice = new ProductPrice(
//                        productRepository.findById(rnd.nextLong(1,200)).get(),
//                        LocalDateTime.of(rnd.nextInt(2010,2023),
//                                rnd.nextInt(1,12),
//                                rnd.nextInt(1,28),
//                                rnd.nextInt(0,24),
//                                rnd.nextInt(0,60)),
//                        rnd.nextDouble(100000,1000000),
//                        faker.lorem().paragraph(1)
//                );
//                productPriceRepository.save(productPrice);
//                Order order = new Order(
//                        LocalDateTime.of(rnd.nextInt(2010,2023),
//                                rnd.nextInt(1,12),
//                                rnd.nextInt(1,28),
//                                rnd.nextInt(0,24),
//                                rnd.nextInt(0,60)),
//                        employeeRepository.findById(rnd.nextLong(1,200)).get(),
//                        customerRepository.findById(rnd.nextLong(1,200)).get()
//                );
//                orderRepository.save(order);
//                OrderDetail orderDetail = new OrderDetail(
//                        rnd.nextDouble(1,100),
//                        rnd.nextDouble(100000,1000000),
//                        faker.coffee().notes(),
//                        orderRepository.findById(rnd.nextLong(1,193)).get(),
//                        productRepository.findById(rnd.nextLong(1,200)).get()
//                );
//                orderDetailRepository.save(orderDetail);
//            }
        };
    }
}
