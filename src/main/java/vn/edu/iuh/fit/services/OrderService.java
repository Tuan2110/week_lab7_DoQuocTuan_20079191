package vn.edu.iuh.fit.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.models.Order;
import vn.edu.iuh.fit.models.OrderDetail;
import vn.edu.iuh.fit.models.Product;
import vn.edu.iuh.fit.repositories.OrderRepository;
import vn.edu.iuh.fit.repositories.ProductRepository;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;

    public double getTotalPriceThisWeek(){
//        double totalPrice = 0;
//        for (Order order : orderRepository.findAll()) {
//            WeekFields weekFields = WeekFields.of(Locale.getDefault());
//            int weekOrder = order.getOrderDate().get(weekFields.weekOfWeekBasedYear());
//            int weekNow = LocalDateTime.now().get(weekFields.weekOfWeekBasedYear());
//            if(weekNow == weekOrder && order.getOrderDate().getYear()==LocalDateTime.now().getYear()){
//                for (OrderDetail od : order.getOrderDetails()) {
//                    totalPrice += od.getPrice()*od.getQuantity();
//                }
//            }
//        }
        return orderRepository.getTotalPriceThisWeek();
    }
    public int getTotalOrderThisWeek(){
        return orderRepository.getTotalOrderThisWeek();
    }
    public int getTotalCustomerThisWeek(){
        return orderRepository.getTotalCustomerThisWeek();
    }
    public int getTotalProductThisWeek(){
        return orderRepository.getTotalProductThisWeek();
    }
    public double getTotalPriceLastWeek(){
        return orderRepository.getTotalPriceLastWeek();
    }
    public int getTotalOrderLastWeek(){
        return orderRepository.getToTalOrderLastWeek();
    }
    public int getTotalCustomerLastWeek(){
        return orderRepository.getToTalCustomerLastWeek();
    }
    public int getTotalProductLastWeek(){
        return orderRepository.getToTalProductLastWeek();
    }
    public LinkedHashMap<Product,Integer> getTop10Product(){
        LinkedHashMap<Product,Integer> top10Product = new LinkedHashMap<>();
        for (Object[] objects : orderRepository.getTop10Product()) {
            Product product = productRepository.findById((Long) objects[0]).get();
            Double quantity = (Double) objects[1];
            top10Product.put(product,quantity.intValue());
        }
        return top10Product;
    }
    public List<Order> getOrderByPeriod(Date start, Date end){
        LocalDateTime startDateTime = start.toLocalDate().atStartOfDay();
        LocalDateTime endDateTime = end.toLocalDate().atStartOfDay();
        return orderRepository.getOrderByPeriod(startDateTime,endDateTime);
    }
    public double getTotalPriceByPeriod(Date start, Date end){
        LocalDateTime startDateTime = start.toLocalDate().atStartOfDay();
        LocalDateTime endDateTime = end.toLocalDate().atStartOfDay();
        return orderRepository.getTotalPriceByPeriod(startDateTime,endDateTime);
    }
    public int getTotalOrderByPeriod(Date start, Date end){
        LocalDateTime startDateTime = start.toLocalDate().atStartOfDay();
        LocalDateTime endDateTime = end.toLocalDate().atStartOfDay();
        return orderRepository.getTotalOrderByPeriod(startDateTime,endDateTime);
    }
}
