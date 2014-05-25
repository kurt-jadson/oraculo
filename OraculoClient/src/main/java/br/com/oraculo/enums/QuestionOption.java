package br.com.oraculo.enums;

/**
 *
 * @author kurt
 */
public enum QuestionOption {
	A(1), B(2), C(3), D(4), NONE(0);

	private final int identifierNumber;

	private QuestionOption(int identifierNumber) {
		this.identifierNumber = identifierNumber;
	}

	public int getIdentifierNumber() {
		return identifierNumber;
	}

	public static QuestionOption getByIdentifierNumber(int idfNm) {
		QuestionOption[] options = QuestionOption.values();

		for(QuestionOption opt : options) {
			if(opt.getIdentifierNumber() == idfNm) {
				return opt;
			}
		}

		return NONE;
	}
}