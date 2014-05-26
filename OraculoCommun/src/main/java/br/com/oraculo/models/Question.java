package br.com.oraculo.models;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author kurt
 */
@Entity
@Table(name = "QUESTION")
public class Question implements Serializable {

	public static final String NQ_FIND_ALL = "Question.findAll";

	private static final long serialVersionUID = 4L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	private String question;
	@NotNull
	@Column(name = "ALTERNATIVE_A")
	private String alternativeA;
	@NotNull
	@Column(name = "ALTERNATIVE_B")
	private String alternativeB;
	@NotNull
	@Column(name = "ALTERNATIVE_C")
	private String alternativeC;
	@NotNull
	@Column(name = "ALTERNATIVE_D")
	private String alternativeD;
	@Enumerated(EnumType.STRING)
	private QuestionOption answer;
	private Integer amount;
	private Integer timeToAnswer;

	public Long getId() {
		return id;
	}

	public String getQuestion() {
		return question;
	}

	public String getAlternativeA() {
		return alternativeA;
	}

	public String getAlternativeB() {
		return alternativeB;
	}

	public String getAlternativeC() {
		return alternativeC;
	}

	public String getAlternativeD() {
		return alternativeD;
	}

	public QuestionOption getAnswer() {
		return answer;
	}

	public Integer getAmount() {
		return amount;
	}

	public Integer getTimeToAnswer() {
		return timeToAnswer;
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 53 * hash + (this.id != null ? this.id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Question other = (Question) obj;
		if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Question{"
				+ "id=" + id
				+ ", question=" + question
				+ ", alternativeA=" + alternativeA
				+ ", alternativeB=" + alternativeB
				+ ", alternativeC=" + alternativeC
				+ ", alternativeD=" + alternativeD
				+ ", answer=" + answer
				+ ", amount=" + amount
				+ ", timeToAnswer=" + timeToAnswer
				+ '}';
	}
}