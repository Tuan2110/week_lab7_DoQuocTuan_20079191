package vn.edu.iuh.fit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import vn.edu.iuh.fit.configs.PaymentConfig;
import vn.edu.iuh.fit.dtos.ProductDTO;
import vn.edu.iuh.fit.exception.OutOfStockException;
import vn.edu.iuh.fit.repositories.ProductPriceRepository;
import vn.edu.iuh.fit.services.CartService;
import vn.edu.iuh.fit.services.ProductService;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductPriceRepository productPriceRepository;

    @GetMapping
    public ModelAndView cart(){
        return shoppingCart();
    }

    @GetMapping("/shoppingCart/addProduct/{productId}")
    public ModelAndView addProductToCart(@PathVariable("productId") Long productId){
        ProductDTO productDTO = productService.findById(productId).map(productService::convertToDTO).get();
        cartService.addProduct(productDTO);
        return shoppingCart();
    }

    @GetMapping("/shoppingCart/removeProduct/{productId}")
    public ModelAndView removeProductFromCart(@PathVariable("productId") Long productId){
        ProductDTO productDTO = productService.findById(productId).map(productService::convertToDTO).get();
        cartService.removeProduct(productDTO);
        return shoppingCart();
    }

    @GetMapping("/shoppingCart/checkout")
    public ModelAndView checkout(){
        try {
            String orderType = "other";
//            long amount = Integer.par seInt(req.getParameter("amount"))*100;
//            String bankCode = req.getParameter("bankCode");
            long amount = cartService.getTotal().longValue()*100*23000;
            String vnp_TxnRef = PaymentConfig.getRandomNumber(8);
//            String vnp_IpAddr = PaymentConfig.getIpAddress(req);
            String vnp_TmnCode = PaymentConfig.vnp_TmnCode;

            Map<String, String> vnp_Params = new HashMap<>();
            vnp_Params.put("vnp_Version", PaymentConfig.vnp_Version);
            vnp_Params.put("vnp_Command", PaymentConfig.vnp_Command);
            vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
            vnp_Params.put("vnp_Amount", String.valueOf(amount));
            vnp_Params.put("vnp_CurrCode", "VND");
            vnp_Params.put("vnp_BankCode", "NCB");
            vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
            vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
            vnp_Params.put("vnp_Locale", "vn");
            vnp_Params.put("vnp_ReturnUrl", PaymentConfig.vnp_ReturnUrl);
            vnp_Params.put("vnp_OrderType", orderType);
            vnp_Params.put("vnp_IpAddr", PaymentConfig.vnp_IpAddr);


            Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            String vnp_CreateDate = formatter.format(cld.getTime());
            vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

            cld.add(Calendar.MINUTE, 15);
            String vnp_ExpireDate = formatter.format(cld.getTime());
            vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

            List fieldNames = new ArrayList(vnp_Params.keySet());
            Collections.sort(fieldNames);
            StringBuilder hashData = new StringBuilder();
            StringBuilder query = new StringBuilder();
            Iterator itr = fieldNames.iterator();
            while (itr.hasNext()) {
                String fieldName = (String) itr.next();
                String fieldValue = (String) vnp_Params.get(fieldName);
                if ((fieldValue != null) && (fieldValue.length() > 0)) {
                    //Build hash data
                    hashData.append(fieldName);
                    hashData.append('=');
                    hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                    //Build query
                    query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                    query.append('=');
                    query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                    if (itr.hasNext()) {
                        query.append('&');
                        hashData.append('&');
                    }
                }
            }
            String queryUrl = query.toString();
            String vnp_SecureHash = PaymentConfig.hmacSHA512(PaymentConfig.secretKey, hashData.toString());
            queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
            String paymentUrl = PaymentConfig.vnp_PayUrl + "?" + queryUrl;

            shoppingCart().addObject("paymentUrl",paymentUrl);
            System.out.println(paymentUrl);

            cartService.checkout();
        } catch (OutOfStockException e) {
            return shoppingCart().addObject("outOfStockMessage",e.getMessage());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return shoppingCart();
    }
    @GetMapping("/shoppingCart")
    private ModelAndView shoppingCart() {
        ModelAndView modelAndView = new ModelAndView("shopping/cart");
        modelAndView.addObject("cart",cartService.getProducts());
        modelAndView.addObject("total",cartService.getTotal().toString());
        return modelAndView;
    }
}
