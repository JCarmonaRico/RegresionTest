package properties;

public class DestinoMensaje {

		private String nombredestino;
		private String mensajedestino;
		
		public DestinoMensaje() {
		}
		
		public DestinoMensaje(String nombredestino, String mensajedestino) {
			super();
			this.nombredestino = nombredestino;
			this.mensajedestino = mensajedestino;
		}
		
		public String getNombreDestino() {
			return nombredestino;
		}
		public void setNombreDestino(String nombredestino) {
			this.nombredestino = nombredestino;
		}	
		
		public String getMensajeDestino() {
			return mensajedestino;
		}
		public void setMensajeDestino(String mensajedestino) {
			this.mensajedestino = mensajedestino;
		}

}
