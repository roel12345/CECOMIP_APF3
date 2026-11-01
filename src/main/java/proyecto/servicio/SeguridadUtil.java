package proyecto.servicio;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/** Almacena contraseñas nuevas con PBKDF2 + salt; acepta hashes legados SHA-256 solo para migración. */
public final class SeguridadUtil {
    private static final int ITERACIONES = 160000;
    private static final SecureRandom RANDOM = new SecureRandom();
    private SeguridadUtil() { }

    /** Hash con sal aleatoria; una misma contraseña genera valores diferentes. */
    public static String hashClaveSegura(String clave) {
        if (clave == null || clave.length() < 10) throw new IllegalArgumentException("La contraseña debe tener al menos 10 caracteres");
        byte[] salt = new byte[16]; RANDOM.nextBytes(salt);
        byte[] resultado = pbkdf2(clave, salt, ITERACIONES);
        return "pbkdf2$" + ITERACIONES + "$" + Base64.getEncoder().encodeToString(salt) + "$" + Base64.getEncoder().encodeToString(resultado);
    }

    /** Comprueba hash PBKDF2 y conserva compatibilidad con registros SHA-256 existentes. */
    public static boolean verificarClave(String clave, String almacenado) {
        if (clave == null || almacenado == null) return false;
        if (almacenado.startsWith("pbkdf2$")) {
            try {
                String[] partes = almacenado.split("\\$");
                if (partes.length != 4) return false;
                int iter = Integer.parseInt(partes[1]);
                if (iter < 10000 || iter > 1000000) return false;
                byte[] sal = Base64.getDecoder().decode(partes[2]);
                byte[] esperado = Base64.getDecoder().decode(partes[3]);
                return MessageDigest.isEqual(esperado, pbkdf2(clave, sal, iter));
            } catch (RuntimeException e) { return false; }
        }
        return MessageDigest.isEqual(almacenado.getBytes(StandardCharsets.UTF_8), hash(clave).getBytes(StandardCharsets.UTF_8));
    }

    private static byte[] pbkdf2(String clave, byte[] sal, int iteraciones) {
        PBEKeySpec spec = new PBEKeySpec(clave.toCharArray(), sal, iteraciones, 256);
        try { return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded(); }
        catch (Exception e) { throw new IllegalStateException("No se pudo generar hash seguro", e); }
        finally { spec.clearPassword(); }
    }

    /** Solo para comprobar contraseñas provenientes del proyecto TSV original. */
    @Deprecated public static String hash(String texto) {
        try {
            byte[] bytes = MessageDigest.getInstance("SHA-256").digest(texto.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) sb.append(String.format("%02x", b & 0xff));
            return sb.toString();
        } catch (Exception e) { throw new IllegalStateException("No se pudo calcular hash legado", e); }
    }
}
