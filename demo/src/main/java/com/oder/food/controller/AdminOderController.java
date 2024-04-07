package com.oder.food.controller;
import com.oder.food.model.Oder;
import com.oder.food.model.User;
import com.oder.food.requests.OderRequest;
import com.oder.food.service.OderService;
import com.oder.food.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminOderController {

    @Autowired
    private OderService oderService;

    @Autowired
    private UserService userService;

    @GetMapping("/oder/restaurant/{id}")
    public ResponseEntity<List<Oder>> getOderHistory(
            @PathVariable Long id,
            @RequestParam(required = false) String  oder_status,
            @RequestHeader("Authorization") String jwt) throws Exception {

        User user=userService.findUserByJwtToken(jwt);
        List<Oder> oders= oderService.getRestaurantsOder(id,oder_status);
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


}
