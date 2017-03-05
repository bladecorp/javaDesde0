package net.tecgurus.jd0.util;

public class Constantes {
	
	public class Layout{
		public static final int PNL_ALTO = 592;
		public static final int PNL_ANCHO = 1330;
	}
	
	public class Imagen{
		
		public static final String LOGO = "logo.png";
		public static final String BACKGROUND_LOGIN = "background.jpg";
		public static final String LOGIN = "btnLogin.png";
		public static final String CLEAR = "clear48.png";
		public static final String REFRESH = "refresh48.png";
		public static final String SAVE = "save48.png";
		public static final String EN_CONSTRUCCION = "contruccion.png";
		public static final String EXCEL = "excel64.png";
	}

	public class TipoEdoCivil{
		
		public static final int SOLTERO = 1;
		public static final int CASADO = 2;
		public static final int DIVORCIADO = 3;
		public static final int VIUDO = 4;
		public static final int OTRO = 5;
	}
	
	public class TipoCuenta{
		public static final int CUENTA_AHORRO = 1;
		public static final int TARJETA_CREDITO = 2;
		public static final int FONDO_INVERSION = 3;
		public static final int AFORE = 4;
		public static final int CUENTA_NOMINA = 5;
	}
	
	public class Caracteristicas{
		public static final int SIN_COMISION = 1;
		public static final int BANCA_LINEA = 2;
		public static final int DISPOSICION_EFECTIVO = 3;
		public static final int BENEFICIOS_VIP = 4;
	}
	
	public class TipoMovimiento{
		public static final int TODOS = 0; //NO EXISTE EN BASE
		public static final int ABONO = 1;
		public static final int RETIRO = 2;
		public static final int CONSULTA = 3;
	}
	
}
