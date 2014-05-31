package br.com.oraculo.models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author kurt
 */
public class Client implements Serializable, Mergeable<Client> {
	
	private static final long serialVersionUID = 1L;
	public static final long ALREADY_ANSWERED = -1l;
	public static final long NOT_YET_ANSWERED = -2l;
	private String id;
	private String nickname;
	private Map<Question, Long> timeoutQuestion;

	public Client() {
		timeoutQuestion = new HashMap<Question, Long>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public void addQuestion(Question question) {
		timeoutQuestion.put(question, System.currentTimeMillis());
	}

	public void markAsAnswered(Question question) {
		timeoutQuestion.put(question, -1L);
	}

	public long getStartedTime(Question question) {
		Long l = timeoutQuestion.get(question);
		return l == null ? NOT_YET_ANSWERED : l;
	}

	public long getTimeElapsed(Question question) {
		long currentTime = System.currentTimeMillis();
		return currentTime - timeoutQuestion.get(question);
	}

	public Map<Question, Long> getTimeoutQuestions() {
		return timeoutQuestion;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 43 * hash + (this.id != null ? this.id.hashCode() : 0);
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
		final Client other = (Client) obj;
		if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
			return false;
		}
		return true;
	}

	public void merge(Client m) {
		Map<Question, Long> mTimeouts = m.getTimeoutQuestions();

		for(Entry<Question, Long> entry : timeoutQuestion.entrySet()) {
			for(Entry<Question, Long> mEntry : mTimeouts.entrySet()) {
				if(!entry.equals(mEntry)) {
					if(timeoutQuestion.containsKey(mEntry.getKey())) {
						Long l = timeoutQuestion.get(entry.getKey());
						Long ml = mTimeouts.get(mEntry.getKey());
						Long newValue = ml < l ? ml : l;
						timeoutQuestion.put(mEntry.getKey(), newValue);
					} else {
						timeoutQuestion.put(mEntry.getKey(), mEntry.getValue());
					}
				}
			}
		}

	}

}