package actor;

public class MSG {
	enum MType { LOGIN, REJECT, RES_PARAMS, REQ_AUTH, AUTHENTICATE}
	
	public static class SMS_Start extends Message {
		public final String sms_start; 
		public SMS_Start(String sms_start) {
			super(MType.AUTHENTICATE);
			this.sms_start = sms_start;
		}
	}
	
	public static class LoginRequest extends Message {
		public final String username;
		public LoginRequest(String username) {
			super(MType.LOGIN);
			this.username = username;
		}
	}
	public static class ResultResponse extends Message {
		public final String error;
		public ResultResponse(String error) {
			super(MType.REJECT);
			this.error = error;
		}
		public String toString() {
			return String.format("Result(%s)", error==null?"":error);
		}
	}
	
	public static class ParamsResponse extends Message {
		public final String salt;
		public ParamsResponse(String salt) {
			super(MType.RES_PARAMS);
			this.salt = salt;
		}
	}
	
	public static class AuthRequest extends Message {
		public final byte digest[];
		public AuthRequest(byte[] digest) {
			super(MType.REQ_AUTH);
			this.digest = digest;
		}
	}
	
	
	
}
