package com.bintohimo.fakeapi;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"${url.path}", "${url.path}/basicauth"})
public class FakeApiController {

    @RequestMapping(value = "/get200", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity status() {
        return ResponseEntity.ok("ok");
    }

    @RequestMapping(value = "/get404", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity get404() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found!");
    }

    @RequestMapping(value = "/echo", method = {RequestMethod.POST, RequestMethod.PUT})
    public @ResponseBody
    ResponseEntity echo(@RequestBody final String info) {
        return ResponseEntity.ok(info);
    }

    @RequestMapping(value = "/echo/{info}", method = {RequestMethod.POST, RequestMethod.PUT, RequestMethod.GET})
    public @ResponseBody
    ResponseEntity echoUrl(@PathVariable(value = "info")  final String info) {
        return ResponseEntity.ok(info);
    }
}
