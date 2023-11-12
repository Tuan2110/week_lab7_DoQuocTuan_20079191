package vn.edu.iuh.fit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.edu.iuh.fit.models.Order;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("select sum(od.price*od.quantity) from Order o join OrderDetail od on o.order_id = od.order.order_id where week(o.orderDate)=week(now()) and year(o.orderDate)=year(now())")
    double getTotalPriceThisWeek();
    @Query("select count(o) from Order o where week(o.orderDate)=week(now()) and year(o.orderDate)=year(now())")
    int getTotalOrderThisWeek();
    @Query("select count(distinct o.customer) from Order o where week(o.orderDate)=week(now()) and year(o.orderDate)=year(now())")
    int getTotalCustomerThisWeek();
    @Query("select sum(od.quantity) from Order o join OrderDetail od on o.order_id = od.order.order_id where week(o.orderDate)=week(now()) and year(o.orderDate)=year(now())")
    int getTotalProductThisWeek();
    @Query("select sum(od.price*od.quantity) from Order o join OrderDetail od on o.order_id = od.order.order_id where week(o.orderDate)=week(now())-1 and year(o.orderDate)=year(now())")
    double getTotalPriceLastWeek();
    @Query("select count(o) from Order o where week(o.orderDate)=week(now())-1 and year(o.orderDate)=year(now())")
    int getToTalOrderLastWeek();
    @Query("select count(distinct o.customer) from Order o where week(o.orderDate)=week(now())-1 and year(o.orderDate)=year(now())")
    int getToTalCustomerLastWeek();
    @Query("select sum(od.quantity) from Order o join OrderDetail od on o.order_id = od.order.order_id where week(o.orderDate)=week(now())-1 and year(o.orderDate)=year(now())")
    int getToTalProductLastWeek();
    @Query("select od.product.id,sum(od.quantity) from OrderDetail od join Order o on od.order.order_id = o.order_id  " +
            " where week(o.orderDate)=week(now()) and year(o.orderDate)=year(now()) group by od.product.id order by sum(od.quantity) desc limit 10")
    List<Object[]> getTop10Product();
    @Query("select o from Order o where o.orderDate between ?1 and ?2")
    List<Order> getOrderByPeriod(LocalDateTime start, LocalDateTime end);
    @Query("select sum(od.price*od.quantity) from Order o join OrderDetail od on o.order_id = od.order.order_id where o.orderDate between ?1 and ?2")
    double getTotalPriceByPeriod(LocalDateTime start, LocalDateTime end);
    @Query("select count(o) from Order o where o.orderDate between ?1 and ?2")
    int getTotalOrderByPeriod(LocalDateTime start, LocalDateTime end);
}