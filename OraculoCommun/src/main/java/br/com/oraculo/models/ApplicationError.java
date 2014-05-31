package br.com.oraculo.models;

/**
 *
 * @author kurt
 */
public enum ApplicationError {

	UNKNOW_ERROR(0),
	GENERIC_PROTOCOL_ERROR(1),
	BUSY_ROOM_ERROR(10),
	NO_MORE_QUESTIONS(100);

	private int errorNumber;

	private ApplicationError(int errorNumber) {
		this.errorNumber = errorNumber;
	}

	public int getErrorNumber() {
		return errorNumber;
	}

	public static ApplicationError getApplicationError(int errorNumber) {
		for(ApplicationError appErr : ApplicationError.values()) {
			if(appErr.getErrorNumber() == errorNumber) {
				return appErr;
			}
		}

		return ApplicationError.UNKNOW_ERROR;
	}

}
