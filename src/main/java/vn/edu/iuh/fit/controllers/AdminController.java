package vn.edu.iuh.fit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.dtos.ProductDTO;
import vn.edu.iuh.fit.models.Customer;
import vn.edu.iuh.fit.models.Order;
import vn.edu.iuh.fit.models.Product;
import vn.edu.iuh.fit.services.CustomerService;
import vn.edu.iuh.fit.services.OrderService;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private CustomerService customerService;

    @GetMapping("/report")
    public String report(Model model){
        double totalPriceThisWeek = orderService.getTotalPriceThisWeek();
        int totalOrderThisWeek = orderService.getTotalOrderThisWeek();
        int totalCustomerThisWeek = orderService.getTotalCustomerThisWeek();
        int totalProductThisWeek = orderService.getTotalProductThisWeek();
        double totalPriceLastWeek = orderService.getTotalPriceLastWeek();
        int totalOrderLastWeek = orderService.getTotalOrderLastWeek();
        int totalCustomerLastWeek = orderService.getTotalCustomerLastWeek();
        int totalProductLastWeek = orderService.getTotalProductLastWeek();
        LinkedHashMap<Product,Integer> top10Product = orderService.getTop10Product();
        model.addAttribute("totalPrice",String.format("%.2f%n",totalPriceThisWeek));
        model.addAttribute("totalOrder",totalOrderThisWeek);
        model.addAttribute("totalCustomer",totalCustomerThisWeek);
        model.addAttribute("totalProduct",totalProductThisWeek);
        String percentTotalPrice = String.format("%.2f", (totalPriceThisWeek-totalPriceLastWeek)/totalPriceLastWeek*100);
        String percentTotalOrder = String.format("%.2f",(double)(totalOrderThisWeek-totalOrderLastWeek)/totalOrderLastWeek*100);
        String percentTotalCustomer = String.format("%.2f",(double)(totalCustomerThisWeek-totalCustomerLastWeek)/totalCustomerLastWeek*100);
        String percentTotalProduct = String.format("%.2f",(double)(totalProductThisWeek-totalProductLastWeek)/totalProductLastWeek*100);
        if(totalPriceThisWeek>totalPriceLastWeek) {
            model.addAttribute("totalPriceChange", "up "+percentTotalPrice+"% last week");
        }else if(totalPriceThisWeek<totalPriceLastWeek) {
            model.addAttribute("totalPriceChange", "down "+percentTotalPrice+"% last week");
        }
        if(totalOrderThisWeek>totalOrderLastWeek) {
            model.addAttribute("totalOrderChange", "up "+percentTotalOrder+"% last week");
        }else if(totalOrderThisWeek<totalOrderLastWeek) {
            model.addAttribute("totalOrderChange", "down "+percentTotalOrder+"% last week");
        }
        if(totalCustomerThisWeek>totalCustomerLastWeek) {
            model.addAttribute("totalCustomerChange", "up "+percentTotalCustomer+"% last week");
        }else if(totalCustomerThisWeek<totalCustomerLastWeek) {
            model.addAttribute("totalCustomerChange", "down "+percentTotalCustomer+"% last week");
        }
        if(totalProductThisWeek>totalProductLastWeek) {
            model.addAttribute("totalProductChange", "up "+percentTotalProduct+"% last week");
        }else if(totalProductThisWeek<totalProductLastWeek) {
            model.addAttribute("totalProductChange", "down "+percentTotalProduct+"% last week");
        }
        model.addAttribute("top10Product",top10Product);
        return "admin/dashboard";
    }
    @GetMapping("/customer")
    public String getCustomers(Model model,
                               @RequestParam("page") Optional<Integer> page,
                               @RequestParam("size") Optional<Integer> size){
        int currentPage = page.orElse(0);
        int pageSize = size.orElse(9);
        Page<Customer> customerPage= customerService.findAll(
                currentPage,pageSize,"id","asc");
        model.addAttribute("customers", customerPage);
        int totalPages = customerPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(0, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "admin/customer";
    }
    @GetMapping("/add-customer")
    public String addCustomer(){
        return "admin/addcust";
    }
    @GetMapping("/update-customer/{id}")
    public String editCustomer(@PathVariable Long id, Model model){
        Customer customer = customerService.findById(id);
        model.addAttribute("customer",customer);
        return "admin/updatecust";
    }
    @PostMapping("/customer/update")
    public String updateCustomer(Customer customer){
        customerService.updateCustomer(customer);
        return "redirect:/admin/customer";
    }
    @GetMapping("/customer/delete/{id}")
    public String deleteCustomer(@PathVariable Long id){
        customerService.deleteCustomer(id);
        return "redirect:/admin/customer";
    }
    @PostMapping("/customer/add")
    public String addCustomer(Customer customer){
        customerService.addCust(customer);
        return "redirect:/admin/customer";
    }
    @GetMapping("/order/period")
    public String getOrderByPeriod(@RequestParam(value = "start",required = false) Date start,
                                   @RequestParam(value = "end",required = false) Date end,
                                   Model model){
        if(start!=null && end!=null){
            List<Order> orders = orderService.getOrderByPeriod(start,end);
            double totalPrice = orderService.getTotalPriceByPeriod(start,end);
            int totalOrder = orderService.getTotalOrderByPeriod(start,end);
            model.addAttribute("orders",orders);
            model.addAttribute("totalPrice",String.format("%.2f%n",totalPrice));
            model.addAttribute("totalOrder",totalOrder);
            model.addAttribute("start",start);
            model.addAttribute("end",end);
        }
        return "admin/orderperiod";
    }
}
