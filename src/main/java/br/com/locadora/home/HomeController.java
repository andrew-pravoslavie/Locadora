package br.com.locadora.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Controller
@Tag(name = "Home", description = "Página inicial")
public class HomeController {

	 // Página pública (opcional)
    @GetMapping("/home")
    @Operation(summary = "Exibir página inicial")
    public String homePublica() {
        return "index"; // Página genérica (sem login necessário)
    }


}
