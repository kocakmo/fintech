package com.fincom.fintech.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fincom.fintech.jpa.OrderRepository;
import com.fincom.fintech.jpa.UserRepository;
import com.fincom.fintech.model.MarketOrder;
import com.fincom.fintech.model.User;
import com.fincom.fintech.service.OrderServiceImpl;
import com.fincom.fintech.service.UserService;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("orders")
public class OrderController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderServiceImpl orderService;

    @GetMapping("/neworder")
    public String addNewOrder(Model model) {
        MarketOrder order = new MarketOrder();
        model.addAttribute("order", order);
        commonSymbolFunctionality(model);
        return "neworder";
    }
    @PostMapping("/saveorder")
    public String saveOrder(@ModelAttribute("order") MarketOrder order,Model model) {
        order.setUser(userRepository.findByUserName(getUserName()));
        order.setOrderStatus(1);
        orderService.createMarketOrder(order);
        return commonListingFunctionality(model);
    }

    @GetMapping("/listorder")
    public String listOrder(Model model){

        return commonListingFunctionality(model);
    }

    @GetMapping("/getSymbols")
    public String getNasdaqSymbols(Model model) {

        commonSymbolFunctionality(model);

        return "nasdaqSymbols";
    }
    @DeleteMapping("/{orderId}")
    public String deleteOrder(@PathVariable Long orderId,Model model){
        orderRepository.findByOrderId(orderId).setOrderStatus(0);
        return commonListingFunctionality(model);
    }

    private String commonListingFunctionality(Model model) {
        List<MarketOrder> userOrders =  orderRepository.findByUserAndOrderStatus(userRepository.findByUserName(getUserName()),1);
        model.addAttribute("orders",userOrders);
        return "userorders";
    }


    private void commonSymbolFunctionality(Model model){
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "https://www.alphavantage.co/query?function=TOP_GAINERS_LOSERS&apikey=" + "demo";
        String jsonResponse = restTemplate.getForObject(apiUrl, String.class);

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(apiUrl);

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(response.getEntity().getContent());


                JsonNode topGainersNode = rootNode.path("top_gainers");
                JsonNode topLosersNode = rootNode.path("top_losers");
                JsonNode mostActiveNode = rootNode.path("most_actively_traded");


                model.addAttribute("topGainers", topGainersNode);
                model.addAttribute("topLosers", topLosersNode);
                model.addAttribute("mostActive", mostActiveNode);
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
    }


    private String getUserName() {
    String userName = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                userName = ((UserDetails) principal).getUsername();


            }
        }
        return userName;
    }

}
