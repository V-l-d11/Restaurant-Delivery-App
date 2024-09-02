package com.oder.food.controller;
import com.oder.food.model.Oder;
import com.oder.food.model.User;
import com.oder.food.requests.OderRequest;
import com.oder.food.service.OderService;
import com.oder.food.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminOderController {

    @Autowired
    private OderService oderService;

    @Autowired
    private UserService userService;

    @GetMapping("/oder/restaurant/{id}")
    public ResponseEntity<Page<Oder>> getOderHistory(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String  oder_status,
            @RequestHeader("Authorization") String jwt) throws Exception {

        User user=userService.findUserByJwtToken(jwt);
        Page<Oder> oders= oderService.getRestaurantsOder(id,oder_status, page,size);
        return new ResponseEntity<>(oders, HttpStatus.OK);
    }

    @PutMapping("/oder/{id}/{orderStatus}")
    public ResponseEntity<Oder> updateOderStatus(
            @PathVariable Long id,
            @PathVariable  String orderStatus,
            @RequestHeader("Authorization") String jwt) throws Exception {

        User user=userService.findUserByJwtToken(jwt);
        Oder oders= oderService.updateOder(id,orderStatus);
        return new ResponseEntity<>(oders, HttpStatus.OK);
    }


    @DeleteMapping("/oder")
    public  ResponseEntity<String> deleteOder (@RequestParam Long oderId,   @RequestHeader("Authorization") String jwt) throws  Exception{
        User user= userService.findUserByJwtToken(jwt);
        oderService.calncelOder(oderId);
        return new ResponseEntity<>("Oder", HttpStatus.OK);
    }

//    @GetMapping("/oder/date")
//    public Page<Oder> getOrdersByCreateAt(
//            @RequestParam("createAt") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate createAt,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size) throws Exception {
//
//        Date utilDate = Date.from(createAt.atStartOfDay(ZoneId.systemDefault()).toInstant());
//        return oderService.getOderByCreateAt(utilDate, page,size);
//    }


    @GetMapping("/oder/date")
    public  ResponseEntity<Page<Oder>> getOrdersByCreateAt(
            @RequestParam("createAt") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate createAt,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) throws Exception {
        Date start = Date.from(createAt.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date end = Date.from(createAt.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
        Page<Oder> oders= oderService.getOdersByDateRange(start,end ,page,size);

        return  new ResponseEntity<>(oders, HttpStatus.OK);
    }


    @GetMapping("/oder/date-range")
    public Page<Oder> getOdersByDateRange(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size

    ) throws  Exception{
        Date start = Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date end = Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return oderService.getOdersByDateRange(start, end, page,size);
    }

    @GetMapping("/oder/customer/")
    public ResponseEntity<Page<Oder>> getCustomerOders(
            @RequestParam String fullName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestHeader("Authorization") String jwt
    ) throws Exception{
        User user = userService.findUserByJwtToken(jwt);
        Page<Oder> orders= oderService.getOdersByCustomerFullName(fullName, page,size);
        return new ResponseEntity<>(orders,HttpStatus.OK);
    }

}
