package proyecto;

import org.junit.jupiter.api.Test;
import proyecto.servicio.SeguridadUtil;
import proyecto.servicio.CalculadoraOro;
import static org.junit.jupiter.api.Assertions.*;

class SeguridadYCalculosTest {
    @Test void oroFino() { assertEquals(925.0, CalculadoraOro.gramosFinos(1000,92.5), 0.00001); }
    @Test void venta() { assertEquals(114537.5, CalculadoraOro.totalVenta(500,93.5,245),0.00001); }
    @Test void pesoInvalido() { assertThrows(IllegalArgumentException.class, () -> CalculadoraOro.gramosFinos(-1,90)); }
    @Test void purezaInvalida() { assertThrows(IllegalArgumentException.class, () -> CalculadoraOro.gramosFinos(100,101)); }
    @Test void precioInvalido() { assertThrows(IllegalArgumentException.class, () -> CalculadoraOro.totalVenta(100,90,-5)); }
    @Test void contrasenaNueva() {
        String h1=SeguridadUtil.hashClaveSegura("ClaveSegura123!");
        String h2=SeguridadUtil.hashClaveSegura("ClaveSegura123!");
        assertNotEquals(h1,h2);
        assertTrue(SeguridadUtil.verificarClave("ClaveSegura123!",h1));
        assertFalse(SeguridadUtil.verificarClave("incorrecto",h1));
    }
    @Test void contrasenaLegada() {
        assertTrue(SeguridadUtil.verificarClave("admin123", SeguridadUtil.hash("admin123")));
        assertFalse(SeguridadUtil.verificarClave("equivocada", SeguridadUtil.hash("admin123")));
    }
    @Test void longitudContrasena() { assertThrows(IllegalArgumentException.class, () -> SeguridadUtil.hashClaveSegura("corta")); }
}
