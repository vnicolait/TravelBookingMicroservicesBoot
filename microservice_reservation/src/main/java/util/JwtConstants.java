package util;

public class JwtConstants {

	public static final String KEY="11111111111111111111111111aaaaaaaaaaaaaaaaaaaaa";
	static {
        System.out.println("=== CLAVE JWT CARGADA RESERVA===");
        System.out.println(KEY);
        System.out.println("Tamaño: " + KEY.length());
    }
	public static final long TIME_TOKEN = 86_400_000; // 1 dia
	public static final String HEADER = "Authorization";
	public static final String PREFIX_TOKEN = "Bearer ";

}