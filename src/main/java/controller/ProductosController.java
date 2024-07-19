package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import model.Producto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import service.ProductosService;


@CrossOrigin ("*")// evitar solicitud bloqueada- spring boot bloquea ingresos desconocidos
@RestController
public class ProductosController {
    
    @Autowired
    ProductosService productosService;


    @GetMapping(value="productos")
    public ResponseEntity<Flux<Producto>> productos(){
        return new ResponseEntity<>(productosService.catalogo(), HttpStatus.OK);
    }
    
    @GetMapping(value="productos/{categoria}")
    public ResponseEntity<Flux<Producto>> productosCategoria(@PathVariable ("categoria") String categoria){
        return new ResponseEntity<>(productosService.productoCategoria(categoria), HttpStatus.OK);

    }
    @GetMapping(value = "producto")
    public ResponseEntity<Mono<Producto>> productoCodigo(@RequestParam("cod") int cod) {
        return  new ResponseEntity<>(productosService.productoCodigo(cod),HttpStatus.OK);
    }

    @PostMapping(value = "alta", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Mono<Void>> altaProducto(@RequestBody Producto producto) {
        return new ResponseEntity<>(productosService.altaProducto(producto), HttpStatus.OK) ;
    }

    @DeleteMapping(value = "eliminar")
    public Mono<ResponseEntity<Producto>> eliminarProducto(@RequestParam("cod") int cod) {
        return productosService.eliminarProducto(cod) //Mono<Producto>
            .map(p->new ResponseEntity<>(p,HttpStatus.OK))//Mono<ResponseEntity<Producto>
            .switchIfEmpty(Mono.just(new ResponseEntity<>(HttpStatus.NOT_FOUND)));//Mono<ResponseEntity<Producto>
    }

    @PutMapping(value = "actualizar")
    public Mono<ResponseEntity<Producto>> actualizarPrecio(@RequestParam("cod") int cod, @RequestParam("precio") double precio) {
        return productosService.actualizarPrecio(cod, precio)//Mono<Producto>
            .map(p-> new ResponseEntity<>(p,HttpStatus.OK))//Mono<ResponseEntity<Porducto>
            .switchIfEmpty(Mono.just(new ResponseEntity<>(HttpStatus.NOT_FOUND)));//Mono<ResponseEntity<Porducto>
    }
}
